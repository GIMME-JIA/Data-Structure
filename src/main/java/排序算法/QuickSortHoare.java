package 排序算法;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 双边循环要点
 * 选择最左侧元素作为基准点j找比基准点小的，i找比基准点大的，
 * 一旦找到，二者进行交换i从左向右oj从右向左最后基准点与i交换，i即为基准点最终索引
 */
public class QuickSortHoare {

    public static void sort(int[] a) {
        quick(a, 0, a.length - 1);
    }

    private static void quick(int[] a, int left, int right) {

        if (left >= right) {
            return;
        }
        int p = partition(a, left, right);    // p代表基准点索引
        quick(a, left, p - 1);      // 基准点左边
        quick(a, p + 1, right);     // 基准点右边

    }

    /**
     * 分区
     * partition
     * n. 隔墙，隔板；（国家的）分裂，分治；（化学）分离层；（计算机）存储分区；（数学）分割
     * v. 分割，分裂（国家）；（用隔板、隔扇等）隔开，分隔
     *
     *      注意：
     *          1、内层循环要加i < j
     *          2、先处理j，再处理i
     *          分区不平衡：
     *          1、初始为逆序
     *              改进：随机基准点
     *          2、重复元素
     *              改进：while (i <= j) {     // 这里的等于不能少
     *                     // 1、j从右向左找小的
     *                     while (i <= j && a[j] > pv) {
     *                         j--;
     *                     }
     *                     // 2、i从左向右找大的
     *                     while (i <= j && a[i] < pv) {
     *                         i++;
     *                     }
     *                     // 3
     *                     if(i<=j){
     *                         swap(a, i, j);
     *                         i++;
     *                         j--;
     *                     }
     *                      swap(a, left, j);
     *                 }
     * @param a
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] a, int left, int right) {
        /*
        以随机元素作为基准点
        int index = ThreadLocalRandom.current().nextInt(right - left + 1) + left;
        swap(a,left,index);     // 把随机生成的基准点放到最左边
         */

        // 1、记录基准点,最左边
        int pv = a[left];
        int i = left;   // 找比基准点大的
        int j = right;   // 遍历找小
        while (i < j) {
            // 1、j从右向左找小的
            while (i < j && a[j] > pv) {
                j--;
            }
            // 2、i从左向右找大的
            while (i < j && a[i] <= pv) {
                i++;
            }
            // 3
            swap(a, i, j);
        }
        swap(a, left, i);
        return i;
    }

    /**
     * 交换数组元素
     *
     * @param a
     * @param i
     * @param j
     */
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

  /*  @Test
    public void testSort() {
        // Test case 1: Array is already sorted
        int[] arr1 = {1, 2, 3, 4, 5};
        QuickSortLomuto.sort(arr1);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr1);

        // Test case 2: Array is reverse sorted
        int[] arr2 = {5, 4, 3, 2, 1};
        QuickSortLomuto.sort(arr2);
        Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr2);

        // Test case 3: Array with duplicate elements
        int[] arr3 = {2, 1, 3, 2, 4};
        QuickSortLomuto.sort(arr3);
        Assert.assertArrayEquals(new int[]{1, 2, 2, 3, 4}, arr3);

        // Test case 4: Array with negative numbers
        int[] arr4 = {-5, -2, -3, -1, 0};
        QuickSortLomuto.sort(arr4);
        Assert.assertArrayEquals(new int[]{-5, -3, -2, -1, 0}, arr4);
    }
*/
}
