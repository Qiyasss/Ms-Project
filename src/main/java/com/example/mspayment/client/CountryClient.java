package com.example.mspayment.client;


import com.example.mspayment.model.client.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MicroServicesProject", url = "${client.MicroServicesProject.endpoint}")
public interface CountryClient {

    @GetMapping("/api/countries")
    List<CountryDto> getAllAvailableCountries(@RequestParam String currency);

}
