package com.github.khushipandey1805.bobo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.khushipandey1805.bobo.TokenType.*;

class Scanner{
    private final String source;
    private final List<Token> tokens=new ArrayList<>();
    private int start=0;
    private int current=0;
    private int line=1;

    //constructor
    Scanner(String source){
        this.source=source;
    }

    List<Token> scanTokens(){
        while(!isAtEnd()){
            start=current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken(){
        char c=advance();
        switch(c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
            case '!': addToken(match('=')? BANG_EQUAL: BANG); break;
            case '=': addToken(match('=')? EQUAL_EQUAL: EQUAL); break;
            case '<': addToken(match('=')? LESS_EQUAL: LESS); break;
            case '>': addToken(match('=')? GREATER_EQUAL: GREATER); break;
            case '#': while(peek()!='\n' && !isAtEnd()) advance(); break; //comment till end of line
            case ' ': //ignore whitespace
            case '\r': //ignore carriage return
            case '\t': break; //ignore tab
            case '\n': line++; break;
            case '"': string(); break;
            default: 
                if(isDigit(c)) //any digit
                    number();
                else 
                    Bobo.error(line, "Unexpected character."); 
                break;
        }
    }

    private void number(){
        while(isDigit(peek()))
            advance();
        if(peek()=='.' && isDigit(peekNext())){ // we dont support 123., currently dont support .123
            advance();
            while(isDigit(peek()))
                advance();
        }
        addToken(NUMBER, Double.parseDouble(source.susbstring(start,current))); //future notes: do the parsing ourselves instead of using java parsing methods
    }

    private void string(){ //escape characters not supported atp, will add later maybe
        while(peek()!='"' && !isAtEnd()){
            if(peek()=='\n') //multi line strings allowed :o (because easier lmao, might change later)
                line++;
            advance();
        }
        if(!isAtEnd()){
            Bobo.error(line, "Unterminated string");
            return;
        }
        advance(); //to "eat up" the closing "
        String value=source.substring(start+1, current-1); //only the text inside double quotes
        addToken(STRING, value);
    }

    private boolean match(char expected){ //is next character what i want it to be?
        if(isAtEnd()) return false;
        if(source.charAt(current)!=expected)
            return false;
        current++;
        return true;
    }

    private char peek(){ //immediate next character, just peeking no advancing
        if(isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private char peekNext(){ //the character after the next one; again, no advancing
        if(current+1>=source.length())
            return '\0';
        return source.charAt(current+1);
    }

    private boolean isDigit(char c){ //overriding the standard function isDigit(), to reduce complexities of unwanted digits
        if(c>='0' && c<='9')
            return true;
        return false;
    }

    private boolean isAtEnd(){ //at the eof?
        return current>=source.length();
    }

    private char advance(){ //eat up the characters, move on
        return source.charAt(current++);
    }
    
    private void addToken(TokenType type){
        addToken(type,null);
    }

    private void addToken(TokenType type, Object literal){
        String text=source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}