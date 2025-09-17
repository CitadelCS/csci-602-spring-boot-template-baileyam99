package edu.citadel.hw1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SalariedEmployee extends Employee {
    private double annualSalary;

    @Override
    public double getMonthlyPay() {
        return annualSalary / 12.0;
    }

    @Override
    public String toString() {
        return String.format(
                "SalariedEmployee[name=%s, hireDate=%s, annualSalary=%.1f]",
                getName(), getHireDate(), annualSalary
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHireDate(), annualSalary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalariedEmployee other = (SalariedEmployee) obj;
        return Double.compare(other.annualSalary, annualSalary) == 0 &&
                Objects.equals(getName(), other.getName()) &&
                Objects.equals(getHireDate(), other.getHireDate());
    }
}
