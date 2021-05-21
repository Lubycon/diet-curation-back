package com.lubycon.eatitall.api.controller;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.api.model.response.restaurant.CurationRestaurantResponse;
import com.lubycon.eatitall.domain.restaurant.service.CurationRestaurantService;
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
@Api(tags = {"2. 큐레이션 - 식당"})
@RequestMapping("/api/curation")
public class CurationRestaurantController {

  private final CurationRestaurantService curationRestaurantService;

  @ApiOperation(value = "[큐레이션 상세, 큐레이션 상세에서 지도 이동시] 큐레이션 식당 리스트 조회",
      notes = "curationId에 해당하는 모든 식당을 조회한다.")
  @GetMapping("/{curationId}/restaurant")
  public ResponseEntity<Response> retrieveRestaurantsByCurationId(@PathVariable Long curationId) {
    List<CurationRestaurantResponse> curationRestaurants = curationRestaurantService
        .retrieveRestaurantsByCurationId(curationId);
    Response response = new Response("curationRestaurants", curationRestaurants);
    return ResponseEntity.ok().body(response);
  }

}
