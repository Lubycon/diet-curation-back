package com.lubycon.eatitall.api.controller;

import static com.lubycon.eatitall.common.util.MessageUtils.MSG_INVALID_ADMIN_KEY;

import com.lubycon.eatitall.api.model.response.common.Response;
import com.lubycon.eatitall.common.exception.InvalidRequestException;
import com.lubycon.eatitall.domain.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.transaction.Transactional;
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

  @Value("${google.spreadSheets.apiKey}")
  private String apiKey;

  @Value("${google.spreadSheets.adminAuthKey}")
  private String adminAuthKey;

  private final AdminService adminService;

  @ApiOperation(
      value = "어드민 시트의 식당 데이터를 갱신한다.",
      notes = "https://docs.google.com/spreadsheets/d/1pWmLRMVj_Ss8CcYOcWxKRk3L9fDsbvhsfm74h57h7LY/edit#gid=233067311")
  @GetMapping("/sheet/renew")
  @Transactional
  public ResponseEntity<Response> renewAdminSheet(@RequestParam(required = true) String adminKey) {
    if (!adminKey.equals(adminAuthKey)) {
      throw new InvalidRequestException(MSG_INVALID_ADMIN_KEY);
    }
    adminService.renewAdminSheet(apiKey);
    return ResponseEntity.ok().body(new Response());
  }

}
