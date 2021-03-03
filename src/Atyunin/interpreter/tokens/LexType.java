package Atyunin.interpreter.tokens;

public enum LexType {

    NAME,

    //ключевые слова
    WHILE,
    IF,
    ELIF,
    ELSE,

    //литералы
    NUM,

    //операторы
    OP_ASSIGN,
    OP_MUL,
    OP_DIV,
    OP_ADD,
    OP_SUB,
    SEMICOLON,
    L_BRACKET,
    R_BRACKET,
    L_BRACE,
    R_BRACE
}
