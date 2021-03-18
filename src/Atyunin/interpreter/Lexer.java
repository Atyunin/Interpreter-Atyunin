package Atyunin.interpreter;

import Atyunin.interpreter.tokens.LexType;
import Atyunin.interpreter.tokens.Lexeme;
import Atyunin.interpreter.tokens.Terminal;

import java.util.ArrayList;

public class Lexer {

    private ArrayList <Lexeme> lexeme_list;
    private ArrayList <Terminal> terminals;

    public Lexer () {

        lexeme_list = new ArrayList<Lexeme>();

        terminals = new ArrayList<Terminal>();

        terminals.add(new Terminal("while", LexType.WHILE,1));
        terminals.add(new Terminal("if", LexType.IF,1));
        terminals.add(new Terminal("elif", LexType.ELIF,1));
        terminals.add(new Terminal("else", LexType.ELSE,1));
        terminals.add(new Terminal("[a-zA-Z]+", LexType.NAME));
        terminals.add(new Terminal("0|([1-9][0-9]*)", LexType.NUM));
        terminals.add(new Terminal("[;]", LexType.SEMICOLON));
        terminals.add(new Terminal("[=]", LexType.OP_ASSIGN));
        terminals.add(new Terminal("[*]", LexType.OP_MUL));
        terminals.add(new Terminal("[/]", LexType.OP_DIV));
        terminals.add(new Terminal("[+]", LexType.OP_ADD));
        terminals.add(new Terminal("[-]", LexType.OP_SUB));
        terminals.add(new Terminal("[(]", LexType.L_BRACKET));
        terminals.add(new Terminal("[)]", LexType.R_BRACKET));
        terminals.add(new Terminal("[{]", LexType.L_BRACE));
        terminals.add(new Terminal("[}]", LexType.R_BRACE));
    }

    public void analysis (String source) throws Exception {

        double time_analysis = System.nanoTime();

        int position = 0;
        StringBuilder buffer = null;

        while (position != source.length()) {

            if ((source.charAt(position) == ' ' || source.charAt(position) == '\n') && buffer == null) {
                position++;
                continue;
            }

            if (buffer == null)
                buffer = new StringBuilder();

            buffer.append(source.charAt(position++));

            Terminal terminal = lookTerminal(buffer);

            if (terminal == null) {

                if (buffer.length() == 1)
                    throw new Exception();

                buffer.deleteCharAt(buffer.length() - 1);
                position--;
                terminal = lookTerminal(buffer);
                lexeme_list.add(new Lexeme(terminal.get_type(), buffer.toString()));

                buffer = null;
                continue;
            }
        }

        System.out.println("[Lexer] time analysis: " + (System.nanoTime() - time_analysis) / 1_000_000_000.0 + "ms");
    }

    private Terminal lookTerminal(StringBuilder string) {

        Terminal found_terminal = null;

        for (Terminal terminal: terminals) {

            if (terminal.matches(string)) {
                found_terminal = terminal.compare_priority(found_terminal);
            }
        }

        return found_terminal;
    }
    
    public void printLexemeList() {

        System.out.println("[Lexer] table lexemes: ");
        System.out.printf("%-20s%-20s\n", "Name lexeme", "Value");

        for (Lexeme lexeme: lexeme_list) {

            lexeme.println();
        }
    }
}
