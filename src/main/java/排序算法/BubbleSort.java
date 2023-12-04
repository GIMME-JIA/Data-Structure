package 排序算法;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class BubbleSort {
    /**
     * 冒泡排序
     *
     * @param a
     */
    private static void bubbleSort(int[] a) {
        int j = a.length - 1;

        do {
            int x = 0;
            for (int i = 0; i < j; i++) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    x = i;      // 记录交换的位置
                }
            }
            j = x;  // 下次遍历的终点就是最后一次交换的位置
        } while (j != 0);
    }


}
