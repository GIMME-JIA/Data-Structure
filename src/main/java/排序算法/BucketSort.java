package 排序算法;

/**
 * 桶排序
 */
public class BucketSort {

    /**
     * 桶排序的实现
     *      要点：
     *
     * @param a 要排序的数组
     * @param range 每个桶的大小
     */
    public static void bucketSort(int[] a, int range) {
        int max = a[0];
        int min = a[0];
        // 找到待排序数组的最大最小值
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
            if (a[i] < min) {
                min = a[i];
            }
        }

        // TODO: 2023/11/29 学完动态数组回来放行
        // 1. 准备桶
       /* DynamicArray[] buckets = new DynamicArray[(max - min) / range + 1];  //数组长度为 (max - min) / range + 1
        System.out.println(buckets.length);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new DynamicArray();
        }
        // 2. 放入年龄数据
        for (int age : a) {
            buckets[(age - min) / range].addLast(age);  // 计算桶的索引：(age - min) / range
        }
        int k = 0;
        for (DynamicArray bucket : buckets) {
            // 3. 排序桶内元素
            int[] array = bucket.array();
            InsertionSort.insertionSort(array);
            System.out.println(Arrays.toString(array));
            // 4. 把每个桶排序好的内容，依次放入原始数组
            for (int v : array) {
                a[k++] = v;
            }
        }*/
    }
}
