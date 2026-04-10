package com.alura.tabela_fipe.principal;

import com.alura.tabela_fipe.model.DadosModelos;
import com.alura.tabela_fipe.model.DadosVeiculo;
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
                """;
        System.out.println(menu);

        Map<String, String> tipos = new HashMap<>();
        tipos.put("carro", "carros");
        tipos.put("moto", "motos");
        tipos.put("caminhao", "caminhoes");

        System.out.println("Digite uma das opções para consultar valores");
        String opcao = sc.nextLine().toLowerCase();
        String tipoApi = tipos.get(opcao);

        var json = "";
        if (tipoApi != null){
            json = consumoApi.obterDados(ENDERECO +tipoApi+"/marcas");
        } else {
            System.out.println("Opção inválida");
        }
        List<DadosVeiculo> listaVeiculo = conversor.obterLista(json, DadosVeiculo.class);
        List<Veiculo> veiculos = listaVeiculo.stream()
                .map(Veiculo::new).toList();
        veiculos.forEach(System.out::println);

        System.out.println("Informe o código da marca: ");
        int codigo = sc.nextInt();

        json = consumoApi.obterDados(ENDERECO +tipoApi+"/marcas/"+codigo+"/modelos");

        //tinhamos um objeto modelo que dentro dele estava a lista, peguei esse objeto fiz a desserialização e depois pude extrair a lista.
        DadosModelos dadosModelos = conversor.obterDados(json, DadosModelos.class);

        List<DadosVeiculo> listaModelos = dadosModelos.modelos();
        List<Veiculo> modelos = listaModelos.stream()
                .map(Veiculo::new).toList();
        modelos.forEach(System.out::println);
        sc.nextLine();

        System.out.println("Digite um trecho do nome do modelo que queira pesquisar: ");
        var trecho = sc.nextLine().toUpperCase();

        List<Veiculo> modeloEspecifico = modelos.stream()
                .filter(v -> v.getNome().toUpperCase().contains(trecho.toUpperCase()))
                .toList();
        modeloEspecifico.forEach(System.out::println);



    }

}
