package hw_3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streams {
    private static final String filename = "table.txt";

    public static void main(String[] args) {
        List<Employee> parsed;
        try {
            //read all lines from file
            //by streams make List<Employee>
            parsed = Files
                    .readAllLines(new File(filename).toPath())
                    .stream()
                    .map(Employee::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            //return if fail
            return;
        }
        //work with parsed
        //...
        System.out.println("maxSalary");
        System.out.println(maxSalary(parsed));

        System.out.println("minSalary");
        System.out.println(minSalary(parsed));

        System.out.println("averageSalary");
        System.out.println(averageSalary(parsed));

        System.out.println("jobCount");
        System.out.println(jobCount(parsed));

        System.out.println("abc");
        System.out.println(abc(parsed));
    }

    public static int maxSalary(List<Employee> list) {
        //stream of Employee -> stream of int (salary)
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .max()
                .getAsInt();
    }

    public static int minSalary(List<Employee> list) {
        //similar
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .min()
                .getAsInt();
    }

    public static double averageSalary(List<Employee> list) {
        //similar
        return list
                .stream()
                .mapToInt((e) -> e.salary)
                .average()
                .getAsDouble();
    }

    public static Map<String, Integer> jobCount(List<Employee> list) {
        //get from Employees position as key
        //1 as value
        //sum as merging function
        return list
                .stream()
                .collect(Collectors
                        .toMap(
                                (e) -> e.position,
                                (e) -> 1,
                                Integer::sum));
    }

    public static Map<Character, Integer> abc(List<Employee> list) {
        //get from Employees
        //first character as key
        //1 as value
        //sum as merging
        return list
                .stream()
                .collect(Collectors
                        .toMap(
                                (e) -> e.surname.charAt(0),
                                (e) -> 1,
                                Integer::sum));
    }

    private static class Employee {
        String surname, position;
        int salary;

        Employee(String line) {
            //split by commas and trim against spaces
            String[] divided = line.split(",");
            surname = divided[0].trim();
            position = divided[1].trim();
            salary = Integer.parseInt(divided[2].trim());
        }
    }
}
