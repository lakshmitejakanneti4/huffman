class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left, right;

    // Constructor
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
}
