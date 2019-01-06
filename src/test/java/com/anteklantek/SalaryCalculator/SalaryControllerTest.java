package com.anteklantek.SalaryCalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalaryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGivenMinusSalaryBadRequestIsResponded() throws Exception {
        mvc.perform(get("/salary?dayGrossSalary=-21").contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGivenNotANumberSalaryBadRequestIsResponded() throws Exception {
        mvc.perform(get("/salary?dayGrossSalary=abc").contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
