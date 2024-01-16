import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solution {

    static class Employee {
        int id;
        int salary;
        int parent;
        int child;
        int children;
        int childrenLeft;
    }
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<Employee>();

        // Input loading
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 1; i <= n; i++) {
            Employee employee = new Employee();
            employee.id = i;
            employee.parent = scanner.nextInt();
            employee.salary = scanner.nextInt();

            // In case n = 1
            if (employee.parent == employee.id) {
                employee.salary = n;
            }

            employees.add(employee);


        }

        int[] usedSalaries = new int[n+1];
        List<Employee> left = new ArrayList<Employee>();

        for (Employee emp : employees) {
            employees.get(emp.parent - 1).childrenLeft++;
        }
        for (Employee emp : employees) {
            if (emp.childrenLeft == 0) {
                left.add(emp);
            }
        }

        //  Children manipulation

        for(int i = 0; i < left.size(); i++){
            Employee tempEmployee = left.get(i);
            if(tempEmployee.salary == 0){
                Employee parentEmp = employees.get(tempEmployee.parent - 1);
                parentEmp.childrenLeft -= 1;
                if (parentEmp.childrenLeft == 0){
                    left.add(parentEmp);
                }
                parentEmp.children++;
                parentEmp.children += tempEmployee.children;
            }
        }

        // Setting used salaries array and childs

        for(Employee emp : employees){
            if (emp.salary > 0) {
                usedSalaries[emp.salary] = emp.id;
            } else if (employees.get(emp.parent - 1).child == 0) {
                employees.get(emp.parent - 1).child = emp.id;
            } else {
                employees.get(emp.parent - 1).child = -1;
            }
        }

        // General Salaries algorithm
        int i = 0;
        int freeSalaries = 0;
        int possibleSalaries = 0;
        while (i < n) {
            while (i < n && usedSalaries[i + 1] == 0) {
                i++;
                freeSalaries++;
                possibleSalaries++;
            }
            while (i < n && usedSalaries[i + 1] > 0) {
                i++;
                Employee employee = employees.get(usedSalaries[i] - 1);
                int tempSalary = i;
                freeSalaries -= employee.children;
                if (freeSalaries == 0) {
                    while (possibleSalaries > 0 && employee.child > 0) {
                        possibleSalaries--;
                        while (usedSalaries[tempSalary] > 0) {
                            tempSalary--;
                        }
                        employee = employees.get(employee.child - 1);
                        employee.salary = tempSalary;
                        usedSalaries[tempSalary] = employee.id;
                    }
                    possibleSalaries = 0;
                }
                if (employee.children > 0) {
                    possibleSalaries = 0;
                }
            }
        }

        // Output array
        int[] salariesArr = new int[n];
        for(Employee employee : employees){
            salariesArr[employee.id - 1] = employee.salary;
        }

        for (int salary : salariesArr){
            System.out.println(salary);
        }

    }
}