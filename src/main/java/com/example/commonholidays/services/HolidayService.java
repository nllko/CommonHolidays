package com.example.commonholidays.services;

import com.example.commonholidays.models.Holiday;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

  public Map<LocalDate, List<Holiday>> findCommonElements(Holiday[] arr1, Holiday[] arr2) {
    Map<LocalDate, List<Holiday>> commonHolidays = new HashMap<>();
    for (Holiday holiday1 : arr1) {
      for (Holiday holiday2 : arr2) {
        if (holiday1.equals(holiday2)) {
          commonHolidays.put(holiday1.getDate(), Arrays.asList(holiday1,holiday2));
        }
      }
    }

    return commonHolidays;
  }
}
