package com.lubycon.eatitall.domain.restaurant.entity;

import static com.lubycon.eatitall.common.util.CommonUtils.userIp;

import com.lubycon.eatitall.common.entity.BaseEntity;
import com.lubycon.eatitall.domain.curation.entity.Curation;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class CurationRestaurant extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "curation_id")
  private Curation curation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @Builder
  public CurationRestaurant(Curation curation, Restaurant restaurant) {
    this.setCreatedIp(userIp());
    this.curation = curation;
    this.restaurant = restaurant;
  }

}