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
                default:
                    System.out.println("Opção inválida");
                    controller.showMenu();
            }
        }
    }
}
