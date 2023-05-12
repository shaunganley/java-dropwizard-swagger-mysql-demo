package org.kainos.ea.api;

import org.kainos.ea.cli.Contractor;
import org.kainos.ea.cli.Employee;
import org.kainos.ea.cli.IPayable;
import org.kainos.ea.cli.SalesEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public List<Employee> getEmployees() {
        Employee employee = new Employee(1, "Shaun", 20000);
        SalesEmployee salesEmployee = new SalesEmployee(1, "Shaun", 20000,
                1000, 0.01f);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        for (Employee e: employees) {
            System.out.println(e.calcPay());
        }
        
        return employees;
    }
}
