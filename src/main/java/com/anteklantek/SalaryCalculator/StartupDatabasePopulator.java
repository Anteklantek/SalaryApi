package com.anteklantek.SalaryCalculator;

import com.anteklantek.SalaryCalculator.model.Country;
import com.anteklantek.SalaryCalculator.repository.CountryRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Component
public class StartupDatabasePopulator implements CommandLineRunner {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void run(String... args) throws Exception {
        CsvMapper mapper = new CsvMapper();
        File file = ResourceUtils.getFile("classpath:country.csv");
        CsvSchema schema = mapper.schemaFor(Country.class).withHeader();
        MappingIterator<Country> it = mapper.readerFor(Country.class).with(schema).readValues(file);
        while (it.hasNextValue()) {
            Country country = it.nextValue();
            countryRepository.save(country);
        }

    }
}
