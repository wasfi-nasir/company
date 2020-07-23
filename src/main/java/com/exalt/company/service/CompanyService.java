package com.exalt.company.service;

import com.exalt.company.model.Company;

import java.util.List;


public interface CompanyService {

    Company createCompany(Company address);

    Company updateCompany(String id, Company address);

    String deleteCompany(String id);

    Company findCompany(String id);

    List<Company> findByDistance(Double longitude, Double latitude, int distance);
}
