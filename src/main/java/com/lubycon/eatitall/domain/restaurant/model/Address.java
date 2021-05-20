package com.lubycon.eatitall.domain.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

  @Column(nullable = false, length = 20)
  private String district;

  @Column(nullable = false, length = 100)
  private String street;

  @Column(nullable = false, length = 300)
  private String detailedAddress;

  @Column(nullable = false, length = 500)
  private String fullAddress;

  @Builder
  public Address(String district, String street, String detailedAddress, String fullAddress) {
    this.district = district;
    this.street = street;
    this.detailedAddress = detailedAddress;
    this.fullAddress = fullAddress;
  }
}
