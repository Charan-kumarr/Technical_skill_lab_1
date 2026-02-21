import java.util.*;

/*
 * Global Autocomplete System
 * - Stores up to 1M strings
 * - Prefix search in O(L)
 * - Returns Top 5 most frequent suggestions
 */

public class AutocompleteSystem {

    private static final int TOP_K = 5;

    // Word class
    static class Word {
        String text;
        int frequency;

        Word(String text) {
            this.text = text;
            this.frequency = 1;
        }
    }

    // Trie Node
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;

        // Min heap to maintain top 5 frequent words
        PriorityQueue<Word> topWords = new PriorityQueue<>(
                (a, b) -> a.frequency - b.frequency
        );
    }

    private TrieNode root;
    private Map<String, Word> wordMap; // global word frequency tracker

    public AutocompleteSystem() {
        root = new TrieNode();
        wordMap = new HashMap<>();
    }

    // Insert word
    public void insert(String word) {
        Word w;

        if (wordMap.containsKey(word)) {
            w = wordMap.get(word);
            w.frequency++;
        } else {
            w = new Word(word);
            wordMap.put(word, w);
        }

        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);

            updateTopWords(current, w);
        }

        current.isEndOfWord = true;
    }

    // Maintain top 5 words at each node
    private void updateTopWords(TrieNode node, Word word) {

        node.topWords.remove(word); // remove old if exists
        node.topWords.offer(word);

        if (node.topWords.size() > TOP_K) {
            node.topWords.poll(); // remove smallest
        }
    }

    // Get Top 5 suggestions (O(L))
    public List<Word> getTopSuggestions(String prefix) {

        TrieNode current = root;

        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return Collections.emptyList();
            }
            current = current.children.get(ch);
        }

        List<Word> result = new ArrayList<>(current.topWords);

        // Sort descending before returning
        result.sort((a, b) -> b.frequency - a.frequency);

        return result;
    }

    // Main method (Example Run)
    public static void main(String[] args) {

        AutocompleteSystem system = new AutocompleteSystem();

        system.insert("application");
        system.insert("application");
        system.insert("application");
        system.insert("application");

        system.insert("apple");
        system.insert("app");

        system.insert("apple");
        system.insert("apple");

        system.insert("apply");

        List<Word> suggestions = system.getTopSuggestions("appl");

        System.out.println("Top suggestions for \"appl\":");

        for (Word w : suggestions) {
            System.out.println(w.text + " (" + w.frequency + ")");
        }
    }
}
