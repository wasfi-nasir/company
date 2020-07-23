package com.exalt.company;

import com.exalt.company.model.Address;
import com.exalt.company.model.Company;
import com.exalt.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CompanyTests {

    @Autowired
    private CompanyRepository companyRepository;

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @BeforeEach
    void createObject() {
        Company company = new Company();
        company.setName("exaltt");
        Address address = new Address();
        address.setTimeZone("2");
        address.setStreet("al-ersal");
        address.setCountry("pal");
        address.setCity("ramallah");
        company.setAddress(address);
        companyRepository.save(company);
    }

    @Test
    public void testFindById() throws URISyntaxException {
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        final String baseUrl = "http://localhost:" + port + "/api/company/" + id;
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
        company.setAddress(address);
        ResponseEntity<Company> result = testRestTemplate.postForEntity("http://localhost:" + port + "/api/company/", company, Company.class);
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
        company.setAddress(address);
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        final String baseUrl = "http://localhost:" + port + "/api/company/" + id;
        URI uri = new URI(baseUrl);
        testRestTemplate.put(uri, company );
        ResponseEntity<Company> result = testRestTemplate.getForEntity(uri, Company.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("exaltt", result.getBody().getName());
    }

    @Test
    public void testDelete() throws URISyntaxException {
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        final String baseUrl = "http://localhost:" + port + "/api/company/" + id;
        URI uri = new URI(baseUrl);
        testRestTemplate.delete(uri);
        ResponseEntity<Company> result = testRestTemplate.getForEntity(uri, Company.class);
        System.out.println(result.getBody());
        assertEquals(404, result.getStatusCodeValue());
    }
}
