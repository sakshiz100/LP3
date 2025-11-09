import java.util.Scanner;

public class A1 {

    // --- Recursive Implementation ---
    /**
     * Calculates the nth Fibonacci number recursively.
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) (due to recursion stack depth)
     */
    public static long fibonacciRecursive(int n) {
        if (n <= 1) {
            return n; // Base case: F(0)=0, F(1)=1
        }
        // Recurrence relation: F(n) = F(n-1) + F(n-2)
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    // --- Non-Recursive (Iterative) Implementation ---
    /**
     * Calculates the nth Fibonacci number non-recursively (iteratively).
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static long fibonacciNonRecursive(int n) {
        if (n <= 1) {
            return n; // Base case
        }

        long fnm2 = 0; // fnm2:=0
        long fnm1 = 1; // fnm1:=1
        long fn = 0;

        // for i:=2 to n do
        for (int i = 2; i <= n; i++) {
            fn = fnm1 + fnm2; // fn:=fnm1+fnm2
            fnm2 = fnm1; // fnm2:=fnm1
            fnm1 = fn; // fnm1:=fn
        }

        return fn;
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number n to find the nth Fibonacci number: ");
        int n = scanner.nextInt();
        scanner.close();

        if (n < 0) {
            System.out.println("Fibonacci number is not defined for negative input.");
            return;
        }

        // 1. Non-Recursive Solution
        long resultNonRec = fibonacciNonRecursive(n);
        System.out.println("\nNon-Recursive Fibonacci of " + n + " is: " + resultNonRec);
        System.out.println("Time Complexity (Non-Recursive): O(n)");
        System.out.println("Space Complexity (Non-Recursive): O(1)");

        // 2. Recursive Solution
        long resultRec = fibonacciRecursive(n);
        System.out.println("\nRecursive Fibonacci of " + n + " is: " + resultRec);
        // The time complexity is exponential, proportional to 2^n
        System.out.println("Time Complexity (Recursive): O(2^n)");
        // Space complexity is O(n) due to the depth of the recursion stack.
        System.out.println("Space Complexity (Recursive): O(n)");
    }
}
