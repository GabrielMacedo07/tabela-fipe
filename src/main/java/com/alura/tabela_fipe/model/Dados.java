package com.alura.tabela_fipe.model;

public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return "Código: " + codigo +
                " Descrição: " + nome;
    }
}
