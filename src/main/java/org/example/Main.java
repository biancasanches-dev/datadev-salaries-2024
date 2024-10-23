package org.example;

import org.example.model.DevSalary;
import org.example.service.CsvReaderService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<DevSalary> devSalaries = CsvReaderService.getDatabase();

//        devSalaries.stream()
//                .limit(300)
//                .forEach(System.out::println);

        System.out.println("_".repeat(70));
        // Iago Teles //
        System.out.println("Qual a média salarial dos Data enggineer?");

        OptionalDouble mediaSalarial =  devSalaries.stream()
                .filter(dados -> dados.job_title().equalsIgnoreCase("Data Engineer"))
                .mapToDouble(dados -> dados.salary_in_usd())
                .average();

        System.out.println("A média de salários de quem trabalha com Data Engineer nesse csv é: " +
                NumberFormat.getInstance(new Locale("pt", "BR")).format(mediaSalarial.orElse(0.0)) + " Dólares");
        System.out.println("_".repeat(70));

        // Iago Teles //
        System.out.println("Qual cargo mais aparece no dataset? E o que menos aparece?");
        Map<String, Long> cargoFrequencia = devSalaries.stream()
                .collect(Collectors.groupingBy(DevSalary::job_title, Collectors.counting()));

        Optional<Map.Entry<String, Long>> cargoMaisFrequente = cargoFrequencia.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        Optional<Map.Entry<String, Long>> cargoMenosFrequente = cargoFrequencia.entrySet().stream()
                .min(Map.Entry.comparingByValue());

        cargoMaisFrequente.ifPresent(entry ->
                System.out.println("Cargo que mais aparece: " + entry.getKey() + " com " + entry.getValue() + " ocorrências."));

        cargoMenosFrequente.ifPresent(entry ->
                System.out.println("Cargo que menos aparece: " + entry.getKey() + " com " + entry.getValue() + " ocorrências."));
        System.out.println("_".repeat(70));


    }
}