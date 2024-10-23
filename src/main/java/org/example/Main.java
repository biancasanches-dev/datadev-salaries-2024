package org.example;

import org.example.controller.MenuController;
import org.example.model.DevSalary;
import org.example.service.CsvReaderService;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MenuController controller = new MenuController();

        List<DevSalary> devSalaries = CsvReaderService.getDatabase();

        devSalaries.stream()
                .limit(300)
                .forEach(System.out::println);

        String opt = "";
        while (!opt.equals("0")) {
            controller.showMenu();
            opt = scanner.nextLine();
            if (opt.equalsIgnoreCase("0")) {
                System.out.println("Finalizando a aplicação");
                scanner.close();
                System.exit(0);
            }

            switch (opt) {
                case "1":
                    controller.showDataDescription();
                    break;
                case "2":
                    controller.calcularMediaSalarial();
                    break;
                case "3":
                    controller.calcularMaiorSalario();
                    break;
                case "4":
                    controller.calcularMediaSalarialJuniores();
                    break;
                case "5":
                    controller.calcularDiferencaSalarialRemotoEPresencial();
                    break;
                case "6":
                    controller.calcularMaiorMediaSalarial();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }


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
