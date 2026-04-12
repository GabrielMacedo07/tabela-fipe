package com.alura.tabela_fipe.principal;

import com.alura.tabela_fipe.model.Dados;
import com.alura.tabela_fipe.model.Modelos;
import com.alura.tabela_fipe.model.Veiculo;
import com.alura.tabela_fipe.service.ConsumoApi;
import com.alura.tabela_fipe.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";


    public void exibeMenu(){
        String menu = """
                ***OPÇÕES***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consulta:
                """;
        System.out.print(menu);
        String endereco = "";
        String opcao = sc.nextLine().toLowerCase();

        if(opcao.toUpperCase().contains("CARR")){
            endereco = ENDERECO + "/carros/marcas";
        } else if (opcao.toUpperCase().contains("MOTO")) {
            endereco = ENDERECO + "/motos/marcas";
        } else if (opcao.toUpperCase().contains("CAMINH")) {
            endereco = ENDERECO + "/caminhoes/marcas";
        } else {
            System.out.println("digite um opção válida");
        }

        var json = consumoApi.obterDados(endereco);

        List<Dados> listaMarca = conversor.obterLista(json, Dados.class);
        listaMarca.stream()
                .map(Dados::toString)
                .forEach(System.out::println);

        System.out.println("Informe o código da marca: ");
        int codigoMarca = sc.nextInt();
        sc.nextLine();

        endereco = endereco + "/" + codigoMarca +"/modelos";
        json = consumoApi.obterDados(endereco);

        System.out.println("Modelos dessa marca: ");
        var modeloLista = conversor.obterDados(json, Modelos.class);
        modeloLista.modelos().stream()
                        .map(Dados::toString)
                                .forEach(System.out::println);

        System.out.println("Digite um trecho do nome do modelo que queira pesquisar: ");
        var trecho = sc.nextLine().toUpperCase();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(v -> v.nome().toUpperCase().contains(trecho.toUpperCase()))
                .collect(Collectors.toList());
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o codígo do modelo para a busca de valores e anos disponíveis: ");
        var codigoModelo = sc.nextLine();

        endereco = endereco + "/" + codigoModelo +"/anos";
        json = consumoApi.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++){
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }
        System.out.println("Todos os veículos filtrados por ano: ");
        veiculos.forEach(System.out::println);
    }

}
