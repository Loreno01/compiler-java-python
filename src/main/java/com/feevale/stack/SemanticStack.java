/*
**===========================================================================
**  @file    SemanticStack.java
**  @author  Eduardo Lorscheiter, Lucca Bagesteiro, Loreno Enrique Ribeiro e Yolanda Colombo 
**  @class   Projeto - Compiladores
**  @date    Junho/2026
**  @version 1.0
**  @brief   Pilha semântica do compilador
**===========================================================================
*/

package com.feevale.stack;

import java.util.Stack;

public class SemanticStack {
    private Stack<SemanticStackNode> stack;

    public SemanticStack() {
        stack = new Stack<SemanticStackNode>();
    }

    public SemanticStackNode pop() {
        SemanticStackNode semanticStackNode;
        semanticStackNode = (SemanticStackNode) stack.pop();

        return semanticStackNode;
    }

    public SemanticStackNode push(String c, int r) {
        return push(c, r, null);
    }

    public SemanticStackNode push(String c, int r, String type) {
        SemanticStackNode semanticStackNode = new SemanticStackNode(c, r, type);
        stack.push(semanticStackNode);

        return semanticStackNode;
    }

    public SemanticStackNode push(SemanticStackNode semanticStackNode) {
        stack.push(semanticStackNode);

        return semanticStackNode;
    }
}