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
    private static final Map<String, TokenType> keywords;

    static{
        keywords=new HashMap<>(); //can change stuff for fun!!
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("elif", ELIF);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("func", FUNC);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

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
            case '#': while(peek()!='\n' && !isAtEnd()) advance(); break; //comment till end of line, add multiline comments some day
            case ' ': //ignore whitespace
            case '\r': //ignore carriage return
            case '\t': break; //ignore tab
            case '\n': line++; break;
            case '"': string(); break;
            default: 
                if(isDigit(c)) //any digit
                    number();
                else if(isAlpha(c)) //any letter
                    identifier(); //we assume its an identifier if it starts with a letter
                else 
                    Bobo.error(line, "Unexpected character."); 
                break;
        }
    }

    private void identifier(){
        while(isAlphaNumeric(peek()))
            advance();
        String text=source.substring(start, current);
        TokenType type=keywords.get(text);
        if(type==null)
            type=IDENTIFIER;
        addToken(type);
    }

    private void number(){
        while(isDigit(peek()))
            advance();
        if(peek()=='.' && isDigit(peekNext())){ // we dont support 123., currently dont support .123
            advance();
            while(isDigit(peek()))
                advance();
        }
        addToken(NUMBER, Double.parseDouble(source.substring(start,current))); //future notes: do the parsing ourselves instead of using java parsing methods
    }

    private void string(){ //escape characters not supported atp, will add later maybe
        while(peek()!='"' && !isAtEnd()){
            if(peek()=='\n') //multi line strings allowed :o (because easier lmao, might change later)
                line++;
            advance();
        }
        if(isAtEnd()){
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

    private boolean isAlpha(char c){
        return((c>='a' && c<='z') ||(c>='A' && c<='Z') || (c=='_') );
    }

    private boolean isAlphaNumeric(char c){
        return isAlpha(c) || isDigit(c);
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