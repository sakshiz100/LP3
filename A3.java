import java.util.Arrays;
import java.util.Comparator;

public class A3 {

    // Class to represent an item in the Knapsack problem
    static class Item {
        int value;
        int weight;
        double ratio; // value/weight ratio

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
            this.ratio = (double) value / weight; // Calculate value-to-weight ratio
        }
    }

    /**
     * Solves the Fractional Knapsack problem using the greedy approach.
     */
    public static double getMaxValue(Item[] items, int capacity) {
        // Sort items by their value-to-weight ratio in descending order
        // O(n log n) time complexity
        Arrays.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                // For descending order, compare o2.ratio to o1.ratio
                if (o2.ratio > o1.ratio)
                    return 1;
                if (o2.ratio < o1.ratio)
                    return -1;
                return 0;
            }
        });

        int currentWeight = 0;
        double finalValue = 0.0;

        // Iterate through the sorted items and add them to the knapsack
        // O(n) time complexity
        for (Item item : items) {
            if (currentWeight + item.weight <= capacity) {
                // Take the whole item
                currentWeight += item.weight;
                finalValue += item.value;
            } else {
                // Take a fraction of the item
                int remainingCapacity = capacity - currentWeight;

                // Fraction = (remainingCapacity / item.weight)
                double fraction = (double) remainingCapacity / item.weight;

                // Add the fraction's value
                finalValue += item.value * fraction;

                // Knapsack is full, so we stop
                break;
            }
        }

        return finalValue;
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        // Example data
        Item[] items = {
                new Item(60, 10), // Value=60, Weight=10, Ratio=6.0
                new Item(100, 20), // Value=100, Weight=20, Ratio=5.0
                new Item(120, 30) // Value=120, Weight=30, Ratio=4.0
        };
        int knapsackCapacity = 50; // W

        double maxValue = getMaxValue(items, knapsackCapacity);

        System.out.println("Maximum value in Knapsack for capacity " + knapsackCapacity + " is: "
                + String.format("%.2f", maxValue));

        // Total time is dominated by the sorting step
        System.out.println("\nTime Complexity: O(n log n) due to sorting");
        System.out.println("Space Complexity: O(1) (if sorting is in-place) or O(n) for storing items/ratios.");
    }
}