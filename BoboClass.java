package com.github.khushipandey1805.bobo;

import java.util.List;
import java.util.Map;

class BoboClass{
    final String name;
    BoboClass(String name){
        this.name=name;
    }
    @Override
    public String toString(){
        return name;
    }
}