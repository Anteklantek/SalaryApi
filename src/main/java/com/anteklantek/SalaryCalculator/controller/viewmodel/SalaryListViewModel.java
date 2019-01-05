package com.anteklantek.SalaryCalculator.controller.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalaryListViewModel {

    private List<SalaryViewModel> salaryItemsList;
}
