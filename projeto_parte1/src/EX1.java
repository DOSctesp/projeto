import java.util.Scanner;

public class EX1 {

    // Método para desencriptar usando o deslocamento fornecido
    public static String decrypt(String cipherText, int shift) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                // Aplica o deslocamento inverso para a cifra de César
                char decryptedChar = (char) ((c - 'A' - shift + 26) % 26 + 'A');
                decryptedText.append(decryptedChar);
            } else {
                // Preserva os espaços e outros caracteres não alfabéticos
                decryptedText.append(c);
            }
        }

        return decryptedText.toString();
    }

    // Método para verificar se a mensagem contém pelo menos duas palavras do nome
    public static boolean containsTwoWords(String message, String[] nameWords) {
        int count = 0;
        for (String word : nameWords) {
            if (message.contains(word)) {
                count++;
            }
            if (count >= 2) {
                return true; // Encontrou pelo menos duas palavras
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada da cifra encriptada
        System.out.println("Digite a cifra encriptada (texto em maiúsculas):");
        String cipherText = scanner.nextLine().toUpperCase();

        // Entrada do nome completo do aluno
        System.out.println("Digite o nome completo do aluno:");
        String fullName = scanner.nextLine().toUpperCase();

        // Quebra o nome completo em palavras para comparação exata
        String[] nameWords = fullName.split(" ");

        // Testa todos os possíveis deslocamentos de 0 a 25
        for (int shift = 0; shift < 26; shift++) {
            String decryptedMessage = decrypt(cipherText, shift);

            // Verifica se a mensagem contém pelo menos duas palavras do nome
            if (containsTwoWords(decryptedMessage, nameWords)) {
                System.out.println("Deslocamento encontrado: " + shift);
                System.out.println("Mensagem desencriptada: " + decryptedMessage);
                break;
            }
        }

        scanner.close();
    }
}
