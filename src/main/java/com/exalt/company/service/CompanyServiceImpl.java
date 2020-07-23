package com.exalt.company.service;

import com.exalt.company.exception.CommonException;
import com.exalt.company.exception.ErrorEnums;
import com.exalt.company.model.Company;
import com.exalt.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        if (company.getId() != null) {
            throw new CommonException(ErrorEnums.ID_IS_AUTO_GENERATE);
        }
        else {
            companyRepository.save(company);
            return company;
        }
    }

    @Override
    public Company updateCompany(String id, Company company) {
        Company temp = companyRepository.findById(id).get();
        temp.setName(company.getName());
        companyRepository.save(temp);
        return temp;
    }

    @Override
    public String deleteCompany(String id) {
        companyRepository.findById(id).orElseThrow(() -> new CommonException(ErrorEnums.USER_NOT_FOUND));
        companyRepository.deleteById(id);
        return "Address has been deleted.";
    }

    @Override
    public Company findCompany(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorEnums.USER_NOT_FOUND));
    }

    @Override
    public List<Company> findByDistance(Double longitude, Double latitude, int distance) {
        return companyRepository.geoSearch(longitude, latitude, distance * 1000);
    }
}
