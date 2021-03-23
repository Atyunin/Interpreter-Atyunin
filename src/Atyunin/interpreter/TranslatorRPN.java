package Atyunin.interpreter;

import static Atyunin.interpreter.tokens.LexType.*;
import static Atyunin.interpreter.tree.NodeType.*;

import Atyunin.interpreter.tokens.Lexeme;
import Atyunin.interpreter.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TranslatorRPN {

    private TreeNode AST;

    private LinkedList <Lexeme> texas;
    private ArrayList <Lexeme> RPN;

    public TranslatorRPN (TreeNode AST) {

        this.AST = AST;

        this.texas = new LinkedList<>();
        this.RPN = new ArrayList<>();
    }

    public void translate () {

        for(TreeNode node: AST.getNodes()) {

            languageConst(node);
        }
    }

    private void languageConst(TreeNode node) {

        TreeNode nodeConst = node.getNodes().get(0);

        if (nodeConst.getType() == AssignConst) {

            assignConst(nodeConst);
        } else if (nodeConst.getType() == IfConst) {
            
            ifConst(nodeConst);
        }
    }

    private void ifConst(TreeNode nodeConst) {

        expression(nodeConst.getNodes().get(0));
        flushTexas();

        Lexeme point = new Lexeme(JMP_VALUE);
        addOprand(point);

        addOprand(nodeConst.getLeafs().get(0));
        block(nodeConst.getNodes().get(1));

        Lexeme endPoint = new Lexeme(JMP_VALUE);
        addOprand(endPoint);
        addOprand(new Lexeme (JMP));

        point.set_value(Integer.toString(RPN.size()));

        for (int i = 2; i < nodeConst.getNodes().size(); i++) {

            if (nodeConst.getNodes().get(i).getType() == ElifConst) {

                elifConst(nodeConst.getNodes().get(i), endPoint);
            } else if (nodeConst.getNodes().get(i).getType() == ElseConst) {

                elseConst(nodeConst.getNodes().get(i));
            }
        }

        endPoint.set_value(Integer.toString(RPN.size()));
    }

    private void elifConst(TreeNode nodeConst, Lexeme endPoint) {

        expression(nodeConst.getNodes().get(0));
        flushTexas();

        Lexeme point = new Lexeme(JMP_VALUE);
        addOprand(point);

        addOprand(nodeConst.getLeafs().get(0));
        block(nodeConst.getNodes().get(1));

        addOprand(endPoint);
        addOprand(new Lexeme (JMP));

        point.set_value(Integer.toString(RPN.size()));
    }

    private void elseConst(TreeNode nodeConst) {

        block(nodeConst.getNodes().get(0));
    }

    private void block(TreeNode topNode) {

        for(TreeNode node: topNode.getNodes()) {

            languageConst(node);
        }
    }

    private void assignConst(TreeNode nodeConst) {

        addOprand(nodeConst.getLeafs().get(0));
        addOperator(nodeConst.getLeafs().get(1));
        expression(nodeConst.getNodes().get(0));
        flushTexas();
    }

    private void expression(TreeNode topNode) {

        List<TreeNode> nextNodes = topNode.getNodes();

        if (nextNodes.get(0).getType() == Member) {

            member(nextNodes.get(0));
        } else if (nextNodes.get(0).getType() == BracketMember) {

            bracketMember(nextNodes.get(0));
        }
        if (nextNodes.size()>1) {
            op(nextNodes.get(1));
            expression(nextNodes.get(2));
        }
    }

    private void bracketMember(TreeNode node) {

        addOperator(node.getLeafs().get(0));
        expression(node.getNodes().get(0));
        addOperator(node.getLeafs().get(1));
    }

    private void op(TreeNode node) {

        addOperator(node.getLeafs().get(0));
    }

    private void member(TreeNode node) {

        addOprand(node.getLeafs().get(0));
    }

    private int opPriority (Lexeme op) {

        int priority;

        if (op.getType() == OP_ASSIGN)
            priority = 0;
        else if (op.getType() == OP_ADD)
            priority = 1;
        else if (op.getType() == OP_SUB)
            priority = 1;
        else if (op.getType() == OP_MUL)
            priority = 2;
        else if (op.getType() == OP_DIV)
            priority = 2;
        else
            priority = 0;

        return priority;
    }

    private void addOprand (Lexeme lexeme) {

        RPN.add(lexeme);
    }

    private void addOperator (Lexeme lexeme) {

        if (lexeme.getType() == L_BRACKET) {
            texas.addFirst(lexeme);
            return;
        }

        if (lexeme.getType() == R_BRACKET) {

            while (texas.peek().getType() != L_BRACKET) {

                RPN.add(texas.removeFirst());
            }

            texas.removeFirst();
            return;
        }

        while (true) {

            if (texas.peek()!=null && opPriority(texas.peek()) > opPriority(lexeme))
                RPN.add(texas.removeFirst());
            else {

                texas.addFirst(lexeme);
                return;
            }
        }
    }

    private void flushTexas () {

        int size = texas.size();

        for (int i = 0; i < size; i++) {

            RPN.add(texas.removeFirst());
        }
    }

    public void print () {

        System.out.println("[RPN translator] reverse polish notation: ");
        System.out.printf("%-4s%-20s%-20s\n","№", "Name lexeme", "Value");

        int i = 0;

        for (Lexeme lexeme: RPN) {
            System.out.printf("%-4s", i++);
            lexeme.println();
        }
    }
}
