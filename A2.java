import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class A2 {

    // A node in the Huffman Tree
    static class HuffmanNode {
        char data;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(char data, int frequency) {
            this.data = data;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.data = '\0'; // Internal node has null character
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
    }

    // Comparator for the PriorityQueue (Min Heap)
    static class FrequencyComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode x, HuffmanNode y) {
            // Compare nodes based on their frequency (Min-Heap based on frequency)
            return x.frequency - y.frequency;
        }
    }

    /**
     * Builds the Huffman Tree using a greedy approach (Min-Heap).
     */
    public static HuffmanNode buildHuffmanTree(HashMap<Character, Integer> charFrequencies) {
        // Create a Min Heap (PriorityQueue)
        PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>(new FrequencyComparator());

        // i) Create a leaf node for each unique character and build a min heap of all
        // leaf nodes
        for (Map.Entry<Character, Integer> entry : charFrequencies.entrySet()) {
            minHeap.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Repeat steps #2 and #3 until the heap contains only one node
        while (minHeap.size() > 1) {
            // ii) Extract two nodes with the minimum frequency from the min heap
            HuffmanNode x = minHeap.poll();
            HuffmanNode y = minHeap.poll();

            // iii) Create a new internal node with a frequency equal to the sum of the two
            // nodes frequencies
            // Make the first extracted node as its left child and the other extracted node
            // as its right child
            HuffmanNode newNode = new HuffmanNode(x.frequency + y.frequency, x, y);

            // iv) Add this node to the min heap
            minHeap.add(newNode);
        }

        // The remaining node is the root node and the tree is complete
        return minHeap.poll();
    }

    /**
     * Traverses the Huffman Tree to generate codes for each character.
     * While moving to the left child, write '0' to the code.
     * While moving to the right child, write '1' to the code.
     * Print the code when a leaf node is encountered.
     */
    public static void generateCodes(HuffmanNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            // Leaf node: a character node
            huffmanCodes.put(root.data, code);
            return;
        }

        // Go left (0)
        generateCodes(root.left, code + "0", huffmanCodes);
        // Go right (1)
        generateCodes(root.right, code + "1", huffmanCodes);
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        // Example frequencies from the lab manual
        HashMap<Character, Integer> frequencies = new HashMap<>();
        frequencies.put('a', 5);
        frequencies.put('b', 9);
        frequencies.put('c', 12);
        frequencies.put('d', 13);
        frequencies.put('e', 16);
        frequencies.put('f', 45);

        HuffmanNode root = buildHuffmanTree(frequencies);

        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);

        System.out.println("Huffman Codes:");
        System.out.println("Character | Code-Word");
        System.out.println("---------------------");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.printf("%-9s | %s\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nTime Complexity: O(n log n) where n is the number of unique characters.");
        System.out.println("Space Complexity: O(n) to store the tree and codes.");
    }
}