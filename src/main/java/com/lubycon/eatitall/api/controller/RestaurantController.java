package com.lubycon.eatitall.api.controller;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantDetailResponse;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

  private final RestaurantService restaurantService;

  @GetMapping
  public ResponseEntity<Response> retrieveAllRestaurants() {
    List<RestaurantResponse> restaurants = restaurantService.retrieveAllRestaurants();
    Response response = new Response("restaurants", restaurants);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<Response> retrieveRestaurantByRestaurantId(@PathVariable Long restaurantId) {
    RestaurantDetailResponse restaurant = restaurantService.retrieveRestaurantByRestaurantId(restaurantId);
    Response response = new Response("restaurant", restaurant);
    return ResponseEntity.ok().body(response);
  }

}
