package Graph图.相关类;

import java.util.List;

// 边类
public class Edge implements Comparable<Edge>{

     public Vertex linked;      // 有向图中，连接指向的顶点

     public int weight;     // 权值

    public int start;      // 起始顶点
    public int end;        // 结束顶点

    List<Vertex> vertices;      // 顶点集合，记录指向的顶点

    public Edge(Vertex linked) {
        this.linked = linked;
    }

    public Edge(Vertex linked, int weight) {
        this.linked = linked;
        this.weight = weight;
    }


    public Edge(List<Vertex> vertices, int start, int end, int weight) {
        this.weight = weight;
        this.start = start;
        this.end = end;
        this.vertices = vertices;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight,o.weight);
    }

    @Override
    public String toString() {
        return vertices.get(start).name + "<->" + vertices.get(end).name + "(" + weight + ")";
    }
}
