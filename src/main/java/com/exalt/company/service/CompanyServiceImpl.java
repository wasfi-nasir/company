package com.exalt.company.service;

import com.exalt.company.exception.CommonException;
import com.exalt.company.exception.ErrorEnums;
import com.exalt.company.model.Address;
import com.exalt.company.model.Company;
import com.exalt.company.repo.AddressRepository;
import com.exalt.company.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
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

    @Transactional
    @Override
    public Company updateCompany(String id, Company company) {
        Company temp = companyRepository.findById(id).get();
        temp.setName(company.getName());
        companyRepository.save(temp);
        return temp;
    }

    @Transactional
    @Override
    public String deleteCompany(String id) {
        companyRepository.findById(id).orElseThrow(() -> new CommonException(ErrorEnums.USER_NOT_FOUND));
        companyRepository.deleteById(id);
        return "Address has been deleted.";
    }

    @Transactional(readOnly = true)
    @Override
    public Company findCompany(String id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorEnums.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Company> findByDistance(Double longitude, Double latitude, int distance) {
        return companyRepository.geoSearch(longitude, latitude, distance * 1000);
    }

    @Override
    @Transactional
    public Company creation() {
            Company company = new Company();
            company.setName("exalt");
            Address address = new Address();
            address.setTimeZone("2");
            address.setStreet("al-ersal");
            address.setCountry("pal");
            address.setCity("ramallah");
            GeoJsonPoint point = new GeoJsonPoint(1, 2);
            address.setPosition(point);
            addressRepository.save(address);
            company.setAddress(address);
            if(true)
                throw new CommonException(ErrorEnums.USER_NOT_FOUND);
            companyRepository.save(company);
            return company;
    }

}
