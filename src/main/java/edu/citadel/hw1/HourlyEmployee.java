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
class HourlyEmployee extends Employee {
    private double wageRate;
    private double hoursWorked;

    @Override
    public double getMonthlyPay() {
        return wageRate * hoursWorked;
    }

    @Override
    public String toString() {
        return String.format(
                "HourlyEmployee[name=%s, hireDate=%s, wageRate=%.2f, hoursWorked=%.1f]",
                getName(), getHireDate(), wageRate, hoursWorked
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHireDate(), wageRate, hoursWorked);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HourlyEmployee other = (HourlyEmployee) obj;
        return Double.compare(other.wageRate, wageRate) == 0 &&
                Double.compare(other.hoursWorked, hoursWorked) == 0 &&
                Objects.equals(getName(), other.getName()) &&
                Objects.equals(getHireDate(), other.getHireDate());
    }
}
