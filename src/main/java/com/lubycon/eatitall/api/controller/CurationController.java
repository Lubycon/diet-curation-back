package com.lubycon.eatitall.api.controller;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.api.model.response.curation.CurationDetailResponse;
import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.domain.curation.service.CurationService;
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
@Api(tags = {"1. 큐레이션"})
@RequestMapping("/api/curation")
public class CurationController {

  private final CurationService curationService;

  @ApiOperation(
      value = "[메인페이지, 메인페이지에서 지도 이동시] 전체 큐레이션 리스트 조회",
      notes = "모든 큐레이션을 조회한다.")
  @GetMapping
  public ResponseEntity<Response> retrieveAllCurations() {
    List<CurationResponse> curations = curationService.retrieveAllCurations();
    Response response = new Response("curations", curations);
    return ResponseEntity.ok().body(response);
  }

  @ApiOperation(
      value = "[큐레이션 상세페이지] 큐레이션 단건 조회",
      notes = "curationId로 큐레이션을 단건 조회한다.")
  @GetMapping("/{curationId}")
  public ResponseEntity<Response> retrieveCurationByCurationId(@PathVariable Long curationId) {
    CurationDetailResponse curation = curationService.retrieveCurationByCurationId(curationId);
    Response response = new Response("curation", curation);
    return ResponseEntity.ok().body(response);
  }

}
