package com.lubycon.eatitall.api.controller;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_INVALID_ADMIN_KEY;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.common.exception.InvalidRequestException;
import com.lubycon.eatitall.domain.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"4. 어드민"})
@RequestMapping("/api/admin")
public class AdminController {

  @Value("${google.spreadSheets.adminAuthKey}")
  private String adminAuthKey;

  private final AdminService adminService;

  private final String googleSheetUrl = "https://docs.google.com/spreadsheets/d/1pWmLRMVj_Ss8CcYOcWxKRk3L9fDsbvhsfm74h57h7LY/edit#gid=233067311";

  @ApiOperation(
      value = "어드민 시트의 식당 데이터를 갱신한다.",
      notes = googleSheetUrl)
  @GetMapping("/sheet/renew/restaurant")
  public ResponseEntity<Response> renewRestaurantSheet(@RequestParam(required = true) String adminKey) {
    if (!adminKey.equals(adminAuthKey)) {
      throw new InvalidRequestException(MSG_INVALID_ADMIN_KEY);
    }
    adminService.renewRestaurantSheet();
    return ResponseEntity.ok().body(new Response());
  }

  @ApiOperation(
      value = "어드민 시트의 큐레이션 데이터를 갱신한다.",
      notes = googleSheetUrl)
  @GetMapping("/sheet/renew/curation")
  public ResponseEntity<Response> renewCurationSheet(@RequestParam(required = true) String adminKey) {
    if (!adminKey.equals(adminAuthKey)) {
      throw new InvalidRequestException(MSG_INVALID_ADMIN_KEY);
    }
    adminService.renewCurationSheet();
    return ResponseEntity.ok().body(new Response());
  }

  @ApiOperation(
      value = "어드민 시트의 큐레이션-식당 데이터를 갱신한다.",
      notes = googleSheetUrl)
  @GetMapping("/sheet/renew/curationRestaurant")
  public ResponseEntity<Response> renewAdminSheet(@RequestParam(required = true) String adminKey) {
    if (!adminKey.equals(adminAuthKey)) {
      throw new InvalidRequestException(MSG_INVALID_ADMIN_KEY);
    }
    adminService.renewCurationRestaurantSheet();
    return ResponseEntity.ok().body(new Response());
  }
}
