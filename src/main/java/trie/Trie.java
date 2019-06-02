package trie;

import javax.swing.*;
import java.util.*;

public class Trie {

    private static Trie instance;

    private TrieNode root;

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    private Trie() {
        root = new TrieNode('\0');
    }

    public static synchronized Trie getInstance() {
        if(instance == null) {
            instance = new Trie();
        }
        return instance;
    }

    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            TrieNode t;
            if(children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new TrieNode(c);
                children.put(c, t);
            }
            children = t.children;
            // set leaf node
            if(i == word.length() - 1) {
                t.isLeaf = true;
            }
        }
    }

    public TrieNode searchNode(TrieNode root, String word) {
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }
        return t;
    }

    public boolean search(TrieNode root, String word) {
        TrieNode t = searchNode(root, word);
        if(t != null && t.isLeaf) {
            return true;
        } else {
            return false;
        }
    }

    public boolean startWith(TrieNode root, String prefix) {
        if(searchNode(root, prefix) == null) {
            return false;
        } else {
            return true;
        }
    }

   public void printAllWords(ArrayList<String> result, TrieNode trieNode, String prefix, String str) {
        TrieNode current = trieNode;
        if(trieNode.children == null || trieNode.children.size() == 0) {
            return;
        }

        Iterator<TrieNode> iterator = current.children.values().iterator();
        while (iterator.hasNext()) {
            TrieNode node = iterator.next();
            str += node.c;
            printAllWords(result, node, prefix, str);
            if(node.isLeaf) {
                String temp = prefix + str;
                result.add(temp);
                str = str.substring(0, str.length() - 1);
            } else {
                str = str.substring(0, str.length() - 1);
            }
        }
   }

}







