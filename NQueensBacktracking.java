import java.util.Arrays;
import java.util.Scanner;

public class NQueensBacktracking {

    private int N;
    private int[][] board;
    private int solutionCount = 0;

    public NQueensBacktracking(int n) {
        this.N = n;
        this.board = new int[n][n];
    }

    /**
     * Utility function to print the solution board (binary matrix).
     */
    private void printSolution() {
        solutionCount++;
        System.out.println("\nSolution " + solutionCount + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks if a queen can be safely placed at board[row][col].
     */
    private boolean isSafe(int row, int col) {
        int i, j;

        // 1. Check this row on the left side (for queens in previous columns) [cite:
        // 445]
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // 2. Check upper diagonal on left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // 3. Check lower diagonal on left side
        for (i = row, j = col; j >= 0 && i < N; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Recursive backtracking function to place queens column by column.
     */
    private boolean solveNQUtil(int col) {
        // Base case: If all columns are processed (all queens placed), return true
        // [cite: 449, 450]
        if (col >= N) {
            printSolution();
            // Since N-Queens can have multiple solutions, return 'false' to find others, or
            // 'true' to stop after the first.
            // We use 'true' to indicate a solution was found in this branch, but the
            // calling function handles finding others.
            return true;
        }

        // If the current column already has a queen (the pre-placed one), skip all
        // other placements in this column.
        boolean isPrePlaced = false;
        for (int row = 0; row < N; row++) {
            if (board[row][col] == 1) {
                isPrePlaced = true;
                break;
            }
        }

        if (isPrePlaced) {
            // Only continue the search from the next column
            return solveNQUtil(col + 1);
        }

        boolean foundSolution = false;

        // Try all rows in the current column [cite: 451]
        for (int i = 0; i < N; i++) {
            // If the queen can be placed safely in this row [cite: 453]
            if (isSafe(i, col)) {

                // Place the queen [cite: 453]
                board[i][col] = 1;

                // Recursively check if placing the queen here leads to a solution [cite: 453]
                if (solveNQUtil(col + 1)) {
                    foundSolution = true;
                    // We DO NOT break here, to continue searching for other solutions
                }

                // If placing queen doesn't lead to a solution, then backtrack [cite: 456]
                board[i][col] = 0; // Unmark (Backtrack)
            }
        }

        return foundSolution;
    }

    /**
     * Main solver function to set up the initial queen and start backtracking.
     */
    public void solveNQueens(int initialRow, int initialCol) {

        // Initialize the board for N x N
        for (int[] row : board) {
            Arrays.fill(row, 0);
        }

        // Step 1: Place the first queen
        if (initialRow >= 0 && initialRow < N && initialCol >= 0 && initialCol < N) {
            board[initialRow][initialCol] = 1;
        } else {
            System.out.println("Invalid initial position for the first Queen.");
            return;
        }

        // Start backtracking from the *first column (col=0)*
        solveNQUtil(0);

        if (solutionCount == 0) {
            System.out.println("Solution does not exist for N=" + N + " with the first queen placed at (" + initialRow
                    + ", " + initialCol + ")");
        } else {
            System.out.println("\nTotal solutions found: " + solutionCount);
        }

        System.out.println("Time Complexity (Worst Case): O(N!) [cite: 457]");
        System.out.println("Space Complexity: O(N^2) for the board array + O(N) for recursion stack.");
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the board size N (e.g., 4, 8): ");
        int n = scanner.nextInt();

        System.out.print("Enter the row for the pre-placed Queen (0 to N-1, e.g., 1 for 4x4): ");
        int initialRow = scanner.nextInt();

        System.out.print("Enter the column for the pre-placed Queen (0 to N-1, e.g., 0 for 4x4): ");
        int initialCol = scanner.nextInt();
        scanner.close();

        NQueensBacktracking queenSolver = new NQueensBacktracking(n);
        queenSolver.solveNQueens(initialRow, initialCol);
    }
}