package com.demo;

import java.util.*;

public class AutoComplete {
    public static void main(String[] args) {
        //AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2])
        String[] str = new String[]{ "i love you", "island","ironman", "i love leetcode" };
        int[] counts = new int[]{5,3,2,2};
        AutoComplete ac = new AutoComplete(str, counts);

        String input = "i love you";
        for(int i=0; i<input.length(); i++){
            List<String> rec = ac.input(input.charAt(i));
            //System.out.println(input.charAt(i) + " : ----------");
            for(String s: rec){
                //System.out.println(s);
            }
        }
    }
    static class Trie{
        private TrieNode root;

        public Trie(){
            root = new TrieNode();
        }

        public TrieNode search(String prefix){
            TrieNode node = root;
            Map<Character, TrieNode> childs = root.children;

            for(int i=0; i<prefix.length(); i++){
                char currentCh = prefix.charAt(i);

                if(!childs.containsKey(currentCh)){
                    return null;
                }

                node = childs.get(currentCh);
                childs = node.children;
            }
            return node;
        }

        public void insert(String s){
            TrieNode node = root;

            for(int i=0; i<s.length(); i++){
                char currentCharacter = s.charAt(i);
                Map<Character, TrieNode> childs = node.children;
                if(!childs.containsKey(currentCharacter)){
                    childs.put(currentCharacter, new TrieNode());
                }

                System.out.println( currentCharacter + ": " + s);
                childs.get(currentCharacter).string.add(s);

                if( i == s.length() - 1){
                    node.isLeaf = true;
                }

                node = childs.get(currentCharacter);
            }
        }

    }

    static class TrieNode {

        public boolean isLeaf;
        public List<String> string;
        public Map<Character, TrieNode> children;

        public TrieNode(){
            isLeaf = false;
            string = new ArrayList<>();
            children = new HashMap<>();
        }

    }

    HashMap<String, Integer> count = new HashMap<String, Integer>();
    Trie trie = new Trie();
    String curr = "";

    public AutoComplete(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i ++) {
            count.put(sentences[i], times[i]);
            trie.insert(sentences[i]);
        }
    }

    public List<String> input(char c) {
        List<String> res = new LinkedList<String>();
        if (c == '#') {
            if (!count.containsKey(curr)) {
                trie.insert(curr);
                count.put(curr, 1);
            }
            else {
                count.put(curr, count.get(curr) + 1);
            }
            curr = "";
        }
        else {
            curr += c;
            res = getSuggestions();
        }

        return res;
    }
    private List<String> getSuggestions() {
        List<String> res = new LinkedList<String>();
        TrieNode node = trie.search(curr);
        if (node == null) {
            return res;
        }
        List<String> cands = node.string;
        Collections.sort(cands, new Comparator<String>(){
            public int compare(String s1, String s2) {
                if (count.get(s1) != count.get(s2)) {
                    return count.get(s2) - count.get(s1);
                }
                return s1.compareTo(s2);
            }
        });
        int added = 0;
        for (String s:cands) {
            res.add(s);
            added ++;
            if (added > 2) {
                break;
            }
        }
        return res;
    }
}
