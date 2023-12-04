package Graph图.最短路径;


import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FloydWarshall {
    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");

        v1.edges = List.of(new Edge(v3, -2));
        v2.edges = List.of(new Edge(v1, 4), new Edge(v3, 3));
        v3.edges = List.of(new Edge(v4, 2));
        v4.edges = List.of(new Edge(v2, -1));
        List<Vertex> graph = List.of(v1, v2, v3, v4);
        /*
                直接连通
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   3   ∞
            v3  ∞   ∞   0   2
            v4  ∞   -1  ∞   0

                k=0 借助v1到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   2   ∞
            v3  ∞   ∞   0   2
            v4  ∞   -1  ∞   0

                k=1 借助v2到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  ∞
            v2  4   0   2   ∞
            v3  ∞   ∞   0   2
            v4  3   -1  1   0

                k=2 借助v3到达其它顶点
                v1  v2  v3  v4
            v1  0   ∞   -2  0
            v2  4   0   2   4
            v3  ∞   ∞   0   2
            v4  3   -1  1   0

                k=3 借助v4到达其它顶点
                v1  v2  v3  v4
            v1  0   -1   -2  0
            v2  4   0   2   4
            v3  5   1   0   2
            v4  3   -1  1   0
         */
        floydWarshall(graph);
    }


    /**
     * floyd-warshall多源最短路径算法
     */
    public static void floydWarshall(List<Vertex> graph) {
        // 1)创建dist数组，记录两顶点间的距离
        int size = graph.size();
        int[][] dist = new int[size][size];
        Vertex[][] prev = new Vertex[size][size];       // 记录到达某路径的前一个顶点
        for (int i = 0; i < size; i++) {
            Vertex row = graph.get(i);  // 第i行
//            通过map集合记录顶点对应的边，权值
            Map<Vertex, Integer> map = row.edges.stream().collect(Collectors.toMap(edge -> edge.linked, edge -> edge.weight));
            for (int j = 0; j < size; j++) {
                Vertex vol = graph.get(j);  // 第j列

                // 判断顶点：
                // 1、顶点相同，初始化为0
                if (row == vol) {
                    dist[i][j] = 0;
                } else {
                    // 顶点不相同，判断是否联通

                    // 2、顶点联通，记录权值
                    // 3、顶点不连通，记为无穷
                    dist[i][j] = map.getOrDefault(vol, Integer.MAX_VALUE);
                    prev[i][j] = map.get(vol) !=null ? row : null;      // 记录i到j顶点的前一个顶点，记录的是列row，比如1->2联通，那么prev就是1
                }

            }
        }
        print(dist);
        print(prev);

        // 2）看能否借路到达其它顶点
        /*
            v2->v1          v1->v?
            dist[1][0]   +   dist[0][0]
            dist[1][0]   +   dist[0][1]
            dist[1][0]   +   dist[0][2]
            dist[1][0]   +   dist[0][3]
         */
        for (int k = 0; k < size; k++) {                // 要执行的次数，也就是每次添加的顶点
            for (int i = 0; i < size; i++) {            // 行顶点
                for (int j = 0; j < size; j++) {        // 列顶点
//                    dist[i][k] + dist[k][j]   i顶点借助k顶点到达j顶点，i—>k , k->j  // 同时判断两者是否联通
                    // dist[i][j] 原来路径
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        prev[i][j] = prev[k][j];        // 更新新的最短路径的前一个顶点
                    }
                }
            }
//            print(dist);
            print(prev);
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path(prev,graph,i,j);
            }

        }

//        print(dist);
    }

    /**
     * 打印距离二维数组
     *
     * @param dist
     */
    static void print(int[][] dist) {
        System.out.println("-------------");
        for (int[] row : dist) {
            System.out.println(Arrays.stream(row).boxed()
                    .map(x -> x == Integer.MAX_VALUE ? "∞" : String.valueOf(x))
                    .map(s -> String.format("%2s", s))
                    .collect(Collectors.joining(",", "[", "]")));
        }
    }

    /**
     * 打印顶点二维数组
     *
     * @param prev
     */
    static void print(Vertex[][] prev) {
        System.out.println("-------------------------");
        for (Vertex[] row : prev) {
            System.out.println(Arrays.stream(row).map(v -> v == null ? "null" : v.name)
                    .map(s -> String.format("%5s", s))
                    .collect(Collectors.joining(",", "[", "]")));
        }
    }


    /**
     * 打印路径
     * @param prev
     * @param graph
     * @param i
     * @param j
     */
    static void path(Vertex[][] prev, List<Vertex> graph, int i, int j) {
        LinkedList<String> stack = new LinkedList<>();
        System.out.print("[" + graph.get(i).name + "," + graph.get(j).name + "] ");
        stack.push(graph.get(j).name);
        while (i != j) {
            Vertex p = prev[i][j];
            stack.push(p.name);
            j = graph.indexOf(p);
        }
        System.out.println(stack);
    }

}
