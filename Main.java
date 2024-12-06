import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String text = "Huffman encoding algorithm";
        // Get unique characters
        Set<Character> charSet = text.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        char[] characters = new char[charSet.size()];
        int index = 0;
        for (Character c : charSet) {
            characters[index++] = c;
        }

        // Calculate frequencies
        int[] frequencies = new int[characters.length];
        for (int i = 0; i < characters.length; i++) {
            char currentChar = characters[i];
            frequencies[i] = (int) text.chars().filter(ch -> ch == currentChar).count();
        }

        // Build Huffman Tree
        HuffmanNode root = HuffmanEncoding.buildHuffmanTree(characters, frequencies);

        // Generate Huffman Codes
        Map<Character, String> huffmanCodes = new HashMap<>();
        HuffmanEncoding.generateCodes(root, "", huffmanCodes);

        // Display Huffman Codes
        System.out.println("Huffman Codes: " + huffmanCodes);

        // Encode the input text
        String encodedText = HuffmanEncoding.encode(text, huffmanCodes);
        System.out.println("Encoded Text: " + encodedText);

        // Decode the encoded text
        String decodedText = HuffmanEncoding.decode(encodedText, root);
        System.out.println("Decoded Text: " + decodedText);
    }
}
