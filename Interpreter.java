package com.github.khushipandey1805.bobo;

class Interpreter implements Expr.Visitor<Object>{
    @Override
    public Object visitLiteralExpr(Expr.Literal expr){
        return expr.value;
    }
    @Override
    public Object visitUnaryExpr(Expr.Unary expr){
        Object right=evaluate(expr.right);
        switch(expr.operator.type){
            case MINUS:
                return -(double)right;
            case BANG:
                return !isTruthy(right);
        }
        return null;
    }
    private boolean isTruthy(Object object){
        if(object==null) //null is falsey
            return false;
        if(object instanceof Boolean) //false is falsey
            return (boolean)object;
        return true; //everything else is truthy, even 0! (too complicatd rn :((())))
    }
    private boolean isEqual(Object a, Object b){
        if(a==null && b==null)
            return true;
        if(a==null)
            return false;
        return a.equals(b);
    }
    @Override
    public Object visitGroupingExpr(Expr.Grouping expr){
        return evaluate(expr.expression);
    }
    private Object evaluate(Expr expr){
        return expr.accept(this);
    }
    @Override
    public Object visitBinaryExpr(Expr.Binary expr){
        Object left=evaluate(expr.left);
        Object right=evaluate(expr.right);
        switch(expr.operator.type){
            case GREATER:
                return (double)left>(double)right;
            case GREATER_EQUAL:
                return (double)left>=(double)right;
            case LESS:
                return (double)left<(double)right;
            case LESS_EQUAL:
                return (double)left<=(double)right;
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
            case MINUS:
                return (double)left-(double)right;
            case PLUS:
                if(left instanceof Double && right instanceof Double){
                    return (double)left+(double)right;
                }
                if(left instanceof String && right instanceof String){
                    return (String)left+(String)right;
                }
                break;
            case SLASH:
                return (double)left/(double)right;
            case STAR:
                return (double)left*(double)right;
        }
        return null;
    }
}