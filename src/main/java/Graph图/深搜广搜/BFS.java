package Graph图.深搜广搜;

import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class BFS {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");

        v1.edges = List.of(new Edge(v2), new Edge(v3), new Edge(v6));
        v2.edges = List.of(new Edge(v4));
        v3.edges = List.of(new Edge(v4), new Edge(v6));
        v4.edges = List.of(new Edge(v5));
        v5.edges = List.of();
        v6.edges = List.of(new Edge(v5));
        Bfs(v1);
    }

    public static void Bfs(Vertex v){
        Queue<Vertex> queue = new ArrayDeque<>();

        queue.add(v);

        while(!queue.isEmpty()){
            Vertex poll = queue.poll();
            poll.visited = true;
            System.out.println(poll.name);

            for (Edge edge : poll.edges) {
                if(!edge.linked.visited){
                    edge.linked.visited = true;
                    queue.add(edge.linked);
                }
            }
        }
    }
}
