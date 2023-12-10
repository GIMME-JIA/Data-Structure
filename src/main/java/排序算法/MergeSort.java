package 排序算法;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * 归并排序:适合数据量大的排序
 */
public class MergeSort {

    /**
     * 归并排序递归版
     *
     * @param a
     */
    private static void mergeSort(int[] a) {
        int[] a2 = new int[a.length];
        split(a, 0, a.length - 1, a2);
    }

    /**
     * 归并分治和
     *
     * @param a     原数组
     * @param left  左边界
     * @param right 右边界
     * @param a2    拷贝数组
     */
    private static void split(int[] a, int left, int right, int[] a2) {

        // 2、治
        if (left == right) {
            return;
        }

        // 1、分
        int mid = (left + right) >>> 1;
        split(a, 0, mid, a2);
        split(a, mid + 1, right, a2);

        // 3、和
        merge(a, left, mid, mid + 1, right, a2);

        System.arraycopy(a2, left, a, left, right - left + 1);
    }

    /**
     * a1 原始数组
     * i~iEnd 第一个有序范围
     * j~jEnd 第二个有序范围
     * a2 临时数组
     * <p>
     * 1. 初始化指针k为i，并进入一个循环，条件为i <= iEnd且j <= jEnd。
     * 2. 在循环中，比较a1[i]和a1[j]的大小。
     * 3. 如果a1[i]小于a1[j]，将a1[i]赋值给a2[k]，并将i指针向后移动一位。
     * 4. 否则，将a1[j]赋值给a2[k]，并将j指针向后移动一位。
     * 5. 不断更新k指针。
     * 6. 当i > iEnd时，说明a1数组已经全部被遍历完，此时从a1数组的j位置开始，将剩余的元素复制到a2数组的k位置，复制的长度为jEnd - j + 1。
     * 7. 当j > jEnd时，说明a2数组已经全部被遍历完，此时从a1数组的i位置开始，将剩余的元素复制到a2数组的k位置，复制的长度为iEnd - i + 1。  最终，a2数组中的元素按照大小顺序排列，即为合并后的结果。
     *
     * @param a1
     * @param i
     * @param iEnd
     * @param j
     * @param jEnd
     * @param a2
     */
    public static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2) {
        int k = i;
        while (i <= iEnd && j <= jEnd) {
            if (a1[i] < a1[j]) {
                a2[k] = a1[i];
                i++;
            } else {
                a2[k] = a1[j];
                j++;
            }
            k++;
        }
        if (i > iEnd) {
            System.arraycopy(a1, j, a2, k, jEnd - j + 1);
        }
        if (j > jEnd) {
            System.arraycopy(a1, i, a2, k, iEnd - i + 1);
        }
    }


    private static void MergeSort2(int[] a) {
        int[] a2 = new int[a.length];
        // width 代表有序区间的宽度，取值依次为 1、2、4。。。。
        for (int width = 1; width < a.length; width *= 2) {
            // [left，right]分别代表待合并区间的左右边界
            for (int left = 0; left < a.length; left += 2 * width) {
                int right = Math.min(left + 2 * width - 1, a.length - 1);
                int m = Math.min(left + width - 1, a.length - 1);
                merge(a, left, m, m + 1, right, a2);
            }
            System.arraycopy(a2, 0, a, 0, a.length);
        }
    }


    @Test
    public void testMergeSort2() {
        int[] a = {5, 2, 8, 9, 1, 3};
        MergeSort.MergeSort2(a);
        int[] expected = {1, 2, 3, 5, 8, 9};
        assertArrayEquals(expected, a);
    }

    @Test
    public void testMergeSort() {
        int[] arr1 = {5, 1, 4, 2, 8};
        mergeSort(arr1);
        int[] expected1 = {1, 2, 4, 5, 8};
        assertArrayEquals(expected1, arr1);

        int[] arr2 = {9, 7, 3, 6, 10};
        mergeSort(arr2);
        int[] expected2 = {3, 6, 7, 9, 10};
        assertArrayEquals(expected2, arr2);

        int[] arr3 = {2, 4, 1, 3, 5};
        mergeSort(arr3);
        int[] expected3 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected3, arr3);
    }

}
