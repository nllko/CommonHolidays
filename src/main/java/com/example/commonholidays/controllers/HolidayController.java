package com.example.commonholidays.controllers;

import com.example.commonholidays.models.Holiday;
import com.example.commonholidays.services.HolidayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/holidays")
public class HolidayController {

  private final HolidayService holidayService;

  public HolidayController(HolidayService holidayService) {
    this.holidayService = holidayService;
  }


  @GetMapping("/{firstCode}/{secondCode}/{year}")
  public String getCommonHolidays(@PathVariable String firstCode, @PathVariable String secondCode,
      @PathVariable int year, Model model) {
    String firstCountryUri =
        "https://date.nager.at/api/v3/publicholidays/" + year + "/" + firstCode;
    String secondCountryUri =
        "https://date.nager.at/api/v3/publicholidays/" + year + "/" + secondCode;
    RestTemplate restTemplate = new RestTemplate();
    Holiday[] firstCountryHolidays = restTemplate.getForObject(firstCountryUri, Holiday[].class);
    Holiday[] secondCountryHolidays = restTemplate.getForObject(secondCountryUri, Holiday[].class);

    model.addAttribute("holidays",
        holidayService.findCommonElements(firstCountryHolidays, secondCountryHolidays));
    model.addAttribute("firstCountry", firstCode);
    model.addAttribute("secondCountry", secondCode);
    return "holidays/list";
  }

}
