package org.example;

import org.example.model.DevSalary;
import org.example.service.CsvReaderService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<DevSalary> devSalaries = CsvReaderService.getDatabase();

        devSalaries.stream()
                .limit(300)
                .forEach(System.out::println);
    }
}