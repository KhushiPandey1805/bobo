package com.github.khushipandey1805.bobo;

import java.util.List;
import java.util.Map;

class BoboClass implements BoboCallable{
    final String name;
    BoboClass(String name){
        this.name=name;
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