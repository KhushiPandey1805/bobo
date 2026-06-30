package com.github.khushipandey1805.bobo;

class Return extends RuntimeException{
    final Object value;

    Return(Object value){
        super(null, null, false, false);
        this.value=value;
    }
}