package com.jonas.demoparkapi.web.exception;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter @ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL) // anotação para não incluir campos nulos
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest resquest, HttpStatus status, String message) {
        this.path = resquest.getRequestURI(); // pega o caminho da requisição
        this.method = resquest.getMethod(); // pega o método da requisição
        this.status = status.value(); // pega o status da requisição
        this.statusText = status.getReasonPhrase(); // pega o texto do status da requisição
        this.message = message; // pega a mensagem de erro
    }

    public ErrorMessage(HttpServletRequest resquest, HttpStatus status, String message, BindingResult result) {
        this.path = resquest.getRequestURI(); // pega o caminho da requisição
        this.method = resquest.getMethod(); // pega o método da requisição
        this.status = status.value(); // pega o status da requisição
        this.statusText = status.getReasonPhrase(); // pega o texto do status da requisição
        this.message = message; // pega a mensagem de erro
        addErrors(result); // chama o método addErrors passando o resultado da validação
    }

    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();

        for(FieldError fieldError : result.getFieldErrors()) { // percorre a lista de erros
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage()); // adiciona o campo e a mensagem de erro no map
        }
    }
}
