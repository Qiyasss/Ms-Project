package com.example.microservicesproject.service;

import com.example.microservicesproject.model.CountryResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    public List<CountryResponse> getAllCountriesMethod(String currency) {

        if(currency.equals("USD")) {

          return List.of(new CountryResponse("USA", BigDecimal.valueOf(111)),
                  new CountryResponse("England", BigDecimal.valueOf(121)));

        }

        return new ArrayList<>();
    }

}
