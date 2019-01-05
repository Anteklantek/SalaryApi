package com.anteklantek.SalaryCalculator.repository;

import com.anteklantek.SalaryCalculator.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findFirstByName(String name);
}
