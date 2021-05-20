package com.lubycon.eatitall.domain.menu.entity;

import static com.lubycon.eatitall.common.util.CommonUtils.userIp;

import com.lubycon.eatitall.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Menu extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long menuId;

  @Column(nullable = false)
  private Long restaurantId;

  @Column(nullable = false, length=100)
  private String name;

  @Column(nullable = true, length = 500)
  private String description;

  @Column(nullable = false)
  private Integer price;

  @Builder
  public Menu(Long restaurantId, String name, String description, Integer price) {
    this.setCreatedIp(userIp());
    this.restaurantId = restaurantId;
    this.name = name;
    this.description = description;
    this.price = price;
  }
}
