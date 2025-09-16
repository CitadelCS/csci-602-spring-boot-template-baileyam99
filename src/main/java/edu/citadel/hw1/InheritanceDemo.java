package edu.citadel.hw1;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.System;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class InheritanceDemo {
    public static void main(String[] args) {
        ArrayList<Employee> employeeList = new ArrayList<>();
        HourlyEmployee hourlyEmployeeOne = new HourlyEmployee();
        HourlyEmployee hourlyEmployeeTwo = new HourlyEmployee();
        SalariedEmployee salariedEmployeeOne = new SalariedEmployee();
        SalariedEmployee salariedEmployeeTwo = new SalariedEmployee();

        hourlyEmployeeOne.setName("John Doe");
        hourlyEmployeeOne.setHireDate(LocalDate.parse("2009-05-21"));
        hourlyEmployeeOne.setWageRate(50.5);
        hourlyEmployeeOne.setHoursWorked(160);

        hourlyEmployeeTwo.setName("Jane Doe");
        hourlyEmployeeTwo.setHireDate(LocalDate.parse("2005-09-01"));
        hourlyEmployeeTwo.setWageRate(150.5);
        hourlyEmployeeTwo.setHoursWorked(80);

        salariedEmployeeOne.setName("Moe Howard");
        salariedEmployeeOne.setHireDate(LocalDate.parse("2004-01-01"));
        salariedEmployeeOne.setAnnualSalary(75000);

        salariedEmployeeTwo.setName("Curly Howard");
        salariedEmployeeTwo.setHireDate(LocalDate.parse("2018-01-01"));
        salariedEmployeeTwo.setAnnualSalary(105000);

        employeeList.add(hourlyEmployeeOne);
        employeeList.add(hourlyEmployeeTwo);
        employeeList.add(salariedEmployeeOne);
        employeeList.add(salariedEmployeeTwo);

        System.out.println("List of Employees (before sorting)");
        employeeList.forEach(System.out::println);
        System.out.println();

        Collections.sort(employeeList);

        System.out.println("List of Employees (after sorting)");
        employeeList.forEach(System.out::println);
        System.out.println();

        System.out.println("Monthly Pay");
        AtomicReference<Double> totalPay = new AtomicReference<>((double) 0);
        employeeList.forEach(employee -> {
            System.out.printf("%s: $%,.2f%n", employee.getName(), employee.getMonthlyPay());
            totalPay.updateAndGet(v -> v + employee.getMonthlyPay());
        });
        Double outputTotalPay = totalPay.get();
        System.out.printf("Total Monthly Pay: $%,.2f%n", outputTotalPay);
    }
}
