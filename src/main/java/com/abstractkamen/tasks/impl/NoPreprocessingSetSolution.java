package com.abstractkamen.tasks.impl;

import com.abstractkamen.tasks.CountValidEnglishSolution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoPreprocessingSetSolution implements CountValidEnglishSolution {

    @Override
    public void solve(InputStream input, int targetLength, boolean debug) throws IOException {
        System.out.printf("    No Preprocessing Set Solution with target len [%d]%n", targetLength);
        final long startInput = System.currentTimeMillis();
        List<String> inList = loadAllWords(input);
        final Set<String> validEnglishWords = new HashSet<>(inList);
        System.out.printf("Read Input [%d] lines in [%s]%n", validEnglishWords.size(), Formats.getFormattedMillisTime(startInput, System.currentTimeMillis()));
        int wordsFound = 0;
        final long startLookingAtWords = System.currentTimeMillis();
        for (String word : validEnglishWords) {
            if (word.length() == targetLength && isValid(word, validEnglishWords, debug)) {
                if (debug) {
                    System.out.println(word);
                }
                wordsFound++;
            }
        }
        System.out.printf("Found [%d] valid words in [%s]%n", wordsFound, Formats.getFormattedMillisTime(startLookingAtWords, System.currentTimeMillis()));
    }

    private static List<String> loadAllWords(InputStream input) throws IOException {
        try (var br = new BufferedReader(new InputStreamReader(input))) {
            return br.lines().skip(2).toList();
        }
    }

    private boolean isValid(String word, Set<String> validEnglishWords, boolean debug) {
        if (word.length() == 1 && (word.charAt(0) == 'I' || word.charAt(0) == 'A')) return true;
        if (!validEnglishWords.contains(word)) return false;
        for (int i = 0; i < word.length(); i++) {
            final String subWord = word.substring(0, i) + word.substring(i + 1);
            if (isValid(subWord, validEnglishWords, debug)) {
                if (debug) System.out.println(subWord);
                return true;
            }
        }
        return false;
    }

}
