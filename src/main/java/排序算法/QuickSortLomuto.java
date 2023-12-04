package 排序算法;

import org.junit.Assert;
import org.junit.Test;

/**
 * 单边循环快排（lomuto洛穆托分区方案）
 *      核心：每轮找到一个基准点元素，把比他小的放到左边，比它大的放到右边，成为分区
 *      1、选择最右边元素作为基准点元素
 *      2、j指针负责找到比基准点小的元素，一旦找到与i进行交换
 *      3、i指针指向大于基准点元素的左边界，也是每次交换的目标索引
 *      4、最后基准点与i交换，i即为分区位置
 */
public class QuickSortLomuto {

    public static void sort(int[]a){
        quick(a,0,a.length-1);
    }

    private static void quick(int[] a, int left, int right) {

        if(left >= right){
            return;
        }
        int p = partition(a,left,right);    // p代表基准点索引
        quick(a,left,p-1);      // 基准点左边
        quick(a,p+1,right);     // 基准点右边

    }

    /**
     * 分区
     * partition
     * n. 隔墙，隔板；（国家的）分裂，分治；（化学）分离层；（计算机）存储分区；（数学）分割
     * v. 分割，分裂（国家）；（用隔板、隔扇等）隔开，分隔
     * @param a
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] a, int left, int right) {
        // 1、记录基准点
        int pv = a[right];
        int i = left;   // 找比基准点大的
        int j = left;   // 遍历找小

        while(j<right){
            if(a[j] < pv){  // j指针负责找到比基准点小的元素，一旦找到与i进行交换
                if(i !=j){
                    swap(a,i,j);
                }
                i++;
            }
            j++;
        }
        swap(a,right,i);    // 交换基准点和大的元素
        return i;
    }

    /**
     * 交换数组元素
     * @param a
     * @param i
     * @param j
     */
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    @Test
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

}
