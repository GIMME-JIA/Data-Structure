package 排序算法;





public class SelectionSort {
    /**
     * 交换排序：找最大或者最小，跟最左或最右边交换
     *
     * @param a
     */
    private static void selectionSort(int[] a) {
        // 1、选择轮数：数组长度-1
        // 2、交换的索引位置
        for (int i = 0; i < a.length - 1; i++) {

            int min = i;

            for (int j = i; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            if(min!= i){
                swap(a,min,i);
            }
        }
    }



    /**
     * 交换数组元素位置
     * @param a
     * @param i
     * @param j
     */
    private static void swap(int []a,int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }



    @Test
    public void testSelectionSort() {
        int[] arr1 = {3, 1, 4, 2, 5};
        selectionSort(arr1);
        int[] expected1 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected1, arr1);

        int[] arr2 = {5, 4, 3, 2, 1};
        selectionSort(arr2);
        int[] expected2 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected2, arr2);

        int[] arr3 = {1, 2, 3, 4, 5};
        selectionSort(arr3);
        int[] expected3 = {1, 2, 3, 4, 5};
        assertArrayEquals(expected3, arr3);
    }
}
