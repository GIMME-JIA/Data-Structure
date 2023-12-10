package 排序算法;



import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * 基数排序
 *      基数排序的时间复杂度为O(d * (n + k))，
 *      其中d是字符串的位数（假设字符串的长度为d），n是字符串数组的长度，k是进制数（128以10进制表示的最大值）。
 *      由于d、n和k都是比较小的常数，因此基数排序是一种比较高效的排序算法。
 */
public class RadixSort {

    /**
     * 使用基数排序算法对字符串数组进行排序
     * @param a 要排序的字符串数组
     * @param length 字符串数组的长度
     */
    public static void radixSort(String[] a, int length) {
        ArrayList<String>[] buckets = new ArrayList[128];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 从最后一个字符开始遍历字符串数组a
        for (int i = length - 1; i >= 0 ; i--) {
            for (String s : a) {
                // 将每个字符串按字符拆分并存入对应的桶buckets[i]
                buckets[s.charAt(i) ].add(s);
            }
            int k = 0;
            // 遍历每个桶
            for (ArrayList<String> bucket : buckets) {
                // 将每个桶中的字符串按顺序添加到字符串数组a中
                for (String s : bucket) {
                    a[k++] = s;
                }
                // 清空每个桶中的字符串
                bucket.clear();
            }
        }
    }


    @Test
    public void testRadixSort() {

        String[] a = {"321", "123", "876", "543", "345", "234"};
        int length = 3;

        RadixSort.radixSort(a, length);

        String[] expected = {"123", "234", "321", "345", "543", "876"};

        assertArrayEquals(expected, a);
    }

}
