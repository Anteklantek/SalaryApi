package com.anteklantek.SalaryCalculator.controller.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryListViewModel {

    private List<SalaryViewModel> salaryItemsList = new ArrayList<>();
}
