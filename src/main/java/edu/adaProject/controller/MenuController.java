package edu.adaProject.controller;

import edu.adaProject.service.SalaryService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalDouble;

public class MenuController {
    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    SalaryService salaryService = new SalaryService();

    public void showMenu() {
        System.out.println(
                "\nSelecione uma opção no menu abaixo: \n" +
                        "1 - Descrição dos dados\n" +
                        "2 - Qual a média salarial dos profissionais de dados?\n" +
                        "3 - Qual cargo mais aparece no dataset? E o que menos aparece?\n" +
                        "4 - Qual o maior salário registrado?\n" +
                        "5 - Quais localidades tem a maior e menor média de salário remoto?\n" +
                        "6 - Em qual localização estão concentradas as empresas com maior média salarial\n" +
                        "7 - \n" +
                        "8 - \n" +
                        "0 - Sair");
    }

    public void showDataDescription() {
        System.out.printf("O conjunto de dados de 2024 sobre salários e " +
                "atributos de emprego dos desenvolvedores de dados  oferece " +
                "informações sobre o cenário em evolução \ndos " +
                "desenvolvedores de dados. Inclui variáveis-chave como " +
                "salário, cargo, nível de experiência, tipo de emprego, " +
                "residência do funcionário, proporção \n de trabalho remoto, " +
                "localização da empresa e tamanho da empresa. Esses dados permitem "
                + "análises detalhadas de tendências salariais, padrões de " +
                "emprego\n e variações geográficas em funções de engenharia " +
                "de dados. Pesquisadores, analistas e organizações podem aproveitar esse conjunto de "
                + "dados para\n compreender melhor as tendências de " +
                "remuneração, a distribuição das funções de desenvolvedor de " +
                "dados em diferentes regiões e o impacto do trabalho\n remoto" +
                " e do tamanho da empresa no emprego nesta área.\n"
                + "\nOs campos presentes são:\n"
                + " - *Nível de Experiência*: Júnior(EN), Pleno(MI), Sênior" +
                "(SE), Master(EX)\n"
                + " - *Tipo de contrato de trabalho*: tempo integral(FT), " +
                "meio período(PT), contrato(CT), Freelance(FL)\n"
                + " - *Cargo*: Engenheiro de Dados (Data Engineer), Cientista" +
                " de Dados (Data Scientist), "
                + "Analista de Dados (Data Analyst), Engenheiro de Aprendizado de Máquina (Machine Learning Engineer), "
                + "Cientista Pesquisador (Research Scientist)\n"
                + " - *Salário* (salary)\n"
                + " - *Moeda do Salário* (salary_currency): A moeda na qual " +
                "o salário é pago\n"
                + " - *Salário em dólares* (salary_in_usd): O valor do " +
                "salário convertido em dólares\n"
                + " - *Residência do empregado* (employee_residence): Local " +
                "de residência do empregado\n"
                + " - *Proporção de trabalho remoto* (remote_ratio): " +
                "Percentual de trabalho realizado remotamente\n"
                + " - *Localização da empresa* (company_location): " +
                "Localização geográfica da empresa\n");
    }

    public void calcularMediaSalarial() {
        OptionalDouble mediaSalarial = salaryService.calcularMediaSalarial();
        System.out.println("A média de salários de quem trabalha com Data Engineer nesse csv é: " +
                NumberFormat.getInstance(new Locale("pt", "BR")).format(mediaSalarial.orElse(0.0)) + " Dólares");
        System.out.println("_".repeat(70));
    }

    public void calcularMaiorSalario() {
        double maiorSalario = salaryService.calcularMaiorSalario();
        String valorFormatado = decimalFormat.format(maiorSalario);
        System.out.println("O maior salário anual registrado é de: US$ " + valorFormatado);
    }

    public void calcularFrequenciaCargos() {
        salaryService.calcularFrequenciaCargos();
    }

    public void calcularLocalComMaiorEMenorSalarioRemoto() {
        Map<String, Double> result = salaryService.getLocalMaiorEMenorSalarioRemoto();
        result.forEach((location, salary) -> System.out.println(location + " com salário médio de $" + salary));
    }

    public void mostrarLocalizacaoComMaioresSalarios(){
        salaryService.mostrarLocalizacaoComMaioresSalarios();
    }
}
