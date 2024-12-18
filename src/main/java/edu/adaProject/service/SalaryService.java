package edu.adaProject.service;

import edu.adaProject.model.DevSalary;

import java.text.NumberFormat;
import java.text.DecimalFormat;
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

    // Luana Méro //
    public void mostrarLocalizacaoComMaioresSalarios() {

        Map<String, Double> salarioMedioPorLocalizacao = devSalaries.stream()
                .collect(Collectors.groupingBy(
                        DevSalary::company_location,
                        Collectors.averagingDouble(DevSalary::salary_in_usd)
                ));


        Optional<Map.Entry<String, Double>> localizacaoComMaiorSalario = salarioMedioPorLocalizacao.entrySet().stream()
                .max(Map.Entry.comparingByValue());


        localizacaoComMaiorSalario.ifPresent(entry -> {
            String nomePais = paises.getOrDefault(entry.getKey(), "Desconhecido");
            System.out.println("A localização com maior salário médio é: " +
                    entry.getKey() + " (" + nomePais + ")" +
                    " com um salário médio de " +
                    NumberFormat.getInstance(new Locale("pt", "BR")).format(entry.getValue()) + " Dólares");
        });
    }

    //Nathália
    public String localizacaoComMaiorNumeroDeSalarios() {
        Map<String, Long> salariosPorLocalizacao = devSalaries.stream()
                .collect(Collectors.groupingBy(
                        DevSalary::company_location,
                        Collectors.counting()
                ));

        Map.Entry<String, Long> localizacaoComMaiorSalarios = salariosPorLocalizacao.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (localizacaoComMaiorSalarios == null) {
            return "Nenhuma localização encontrada.";
        }

        return "Localização com o maior número de salários reportados: " +
                localizacaoComMaiorSalarios.getKey() + " | Número de salários: " + localizacaoComMaiorSalarios.getValue();
    }

    /**
     * Catharia De Zagiacomo
     * @return String construída com a lista ordenada dos cargos com maiores salarios.
     */
    public String cargosComMaioresSalarios() {
        /**
         * Configuração de formatador numérico
         * Sem valores decimais e no formato brasileiro
         */
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMaximumFractionDigits(0);
        /**
         * Nova String para construção do retorno da função
         */
        StringBuilder top5Cargos = new StringBuilder();
        /**
         * Contador para os top 5 salários
         * TODO: Adicionar argumento na função para que o usuário selecione quantos salários retornar
         */
        int contador = 0;
        /**
         * Base de dados com informações de Posição e Salário
         */
        Map<String, Double> avgSalaryByPositions = devSalaries.stream()
                .collect(Collectors.groupingBy(
                    DevSalary::job_title,
                    Collectors.averagingInt(DevSalary::salary_in_usd)
                ));

        /**
         * Cria um novo "entrySet" com a ordenação de salário e retona através do "collect"
         */
        Map<String, Double> sortedAvgSalaryByPositions = avgSalaryByPositions.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        /**
         * Seleciona e retorna os top 5 salários
         */
        for (Map.Entry<String, Double> entry : sortedAvgSalaryByPositions.entrySet()) {
            if (contador >= 5) break;
            top5Cargos.append(entry.getKey())
                    .append(": ")
                    .append(nf.format(entry.getValue()))
                    .append(" Dólares")
                    .append("\n");
            contador++;
        }

        String result = top5Cargos.toString();

        return "Os cargos com os maiores salários são: \n" + result;
    }
}
