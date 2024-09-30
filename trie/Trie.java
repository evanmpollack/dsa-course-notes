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
            char character = key.charAt(i);
            int index = getIndex(character);
            curr = curr.children[index];
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
        delete(root, key, 0);
    }

    private void delete(TrieNode node, String key, int index) {
        // pre
        // characters not all present 
        // (also the step after the case where characters are present but word is not registered)
        if (node == null) {
            return;
        }

        // characters are present and word is registered
        // check for index == key length instead of key length - 1 because the first non-root node is at level 1
        if (node.isWord && index == key.length()) {
            node.isWord = false;
            return;
        }

        // recurse
        char character = key.charAt(index);
        int characterIndex = getIndex(character);
        TrieNode child = node.children[characterIndex];
        delete(child, key, index + 1);
        
        // post
        if (isLeaf(child) && !child.isWord) {
            node.children[characterIndex] = null;
        }
    }

    @Override
    public String toString() {
        final char delimiter = ',';
        final String prefix = "";
        return alphabeticalOrder(root, prefix, delimiter, new StringBuilder()).toString();
    }

    // Pre-order traversal yields alphabetical order
    private StringBuilder alphabeticalOrder(TrieNode node, String prefix, char delimiter, StringBuilder sb) {
        // Don't want to record root data
        if (node != root) {
            // Prefix is used to keep the nodes intact as the value tracks the stack due to it's pass by value nature
            prefix += String.valueOf(node.data);
        }

        // Node has to be a word to add it to the output
        // I don't check if it is leaf here to account for
        // the case where a word is a prefix to another word (e.g. chose and chosen)
        if (node.isWord) {
            sb.append(prefix).append(delimiter);
        }

        // Once we reach a leaf, we can begin backtracking
        if (isLeaf(node)) {
            return sb;
        }

        for (TrieNode child : node.children) {
            if (child != null) {
                alphabeticalOrder(child, prefix, delimiter, sb);
            }
        }

        return sb;
    }

    // O(1) because the children array is capped at 26
    private boolean isLeaf(TrieNode node) {
        boolean leaf = true;
        for (TrieNode child : node.children) {
            if (child != null) {
                leaf = false;
                break;
            }
        }
        return leaf;
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
        System.out.println(trie);
        System.out.println(trie.search("coat"));
        System.out.println(trie.search("coats"));
        trie.delete("chose");
        System.out.println(trie.search("chose"));
        System.out.println(trie.search("chosen"));
        System.out.println(trie);
    }
}
