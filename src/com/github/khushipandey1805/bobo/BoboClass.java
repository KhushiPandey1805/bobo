package com.github.khushipandey1805.bobo;

import java.util.List;
import java.util.Map;

class BoboClass implements BoboCallable{
    final String name;
    private final Map<String, BoboFunction> methods;
    BoboClass(String name, Map<String, BoboFunction> methods){
        this.name=name;
        this.methods=methods;
    }
    BoboFunction findMethod(String name){
        if(methods.containsKey(name))
            return methods.get(name);
        return null;
    }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public Object call(Interpreter interpreter, List<Object> arguments){
        BoboInstance instance=new BoboInstance(this);
        return instance;
    }
    @Override
    public int arity(){
        return 0;
    }
}