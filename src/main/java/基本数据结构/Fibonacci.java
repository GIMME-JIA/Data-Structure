package 基本数据结构;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Fibonacci {
    public int climbStairs(int n) {
        int[] cache = new int[n+2];
        cache[1] = 1;
        cache[2] = 2;
        return f(n,cache);
    }

    /**
     记忆化递归的优化实现斐波那契
     */
    public int f(int n, int[] cache){
        if(cache[n] > 0){
            return cache[n];
        }
        cache[n] = f(n -1,cache) + f(n-2,cache);

        return cache[n];
    }

    @Test
    public void testClimbStairs() {
        Fibonacci fibonacci = new Fibonacci();

        // Test case 1

        // Test case 2
        int result2 = fibonacci.climbStairs(3);
        assertEquals(3, result2);

        // Test case 3
        int result3 = fibonacci.climbStairs(2);
        assertEquals(2, result3);

        // Test case 4
        int result4 = fibonacci.climbStairs(10);
        assertEquals(89, result4);

        // Test case 5

    }

    /*
        第二种实现方式
     */
    /**
     *
     * @param n
     */
    public static void print2(int n) {
        int[] row = new int[n];
        for (int i = 0; i < n; i++) {
            // 打印空格
            createRow(row, i);
            for (int j = 0; j <= i; j++) {
                System.out.printf("%-4d", row[j]);
            }
            System.out.println();
        }
    }

    private static void createRow(int[] row, int i) {
        if (i == 0) {
            row[0] = 1;
            return;
        }
        for (int j = i; j > 0; j--) {
            row[j] = row[j - 1] + row[j];
        }
    }


    @Test
    public void testPrint2() {
        int n = 5;
        int[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] actual = new int[n];
        Fibonacci.print2(n);
        for (int i = 0; i < n; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

}
