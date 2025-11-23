package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final String RUSSIAN_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final char MOST_FREQUENT_RUSSIAN_CHAR = 'о';
    private static final char MOST_FREQUENT_ENGLISH_CHAR = 'e';

    public String encrypt(String text, int key) {
        return transform(text, key);
    }

    public String decrypt(String text, int key) {
        return transform(text, -key);
    }

    public String bruteForceDecrypt(String text) {
        StringBuilder allAttempts = new StringBuilder();
        String alphabet = findFirstAlphabet(text);

        if (alphabet == null) {
            return "Не удалось определить алфавит для текста.";
        }

        int alphabetSize = alphabet.length();
        for (int key = 1; key < alphabetSize; key++) {
            allAttempts.append("--- Key: ").append(key).append(" ---\n");
            allAttempts.append(decrypt(text, key)).append("\n\n");
        }
        return allAttempts.toString();
    }

    public String statisticalDecrypt(String text) {
        char mostFrequentChar = findMostFrequentChar(text);
        if (mostFrequentChar == '\0') {
            return "Не удалось найти наиболее частый символ в тексте.";
        }

        String alphabet = getAlphabet(mostFrequentChar);
        char mostFrequentLanguageChar;

        if (RUSSIAN_ALPHABET.equals(alphabet)) {
            mostFrequentLanguageChar = MOST_FREQUENT_RUSSIAN_CHAR;
        } else if (ENGLISH_ALPHABET.equals(alphabet)) {
            mostFrequentLanguageChar = MOST_FREQUENT_ENGLISH_CHAR;
        } else {
             return "Не удалось определить язык для статистического анализа.";
        }

        int key = (alphabet.indexOf(mostFrequentChar) - alphabet.indexOf(mostFrequentLanguageChar) + alphabet.length()) % alphabet.length();
        System.out.println("Предполагаемый ключ: " + key);

        return decrypt(text, key);
    }

    private String transform(String text, int key) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                String alphabet = getAlphabet(character);
                if (alphabet != null) {
                    char lowerCaseChar = Character.toLowerCase(character);
                    int index = alphabet.indexOf(lowerCaseChar);
                    int newIndex = (index + key + alphabet.length()) % alphabet.length();
                    char newChar = alphabet.charAt(newIndex);
                    if (Character.isUpperCase(character)) {
                        result.append(Character.toUpperCase(newChar));
                    } else {
                        result.append(newChar);
                    }
                } else {
                    result.append(character);
                }
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    private char findMostFrequentChar(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        char mostFrequent = '\0';
        int maxCount = 0;

        for (char character : text.toLowerCase().toCharArray()) {
            if (Character.isLetter(character) && getAlphabet(character) != null) {
                frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
            }
        }

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        return mostFrequent;
    }

    private String findFirstAlphabet(String text) {
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                return getAlphabet(character);
            }
        }
        return null;
    }

    private String getAlphabet(char character) {
        char lowerCaseChar = Character.toLowerCase(character);
        if (RUSSIAN_ALPHABET.indexOf(lowerCaseChar) != -1) {
            return RUSSIAN_ALPHABET;
        } else if (ENGLISH_ALPHABET.indexOf(lowerCaseChar) != -1) {
            return ENGLISH_ALPHABET;
        }
        return null;
    }
}
