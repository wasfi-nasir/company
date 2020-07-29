package com.exalt.company.service;

import com.exalt.company.model.Company;

import java.util.List;


public interface CompanyService {

    Company create(Company company);

    Company update(String id, Company company);

    String delete(String id);

    Company findById(String id);

    List<Company> findByDistance(Double longitude, Double latitude, int distance);
}
