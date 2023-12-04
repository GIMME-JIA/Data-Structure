package Graph图.相关类;

import java.util.List;
import java.util.Objects;

// 顶点类
public class Vertex implements Comparable<Vertex> {
     public String name;    // 顶点名称
     public List<Edge> edges;       // 对应的边，在有向图中，对应的是入度的边

     public boolean visited;        // 是否被访问过

    public int indegree;       // 入度

    public int status; // 0未访问，1访问中，2已访问

    public Vertex pre; // 记录前一个节点，用于狄克斯特拉


    static final Integer INF = Integer.MAX_VALUE;
    public int distance = INF;
    public Vertex(String name) {
        this.name = name;
    }


    /**
     * 返回负数，表示当前对象小于比较对象
     * 返回正数，表示当前对象大于比较对象
     * 返回0，表示当前对象等于比较对象
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Vertex o) {
        return this.distance - o.distance;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}



