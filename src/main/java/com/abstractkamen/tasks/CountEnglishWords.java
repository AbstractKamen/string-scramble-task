package com.abstractkamen.tasks;

import com.abstractkamen.tasks.impl.NoPreprocessingSetSolution;
import com.abstractkamen.tasks.impl.PreprocessingSetSolution;
import com.abstractkamen.tasks.impl.PreprocessingTrieSolution;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/*
 * String Scramble Task
 *
 * Find all nine-letter words for which the following is true:
 *   - after removing a single letter from the word the remainder characters also form a valid word
 *   - after removing another letter from the now eight-letter word the remainder still form a valid word
 *   - remove characters until the last valid words can exist 'A' 'I'
 *
 * Example:
 *    |    word     |  len  |
 *    | 'STARTLING' |  9    |
 *    | 'START_ING' |  8    |
 *    | 'STAR__ING' |  7    |
 *    | 'ST_R__ING' |  6    |
 *    | 'ST____ING' |  5    |
 *    | 'S_____ING' |  4    |
 *    | 'S_____IN_' |  3    |
 *    | '______IN_' |  2    |
 *    | '______I__' |  1    |
 *
 * Additional Requirements:
 *   1. Optimise for speed -> should run in less than two seconds
 *   2. Valid words from the english language should be downloaded from here:
 *     - https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt
 *   3. Actual download of words content should not be part of the 'less than two seconds constraint'
 */
public class CountEnglishWords {

    private static final String PREP_SET_COMMAND = "prepset";
    private static final String PREP_TRIE_COMMAND = "preptrie";
    private static final String NO_PREP_SET_COMMAND = "noprep";

    private static final String DEFAULT_DICTIONARY_URL = "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";
    private static final int DEFAULT_TARGET_LEN = 9;


    public static void main(String[] args) throws IOException {
        int i = 0;
        String command = null;
        Args:
        for (; i < args.length; i++) {
            switch (args[i]) {
                case PREP_SET_COMMAND:
                case PREP_TRIE_COMMAND:
                case NO_PREP_SET_COMMAND:
                    command = args[i];
                    break Args;
            }
        }
        String dictionaryUrl = DEFAULT_DICTIONARY_URL;
        int targetLen = DEFAULT_TARGET_LEN;
        boolean debug = false;

        for (; i < args.length; i++) {
            if ("-url".equals(args[i]) && i + 1 < args.length) {
                dictionaryUrl = args[++i];
            } else if ("-targetLen".equals(args[i]) && i + 1 < args.length) {
                // no multithreading here only use cool monad
                targetLen = CompletableFuture.completedFuture(args[++i])
                        .thenApply(Integer::parseInt)
                        .exceptionally(e -> {
                            System.out.println("Warning -targetLen accepts only valid integers. Using default [9] value.");
                            return 9;
                        })
                        .join();
            } else if ("-debug".equals(args[i])) {
                debug = true;
            }
        }

        try (var in = new URL(dictionaryUrl).openStream()) {
            switch (command) {
                case PREP_SET_COMMAND -> new PreprocessingSetSolution().solve(in, targetLen, debug);
                case PREP_TRIE_COMMAND -> new PreprocessingTrieSolution().solve(in, targetLen, debug);
                case NO_PREP_SET_COMMAND -> new NoPreprocessingSetSolution().solve(in, targetLen, debug);
                case null, default -> showHelp();
            }
        }
    }

    private static void showHelp() {
        System.out.printf("Supported Commands: %n    prepset      Preprocessed Set Solution%n    noprep       No Preprocessing Set Solution%n    preptrie     Preprocessed Trie Solution%nSupported Args:%n    -targetLen <integer>    sets the target length for the root word%n    -url       <urlString>  sets the url where a dictionary can be fetched%n    -debug                  if this flag is present valid words will be displayed%n");
    }
}
