package com.example.commonholidays.controllers;

import com.example.commonholidays.models.Holiday;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/common-holidays")
public class HolidayController {

  @GetMapping("{firstCode}/{secondCode}/{year}")
  public String getCommonHolidays(@PathVariable String firstCode, @PathVariable String secondCode,
      @PathVariable int year) {
    String uri = "https://date.nager.at/api/v3/publicholidays/" + year + "/" + firstCode;
    String uri2 = "https://date.nager.at/api/v3/publicholidays/" + year + "/" + secondCode;
    RestTemplate restTemplate = new RestTemplate();
    List<Holiday> holidays = Arrays.stream(
        Objects.requireNonNull(restTemplate.getForObject(uri, Holiday[].class))).toList();
    List<Holiday> holidays2 = Arrays.stream(
        Objects.requireNonNull(restTemplate.getForObject(uri2, Holiday[].class))).toList();
    StringBuilder commonHolidays = new StringBuilder();

    for (Holiday holiday1 : holidays) {
      for (Holiday holiday2 : holidays2) {
        if (holiday1.equals(holiday2)) {
          commonHolidays.append(holiday1.getDate()).append(holiday1.getLocalName())
              .append(holiday2.getLocalName());
        }
      }
    }
    return commonHolidays.toString();
  }

}
