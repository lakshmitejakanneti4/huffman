import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class HuffmanEncodingFrontend {

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Huffman Encoding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 550);

        // Set a custom background color for the window
        frame.getContentPane().setBackground(new Color(240, 240, 255));

        // Create panels with padding and margin
        JPanel inputPanel = new JPanel();
        JPanel outputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Equal spacing of 10
        outputPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Equal spacing of 10
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered buttons with 20 horizontal spacing

        // Add padding to panels
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the input panel
        outputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around output panel
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around button panel

        // Input area
        JLabel inputLabel = new JLabel("Enter text to encode:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 16));
        inputLabel.setForeground(new Color(70, 70, 70));
        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(Color.WHITE);
        inputField.setForeground(Color.BLACK);
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border around input field

        // Output areas
        JLabel codesLabel = new JLabel("Huffman Codes:");
        codesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea codesArea = new JTextArea();
        codesArea.setEditable(false);
        codesArea.setBackground(new Color(255, 255, 230));
        codesArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        codesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border around text area

        JLabel encodedLabel = new JLabel("Encoded Text:");
        encodedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea encodedArea = new JTextArea();
        encodedArea.setEditable(false);
        encodedArea.setBackground(new Color(255, 255, 230));
        encodedArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        encodedArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border around text area

        JLabel decodedLabel = new JLabel("Decoded Text:");
        decodedLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea decodedArea = new JTextArea();
        decodedArea.setEditable(false);
        decodedArea.setBackground(new Color(255, 255, 230));
        decodedArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        decodedArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border around text area

        // Buttons with padding and hover effects
        JButton encodeButton = new JButton("Encode");
        encodeButton.setFont(new Font("Arial", Font.BOLD, 14));
        encodeButton.setBackground(new Color(50, 150, 50));
        encodeButton.setForeground(Color.WHITE);
        encodeButton.setFocusPainted(false);
        encodeButton.setToolTipText("Click to encode the entered text");
        encodeButton.setPreferredSize(new Dimension(120, 40));

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(220, 50, 50));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setToolTipText("Click to clear all fields");
        clearButton.setPreferredSize(new Dimension(120, 40));

        // Add components to panels
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        outputPanel.add(codesLabel);
        outputPanel.add(new JScrollPane(codesArea));
        outputPanel.add(encodedLabel);
        outputPanel.add(new JScrollPane(encodedArea));
        outputPanel.add(decodedLabel);
        outputPanel.add(new JScrollPane(decodedArea));

        buttonPanel.add(encodeButton);
        buttonPanel.add(clearButton);

        // Add panels to frame
        frame.setLayout(new BorderLayout(10, 10)); // Set spacing between panels
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter some text!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Process Huffman Encoding
                Set<Character> charSet = text.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
                char[] characters = new char[charSet.size()];
                int index = 0;
                for (Character c : charSet) {
                    characters[index++] = c;
                }

                int[] frequencies = new int[characters.length];
                for (int i = 0; i < characters.length; i++) {
                    char currentChar = characters[i];
                    frequencies[i] = (int) text.chars().filter(ch -> ch == currentChar).count();
                }

                HuffmanNode root = HuffmanEncoding.buildHuffmanTree(characters, frequencies);
                Map<Character, String> huffmanCodes = new HashMap<>();
                HuffmanEncoding.generateCodes(root, "", huffmanCodes);

                // Generate outputs
                StringBuilder codesOutput = new StringBuilder();
                huffmanCodes.forEach((key, value) -> codesOutput.append(key).append(": ").append(value).append("\n"));

                String encodedText = HuffmanEncoding.encode(text, huffmanCodes);
                String decodedText = HuffmanEncoding.decode(encodedText, root);

                // Set outputs to text areas
                codesArea.setText(codesOutput.toString());
                encodedArea.setText(encodedText);
                decodedArea.setText(decodedText);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear all fields
                inputField.setText("");
                codesArea.setText("");
                encodedArea.setText("");
                decodedArea.setText("");
            }
        });

        // Add hover effects to buttons
        encodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                encodeButton.setBackground(new Color(50, 200, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                encodeButton.setBackground(new Color(50, 150, 50));
            }
        });

        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(255, 70, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(220, 50, 50));
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
