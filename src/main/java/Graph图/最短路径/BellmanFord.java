package Graph图.最短路径;

import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.List;

public class BellmanFord {
    public static void main(String[] args) {

        // 负边情况
        /*Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");

        v1.edges = List.of(new Edge(v2, 2), new Edge(v3, 1));
        v2.edges = List.of(new Edge(v3, -2));
        v3.edges = List.of(new Edge(v4, 1));
        v4.edges = List.of();
        List<Vertex> graph = List.of(v1, v2, v3, v4);*/

//        负环情况
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");

        v1.edges = List.of(new Edge(v2, 2));
        v2.edges = List.of(new Edge(v3, -4));
        v3.edges = List.of(new Edge(v4, 1), new Edge(v1, 1));
        v4.edges = List.of();
        List<Vertex> graph = List.of(v1, v2, v3, v4);

        bellmanFord(graph, v1);
    }

    /**
     * bellmanford算法，缺点：有负环就gg
     * @param graph
     * @param source
     */
    public static void bellmanFord(List<Vertex> graph,Vertex source){
        source.distance = 0;

        /*
        进行n-1次松弛操作
         */
        for (int i = 0; i < graph.size(); i++) {
            // 检测负环
            if(i == graph.size() -1){
                System.out.println("有负环");
                break;
            }
            // 遍历所有边
            for (Vertex start : graph) {
                for (Edge edge : start.edges) {
                    // 处理每一条边
                    Vertex end = edge.linked;   // 边所指向的节点
                    if(start.distance != Integer.MAX_VALUE && end.distance > edge.weight + start.distance){

                        end.distance = edge.weight + start.distance;
                        end.pre = start;
                    }
                }
            }
        }

        for (Vertex v : graph) {
            System.out.println(v + " " + (v.pre != null ? v.pre.name : "null"));
        }

    }
}
