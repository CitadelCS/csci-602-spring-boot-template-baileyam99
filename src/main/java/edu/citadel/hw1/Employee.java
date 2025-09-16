package edu.citadel.hw1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Employee implements Comparable<Employee> {
    private String name;
    private LocalDate hireDate;

    public abstract double getMonthlyPay();

    @Override
    public int compareTo(Employee other) {
        return Double.compare(this.getMonthlyPay(), other.getMonthlyPay());
    }
}
