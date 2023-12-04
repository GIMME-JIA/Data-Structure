package Graph图.最短路径;

import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.*;


public class Dijkstra {

    public static void main(String[] args) {
        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");

        v1.edges = List.of(new Edge(v3, 9), new Edge(v2, 7), new Edge(v6, 14));
        v2.edges = List.of(new Edge(v4, 15));
        v3.edges = List.of(new Edge(v4, 11), new Edge(v6, 2));
        v4.edges = List.of(new Edge(v5, 6));
        v5.edges = List.of();
        v6.edges = List.of(new Edge(v5, 9));

        List<Vertex> graph = List.of(v1, v2, v3, v4, v5, v6);

        /*Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");

        a.edges = List.of(new Edge(b), new Edge(c), new Edge(d));
        b.edges = List.of(new Edge(a),new Edge(d),new Edge(e));
        c.edges = List.of(new Edge(a), new Edge(d));
        d.edges = List.of(new Edge(a),new Edge(b), new Edge(c),new Edge(e));
        e.edges = List.of(new Edge(b),new Edge(d));

        List<Vertex> graph = List.of(a,b,c,d,e);*/
//        dijkstraByPriorityQueue(graph,v1);
        dijkstra(graph,v1);
    }


    /**
     * 迪克特斯拉，缺点：不能有负路径
     *
     * @param graph
     */
    public static void dijkstra(List<Vertex> graph, Vertex source) {
        ArrayList<Vertex> list = new ArrayList<>(graph);
        source.distance = 0;

        while (!list.isEmpty()) {
            // 3、选取当前顶点，集合中距离最小的顶点
            Vertex curr = chooseMinDistanceVertex(list);
            // 4、 更新当前顶点邻居距离
            updateNeighbourDistance(curr, list);
            // 5、移除当前节点
            list.remove(curr);
            System.out.println(curr.name + ":" + curr.distance + "  " + (curr.pre != null ? curr.pre.name : "null"));
        }


    }

    /**
     * 更新当前节点到下一顶点的距离
     * @param curr
     * @param list
     */
    private static void updateNeighbourDistance(Vertex curr, ArrayList<Vertex> list) {
        for (Edge edge : curr.edges) {
            if (list.contains(edge.linked)) {
                if (edge.linked.distance > curr.distance + edge.weight){
                    edge.linked.distance = curr.distance + edge.weight;
                    edge.linked.pre = curr;
                }
            }
        }
    }

    /**
     * 找到最小距离顶点
     *
     * @param list
     * @return
     */
    private static Vertex chooseMinDistanceVertex(ArrayList<Vertex> list) {
        Vertex min = list.get(0);
        for (Vertex vertex : list) {
            if (vertex.distance < min.distance)
                min = vertex;
        }
        return min;
    }


    // 方法二==========================================================================================================

    /**
     * 优先队列实现迪克特斯拉
     * @param graph
     */
    private static void dijkstraByPriorityQueue(List<Vertex> graph,Vertex source){

        // 分配临界距离值
        source.distance = 0;        // 指定开始节点的距离

        // 1、创建一个优先队列，放入所有顶点
        PriorityQueue<Vertex> queue = new PriorityQueue<>(/*Comparator.comparingInt(v -> v.distance)*/graph);

        /*for (Vertex v : graph) {
            queue.offer(v);
        }*/

//        queue.add(source);

        while (!queue.isEmpty()) {
            System.out.println(queue);
            // 选取当前顶点
            Vertex curr = queue.peek();
            // 更新邻居顶点的距离
            if(!curr.visited)
            {
                updateNeighbourDistance2(curr);
                curr.visited = true;
            }

            // 将当前节点从队列中移除
            queue.poll();
            System.out.println(curr.name + ":" + curr.distance + "  " + (curr.pre != null ? curr.pre.name : "null"));
        }

    }

    /**
     * 更新邻居节点距离
     * @param curr
     */
    private static void updateNeighbourDistance2(Vertex curr) {
        for (Edge edge : curr.edges) {
            if (/*queue.contains(edge.linked) && */!edge.linked.visited) {
                if (edge.linked.distance > (edge.weight + curr.distance)) {
                    edge.linked.distance = edge.weight + curr.distance;
                    edge.linked.pre = curr;
//                    queue.offer(edge.linked);
                }
            }
        }

    }

}