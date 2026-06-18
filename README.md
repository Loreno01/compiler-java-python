# Compilador de Java → Python

Trabalho acadêmico da disciplina de **Projeto - Compiladores** da **Universidade Feevale** com o objetivo de desenvolver um compilador que traduz um **subconjunto simplificado de Java** para **Python**.

**Autores:** Eduardo Lorscheiter, Lucca Bagesteiro, Loreno Enrique Ribeiro e Yolanda Colombo

---

## Sobre o projeto

Este compilador implementa o pipeline clássico de um tradutor de linguagens:

```
Arquivo .java → Análise Léxica → Análise Sintática → Ações Semânticas → Arquivo .py
```

A implementação é feita em **Java 17**, com interface gráfica (Swing) para uso manual e modo **headless** (linha de comando / testes automatizados).

---

## Funcionalidades suportadas

### Tipos de dados
- `int`
- `double`
- `String`
- `boolean`

### Estruturas de controle
- `if` / `else`
- `while`
- `for`
- `switch` / `case` / `default` / `break`

### Operadores e expressões
- Aritméticos: `+`, `-`, `*`, `/`, `%`
- Relacionais: `>`, `<`, `>=`, `<=`, `==`, `!=`
- Lógicos: `&&`, `||`, `!`
- Incremento/decremento: `++`, `--`
- Parênteses e precedência de operadores

### Outros recursos
- Declaração simples e múltipla de variáveis
- Atribuição com expressão
- Exibição de resultados (`System.out.println(...)`)
- Literais numéricos, strings e booleanos (`true` / `false`)
- Comentários de linha (`//`) e de bloco (`/* */`)
- Divisão inteira (`int / int`) traduzida corretamente para Python (`//`)

---

## Arquitetura

| Camada | Responsabilidade |
|---------------|------------------|
|  **Léxica**   | Reconhecimento de tokens (`searchNextToken`, `moveLookAhead`) |
| **Sintática** | Analisador recursivo descendente (`g()`, `main_method()`, `cmds()`, etc.) |
| **Semântica** | Tabela de símbolos, pilha semântica e geração de código Python |
| **Interface** | `JFileChooser` + `JOptionPane` (modo gráfico) |

### Geração de código

O compilador mantém uma pilha semântica que constrói expressões Python durante a análise. A tabela de símbolos (`HashMap<String, String>`) associa cada variável ao seu tipo Java, permitindo decisões corretas na tradução — por exemplo, usar `//` quando ambos os operandos são `int`, e `/` quando há envolvimento de `double`.

---

## Estrutura do projeto

```
compiler-java-python/
│
├── .vscode/
│   └── settings.json
│
├── files/
│   ├── Teste.java
│   └── teste.py
│
├── src/main/java/com/feevale/
│   ├── Compiler.java
│   ├── FileSelectionFilter.java
│   │
│   ├── exception/
│   │   ├── LexicalErrorException.java
│   │   ├── SemanticErrorException.java
│   │   └── SyntacticErrorException.java
│   │
│   └── stack/
│       ├── SemanticStack.java
│       └── SemanticStackNode.java
│
├── src/test/java/com/feevale/testing/
│   ├── ComparisonTestRunner.java
│   ├── TestCase.java
│   ├── TestCases.java
│   │
│   └── tests/
│       ├── cases/
│       └── generated/
│
├── pom.xml
└── README.md
```

> As pastas `tests/cases/` e `tests/generated/` são **geradas automaticamente** pelo runner de testes e não precisam ser versionadas.

---

## Como executar

### Execução normal

- Abra o projeto no VSCode
- Clique no arquivo `Compiler.java`
- Clique no botão `Run` do VSCode
- Selecione o arquivo `.java` de entrada
- Selecione o local de saída do arquivo `.py`

### Execução dos testes automáticos (headless)

- Abra o projeto no VSCode
- Clique no arquivo `ComparisonTestRunner.java`
- Clique no botão `Run` do VSCode

---

## Exemplo

**Arquivo Java:**

```java
public class Teste {
    public static void main(String[] args) {
        int x, c;
        int x = 10;
        int c = x / 3;
        System.out.println(c);
    }
}
```

**Arquivo Python gerado:**

```python
# Código Python Gerado Automaticamente

x = 0
c = 0
x = 10
c = x // 3
print(c)
```

**Execução:** ambos imprimem o resultado `3`.

---

## Suite de testes automatizados

O projeto inclui **165 testes automatizados** que comparam a saída (`stdout`) de programas Java originais com a saída dos programas Python gerados.

Para cada teste (`Test001` a `Test165`):

1. Gera o arquivo `.java` a partir de `TestCases.java`
2. Compila Java → Python com `Compiler.compileFile`
3. Compila e executa o Java original (`javac` + `java`)
4. Executa o Python gerado
5. Compara as saídas linha a linha

### Saída esperada

```
Executando 165 testes de comparacao Java x Python...

[ OK ] Test001 - println inteiro 0
[ OK ] Test002 - println inteiro 1
...
[ OK ] Test165 - atribuicao e println

========================================================================
RESUMO DOS TESTES DE COMPARACAO
========================================================================
Total:  165
Passou: 165
Falhou: 0
```

### Cobertura dos testes

| Grupo | Testes | Conteúdo |
|-------|--------|----------|
| Test001–010 | Print de inteiros | `println` com constantes |
| Test011–025 | Declarações | `int`, `double`, `String`, `boolean` |
| Test026–040 | Aritmética | `+`, `-`, `*`, `%`, precedência |
| Test041–055 | Atribuições | Variáveis e expressões |
| Test056–070 | Condicionais | `if`, `else`, comparações |
| Test071–085 | Laços | `while`, `for`, incremento |
| Test086–095 | Switch | `case`, `default`, `break` |
| Test096–105 | Cenários compostos | Combinações de estruturas |
| Test106–165 | Comportamento geral | Fluxos integrados e edge cases |

---

## Como adicionar um novo teste

Edite `src/test/java/com/feevale/testing/TestCases.java` e adicione um caso:

```java
cases.add(new TestCase(
    formatName(index++),
    "descricao do teste",
    "int x = 5;\nSystem.out.println(x);"
));
```

O corpo (`mainBody`) é o conteúdo do método `main`, **sem** a assinatura da classe. O runner gera o `.java` completo automaticamente.

---

## Limitações conhecidas

Este compilador **não** implementa Java completo. Entre as restrições:

- Apenas classes com método `main` no formato esperado pela gramática
- Sem orientação a objetos (classes de usuário, herança, métodos extras)
- Sem arrays, generics, exceções ou bibliotecas externas
- Sem `System.out.print` (apenas `println`)
- Subconjunto fixo de tipos e comandos descritos na gramática em `Compiler.java`

---

## Tecnologias utilizadas para a criação do projeto

- Java 17
- Swing (interface gráfica)
- Maven (build e execução)
- Python 3 (execução do código gerado)

---
