/*
**===========================================================================
**  @file    SyntacticErrorException.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro 
**  @class   Projeto - Compiladores
**  @date    Junho/2026
**  @version 1.0
**  @brief   Exceção para erros sintáticos (2ª validação do compilador)
**===========================================================================
*/

package com.feevale.exception;

public class SyntacticErrorException extends Exception {
 
    private static final long serialVersionUID = -2346384470483785588L;
 
    public SyntacticErrorException() {
        super("Erro sintático!");
    }
 
    public SyntacticErrorException(String message) {
        super(message);
    }
 
    public SyntacticErrorException(Throwable t) {
        super(t);
    }
}
