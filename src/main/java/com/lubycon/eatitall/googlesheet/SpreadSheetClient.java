package com.lubycon.eatitall.googlesheet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    value = "adminSheet",
    url = "https://sheets.googleapis.com/v4/spreadsheets/1pWmLRMVj_Ss8CcYOcWxKRk3L9fDsbvhsfm74h57h7LY")
public interface SpreadSheetClient {
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/values/restaurants",
      produces = MediaType.APPLICATION_JSON_VALUE)
  SpreadSheetResponse restaurants(@RequestParam String key);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/values/curations",
      produces = MediaType.APPLICATION_JSON_VALUE)
  SpreadSheetResponse curations(@RequestParam String key);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/values/curationRestaurants",
      produces = MediaType.APPLICATION_JSON_VALUE)
  SpreadSheetResponse curationRestaurants(@RequestParam String key);
}
