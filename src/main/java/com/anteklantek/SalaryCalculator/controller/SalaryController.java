package com.anteklantek.SalaryCalculator.controller;

import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryListViewModel;
import com.anteklantek.SalaryCalculator.service.SalaryCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Slf4j
@CrossOrigin
public class SalaryController {

    @Autowired
    private SalaryCalculatorService salaryCalculatorService;


    @GetMapping(path = "/salary", produces = "application/json")
    public ResponseEntity<SalaryListViewModel> getRate(@RequestParam BigDecimal dayGrossSalary) {
        return new ResponseEntity<>(salaryCalculatorService.getMonthSalariesForDayGrossSalary(dayGrossSalary), HttpStatus.OK);
    }
}
