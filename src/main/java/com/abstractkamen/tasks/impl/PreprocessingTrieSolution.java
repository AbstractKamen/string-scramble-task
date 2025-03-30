package com.abstractkamen.tasks.impl;

import com.abstractkamen.tasks.CountValidEnglishSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class PreprocessingTrieSolution implements CountValidEnglishSolution {

    private static final int INITIAL_CAPACITY = 279_496;

    @Override
    public void solve(InputStream input, int targetLength, boolean debug) throws IOException {
        System.out.printf("    Preprocessing Trie Solution with target len [%d]%n", targetLength);
        final long startInput = System.currentTimeMillis();
        final Collection<int[]> nineLetterWords = new ArrayList<>(INITIAL_CAPACITY);
        final ValidEnglishWordTrie validEnglishWords = new ValidEnglishWordTrie();
        int totalWords = preprocessing(input, nineLetterWords, validEnglishWords, targetLength);
        System.out.printf("Read Input [%d] lines in [%s]%n", totalWords, Formats.getFormattedMillisTime(startInput, System.currentTimeMillis()));
        int wordsFound = 0;
        final long startLookingAtWords = System.currentTimeMillis();
        for (int[] nineLetterWord : nineLetterWords) {
            if (isValid(nineLetterWord, validEnglishWords, debug)) {
                if (debug) {
                    showWord(nineLetterWord);
                }
                wordsFound++;
            }
        }
        System.out.printf("Found [%d] valid words in [%s]%n", wordsFound, Formats.getFormattedMillisTime(startLookingAtWords, System.currentTimeMillis()));
    }

    private boolean isValid(int[] word, ValidEnglishWordTrie validEnglishWords, boolean debug) {
        if (validEnglishWords.isValidSingleLetterWord(word)) return true;
        if (!validEnglishWords.isValidWord(word)) return false;

        for (int i = 0; i < word.length; i++) {
            int c = word[i];
            if (c == -1) continue;
            word[i] = -1;
            if (isValid(word, validEnglishWords, debug)) {
                if (debug) {
                    showWord(word);
                }
                word[i] = c;
                return true;
            }
            word[i] = c;

        }
        return false;
    }

    private static void showWord(int[] word) {
        for (int j = 0; j < word.length; j++) {
            final int ch = word[j];
            if (ch != -1) System.out.print((char) ch);
        }
        System.out.println();
    }

    private int preprocessing(InputStream input, Collection<int[]> nineLetterWords, ValidEnglishWordTrie validEnglishWords, int targetLength) throws IOException {
        try (var br = new BufferedReader(new InputStreamReader(input))) {
            // skip first two lines
            br.readLine();
            br.readLine();
            String line;
            int totalWords = 0;
            while ((line = br.readLine()) != null) {
                totalWords++;
                final int[] chars = new int[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    chars[i] = line.charAt(i);
                }
                validEnglishWords.insert(chars);
                if (line.length() == targetLength) {
                    nineLetterWords.add(chars);
                }
            }
            return totalWords;
        }
    }

    private static class ValidEnglishWordTrie {
        private static class WordBuffer {
            public static final int SOME_LONG_WORD_LENGTH_I_DEFINITELY_LOOKED_UP = 420;
            private final int[] items = new int[SOME_LONG_WORD_LENGTH_I_DEFINITELY_LOOKED_UP];
            private int length;

            void setToWord(int[] word) {
                length = 0;
                for (int c : word) {
                    if (c != -1) {
                        items[length++] = c;
                    }
                }
            }
        }

        private static class Node {
            private final Node[] children = new Node[26];
            private boolean isEnd;

            void setEnd() {
                isEnd = true;
            }
        }

        private final Node root;
        private final WordBuffer wordBuffer = new WordBuffer();

        public ValidEnglishWordTrie() {
            root = new Node();
        }

        public boolean isValidSingleLetterWord(int[] word) {
            wordBuffer.setToWord(word);
            return wordBuffer.length == 1 && ('A' == wordBuffer.items[0] || 'I' == wordBuffer.items[0]);
        }


        public boolean isValidWord(int[] needle) {
            wordBuffer.setToWord(needle);
            return isValidWord(root, 0);
        }

        private boolean isValidWord(Node n, int i) {
            if (n == null) return false;
            if (wordBuffer.length <= i) return n.isEnd;

            int c = wordBuffer.items[i] - 'A';
            // go deeper
            return isValidWord(n.children[c], i + 1);
        }

        void insert(int[] string) {
            final Node inserted = insert(string, this.root, 0);
            if (inserted != null) {
                inserted.setEnd();
            }
        }

        Node insert(int[] word, Node root, int i) {
            if (i == word.length) {
                return root;
            } else {
                final int c = word[i] - 'A';
                Node child = root.children[c];
                if (child == null) {
                    child = new Node();
                    root.children[c] = child;
                }
                return insert(word, child, i + 1);
            }
        }
    }
}
