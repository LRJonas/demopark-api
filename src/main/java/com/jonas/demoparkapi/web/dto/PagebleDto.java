package com.jonas.demoparkapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class PagebleDto { //classe para representar a paginação

    private List content = new ArrayList<>();

    private boolean first;
    private boolean last;

    @JsonProperty("page") //anotação para mudar o nome do atributo no JSON
    private int number;

    private int size;

    @JsonProperty("pageElements")
    private int numberOfElements;
    private int totalPages;
    private long totalElements;
}
