package Graph图.最小生成树;

import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Prim {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        v1.edges = List.of(new Edge(v2, 2), new Edge(v3, 4), new Edge(v4, 1));
        v2.edges = List.of(new Edge(v1, 2), new Edge(v4, 3), new Edge(v5, 10));
        v3.edges = List.of(new Edge(v1, 4), new Edge(v4, 2), new Edge(v6, 5));
        v4.edges = List.of(new Edge(v1, 1), new Edge(v2, 3), new Edge(v3, 2),
                new Edge(v5, 7), new Edge(v6, 8), new Edge(v7, 4));
        v5.edges = List.of(new Edge(v2, 10), new Edge(v4, 7), new Edge(v7, 6));
        v6.edges = List.of(new Edge(v3, 5), new Edge(v4, 8), new Edge(v7, 1));
        v7.edges = List.of(new Edge(v4, 4), new Edge(v5, 6), new Edge(v6, 1));

        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6, v7);

        prim(graph, v1);
    }


    /**
     * 最小生成树，prim算法,用于无向图,重点看顶点
     * @param graph
     * @param source
     */
    static void prim(List<Vertex> graph,Vertex source){
        ArrayList<Vertex> list = new ArrayList<>(graph);    // 将图的顶点放到集合
        source.distance = 0;    // 初始化起始顶点的距离

        while(!list.isEmpty()){
            // 1、选取当前顶点，集合中距离最小的顶点
            Vertex curr = chooseMinDistVertex(list);
            // 2、更新当前顶点邻居顶点的距离
            updateNeighbordist(list,curr);
            // 3、移除当前顶点
            list.remove(curr);
            curr.visited = true;
            System.out.println(curr.name + ":" + curr.distance + "  " + (curr.pre != null ? curr.pre.name : "null"));

        }
    }

    /**
     * 更新当前顶点邻居顶点的距离
     * @param list
     * @param curr
     */
    private static void updateNeighbordist(ArrayList<Vertex> list, Vertex curr) {
        for (Edge edge : curr.edges) {
            if (!edge.linked.visited) {
                if(edge.linked.distance > edge.weight){
                    edge.linked.distance = edge.weight;     // 比较目标顶点和边的权值
                    edge.linked.pre = curr;     // 记录前一个顶点
                }
            }
        }
    }

    /**
     * 找到集合中顶点距离最小的顶点
     * @param list
     * @return
     */
    private static Vertex chooseMinDistVertex(ArrayList<Vertex> list) {
        Vertex min = list.get(0);
        for (Vertex vertex : list) {
            if(vertex.distance < min.distance){
                min = vertex;
            }
        }
        return min;
    }
}
