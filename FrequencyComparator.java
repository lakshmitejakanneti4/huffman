import java.util.Comparator;

class FrequencyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency; // Ascending order by frequency
    }
}
