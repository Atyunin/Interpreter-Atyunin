package Atyunin.interpreter;

import Atyunin.interpreter.tokens.LexType;
import Atyunin.interpreter.tokens.Lexeme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import static Atyunin.interpreter.tokens.LexType.*;
import static Atyunin.interpreter.tokens.LexType.OP_NOT_EQUAL;

public class StackMachine {

    private ArrayList <Lexeme> RPN;
    private int index;

    private HashMap <String, Integer> variables;
    private LinkedList <Lexeme> stack;

    public StackMachine (ArrayList <Lexeme> RPN) {

        this.RPN = RPN;
        this.variables = new HashMap<>();
        this.stack = new LinkedList<>();
    }

    public void run () {

        for (index = 0; index < RPN.size(); index++) {

            switch (RPN.get(index).getType()) {
                case OP_ADD:
                    stack.addFirst(sum(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_SUB:
                    stack.addFirst(sub(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_MUL:
                    stack.addFirst(mul(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_DIV:
                    stack.addFirst(div(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_ASSIGN:
                    assign(stack.removeFirst(), stack.removeFirst());
                    break;
                case OP_EQUAL:
                    stack.addFirst(equal(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_NOT_EQUAL:
                    stack.addFirst(notEqual(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_MORE:
                    stack.addFirst(more(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_LESS:
                    stack.addFirst(less(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_MORE_EQUAL:
                    stack.addFirst(moreEqual(stack.removeFirst(), stack.removeFirst()));
                    break;
                case OP_LESS_EQUAL:
                    stack.addFirst(lessEqual(stack.removeFirst(), stack.removeFirst()));
                    break;
                case IF:
                    ifConst(stack.removeFirst(), stack.removeFirst());
                    break;
                case ELIF:
                    ifConst(stack.removeFirst(), stack.removeFirst());
                    break;
                case WHILE:
                    whileConst(stack.removeFirst(), stack.removeFirst());
                    break;
                case JMP:
                    index = Integer.parseInt(stack.removeFirst().get_value()) - 1;
                    break;
                default:
                    stack.addFirst(RPN.get(index));
            }
        }
    }

    private void whileConst(Lexeme jmp, Lexeme bool) {

        int jmp_value = Integer.parseInt(jmp.get_value());
        boolean bool_value = Boolean.parseBoolean(bool.get_value());

        if (!bool_value)
            index = jmp_value - 1;
    }

    private Lexeme notEqual(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value != a_value));
    }

    private Lexeme lessEqual(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value <= a_value));
    }

    private Lexeme moreEqual(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value >= a_value));
    }

    private Lexeme less(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value < a_value));
    }

    private Lexeme more(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value > a_value));
    }

    private void ifConst(Lexeme jmp, Lexeme bool) {

        int jmp_value = Integer.parseInt(jmp.get_value());
        boolean bool_value = Boolean.parseBoolean(bool.get_value());

        if (!bool_value)
            index = jmp_value - 1;
    }

    private Lexeme equal(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(BOOLEAN, Boolean.toString(b_value == a_value));
    }

    private Lexeme div(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(NUM, Integer.toString(b_value / a_value));
    }

    private Lexeme mul(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(NUM, Integer.toString(b_value * a_value));
    }

    private Lexeme sub(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(NUM, Integer.toString(b_value - a_value));
    }

    private void assign(Lexeme a, Lexeme b) {

        variables.put(b.get_value(), Integer.parseInt(a.get_value()));
    }

    private Lexeme sum(Lexeme a, Lexeme b) {

        int a_value = a.getType() == NAME ? variables.get(a.get_value()) : Integer.parseInt(a.get_value());
        int b_value = b.getType() == NAME ? variables.get(b.get_value()) : Integer.parseInt(b.get_value());

        return new Lexeme(NUM, Integer.toString(a_value+b_value));
    }

    private boolean isOp(Lexeme lexeme) {

        LexType type = lexeme.getType();

        return type == OP_ADD || type == OP_SUB || type == OP_DIV || type == OP_MUL ||
                type == OP_EQUAL || type == OP_LESS || type == OP_LESS_EQUAL || type == OP_MORE || type == OP_MORE_EQUAL || type == OP_NOT_EQUAL || type == OP_ASSIGN;
    }

    public void print () {

        Set <String> keys = variables.keySet();

        for (String name: keys) {

            System.out.println(name + " " + variables.get(name));
        }
    }
}
