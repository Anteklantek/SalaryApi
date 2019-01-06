package com.anteklantek.SalaryCalculator.controller;

import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryListViewModel;
import com.anteklantek.SalaryCalculator.service.SalaryCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@RestController
@Slf4j
@CrossOrigin
@Validated
public class SalaryController {

    @Autowired
    private SalaryCalculatorService salaryCalculatorService;


    @GetMapping(path = "/salary", produces = "application/json")
    public ResponseEntity<SalaryListViewModel> getRate(@RequestParam @Valid @Min(0) BigDecimal dayGrossSalary) {
        SalaryListViewModel salaryListViewModel = salaryCalculatorService.getMonthSalariesForDayGrossSalary(dayGrossSalary);
        return new ResponseEntity<>(salaryListViewModel, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> constraintViolationHandler(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getConstraintViolations().iterator().next().getPropertyPath() + " " +
                ex.getConstraintViolations().iterator().next().getMessage(), HttpStatus.BAD_REQUEST);
    }
}
