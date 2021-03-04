package Atyunin.interpreter;

import Atyunin.interpreter.tokens.LexType;
import Atyunin.interpreter.tokens.Lexeme;
import Atyunin.interpreter.tokens.Terminal;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        while (position != source.length()) {

            if (source.charAt(position) == ' ' || source.charAt(position) == '\n') {
                position++;
                continue;
            }

            StringBuilder buffer = new StringBuilder();

            while (position != source.length()) {

                buffer.append(source.charAt(position));
                position++;

                Terminal terminal = look_terminal(buffer);

                if (terminal == null) {

                    buffer.deleteCharAt(buffer.length() - 1);
                    position--;
                    terminal = look_terminal(buffer);
                    lexeme_list.add(new Lexeme(terminal.get_type(), buffer.toString()));

                    buffer = null;
                    break;
                }
            }

            if (buffer != null)
                throw new Exception();

        }

        System.out.println("[Lexer] time analysis: " + (System.nanoTime() - time_analysis) / 1_000_000_000.0 + "ms");
    }

    private Terminal look_terminal (StringBuilder string) {

        Terminal found_terminal = null;

        for (Terminal terminal: terminals) {

            if (terminal.matches(string)) {
                found_terminal = terminal.compare_priority(found_terminal);
            }
        }

        return found_terminal;
    }
    
    public void print_lexeme_list() {

        System.out.println("[Lexer] table lexemes: ");
        System.out.printf("%-20s%-20s\n", "Name lexeme", "Value");

        for (Lexeme lexeme: lexeme_list) {

            lexeme.println();
        }
    }
}
