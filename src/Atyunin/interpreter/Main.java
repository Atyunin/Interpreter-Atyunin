package Atyunin.interpreter;

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
    }
}
