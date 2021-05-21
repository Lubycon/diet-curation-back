package com.lubycon.eatitall.api.controller;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantDetailResponse;
import com.lubycon.eatitall.api.model.response.restaurant.RestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"3. 식당"})
@RequestMapping("/api/restaurant")
public class RestaurantController {

  private final RestaurantService restaurantService;

  @ApiOperation(value = "[지도 페이지] 전체 식당 리스트 조회",
      notes = "모든 식당을 조회한다.")
  @GetMapping
  public ResponseEntity<Response> retrieveAllRestaurants() {
    List<RestaurantResponse> restaurants = restaurantService.retrieveAllRestaurants();
    Response response = new Response("restaurants", restaurants);
    return ResponseEntity.ok().body(response);
  }

  @ApiOperation(value = "[큐레이션 상세, 지도에서 식당 클릭시] 식당 단건 조회",
      notes = "restaurantId로 식당을 단건 조회한다.\n식당의 큐레이션정보와 메뉴정보도 포함한다.")
  @GetMapping("/{restaurantId}")
  public ResponseEntity<Response> retrieveRestaurantByRestaurantId(@PathVariable Long restaurantId) {
    RestaurantDetailResponse restaurant = restaurantService.retrieveRestaurantByRestaurantId(restaurantId);
    Response response = new Response("restaurant", restaurant);
    return ResponseEntity.ok().body(response);
  }

}
