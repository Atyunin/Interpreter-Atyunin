package Atyunin.interpreter;

import static Atyunin.interpreter.tokens.LexType.*;
import static Atyunin.interpreter.tree.NodeType.*;

import Atyunin.interpreter.tokens.LexType;
import Atyunin.interpreter.tokens.Lexeme;
import Atyunin.interpreter.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Lexeme> lexemes;
    private int position;

    private TreeNode AST;

    public Parser (List <Lexeme> lexemes) {

        this.lexemes = lexemes;
        this.position = 0;
    }

    public void analysis () {

        while (currentLexeme() != END) {

            try {

                languageToken();
            } catch (Exception e) {

                System.out.println("Синтаксическая ошибка: " + position + currentLexeme());
                e.printStackTrace();
                break;
            }
        }
    }

    private void languageToken() throws Exception {

        if (currentLexeme() == NAME) {

            assign();
        } else if (currentLexeme() == WHILE) {

            whileConst();
        } else if (currentLexeme() == IF) {

            ifConst();

            while (currentLexeme() == ELIF)
                elifConst();

            if (currentLexeme() == ELSE)
                elseConst();
        } else {

            throw new Exception();
        }
    }

    private void elseConst() throws Exception {

        LexCheck(ELSE);
        block();
    }

    private void elifConst() throws Exception {

        LexCheck(ELIF);
        LexCheck(L_BRACKET);
        expression();
        LexCheck(R_BRACKET);
        block();
    }

    private void ifConst() throws Exception {

        LexCheck(IF);
        LexCheck(L_BRACKET);
        expression();
        LexCheck(R_BRACKET);
        block();
    }

    private void block() throws Exception {

        if (currentLexeme() == L_BRACE) {

            nextLexeme();

            while (currentLexeme() != R_BRACE)
                languageToken();

            nextLexeme();

        } else {

            languageToken();
        }
    }

    private void whileConst() throws Exception {

        LexCheck(WHILE);
        LexCheck(L_BRACKET);
        expression();
        LexCheck(R_BRACKET);
        block();
    }

    private void assign() throws Exception {


        LexCheck(NAME);
        LexCheck(OP_ASSIGN);
        expression();
        LexCheck(SEMICOLON);
    }

    private boolean isOp (LexType type) {

        return type == OP_ADD || type == OP_SUB || type == OP_DIV || type == OP_MUL ||
                type == OP_EQUAL || type == OP_LESS || type == OP_LESS_EQUAL || type == OP_MORE || type == OP_MORE_EQUAL || type == OP_NOT_EQUAL;
    }

    private void expression() throws Exception {

        if (currentLexeme() == NUM || currentLexeme() == NAME) {

            nextLexeme();
            if (isOp(currentLexeme())) {

                nextLexeme();
                expression();
            }
        } else if (currentLexeme() == L_BRACKET) {

            LexCheck(L_BRACKET);
            expression();
            LexCheck(R_BRACKET);

            if (isOp(currentLexeme())) {

                nextLexeme();
                expression();
            }
        } else {

            throw new Exception();
        }
    }

    private void LexCheck(LexType type) throws Exception {

        if (currentLexeme() != type)
            throw new Exception();
        else
            nextLexeme();
    }

    private LexType nextLexeme () {

        return lexemes.get(position++).getType();
    }
    private LexType currentLexeme () {

        return lexemes.get(position).getType();
    }
}
