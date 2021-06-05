package com.lubycon.eatitall.domain.admin.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant.RestaurantBuilder;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap.KakaoMapBuilder;
import com.lubycon.eatitall.domain.restaurant.repository.RestaurantJpaRepository;
import com.lubycon.eatitall.googlesheet.SpreadSheetClient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

  private final SpreadSheetClient spreadSheetClient;
  private final RestaurantJpaRepository restaurantJpaRepository;

  @Value("${google.spreadSheets.apiKey}")
  private String apiKey;

  @Value("${aws.s3.accessKey}")
  private String accessKey;

  @Value("${aws.s3.secretKey}")
  private String secretKey;

  private AmazonS3 s3Client;

  @PostConstruct
  public void init() {
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    this.s3Client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.AP_NORTHEAST_2)
        .build();
  }

  @Transactional
  public void renewAdminSheet() {

    List<String[]> restaurants = spreadSheetClient.restaurants(apiKey).getValues();

    int rowIndex = 1, columnIndex;
    for (String[] restaurant : restaurants) {
      columnIndex = 1;
      if (rowIndex > 2) {
        renewSheetData(columnIndex, restaurant);
      }
      rowIndex++;
    }
  }

  private void renewSheetData(int columnIndex, String[] restaurant) {
    boolean isDuplicate = false;
    Restaurant selectedRestaurant = null;
    RestaurantBuilder restaurantBuilder = Restaurant.builder();
    KakaoMapBuilder kakaoMapBuilder = KakaoMap.builder();
    String thumbnailImageUrl = null;
    for (String column : restaurant) {
      if (columnIndex == 1) {
        restaurantBuilder.name(column);
        Restaurant findRestaurant = restaurantJpaRepository.findByName(column);
        if (findRestaurant != null) {
          isDuplicate = true;
          selectedRestaurant = findRestaurant;
        }
      } else if (columnIndex == 2) {
        restaurantBuilder.hashtags(column);
      } else if (columnIndex == 3) {
        thumbnailImageUrl = column;
      } else if (columnIndex == 4) {
        restaurantBuilder.address(column);
      } else if (columnIndex == 5) {
        kakaoMapBuilder.id(Long.valueOf(column));
      } else if (columnIndex == 6) {
        kakaoMapBuilder.latitude(new BigDecimal(column));
      } else if (columnIndex == 7) {
        kakaoMapBuilder.longitude(new BigDecimal(column));
        restaurantBuilder.kakaoMap(kakaoMapBuilder.build());
      } else if (columnIndex == 8) {
        restaurantBuilder.description(column);
      } else if (columnIndex == 9) {
        restaurantBuilder.isHidden(Integer.parseInt(column));
        Restaurant resturantResult = updateOrSaveRestaurant(isDuplicate, selectedRestaurant,
            restaurantBuilder);
        resturantResult
            .updateThumbnailImageUrl("/images/restaurant/" + resturantResult.getId() + ".png");
        Long resturantId = resturantResult.getId();
        uploadImageToS3(resturantId, thumbnailImageUrl);
        break;
      }
      columnIndex++;
    }
  }

  private void uploadImageToS3(Long restaurantId, String imageUrl) {
    try {
      BufferedImage image = ImageIO.read(new URL(imageUrl));
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, "png", baos);
      byte[] buffer = baos.toByteArray();
      InputStream is = new ByteArrayInputStream(buffer);
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentLength(buffer.length);
      meta.setContentType(MediaType.IMAGE_PNG_VALUE);

      s3Client.putObject(
          new PutObjectRequest(
              "file.eat-all.io",
              "images/restaurant/" + restaurantId + ".png",
              is,
              meta
          ).withCannedAcl(CannedAccessControlList.PublicRead)
      );
    } catch (Exception exception) {
      log.error("uploadImageToS3 : {}", exception.getMessage());
      log.error("restaurantId : {}", restaurantId);
      exception.printStackTrace();
    }
  }

  private Restaurant updateOrSaveRestaurant(boolean isDuplicate, Restaurant selectedRestaurant,
      RestaurantBuilder restaurantBuilder) {
    Restaurant buildRestaurant = restaurantBuilder.build();
    if (isDuplicate) {
      selectedRestaurant.updateRestaurant(buildRestaurant);
      return selectedRestaurant;
    }
    restaurantJpaRepository.save(buildRestaurant);
    return buildRestaurant;
  }

}
