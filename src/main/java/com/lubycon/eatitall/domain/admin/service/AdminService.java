package com.lubycon.eatitall.domain.admin.service;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_CURATION_NOT_FOUND;
import static com.lubycon.eatitall.common.util.MessageUtils.MSG_RESTAURANT_NOT_FOUND;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.lubycon.eatitall.common.exception.NotFoundException;
import com.lubycon.eatitall.domain.curation.entity.Curation;
import com.lubycon.eatitall.domain.curation.entity.Curation.CurationBuilder;
import com.lubycon.eatitall.domain.curation.repository.CurationJpaRepository;
import com.lubycon.eatitall.domain.restaurant.entity.CurationRestaurant;
import com.lubycon.eatitall.domain.restaurant.entity.CurationRestaurant.CurationRestaurantBuilder;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant.RestaurantBuilder;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap.KakaoMapBuilder;
import com.lubycon.eatitall.domain.restaurant.repository.CurationRestaurantJpaRepository;
import com.lubycon.eatitall.domain.restaurant.repository.RestaurantJpaRepository;
import com.lubycon.eatitall.googlesheet.SpreadSheetClient;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

  private final SpreadSheetClient spreadSheetClient;
  private final RestaurantJpaRepository restaurantJpaRepository;
  private final CurationJpaRepository curationJpaRepository;
  private final CurationRestaurantJpaRepository curationRestaurantJpaRepository;

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
  public void renewRestaurantSheet() {

    List<String[]> restaurants = spreadSheetClient.restaurants(apiKey).getValues();

    int rowIndex = 1, columnIndex;
    for (String[] restaurant : restaurants) {
      columnIndex = 1;
      if (rowIndex > 2) {
        renewRestaurantSheetData(columnIndex, restaurant);
      }
      rowIndex++;
    }
  }

  private void renewRestaurantSheetData(int columnIndex, String[] restaurant) {
    boolean isDuplicate = false;
    Restaurant selectedRestaurant = null;
    RestaurantBuilder restaurantBuilder = Restaurant.builder();
    KakaoMapBuilder kakaoMapBuilder = KakaoMap.builder();
    String thumbnailImageUrl = null;
    for (String column : restaurant) {
      if (columnIndex == 1) {
        restaurantBuilder.name(column);
        Optional<Restaurant> findRestaurant = restaurantJpaRepository.findByName(column);
        if (findRestaurant.isPresent()) {
          isDuplicate = true;
          selectedRestaurant = findRestaurant.get();
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
        Restaurant restaurantResult = updateOrSaveRestaurant(isDuplicate, selectedRestaurant,
            restaurantBuilder);
        if (ObjectUtils.isEmpty(thumbnailImageUrl)) {
          restaurantResult.updateThumbnailImageUrl(null);
          break;
        }
        Long restaurantId = restaurantResult.getId();
        uploadImageToS3(restaurantId, thumbnailImageUrl, "restaurant/");
        restaurantResult
            .updateThumbnailImageUrl("/images/restaurant/" + restaurantResult.getId() + ".png");
        break;
      }
      columnIndex++;
    }
  }

  private void uploadImageToS3(Long restaurantId, String imageUrl, String imageDir) {
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
              "images/" + imageDir + restaurantId + ".png",
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

  @Transactional
  public void renewCurationSheet() {

    List<String[]> curations = spreadSheetClient.curations(apiKey).getValues();

    int rowIndex = 1, columnIndex;
    for (String[] curation : curations) {
      columnIndex = 1;
      if (rowIndex > 2) {
        renewCurationSheetData(columnIndex, curation);
      }
      rowIndex++;
    }
  }

  private void renewCurationSheetData(int columnIndex, String[] curation) {
    boolean isDuplicate = false;
    Curation selectedCuration = null;
    CurationBuilder curationBuilder = Curation.builder();
    String imageUrl = null;
    for (String column : curation) {
      if (columnIndex == 1) {
        curationBuilder.title(column);
        Optional<Curation> optCuration = curationJpaRepository.findByTitle(column);
        if (optCuration.isPresent()) {
          isDuplicate = true;
          selectedCuration = optCuration.get();
        }
      } else if (columnIndex == 2) {
        imageUrl = column;
      } else if (columnIndex == 3) {
        curationBuilder.isHidden(Integer.parseInt(column));
        Curation curationResult = updateOrSaveCuration(isDuplicate, selectedCuration,
            curationBuilder);
        if (ObjectUtils.isEmpty(imageUrl)) {
          curationResult.updateImageUrl(null);
          break;
        }
        Long curationId = curationResult.getId();
        uploadImageToS3(curationId, imageUrl, "curation/");
        curationResult.updateImageUrl("/images/curation/" + curationResult.getId() + ".png");
        break;
      }
      columnIndex++;
    }
  }

  private Curation updateOrSaveCuration(boolean isDuplicate,
      Curation selectedCuration,
      CurationBuilder curationBuilder) {
    Curation buildCuration = curationBuilder.build();
    if (isDuplicate) {
      selectedCuration.updateCuration(buildCuration);
      return selectedCuration;
    }
    return curationJpaRepository.save(buildCuration);
  }

  @Transactional
  public void renewCurationRestaurantSheet() {

    List<String[]> curationRestaurants = spreadSheetClient.curationRestaurants(apiKey).getValues();

    int rowIndex = 1, columnIndex;
    for (String[] curationRestaurant : curationRestaurants) {
      columnIndex = 1;
      if (rowIndex > 2) {
        renewCurationRestaurantSheetData(columnIndex, curationRestaurant);
      }
      rowIndex++;
    }
  }

  private void renewCurationRestaurantSheetData(int columnIndex, String[] curationRestaurant) {
    boolean isDuplicate = false;
    CurationRestaurant selectedCurationRestaurant = null;
    CurationRestaurantBuilder curationRestaurantBuilder = CurationRestaurant.builder();
    Long curationId = null, restaurantId;
    for (String column : curationRestaurant) {
      if (columnIndex == 1) {
        Curation findCuration = curationJpaRepository.findByTitle(column)
            .orElseThrow(() -> new NotFoundException(MSG_CURATION_NOT_FOUND));
        curationRestaurantBuilder.curation(findCuration);
        curationId = findCuration.getId();
      } else if (columnIndex == 2) {
        Restaurant findRestaurant = restaurantJpaRepository.findByName(column)
            .orElseThrow(() -> new NotFoundException(MSG_RESTAURANT_NOT_FOUND));
        curationRestaurantBuilder.restaurant(findRestaurant);
        restaurantId = findRestaurant.getId();
        Optional<CurationRestaurant> findCurationRestaurant = curationRestaurantJpaRepository
            .findByCurationIdAndRestaurantId(curationId, restaurantId);
        if (findCurationRestaurant.isPresent()) {
          isDuplicate = true;
          selectedCurationRestaurant = findCurationRestaurant.get();
        }
      } else if (columnIndex == 3) {
        curationRestaurantBuilder.isHidden(Integer.parseInt(column));
        updateOrSaveCurationRestaurant(isDuplicate, selectedCurationRestaurant,
            curationRestaurantBuilder);
        break;
      }
      columnIndex++;
    }
  }

  private void updateOrSaveCurationRestaurant(boolean isDuplicate,
      CurationRestaurant selectedCurationRestaurant,
      CurationRestaurantBuilder curationRestaurantBuilder) {
    CurationRestaurant buildCurationRestaurant = curationRestaurantBuilder.build();
    if (isDuplicate) {
      selectedCurationRestaurant.updateCurationRestaurant(buildCurationRestaurant);
      return;
    }
    curationRestaurantJpaRepository.save(buildCurationRestaurant);
  }

}
