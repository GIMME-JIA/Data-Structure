package 排序算法;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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

    /**
     * 递归版冒泡
     *      * low 与 high 为未排序范围
     *      * j 表示的是未排序的边界，下一次递归时的 high
     *        * 发生交换，意味着有无序情况
     *        * 最后一次交换（以后没有无序）时，左侧 i 仍是无序，右侧 i+1 已然有序
     *      * 视频中讲解的是只考虑 high 边界的情况，参考以上代码，理解在 low .. high 范围内的处理方法
     * @param a
     * @param low
     * @param high
     */
    private static void bubbleByRecursion(int[] a, int low, int high) {
        if(low == high) {
            return;
        }
        int j = low;
        for (int i = low; i < high; i++) {
            if (a[i] > a[i + 1]) {
                swap(a, i, i + 1);
                j = i;
            }
        }
        bubbleByRecursion(a, low, j);
    }
    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }



    @Test
    public void testBubbleByRecursion() {
        int[] a = {5, 3, 8, 4, 2};
        BubbleSort.bubbleByRecursion(a, 0, a.length -1);
        assertArrayEquals(new int[]{2, 3, 4, 5, 8}, a, String.valueOf(0));
    }
}
