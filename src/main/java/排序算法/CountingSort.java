package 排序算法;


import java.util.Arrays;



/**
 * 计数排序
 */
public class CountingSort {

    public static void countingSort(int[] a) {
        int max = a[0];
        int min = a[0];
        // 找到数组中的最大值
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }else if(a[i] < min){
                min = a[i];
            }
        }
        // 创建最大值+1的计数数组
        int[] count = new int[max - min + 1];
        for (int i = 0; i < a.length; i++) {
            count[a[i] - min]++;
        }
        System.out.println(Arrays.toString(count));

        //
        int k = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = count[i]; j > 0 ; j--) {
                a[k++] = min + i;
            }
        }
        System.out.println(Arrays.toString(a));
    }



}


