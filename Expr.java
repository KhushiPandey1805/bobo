package com.github.khushipandey1805.bobo;

import java.util.List;

abstract class Expr {
    static class Binary extends Expr {
    Binary(Expr left, Token operate, Expr right){
    this.left=left;
    this.operate=operate;
    this.right=right;
    }

    final Expr left;
    final Token operate;
    final Expr right;
    }
    static class Grouping extends Expr {
    Grouping(Expr expression){
    this.expression=expression;
    }

    final Expr expression;
    }
    static class Literal extends Expr {
    Literal(Object value){
    this.value=value;
    }

    final Object value;
    }
    static class Unary extends Expr {
    Unary(Token operate, Expr right){
    this.operate=operate;
    this.right=right;
    }

    final Token operate;
    final Expr right;
    }
}
