package 排序算法;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * 插入排序：适合数据量小，有序度高的排序
 */
public class InsertionSort {
    /**
     * 插入排序：将下一个元素插入到先前已排序好的数组
     * @param a
     */
    static void insertionSort(int[] a){
        for (int low = 1; low < a.length; low++) {    // 第0个当作已排序的数组
            int willInsert = a[low];  // 记录要插入的元素
            int i = low -1;     // 已排好的最右边元素
            // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
            while(i >= 0 && willInsert < a[i]){
                a[i+1] = a[i];
                i--;
            }
            // 插入空出来的位置
            if(i != low -1){
                a[i+1] =willInsert;
            }
        }
    }

    @Test
    public void testInsertionSort() {
        // Test case 1: sorted array
        int[] input1 = {1, 2, 3, 4, 5};
        insertionSort(input1);
        int[] expected1 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected1, input1);

        // Test case 2: reverse sorted array
        int[] input2 = {5, 4, 3, 2, 1};
        insertionSort(input2);
        int[] expected2 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected2, input2);

        // Test case 3: mixed array
        int[] input3 = {3, 2, 5, 1, 4};
        insertionSort(input3);
        int[] expected3 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected3, input3);

        // Test case 4: empty array
        int[] input4 = {};
        insertionSort(input4);
        int[] expected4 = {};
        assertArrayEquals(expected4, input4);

        // Test case 5: single element array
        int[] input5 = {1};
        insertionSort(input5);
        int[] expected5 = {1};
        assertArrayEquals(expected5, input5);
    }
}
