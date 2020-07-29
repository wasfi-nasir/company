package com.exalt.company.controller;

import com.exalt.company.model.Company;
import com.exalt.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/{id}")
    public Company getById(@PathVariable String id) {
        return companyService.findById(id);
    }

    @PostMapping(value = "")
    public Company create(@RequestBody Company company) {
        return companyService.create(company);
    }

    @PutMapping(value = "/{id}")
    public Company update(@PathVariable String id, @RequestBody Company company) {
        return companyService.update(id, company);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return companyService.delete(id);
    }

    @GetMapping("/findByDistance")
    public List<Company> geoSearch(@RequestParam(value = "long") Double longitude,
                                   @RequestParam(value = "lat") Double latitude, @RequestParam("dist") int distance) {
        return companyService.findByDistance(longitude, latitude, distance);
    }
}
