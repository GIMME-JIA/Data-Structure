package 基础.二分查找;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinarySearch {


    /**
     * 二分查找基础版
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearchBasic(int[] arr,int target){
        
        int i = 0,j = arr.length -1;
        while(i <= j){
            int m = i + j / 2;
            if(target == arr[m]){
                return m;
            } else if (target > arr[m]) {
                i = m + 1;
            } else if (target < arr[m]) {
                j = m - 1;
            }
        }
        return -1;
    }


    /**
     * 二分查找优化版
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearch(int[] arr,int target){
        int i = 0;
        int j = arr.length;
        while(i < j){
            int mid = (i+j) >>> 1;
            if(arr[mid] < target){
                i = mid + 1;
            }else if(arr[mid] > target){
                j = mid;
            }else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 二分查找平衡版
     * @param arr
     * @param target
     * @return
     */
    public static int binarySearchBalance(int[] arr,int target){
        int i = 0;
        int j = arr.length;
        while(j - i > 1){
            int mid = (j + i) >>> 1;
            if(arr[mid] <= target){
                i = mid;
            }else {
                j = mid;
            }
        }
        if(arr[i] == target){
            return i;
        }else {
            return -1;
        }
    }

    /**
     * 返回最左侧元素索引
     * @param a
     * @param target
     * @return
     */
    public static int binarySearchLeftmost1(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m; // 记录候选位置
                j = m - 1;     // 继续向左
            }
        }
        return candidate;
    }

    /**
     * 返回最右侧索引
     * @param a
     * @param target
     * @return
     */
    public static int binarySearchRightmost1(int[] a, int target) {
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m; // 记录候选位置
                i = m + 1;	   // 继续向右
            }
        }
        return candidate;
    }


    /**
     * 二分查找数组中左侧第一个等于目标值的元素的下标
     * @param a 给定的有序数组
     * @param target 目标值
     * @return 若存在等于目标值的元素，则返回该元素的下标；否则返回该元素在数组中应该插入的位置的下标
     */
    public static int binarySearchLeftmost(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target <= a[m]) {
                j = m - 1;
            } else {
                i = m + 1;
            }
        }
        return i;
    }

    /**
     * 二分查找右边最接近的目标元素的索引
     * @param a 给定的有序数组
     * @param target 目标元素
     * @return 返回最右边目标元素的索引
     */
    public static int binarySearchRighttmost(int[] a, int target) {
        int i = 0, j = a.length;
        while (i < j) {
            int m = (i + j) >>> 1;
            if (target <= a[m]) {
                j = m;
            } else {
                i = m + 1;
            }
        }
        return i -1;
    }






    @Test
    public void testBinarySearchBalance() {
        int[] arr = {1, 2, 3, 4, 5};
        int target = 3;
        int result = BinarySearch.binarySearchBalance(arr, target);
        assertEquals(2, result);

        arr = new int[]{1, 2, 3, 4, 5};
        target = 6;
        result = BinarySearch.binarySearchBalance(arr, target);
        assertEquals(-1, result);

        arr = new int[]{1, 2, 3, 4, 5};
        target = 1;
        result = BinarySearch.binarySearchBalance(arr, target);
        assertEquals(0, result);
    }

    @Test
    public void testBinarySearch_NonExistingElement() {
        int[] arr = {1, 2, 3, 4, 6};
        int target = 2;
        int result = BinarySearch.binarySearch(arr, target);
        assertEquals(1, result);
    }

    @Test
    public void testBinarySearch_EmptyArray() {
        int[] arr = {};
        int target = 3;
        int result = BinarySearch.binarySearch(arr, target);
        assertEquals(-1, result);
    }


    
    
}
