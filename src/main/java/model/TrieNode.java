package model;

import java.util.HashMap;

public class TrieNode {

    char c;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean isLeaf;

    public TrieNode() {

    }

    public TrieNode(char c) {
        this.c = c;
    }

}
