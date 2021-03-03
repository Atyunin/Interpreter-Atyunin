package Atyunin.interpreter;

import Atyunin.interpreter.tokens.LexType;
import Atyunin.interpreter.tokens.Lexeme;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    ArrayList <Lexeme> code;

    ArrayList <Pattern> pattern;
    ArrayList <LexType> lexType;

    Lexer () {

        code = new ArrayList<Lexeme>();
        pattern = new ArrayList<Pattern>();
        lexType = new ArrayList<LexType>();

        pattern.add(Pattern.compile("while"));
        lexType.add(LexType.WHILE);

        pattern.add(Pattern.compile("if"));
        lexType.add(LexType.IF);

        pattern.add(Pattern.compile("elif"));
        lexType.add(LexType.ELIF);

        pattern.add(Pattern.compile("else"));
        lexType.add(LexType.ELSE);

        pattern.add(Pattern.compile("[a-zA-Z]+"));
        lexType.add(LexType.NAME);

        pattern.add(Pattern.compile("0|([1-9][0-9]*)"));
        lexType.add(LexType.NUM);

        pattern.add(Pattern.compile("[;]"));
        lexType.add(LexType.SEMICOLON);

        pattern.add(Pattern.compile("[=]"));
        lexType.add(LexType.OP_ASSIGN);

        pattern.add(Pattern.compile("[*]"));
        lexType.add(LexType.OP_MUL);

        pattern.add(Pattern.compile("[/]"));
        lexType.add(LexType.OP_DIV);

        pattern.add(Pattern.compile("[+]"));
        lexType.add(LexType.OP_ADD);

        pattern.add(Pattern.compile("[-]"));
        lexType.add(LexType.OP_SUB);

        pattern.add(Pattern.compile("[(]"));
        lexType.add(LexType.L_BRACKET);

        pattern.add(Pattern.compile("[)]"));
        lexType.add(LexType.R_BRACKET);

        pattern.add(Pattern.compile("[{]"));
        lexType.add(LexType.L_BRACE);

        pattern.add(Pattern.compile("[}]"));
        lexType.add(LexType.R_BRACE);
    }

    public void analysis (String source) {

        double time_analysis = System.nanoTime();

        int position = 0;

        Matcher matcher;

        do {

            while (source.charAt(position) == ' ' || source.charAt(position) == '\n') {
                position++;
                continue;
            }

            for(int i = 0; i < pattern.size(); i++) {
                
                matcher = pattern.get(i).matcher(source);
                
                if (matcher.find(position) && matcher.start() == position) {

                    position = matcher.end();

                    if (lexType.get(i) == LexType.NAME)
                        code.add(Lexeme.create_lexeme(lexType.get(i), source.substring(matcher.start(), matcher.end())));
                    else if (lexType.get(i) == LexType.NUM)
                        code.add(Lexeme.create_lexeme(lexType.get(i), Integer.parseInt(source.substring(matcher.start(), matcher.end()))));
                    else
                        code.add(Lexeme.create_lexeme(lexType.get(i)));

                    break;
                }
            }
        } while (position != source.length() - 1);

        System.out.println("[Lexer] time analysis: " + (System.nanoTime() - time_analysis) / 1_000_000_000.0 + "ms");
    }
    
    public void print_lex () {

        System.out.println("[Lexer] table lexemes: ");
        System.out.printf("%-20s%-20s\n", "Name lexeme", "Value");

        for (Lexeme lexeme: code) {

            System.out.printf("%-20s%-20s\n", lexeme.get_type(), lexeme.get_value() == null ? " " : lexeme.get_value().toString());

        }
    }
}
