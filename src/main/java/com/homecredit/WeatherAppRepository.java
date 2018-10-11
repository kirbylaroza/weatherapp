package com.homecredit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherAppRepository extends CrudRepository<Weather, Long> {

}
