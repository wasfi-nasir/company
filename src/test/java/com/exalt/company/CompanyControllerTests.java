package com.exalt.company;

import com.exalt.company.model.Address;
import com.exalt.company.model.Company;
import com.exalt.company.repo.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @PostConstruct
    public void initialize() {
        this.testRestTemplate = new TestRestTemplate(restTemplateBuilder.rootUri("http://localhost:" + port + "/api/v1/company"));
    }

    @BeforeEach
    void createObject() {
        Company company = new Company();
        company.setName("exaltt");

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        company.setDate(now);

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

    @AfterEach
    public void deleteAll() {
        companyRepository.deleteAll();
    }

    @Test
    public void testFindById() {
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();

        ResponseEntity<Company> result = get(id);
        assertEquals("exaltt", result.getBody().getName());
    }

    @Test
    public void testPost() {
        Company company = new Company();
        company.setName("exalt");

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        company.setDate(now);

        Address address = new Address();
        address.setTimeZone("2");
        address.setStreet("al-ersal");
        address.setCountry("pal");
        address.setCity("ramallah");

        GeoJsonPoint point = new GeoJsonPoint(1, 2);
        address.setPosition(point);

        company.setAddress(address);
        ResponseEntity<Company> result = testRestTemplate.postForEntity("/", company, Company.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("exalt", result.getBody().getName());
    }

    @Test
    public void testPut() throws URISyntaxException {
        Company company = new Company();
        company.setName("EEXXAALLTT");

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        company.setDate(now);

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
        testRestTemplate.put("/" + id, company);
        ResponseEntity<Company> result = get(id);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("EEXXAALLTT", result.getBody().getName());
    }

    @Test
    public void testDelete() {
        List<Company> companies = companyRepository.findAll();
        String id = companies.get(0).getId();
        testRestTemplate.delete("/" + id);
        assertFalse(companyRepository.existsById(id));
    }

    @Test
    public void testGeoSearch() {
        Company company = new Company();
        company.setName("exal LTD");

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        company.setDate(now);

        Address address = new Address();
        address.setTimeZone("3");
        address.setStreet("al-masion");
        address.setCountry("pal");
        address.setCity("ramallah");
        GeoJsonPoint point = new GeoJsonPoint(28, 28);
        address.setPosition(point);
        company.setAddress(address);
        companyRepository.save(company);
        ResponseEntity<List<Company>> companies = testRestTemplate.exchange("/findByDistance?long=34&lat=31&dist=10000000",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Company>>() {
                });
        assertEquals(200, companies.getStatusCodeValue());
        assertEquals("exal LTD", companies.getBody().get(1).getName());
    }

    private ResponseEntity<Company> get(String id) {
        return testRestTemplate.getForEntity("/" + id, Company.class);
    }
}
