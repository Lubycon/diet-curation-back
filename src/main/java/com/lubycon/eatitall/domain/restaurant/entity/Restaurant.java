package com.lubycon.eatitall.domain.restaurant.entity;

import com.lubycon.eatitall.common.entity.BaseEntity;
import com.lubycon.eatitall.domain.restaurant.dto.RestaurantDto;
import com.lubycon.eatitall.domain.restaurant.model.KakaoMap;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@NamedNativeQuery(
    name = "find_restaurant_dto",
    query =
        "select r.id, r.name, r.description, r.hashtags, r.thumbnail_image_url, "
            + "r.address, r.kakao_map_id, r.latitude, r.longitude, "
            + "(select group_concat(curation_id)"
            + "from curation_restaurant "
            + "where restaurant_id = r.id) as curation_ids "
            + "FROM restaurant r ",
    resultSetMapping = "rs_restaurant_dto"
)
@SqlResultSetMapping(
    name = "rs_restaurant_dto",
    classes = @ConstructorResult(
        targetClass = RestaurantDto.class,
        columns = {
            @ColumnResult(name = "id", type = Long.class),
            @ColumnResult(name = "name", type = String.class),
            @ColumnResult(name = "description", type = String.class),
            @ColumnResult(name = "hashtags", type = String.class),
            @ColumnResult(name = "thumbnail_image_url", type = String.class),
            @ColumnResult(name = "address", type = String.class),
            @ColumnResult(name = "kakao_map_id", type = Long.class),
            @ColumnResult(name = "latitude", type = BigDecimal.class),
            @ColumnResult(name = "longitude", type = BigDecimal.class),
            @ColumnResult(name = "curation_ids", type = String.class)
        }
    )
)
public class Restaurant extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Lob
  @Column(nullable = true)
  private String description;

  @Column(nullable = true, length = 300)
  private String hashtags;

  @Column(nullable = true, length = 500)
  private String thumbnailImageUrl;

  @Column(nullable = false, length = 500)
  private String address;

  @Embedded
  private KakaoMap kakaoMap;

  @Builder
  public Restaurant(String name, String description, String hashtags,
      String thumbnailImageUrl, String address,
      KakaoMap kakaoMap) {
    this.name = name;
    this.description = description;
    this.hashtags = hashtags;
    this.thumbnailImageUrl = thumbnailImageUrl;
    this.address = address;
    this.kakaoMap = kakaoMap;
  }
}
