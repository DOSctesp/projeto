import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EX2 {

    public static String decryptCaesarCipher(String text, int shift) {
        StringBuilder decryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char shiftedChar = (char) ((c - 'A' - shift + 26) % 26 + 'A');
                decryptedText.append(shiftedChar);
            } else {
                decryptedText.append(c);
            }
        }
        return decryptedText.toString();
    }

    public static Set<String> loadWordlist(String filename) {
        Set<String> wordlist = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
            String word;
            while ((word = reader.readLine()) != null) {
                wordlist.add(word.trim().toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: Arquivo de wordlist não encontrado.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de wordlist.");
            e.printStackTrace();
        }
        return wordlist;
    }

    public static void tryAllShifts(String encryptedMessage, Set<String> wordlist) {
        int saltLength = 3;

        System.out.println("Iniciando tentativa de desencriptação usando a wordlist...");
        System.out.println("-----------------------------------------------------------");

        for (int shift = 0; shift < 26; shift++) {
            String saltStart = encryptedMessage.substring(0, saltLength);
            String messageWithSaltAtStart = encryptedMessage.substring(saltLength);
            String decryptedSaltStart = decryptCaesarCipher(saltStart, shift);
            String possibleMessageStart = decryptCaesarCipher(messageWithSaltAtStart, shift);


            if (wordlist.contains(possibleMessageStart)) {
                System.out.printf("Cifra encontrada! Shift: %d | Salt no início: '%s' | Mensagem: '%s'%n",
                        shift, decryptedSaltStart, possibleMessageStart);
                System.out.println("-----------------------------------------------------------");
            }

            String saltEnd = encryptedMessage.substring(encryptedMessage.length() - saltLength);
            String messageWithSaltAtEnd = encryptedMessage.substring(0, encryptedMessage.length() - saltLength);
            String decryptedSaltEnd = decryptCaesarCipher(saltEnd, shift);
            String possibleMessageEnd = decryptCaesarCipher(messageWithSaltAtEnd, shift);


            if (wordlist.contains(possibleMessageEnd)) {
                System.out.printf("Cifra encontrada! Shift: %d | Salt no final: '%s' | Mensagem: '%s'%n",
                        shift, decryptedSaltEnd, possibleMessageEnd);
                System.out.println("-----------------------------------------------------------");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<String> wordlist = loadWordlist("src/wordlist.txt");

        if (wordlist.isEmpty()) {
            System.out.println("Erro: A wordlist está vazia ou não foi carregada.");
            scanner.close();
            return;
        }

        System.out.print("Digite a mensagem encriptada: ");
        String encryptedMessage = scanner.nextLine().toUpperCase();

        tryAllShifts(encryptedMessage, wordlist);

        scanner.close();
    }
}
