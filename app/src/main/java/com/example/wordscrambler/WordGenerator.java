package com.example.wordscrambler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordGenerator {
    private static final List<String> easyWords = Arrays.asList("apple", "banana", "cat", "dog", "elephant");
    private static final List<String> mediumWords = Arrays.asList("guitar", "jungle", "kangaroo", "mountain", "ocean");
    private static final List<String> hardWords = Arrays.asList("xylophone", "zombie", "quasar", "vortex", "jigsaw");

    public static String generateWord(Difficulty difficulty) {
        List<String> words = getWordList(difficulty);
        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    private static List<String> getWordList(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return easyWords;
            case MEDIUM:
                return mediumWords;
            case HARD:
                return hardWords;
            default:
                throw new IllegalArgumentException("Unknown difficulty: " + difficulty);
        }
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
