package com.abstractkamen.tasks.impl;

import com.abstractkamen.tasks.CountWordsSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PreprocessingSetSolution implements CountWordsSolution {
    /**
     * Total number of english words counted one by one - by me. Trust me :)
     */
    private static final int INITIAL_CAPACITY = 279_496;

    @Override
    public void solve(InputStream input, int targetLength, boolean debug) throws IOException {
        System.out.printf("    Preprocessing Set Solution with target len [%d]%n", targetLength);
        final long startInput = System.currentTimeMillis();
        final Collection<String> targetWords = new ArrayList<>(INITIAL_CAPACITY);
        final Set<String> validWords = new HashSet<>(INITIAL_CAPACITY);
        preprocessing(input, targetWords, validWords, targetLength);
        System.out.printf("Read Input [%d] lines in [%s]%n", validWords.size(), Formats.getFormattedMillisTime(startInput, System.currentTimeMillis()));
        int wordsFound = 0;
        final long startLookingAtWords = System.currentTimeMillis();
        for (String word : targetWords) {
            if (isValid(word, validWords, debug)) {
                if (debug) {
                    System.out.println(word);
                }
                wordsFound++;
            }
        }
        System.out.printf("Found [%d] valid words in [%s]%n", wordsFound, Formats.getFormattedMillisTime(startLookingAtWords, System.currentTimeMillis()));
    }

    private boolean isValid(String word, Set<String> validWords, boolean debug) {
        if (word.length() == 1 && (word.charAt(0) == 'I' || word.charAt(0) == 'A')) return true;
        if (!validWords.contains(word)) return false;
        for (int i = 0; i < word.length(); i++) {
            final String subWord = word.substring(0, i) + word.substring(i + 1);
            if (isValid(subWord, validWords, debug)) {
                if (debug) System.out.println(subWord);
                return true;
            }
        }
        return false;
    }

    private void preprocessing(InputStream input, Collection<String> targetWords, Set<String> validWords, int targetLength) throws IOException {
        try (var br = new BufferedReader(new InputStreamReader(input))) {
            // skip first two lines
            br.readLine();
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                validWords.add(line);
                if (line.length() == targetLength) {
                    targetWords.add(line);
                }
            }
        }
    }


}
