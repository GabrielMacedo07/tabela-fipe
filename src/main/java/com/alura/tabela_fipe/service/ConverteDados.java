package com.alura.tabela_fipe.service;

import tools.jackson.databind.ObjectMapper;

import java.util.List;

public class ConverteDados implements IConverteDados{

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T>  List<T> obterLista(String json, Class<T> classe) {
        try {
            return mapper.readValue(
                    json,
                    mapper.getTypeFactory().constructCollectionType(List.class, classe)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }
}
