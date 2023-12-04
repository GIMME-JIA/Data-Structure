package Graph图.深搜广搜;

import Graph图.相关类.Edge;
import Graph图.相关类.Vertex;

import java.util.List;
import java.util.Stack;

public class DFS {

    public static void main(String[] args) {
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");


        a.edges = List.of(new Edge(b), new Edge(c), new Edge(d));
        b.edges = List.of(new Edge(a),new Edge(d),new Edge(e));
        c.edges = List.of(new Edge(a), new Edge(d));
        d.edges = List.of(new Edge(a),new Edge(b), new Edge(c),new Edge(e));
        e.edges = List.of(new Edge(b),new Edge(d));
//        v6.edges = List.of(new Edge(e));

        // Dfs1(a);
        System.out.println();
        Dfs1(a);
    }

    /**
     * 递归实现深搜
     * @par am v
     */
    public static void Dfs1(Vertex v){
        // 判断该顶点是否被访问过
        if(!v.visited){
            v.visited = true;   // 设置为已访问
            System.out.println(v.name);

            // 向下递归
            for (Edge edge : v.edges) {
                Dfs1(edge.linked);
            }
        }
    }

    /**
     * 非递归实现dfs
     * @param v
     */
    public static void Dfs2(Vertex v){
        Stack<Vertex> stack = new Stack<>();

        stack.push(v); // 首节点进栈
        
        while(!stack.empty()){
            Vertex top = stack.pop();   // 栈顶元素出栈并返回元素顶点
            top.visited = true;
            System.out.println(top.name);

            for (Edge edge : top.edges) {       // 将该顶点所指向的下一顶点都进栈
                 if(!edge.linked.visited){
                     stack.push(edge.linked);
                 }
            }
            System.out.println(stack);
        }
        
    }
}
