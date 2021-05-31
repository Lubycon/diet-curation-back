package com.lubycon.eatitall.domain.admin.service;

import com.lubycon.eatitall.domain.restaurant.entity.Restaurant;
import com.lubycon.eatitall.domain.restaurant.entity.Restaurant.RestaurantBuilder;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap.KakaoMapBuilder;
import com.lubycon.eatitall.domain.restaurant.repository.RestaurantJpaRepository;
import com.lubycon.eatitall.googlesheet.SpreadSheetClient;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final SpreadSheetClient spreadSheetClient;
  private final RestaurantJpaRepository restaurantJpaRepository;

  public void renewAdminSheet(String apiKey) {
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
        restaurantBuilder.thumbnailImageUrl(column);
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
        updateOrSaveRestaurant(isDuplicate, selectedRestaurant, restaurantBuilder);
        break;
      }
      columnIndex++;
    }
  }

  private void updateOrSaveRestaurant(boolean isDuplicate, Restaurant selectedRestaurant,
      RestaurantBuilder restaurantBuilder) {
    Restaurant buildedRestaurant = restaurantBuilder.build();
    if (isDuplicate) {
      selectedRestaurant.updateRestaurant(buildedRestaurant);
      return;
    }
    restaurantJpaRepository.save(buildedRestaurant);
  }

  private void getImageFromUrl(String imgUrl, String imgFilePath, String imgFormat) {
    try {
      BufferedImage image = ImageIO.read(new URL(imgUrl));
      File imgFile = new File(imgFilePath);
      ImageIO.write(image, imgFormat, imgFile);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

}
