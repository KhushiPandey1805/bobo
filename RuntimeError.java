package com.github.khushipandey1805.bobo;

class RuntimeError extends RuntimeException {
    final Token token;
    RuntimeError(Token token, String message){
        super(message);
        this.token=token;
    }
}