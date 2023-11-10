package com.example.microservicesproject.controller;

import com.example.microservicesproject.model.CountryResponse;
import com.example.microservicesproject.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/countries")
public class CountryController {

private final CountryService countryService;

    @GetMapping
    public List<CountryResponse> getAllCountriesMethod (@RequestParam String currency) {

      return countryService.getAllCountriesMethod(currency);

    }



}
