package edu.adaProject;

import edu.adaProject.controller.MenuController;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MenuController controller = new MenuController();

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
                    controller.calcularFrequenciaCargos();
                    break;
                case "4":
                    controller.calcularMaiorSalario();
                    break;
                case "5":
                    controller.calcularLocalComMaiorEMenorSalarioRemoto();
                    break;
                case "6":
                    controller.mostrarLocalizacaoComMaioresSalarios();
                    break;
                case "7":
                    controller.localizacaoComMaiorNumeroDeSalarios();
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }

    }

}
