package Graph图.并查集;

import java.util.Arrays;

/**
 * 不相交集合————并查集
 */
public class DisjointSet {
    int[] s;
    int[] size; // 每个顶点索引对应的元素个数
    // 索引对应顶点
    // 元素是用来表示与之有关系的顶点
    /*
        索引  0  1  2  3  4  5  6
        元素 [0, 1, 2, 3, 4, 5, 6] 表示一开始顶点直接没有联系（只与自己有联系）

    */

    public DisjointSet(int size) {
        s = new int[size];
        for (int i = 0; i < size; i++) {
            s[i] = i;
            this.size[i] = 1;
        }
    }

    // find 是找到老大，即找根
    public int find(int x) {
        if (x == s[x]) {
            return x;
        }
//        return find(s[x]);
        // 找索引优化：路径压缩：对寻找到的根重新指向
        return s[x] = find(s[x]);
    }

    // union 是让两个集合“相交”，即两根节点索引合并为一个集合，x、y 是根索引
    public void union(int x, int y) {
//        s[y] = x;       // 将x连接到y，以x为根
    // 合并优化：以集合中元素多的一方为根进行合并
        if (size[x] < size[y]){
            // y中的元素多，合并的时候以y为根
            s[x] = y;
            // 更新y集合中的元素
            size[y] += size[x];
        }else {
            // x中的元素多，合并的时候以x为根
            s[y] = x;
            size[x] += size[y];
        }
    }

    @Override
    public String toString() {
        return "内容：" + Arrays.toString(s) + "\n大小：" + Arrays.toString(s);
    }
}
