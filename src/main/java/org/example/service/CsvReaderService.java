package org.example.service;

import org.example.model.DevSalary;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderService {

    public static List<DevSalary> getDatabase() {
        List<DevSalary> devSalaries = new ArrayList<>();

        try {
            Files.lines(Paths.get("src/main/java/org/example/database/dataset_salary_2024.csv"))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(line -> new DevSalary(
                            Integer.parseInt(line[0]),
                            line[1],
                            line[2],
                            line[3],
                            Integer.parseInt(line[4]),
                            line[5],
                            Integer.parseInt(line[6]),
                            line[7],
                            Integer.parseInt(line[8]),
                            line[9],
                            line[10]
                    ))
                    .forEach(devSalaries::add);

            return devSalaries;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler arquivo", e);
        }
    }
}
