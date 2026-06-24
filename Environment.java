package com.github.khushipandey1805.bobo;

import java.util.HashMap;
import java.util.Map;

class Environment{
    private final Map<String, Object> values=new HashMap<>();
    Object get(Token name){
        if(values.containsKey(name.lexeme)){
            return values.get(name.lexeme);
        }
        throw new RuntimeError(name, "You didn't define variable '"+name.lexeme+"' :(((");
    }
    void define(String name, Object value){
        values.put(name,value);
    }
}