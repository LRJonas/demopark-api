package com.jonas.demoparkapi.web.exception;

import com.jonas.demoparkapi.exception.EntityNotFoundException;
import com.jonas.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j // anotação que adiciona um logger privado estático à classe
@RestControllerAdvice // anotação que indica que essa classe é um controlador de exceções
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class) // anotação que indica que esse método é um tratador de exceções
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex,
                                                                        HttpServletRequest request) {
        log.error("Erro na API",ex);

        return ResponseEntity.
                status(HttpStatus.NOT_FOUND). // retorna o status 422
                        contentType(MediaType.APPLICATION_JSON). // retorna o tipo de conteúdo como JSON
                        body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage())); // result foi retirado pois ná há validação de campo
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // anotação que indica que esse método é um tratador de exceções
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Erro na API",ex);

        return ResponseEntity.
                status(HttpStatus.UNPROCESSABLE_ENTITY). // retorna o status 422
                contentType(MediaType.APPLICATION_JSON). // retorna o tipo de conteúdo como JSON
                body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) Inválidos", result));
    }
    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class, CodigoUniqueViolationException.class}) // anotação para indicar a exceção que foi criada para o erro de nome de usuário já existente
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException ex,
                                                                        HttpServletRequest request) {
        log.error("Erro na API",ex);

        return ResponseEntity.
                status(HttpStatus.CONFLICT). // retorna o status 409
                contentType(MediaType.APPLICATION_JSON).
                body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage())); // result foi retirado pois ná há validação de campo
    }
}
