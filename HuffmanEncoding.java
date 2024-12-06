import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
//import java.util.HashMap;

class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left, right;

    // Constructor for the Huffman Node
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
}

// Comparator for the Priority Queue (sort by frequency in ascending order)
class FrequencyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

public class HuffmanEncoding {

    // Method to build the Huffman Tree
    public static HuffmanNode buildHuffmanTree(char[] chars, int[] frequencies) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(new FrequencyComparator());

        // Add all characters with their frequencies as leaf nodes in the priority queue
        for (int i = 0; i < chars.length; i++) {
            queue.add(new HuffmanNode(chars[i], frequencies[i]));
        }

        // Build the tree by merging the two lowest-frequency nodes
        while (queue.size() > 1) {
            HuffmanNode node1 = queue.poll(); // Lowest frequency node
            HuffmanNode node2 = queue.poll(); // Second lowest frequency node

            // Create an internal node with combined frequency
            HuffmanNode internalNode = new HuffmanNode('-', node1.frequency + node2.frequency);
            internalNode.left = node1;
            internalNode.right = node2;

            // Add the new node back to the queue
            queue.add(internalNode);
        }

        // The root of the tree is the last node remaining in the queue
        return queue.poll();
    }

    // Method to generate Huffman Codes from the Huffman Tree
    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }

        // If it's a leaf node, add the character and its corresponding code
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
        }

        // Recursively traverse left and right subtrees
        generateCodes(root.left, code + "0", huffmanCodes);
        generateCodes(root.right, code + "1", huffmanCodes);
    }

    // Method to encode a given text using the Huffman Codes
    public static String encode(String text, Map<Character, String> huffmanCodes) {
        StringBuilder encoded = new StringBuilder();
        for (char character : text.toCharArray()) {
            encoded.append(huffmanCodes.get(character));
        }
        return encoded.toString();
    }

    // Method to decode the encoded text using the Huffman Tree
    public static String decode(String encodedText, HuffmanNode root) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode currentNode = root;

        for (char bit : encodedText.toCharArray()) {
            currentNode = (bit == '0') ? currentNode.left : currentNode.right;

            // If it's a leaf node, append the character and reset the traversal
            if (currentNode.left == null && currentNode.right == null) {
                decoded.append(currentNode.character);
                currentNode = root;
            }
        }

        return decoded.toString();
    }
}
