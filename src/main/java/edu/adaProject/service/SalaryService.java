package edu.adaProject.service;

import edu.adaProject.model.DevSalary;

import java.util.*;
import java.util.stream.Collectors;

public class SalaryService {
    List<DevSalary> devSalaries = CsvReaderService.getDatabase();
    Map<String, String> paises = CsvReaderService.getPaises();


    // Iago Teles //
    public OptionalDouble calcularMediaSalarial() {
        return devSalaries.stream()
                .filter(dados -> dados.job_title().equalsIgnoreCase("Data Engineer"))
                .mapToDouble(dados -> dados.salary_in_usd())
                .average();

    }

    // Iago Teles //
    public void calcularFrequenciaCargos() {
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
    }

    // Elisabete
    public double calcularMaiorSalario() {
        return devSalaries.stream()
                .mapToDouble(DevSalary::salary_in_usd)
                .max()
                .orElse(0.0);
    }

    // Bianca
    public Map<String, Double> getLocalMaiorEMenorSalarioRemoto() {
        Map<String, Double> avgSalaryByLocation = devSalaries.stream()
                .filter(dev -> dev.remote_ratio() > 0)
                .collect(Collectors.groupingBy(
                        DevSalary::company_location,
                        Collectors.averagingInt(DevSalary::salary_in_usd)
                ));

        Optional<Map.Entry<String, Double>> bestPayingLocation = avgSalaryByLocation.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        Optional<Map.Entry<String, Double>> worstPayingLocation = avgSalaryByLocation.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue());

        Map<String, Double> result = new HashMap<>();

        bestPayingLocation.ifPresent(entry -> {
            String nomePais = paises.getOrDefault(entry.getKey(), "País Desconhecido");
            result.put("Melhor localização (Remoto): " + nomePais, entry.getValue());
        });

        worstPayingLocation.ifPresent(entry -> {
            String nomePais = paises.getOrDefault(entry.getKey(), "País Desconhecido");
            result.put("Pior localização (Remoto): " + nomePais, entry.getValue());
        });
        return result;
    }
}
