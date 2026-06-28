package com.github.khushipandey1805.bobo;

import java.util.List;

interface BoboCallable{
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}