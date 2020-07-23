package com.exalt.company;

import com.exalt.company.model.Address;
import com.exalt.company.model.Company;
import com.exalt.company.repo.AddressRepository;
import com.exalt.company.repo.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    void createObject() {
        Company company = new Company();
        company.setName("exaltt");
        Address address = new Address();
        address.setTimeZone("2");
        address.setStreet("al-ersal");
        address.setCountry("pal");
        address.setCity("ramallah");
        GeoJsonPoint point = new GeoJsonPoint(30, 29);
        address.setPosition(point);
        company.setAddress(address);
        companyRepository.save(company);
    }

    @Test
    public void testFindById() throws URISyntaxException {
        List<Company> companies = companyRepository.findAll();
        //Company company = companyRepository.findOne(Query.query(Criteria.where("name").is("exaltt")), Company.class);
        String id = companies.get(0).getId();
        final String baseUrl = "http://localhost:" + port + "/api/v1/company/" + id;
        URI uri = new URI(baseUrl);
        ResponseEntity<Company> result = testRestTemplate.getForEntity(uri, Company.class);
        assertEquals("exaltt", result.getBody().getName());
    }

    @Test
    public void testPost() throws URISyntaxException {
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
        ResponseEntity<Company> result = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/company/", company, Company.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("exalt", result.getBody().getName());
    }

    @Test
    public void testPut() throws URISyntaxException {
        Company company = new Company();
        company.setName("exaltt");
        Address address = new Address();
        address.setTimeZone("22");
        address.setStreet("al-ersal");
        address.setCountry("pal");
        address.setCity("ramallah");
        GeoJsonPoint point = new GeoJsonPoint(1, 2);
        address.setPosition(point);
        company.setAddress(address);
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        final String baseUrl = "http://localhost:" + port + "/api/v1/company/" + id;
        URI uri = new URI(baseUrl);
        testRestTemplate.put(uri, company);
        ResponseEntity<Company> result = testRestTemplate.getForEntity(uri, Company.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("exaltt", result.getBody().getName());
    }

    @Test
    public void testDelete() {
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        testRestTemplate.delete("http://localhost:" + port + "/api/v1/company/" + id);
        assertFalse(companyRepository.existsById(id));
    }

    @Test
    public void testGeoSearch() {
        Company company = new Company();
        company.setName("exal LTD");
        Address address = new Address();
        address.setTimeZone("3");
        address.setStreet("al-masion");
        address.setCountry("pal");
        address.setCity("ramallah");
        GeoJsonPoint point = new GeoJsonPoint(28, 28);
        address.setPosition(point);
        company.setAddress(address);
        companyRepository.save(company);

        List<Company> companies = testRestTemplate.exchange("http://localhost:" + port + "/api/v1/company/findByDistance?long=34&lat=31&dist=10000000",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {
                }).getBody();
        System.out.println(companies.get(0).getName() + companies.get(1).getName());
    }
}
