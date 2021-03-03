package Atyunin.interpreter;

public class Main {

    static public void main (String args[]) {

        TextDriver text = new TextDriver("SourceFiles/Example_1.txt");
        Lexer lexer = new Lexer();
        lexer.analysis(text.get_source());
        lexer.print_lex();
    }
}
