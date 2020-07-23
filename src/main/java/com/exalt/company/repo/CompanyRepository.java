package com.exalt.company.repo;

import com.exalt.company.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

    @Query("  {\"address.position\": { \"$near\": { \"$geometry\": {  \"type\": \"Point\", \"coordinates\": [?0, ?1]  }, $maxDistance : ?2 }  } }")
    List<Company> geoSearch(Double lat, Double lon, int distance);

//    @Query(sort = "{_id:-1}", value = "{id}", fields = "id")
//    List<Company> lastID();
}
