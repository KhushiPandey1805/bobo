package com.github.khushipandey1805.bobo;

import java.util.HashMap;
import java.util.Map;

class BoboInstance{
    private BoboClass klass;
    private final Map<String, Object> fields=new HashMap<>();

    BoboInstance(BoboClass klass){
        this.klass=klass;
    }

    Object get(Token name){
        if(fields.containsKey(name.lexeme)){
            return fields.get(name.lexeme);
        }
        throw new RuntimeError(name, "No property named '"+name.lexeme+"' gang!");
    }

    @Override
    public String toString(){
        return klass.name+" instance";
    }
}