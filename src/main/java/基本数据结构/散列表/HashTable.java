package 基本数据结构.散列表;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 哈希表
 * <p>
 * 给每份数据分配一个编号，放入表格（数组）。
 * 建立编号与表格索引的关系，将来就可以通过编号快速查找数据
 * 1.理想情况编号当唯一，数组能容纳所有数据
 * 2.现实是不能说为了容纳所有数据造一个超大数组,编号也有可能重复
 * <p>
 * 解决
 * 1.有限长度的数组,以【拉链】方式存储数据
 * 2,允许编号适当重复,通过数据自身来进行区分
 */
public class HashTable {

    /**
     * 节点类
     */
    static class Entry {
        int hash;       // 哈希码
        Object key;     // 键
        Object value;   // 值
        Entry next;

        public Entry(int hash, Object key, Object value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    Entry[] table = new Entry[16];
    int size = 0;           // 元素个数
    float loadFactor = 0.75f;       // 加载因子，0.75表示当数组大于四分之三的时候进行扩容
    int threshold = (int) (table.length * loadFactor);      // 阈值

    /*
        求模运算替换为位运算
        - 前提：数组长度是2的n次方
        - hash % 数组长度 等价于 hash & （数组长度-1）
     */

    /**
     * 根据hash获取value
     *
     * @param hash
     * @param key
     * @return value
     */
    Object get(int hash, Object key) {
        int index = hash & (table.length - 1);
        Entry p = table[index];
        while (p != null) {
            if (p.key.equals(key)) {
                return p.value;
            }
            p = p.next;
        }
        return null;
    }

    /**
     * 向hashtable中插入新的key和value，如果key重复，则更新value
     *
     * @param hash
     * @param key
     * @param value
     */
    void put(int hash, Object key, Object value) {
        int index = hash & (table.length - 1);

        Entry p = table[index];
        if (p == null) {
            table[index] = new Entry(hash, key, value);
        } else {
            // 不为空，要判断是否重复
            while (true) {
                if (p.key.equals(key)) {
                    p.value = value;
                    return;
                }
                if (p.next == null) {
                    break;
                }
                p = p.next;
            }

            p.next = new Entry(hash, key, value);

        }

        size++;

        if (size > threshold) {
            resize();
        }
    }

    /**
     * 数组扩容
     * 新建数组，拷贝元素
     */
    private void resize() {
        Entry[] newTable = new Entry[table.length << 1];        // 创建容量翻倍的新数组

        for (int i = 0; i < table.length; i++) {
            // 拷贝元素
            Entry p = table[i];     // 拿到表中每个索引下的链表头节点

            if (p != null) {
                Entry a = null;
                Entry b = null;
                Entry aHead = null;
                Entry bHead = null;
                // 遍历实现拷贝到新table
                while (p != null) {
                    // 拆分链表
                /*
                    规律：
                        1、一个链表最多拆成两个
                        2、hash & table.length == 0 的一组
                        3、hash & table.length ！= 0 的一组

                                                   p
                            0->8->16->24->32->40->48->null
                                        a
                            0->16->32->48->null
                                    b
                            8->24->40->null
                 */
                    if ((p.hash & table.length) == 0) {   // 保留在原来索引处，即a
                        if (a != null) {   // 说明a已经存在节点
                            a.next = p;
                        } else {
                            aHead = p;
                        }
                        a = p;
                    } else {
                        if (b != null) {   // 说明b已经存在节点
                            b.next = p;
                        } else {
                            bHead = p;
                        }
                        b = p;
                    }
                }
                // 循环结束，说明链表已经拆分完毕
                // 将两链表重新并回新数组
                // 规律： a 链表保持索引位置不变，b 链表索引位置+table.length
                if (aHead != null && a != null) {
                    a.next = null;
                    newTable[i] = aHead;
                }
                if (bHead != null && b != null) {
                    b.next = null;
                    newTable[i + table.length] = bHead;
                }
            }
        }

        table = newTable;       // 更新表
        threshold = (int) (loadFactor * table.length);  // 更新阈值
    }


    /**
     * 删除节点
     *
     * @param hash
     * @param key
     * @return 删除节点的值
     */
    Object remove(int hash, Object key) {
        int index = hash & (table.length - 1);
        if (table[index] == null) {
            return null;
        }

        Entry p = table[index];
        Entry pre = null;
        Object value = null;
        while (p != null) {
            if (p.key.equals(key)) {
                // 执行删除
                value = p.value;
                if (pre == null) {    // 说明删掉的是第一个
                    table[index] = p.next;
                } else {
                    pre.next = p.next;
                }
                size--;
            }
            pre = p;
            p = p.next;
        }
        return value;
    }


    /*
        使用Object生成hashcode替代原来的手动生成
     */
    public Object get(Object key) {
        int hash = hash(key);
        return get(hash, key);
    }

    public void put(Object key, Object value) {
        int hash = hash(key);
        put(hash, key, value);
    }

    public Object remove(Object key) {
        int hash = hash(key);
        return remove(hash, key);
    }

    public static int hash(Object key){
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);    // 跟高十六位做异或运算，减少哈希冲突的几率
    }


    /**
     * 检查hash表的分散性，打印每个索引下的entry
     */
    public void print() {
        int[] sum = new int[table.length];
        for (int i = 0; i < table.length; i++) {
            Entry p = table[i];
            while (p != null) {
                sum[i]++;
                p = p.next;
            }
        }
        System.out.println(Arrays.toString(sum));

        Map<Integer, Long> result = Arrays.stream(sum).boxed()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        System.out.println(result);
    }


    /**
     * String.hashcode()的具体实现分析
     *
     * @param args
     */
    public static void main(String[] args) {
       /* String s1 = "bac";
        String s2 = new String("abc");

        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());

        // 原则：值相同的字符串生成相同的 hash 码, 尽量让值不同的字符串生成不同的 hash 码
    *//*
    对于 abc  a * 100 + b * 10 + c
    对于 bac  b * 100 + a * 10 + c
     *//*
        int hash = 0;
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            System.out.println((int) c);
            // (a*10 + b)*10 + c  ==>  a*100 + b*10 + c  2^5
            hash = (hash << 5) - hash + c;
        }
        System.out.println(hash);*/


        // 测试 Object.hashCode
        HashTable table = new HashTable();
        for (int i = 0; i < 200; i++) {
            Object obj = new Object();
            table.put(obj, obj);
        }
        table.print();
    }


   /*
   1、为什么计算索引位置用式子：
            【hash &（数组长度-1）】 等价于 【hash % 数组长度】
            10进制中去除以10，100， 1000时，余数就是被除数的后1，2，3 位
                        10^1 10^2 10^3
            2进制中去除以 10，100，1000时，余数也是被除数的后1，2，3 位
                        2^1 2^2 2^3 2^4
            因此求余数就是求二进制的后几位，而保留二进制后几位可以通过与
            1，3，7，11 ... 等数字按位与来实现，这些数字恰巧是数组长度-1

    2、为什么旧链表会拆分成两条，一条 hash & 旧数组长度==0 另一条！=0
        旧数组长度换算成二进制后，其中的 1 就是我们要检查的倒数第几位
            旧数组长度 8 二进制 => 1000 检查倒数第4位
            旧数组长度 16 二进制 => 10000 检查倒数第5位
        hash & 旧数组长度 就是用来检查扩容前后索引位置（余数）会不会变

    3、为什么拆分后的两条链表,一个原索引不变,另一个是原索引+旧数组长度


    它们都有个共同的前提：数组长度是2的 n 次方
    */


    /**
     思路：
        1、循环遍历数组，拿到每个数字x
        2、以target-x为key到hash表查找
            1）若没找到，x为key，索引为value，放入hash表
            2）若找到了，返回x和配对数的索引
     */
    public int[] twoSum(int[] nums, int target) {
        Hashtable<Integer,Integer> hashtable = new Hashtable<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            int ys = target - x;
            if(hashtable.containsKey(ys)){
                return new int[]{i,hashtable.get(ys)};
            }else {
                hashtable.put(x,i);
            }
        }
        return null;
    }


    /**
     * 要点：
     *      1.用 begin 和 end 表示子串开始和结束位置
     *      2.用 hash 表检查重复字符
     *      3.从左向右查看每个字符，如果：
     *          - 没遇到重复字符，调整 end
     *          - 遇到重复的字符，调整 begin
     *          - 将当前字符放入 hash 表
     *      4.end-begin + 1个
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character,Integer> hashMap = new HashMap<>();
        int begin = 0;
        int maxLength = 0;
        for (int end = 0; end < s.length(); end++) {
            char ch = s.charAt(end);
            if(hashMap.containsKey(ch)){
                begin = Math.max(begin,hashMap.get(ch) + 1);
            }
            hashMap.put(ch,end);        // 更新字符索引
            maxLength = Math.max(end - begin + 1, maxLength);
        }
        return maxLength;
    }
}
