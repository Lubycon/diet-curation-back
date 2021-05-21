package com.lubycon.eatitall.api.controller;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.api.model.response.curation.CurationDetailResponse;
import com.lubycon.eatitall.api.model.response.curation.CurationResponse;
import com.lubycon.eatitall.domain.curation.service.CurationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/curation")
public class CurationController {

  private final CurationService curationService;

  @GetMapping
  public ResponseEntity<Response> retrieveAllCurations() {
    List<CurationResponse> curations = curationService.retrieveAllCurations();
    Response response = new Response("curations", curations);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{curationId}")
  public ResponseEntity<Response> retrieveCurationByCurationId(@PathVariable Long curationId) {
    CurationDetailResponse curation = curationService.retrieveCurationByCurationId(curationId);
    Response response = new Response("curation", curation);
    return ResponseEntity.ok().body(response);
  }

}
