package Graph图.拓扑排序;



import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.*;

public class TopologicalSort {


    public static void main(String[] args) {
        Vertex v1 = new Vertex("网页基础");
        Vertex v2 = new Vertex("Java基础");
        Vertex v3 = new Vertex("JavaWeb");
        Vertex v4 = new Vertex("Spring框架");
        Vertex v5 = new Vertex("微服务框架");
        Vertex v6 = new Vertex("数据库");
        Vertex v7 = new Vertex("实战项目");

        v1.edges = List.of(new Edge(v3)); // +1
        v2.edges = List.of(new Edge(v3)); // +1
        v3.edges = List.of(new Edge(v4));
        v6.edges = List.of(new Edge(v4));
        v4.edges = List.of(new Edge(v5));
        v5.edges = List.of(new Edge(v7));
        v7.edges = List.of(/*new Edge(v1)*/);

        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6, v7);


//        TopoSortByQueue(graph);
        
        LinkedList<String> stack = new LinkedList<>();
        for (Vertex vertex : graph) {
            TopoSortByStack(vertex,stack);
        }

        System.out.println(stack);

    }

    /**
     * 拓扑排序：栈配合dfs实现
     * @param vertex
     */
    public static void TopoSortByStack(Vertex vertex,LinkedList<String> stack){
        // 判断该顶点是否已访问
        if (vertex.status == 2) {
            return;
        }

        // 判断该顶点是否处于访问中，如果是，说明有环
        if (vertex.status == 1) {
            throw new RuntimeException("有环啊啊啊啊");
        }

        // 将该顶点设为访问中
        vertex.status = 1;

        // 把该顶点的邻接顶点都进栈
        for (Edge edge : vertex.edges) {
            TopoSortByStack(edge.linked,stack);
        }

        // 把当前顶点，即从后到前，依次进栈
        stack.push(vertex.name);
        vertex.status = 2;

    }

    /**
     * 拓扑排序：队列实现
     * @param graph
     */
    public static void TopoSortByQueue(List<Vertex> graph){
        // 统计每个节点入度
        for (Vertex vertex : graph) {       // 遍历每个节点
            for (Edge edge : vertex.edges) {        // 遍历每个节点的边
                edge.linked.indegree++;         // 节点的边所指向的节点，其入度+1
            }
        }

        Queue<Vertex> queue = new ArrayDeque<>();

        for (Vertex vertex : graph) {
            if(vertex.indegree == 0)
                queue.offer(vertex);
        }

        int count = 0;

        while(!queue.isEmpty()){
            Vertex poll = queue.poll();
            System.out.println(poll.name);
            count++;
            poll.visited = true;

            // 遍历当前顶点的下一顶点
            for (Edge edge : poll.edges) {
                if ((--edge.linked.indegree)==0) {
                    queue.offer(edge.linked);
                }
            }
        }

        if(count != graph.size())
            System.out.println("有环");
    }
}
