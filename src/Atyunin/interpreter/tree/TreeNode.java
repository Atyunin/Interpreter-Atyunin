package Atyunin.interpreter.tree;

import Atyunin.interpreter.tokens.Lexeme;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private NodeType type;
    private List <TreeNode> nodes;
    private List <Lexeme> leafs;

    public TreeNode(NodeType type) {

        this.leafs = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }

    public TreeNode(NodeType type, List <TreeNode> nodes, List <Lexeme> leafs) {

        this.leafs = leafs;
        this.nodes = nodes;
    }

    public void add (Lexeme leaf) {

        leafs.add(leaf);
    }
}
