package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DemoApplication {

    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nВыберите режим работы:");
                System.out.println("1. Шифрование текста");
                System.out.println("2. Расшифровка текста с помощью ключа");
                System.out.println("3. Расшифровка текста методом brute force");
                System.out.println("4. Расшифровка с помощью статистического анализа");
                System.out.println("5. Выход");

                System.out.print("Введите номер режима: ");
                String mode = scanner.nextLine();

                switch (mode) {
                    case "1":
                        processFile(scanner, caesarCipher, true);
                        break;
                    case "2":
                        processFile(scanner, caesarCipher, false);
                        break;
                    case "3":
                        bruteForceProcess(scanner, caesarCipher);
                        break;
                    case "4":
                        statisticalAnalysisProcess(scanner, caesarCipher);
                        break;
                    case "5":
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный режим. Попробуйте снова.");
                }
            }
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processFile(Scanner scanner, CaesarCipher caesarCipher, boolean encrypt) {
        try {
            System.out.print("Введите путь к исходному файлу: ");
            String inputFilePathStr = scanner.nextLine();
            Path inputFilePath = Paths.get(inputFilePathStr);

            if (!Files.exists(inputFilePath)) {
                System.out.println("Ошибка: Файл не найден по указанному пути.");
                return;
            }

            System.out.print("Введите путь к файлу для сохранения результата: ");
            String outputFilePathStr = scanner.nextLine();
            Path outputFilePath = Paths.get(outputFilePathStr);

            System.out.print("Введите ключ (целое число): ");
            String keyStr = scanner.nextLine();
            int key;
            try {
                key = Integer.parseInt(keyStr);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Ключ должен быть целым числом.");
                return;
            }

            String content = new String(Files.readAllBytes(inputFilePath));
            String result;

            if (encrypt) {
                result = caesarCipher.encrypt(content, key);
                System.out.println("Текст успешно зашифрован.");
            } else {
                result = caesarCipher.decrypt(content, key);
                System.out.println("Текст успешно расшифрован.");
            }

            Files.write(outputFilePath, result.getBytes());
            System.out.println("Результат сохранен в файл: " + outputFilePath.toAbsolutePath());

        } catch (InvalidPathException e) {
            System.out.println("Ошибка: Некорректный путь к файлу.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или записи файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void bruteForceProcess(Scanner scanner, CaesarCipher caesarCipher) {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String inputFilePathStr = scanner.nextLine();
            Path inputFilePath = Paths.get(inputFilePathStr);

            if (!Files.exists(inputFilePath)) {
                System.out.println("Ошибка: Файл не найден по указанному пути.");
                return;
            }

            System.out.print("Введите путь к файлу для сохранения результата: ");
            String outputFilePathStr = scanner.nextLine();
            Path outputFilePath = Paths.get(outputFilePathStr);

            String content = new String(Files.readAllBytes(inputFilePath));
            String result = caesarCipher.bruteForceDecrypt(content);

            Files.write(outputFilePath, result.getBytes());
            System.out.println("Попытки расшифровки сохранены в файл: " + outputFilePath.toAbsolutePath());

        } catch (InvalidPathException e) {
            System.out.println("Ошибка: Некорректный путь к файлу.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или записи файла: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void statisticalAnalysisProcess(Scanner scanner, CaesarCipher caesarCipher) {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String inputFilePathStr = scanner.nextLine();
            Path inputFilePath = Paths.get(inputFilePathStr);

            if (!Files.exists(inputFilePath)) {
                System.out.println("Ошибка: Файл не найден по указанному пути.");
                return;
            }

            System.out.print("Введите путь к файлу для сохранения результата: ");
            String outputFilePathStr = scanner.nextLine();
            Path outputFilePath = Paths.get(outputFilePathStr);

            String content = new String(Files.readAllBytes(inputFilePath));
            String result = caesarCipher.statisticalDecrypt(content);

            Files.write(outputFilePath, result.getBytes());
            System.out.println("Результат статистической расшифровки сохранен в файл: " + outputFilePath.toAbsolutePath());

        } catch (InvalidPathException e) {
            System.out.println("Ошибка: Некорректный путь к файлу.");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении или записи файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
