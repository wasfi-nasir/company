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

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Company getCompany(@PathVariable String id) {
        return companyService.findCompany(id);
    }

    /**
     * @param company
     * @return
     */
    @PostMapping(value = "")
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    /**
     * @param id
     * @param company
     * @return
     */
    @PutMapping(value = "/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable String id) {
        return companyService.deleteCompany(id);
    }

    /**
     * @param longitude
     * @param latitude
     * @param distance
     * @return
     */
    @GetMapping("/findByDistance")
    public List<Company> geoSearch(@RequestParam(value = "long") Double longitude,
                                   @RequestParam(value = "lat") Double latitude, @RequestParam("dist") int distance) {
        return companyService.findByDistance(longitude, latitude, distance);
    }
}
