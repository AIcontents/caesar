package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

    private final CaesarCipher caesarCipher = new CaesarCipher();

    @Test
    void contextLoads() {
    }

    @Test
    void testEncryptRussian() {
        assertEquals("Съефх, щсд зхс ахбъд! Fh, zqd zr cx adpa!", caesarCipher.encrypt("Привет, мир! Ну, как ты тут без меня?", 3));
    }

    @Test
    void testDecryptRussian() {
        assertEquals("Привет, мир! Ну, как ты тут без меня?", caesarCipher.decrypt("Съефх, щсд зхс ахбъд! Fh, zqd zr cx adpa!", 3));
    }

    @Test
    void testEncryptEnglish() {
        assertEquals("Khoor, zruog! Wk, tzs brx gr zlwkrxw ph?", caesarCipher.encrypt("Hello, world! Wh, how are you do without me?", 3));
    }

    @Test
    void testDecryptEnglish() {
        assertEquals("Hello, world! Wh, how are you do without me?", caesarCipher.decrypt("Khoor, zruog! Wk, tzs brx gr zlwkrxw ph?", 3));
    }

    @Test
    void testWithNonAlphabeticChars() {
        assertEquals("123 !@#$%^&*()_+", caesarCipher.encrypt("123 !@#$%^&*()_+", 5));
    }

    @Test
    void testEmptyString() {
        assertEquals("", caesarCipher.encrypt("", 10));
    }

    @Test
    void testBruteForceDecrypt() {
        String encrypted = "Съефх, щсд зхс ахбъд!";
        String bruteForceResult = caesarCipher.bruteForceDecrypt(encrypted);
        assertTrue(bruteForceResult.contains("Привет, мир!"));
    }

    @Test
    void testStatisticalDecryptRussian() {
        String originalText = "В лесу родилась ёлочка, в лесу она росла, зимой и летом стройная, зелёная была. Метель ей пела песенку: спи, ёлочка, бай-бай, мороз снежком укутывал: смотри, не замерзай.";
        String encryptedText = caesarCipher.encrypt(originalText, 5);
        String decryptedText = caesarCipher.statisticalDecrypt(encryptedText);
        assertEquals(originalText, decryptedText);
    }
}
