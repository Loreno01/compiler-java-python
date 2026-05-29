/*
**===========================================================================
**  @file    LexicalErrorException.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro 
**  @class   Projeto - Compiladores
**  @date    Junho/2026
**  @version 1.0
**  @brief   Exceção para erros léxicos (1ª validação do compilador)
**===========================================================================
*/

package com.feevale.exception;

public class LexicalErrorException extends Exception {

    private static final long serialVersionUID = -2346384470483785588L;

    public LexicalErrorException() {
        super("Erro léxico!");
    }

    public LexicalErrorException(String message) {
        super(message);
    }

    public LexicalErrorException(Throwable t) {
        super(t);
    }
}
