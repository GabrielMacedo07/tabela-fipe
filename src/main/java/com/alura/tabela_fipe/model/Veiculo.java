package com.alura.tabela_fipe.model;

public class Veiculo {
    private Integer codigo;
    private String nome;

    public Veiculo(DadosVeiculo dadosVeiculo){
        this.codigo = dadosVeiculo.Cod();
        this.nome = dadosVeiculo.Descricao();
    }

    public void modelos(String json){

    }
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                " Descrição: " + nome;
    }
}
