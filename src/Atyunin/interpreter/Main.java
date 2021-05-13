package Atyunin.interpreter;

import Atyunin.interpreter.util.HashSetInt;
import Atyunin.interpreter.util.LinkedListInt;

import java.util.LinkedList;

public class Main {

    static public void main (String args[]) {

        TextDriver text = new TextDriver("SourceFiles/Example_1.txt");
        Lexer lexer = new Lexer();
        try {
            lexer.analysis(text.get_source());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lexer.printLexemeList();
        Parser parser = new Parser(lexer.getLexemes());
        parser.analysis();
        parser.AST.print();
        TranslatorRPN RPN = new TranslatorRPN(parser.getTree());
        RPN.translate();
        RPN.print();
        StackMachine machine = new StackMachine(RPN.getRPN());
        machine.run();
        machine.print();
    }

    /*public static void main(String[] args) {

        HashSetInt list = new HashSetInt();

        list.add(1);
        list.add(5);
        list.add(9);
        list.add(13);
        list.add(17);
        list.add(21);
        list.add(25);
        list.add(1);
        list.add(5);
        list.add(9);
        list.add(13);
        list.add(17);
        list.add(21);
        list.add(25);


        list.print();

        list.delete(5);

        list.print();
    }*/
}
