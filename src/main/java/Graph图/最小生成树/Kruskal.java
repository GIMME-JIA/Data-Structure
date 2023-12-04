package Graph图.最小生成树;

import Graph图.并查集.DisjointSet;
import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Kruskal {
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        List<Vertex> vertices = List.of(v1, v2, v3, v4, v5, v6, v7);
        PriorityQueue<Edge> queue = new PriorityQueue<>(List.of(
                new Edge(vertices, 0, 1, 2),
                new Edge(vertices, 0, 2, 4),
                new Edge(vertices, 0, 3, 1),
                new Edge(vertices, 1, 3, 3),
                new Edge(vertices, 1, 4, 10),
                new Edge(vertices, 2, 3, 2),
                new Edge(vertices, 2, 5, 5),
                new Edge(vertices, 3, 4, 7),
                new Edge(vertices, 3, 5, 8),
                new Edge(vertices, 3, 6, 4),
                new Edge(vertices, 4, 6, 6),
                new Edge(vertices, 5, 6, 1)
        ));

        kruskal(queue, vertices.size());
    }

    /**
     * 最小生成树，kruskal算法，重点看边的权值
     *
     * @param queue
     * @param size
     */
    static void kruskal(PriorityQueue<Edge> queue, int size) {
        List<Edge> edgeList = new ArrayList<>();    // 最终结果边集合
        // 创建一个并查集,并设置大小
        DisjointSet disjointSet = new DisjointSet(size);
        while (edgeList.size() < size - 1){         // 最小生成树的边为顶点-1
            Edge minEdge = queue.poll();    // 获取权值最小的边
            int start = disjointSet.find(minEdge.start);    // 找到该边的初始顶点
            int end = disjointSet.find(minEdge.end);    // 找到该边的结束顶点
            if (start != end) {
                // 不相等说明这两顶点不相通
                disjointSet.union(start,end);   // 令他们相通
                edgeList.add(minEdge);      // 把这条边加入结果集
            }
        }

        for (Edge edge : edgeList) {
            System.out.println(edge);
        }
    }
}
