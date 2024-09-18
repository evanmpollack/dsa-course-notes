package trie;

public class Trie {
    private final static int ALPHABET_SIZE = 26;
    private final static int ZERO = (int) 'a';

    private final TrieNode root;

    public Trie() {
        root = new TrieNode(Character.MIN_VALUE);
    }

    public boolean search(String key) {
        TrieNode curr = root;
        for (int i = 0; i < key.length() && curr != null; i++) {
           curr = curr.children[getIndex(key.charAt(i))];
        }
        return curr != null && curr.isWord;
    }

    public void insert(String key) {
        TrieNode curr = root;
        for (int i = 0; i < key.length(); i++) {
           char character = key.charAt(i);
           int index = getIndex(character);
           if (curr.children[index] == null) {
               curr.children[index] = new TrieNode(character);
           }

           // curr will end up being last node in chain,
           // not null after iteration
           curr = curr.children[index];
        }
        curr.isWord = true;
    }

    public void delete(String key) {
        delete(root, key);
    }

    private void delete(TrieNode node, String key) {
        if (node == null) {
            return;
        }
    }

    @Override
    public String toString() {
        // use dfs
        return "";
    }

    private int getIndex(char c) {
        return (int) c - ZERO;
    }

    private static class TrieNode {
        char data;
        boolean isWord;
        TrieNode[] children;

        TrieNode(char c) {
            data = c;
            isWord = false;
            children = new TrieNode[ALPHABET_SIZE];
        }
    }


    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("cheese");
        trie.insert("chose");
        trie.insert("chosen");
        trie.insert("coat");
        trie.insert("cod");
        System.out.println(trie.search("coat"));
        System.out.println(trie.search("coats"));
    }
}
