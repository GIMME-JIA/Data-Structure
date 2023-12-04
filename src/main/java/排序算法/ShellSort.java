package 排序算法;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ShellSort {
    /**
     * 希尔排序
     * @param a
     */
    private void shellSort(int [] a){
        for (int gap = a.length >> 1;gap >= 1;gap = gap>>1){
            for (int low = gap; low < a.length; low++) {    // 第0个当作已排序的数组
                int willInsert = a[low];  // 记录要插入的元素
                int i = low -gap;     // 已排好的最右边元素
                // 自右向左找插入位置，如果比待插入元素大，则不断右移，空出插入位置
                while(i >= 0 && willInsert < a[i]){
                    a[i+gap] = a[i];
                    i-=gap;
                }
                // 插入空出来的位置
                if(i != low -gap){
                    a[i+gap] =willInsert;
                }
            }
        }
    }

    @Test
    public void testShellSort() {
        int[] a = {9, 4, 6, 2, 1, 7, 8, 5, 3};
        shellSort(a);
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expected, a);
    }
}
