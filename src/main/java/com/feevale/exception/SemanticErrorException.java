/*
**===========================================================================
**  @file    SemanticErrorException.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro 
**  @class   Projeto - Compiladores
**  @date    Junho/2026
**  @version 1.0
**  @brief   Exceção para erros semânticos (3ª validação do compilador)
**===========================================================================
*/

package com.feevale.exception;

public class SemanticErrorException extends Exception {

    private static final long serialVersionUID = -2346384470483785588L;

    public SemanticErrorException() {
        super("Erro semântico!");
    }

    public SemanticErrorException(String message) {
        super(message);
    }

    public SemanticErrorException(Throwable t) {
        super(t);
    }
}
