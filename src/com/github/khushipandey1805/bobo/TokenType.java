package com.github.khushipandey1805.bobo;

enum TokenType{
    //single character tokens
    LEFT_PAREN, RIGHT_PAREN, 
    LEFT_BRACE, RIGHT_BRACE, //no square braces currently :(
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR, HASH,

    //one or two character tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    //Literals
    IDENTIFIER, STRING, NUMBER,

    //Keywords
    AND, CLASS, ELIF, ELSE, FALSE, FUNC, FOR, IF, NIL, OR, PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}