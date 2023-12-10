package 基础.链表;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.function.Consumer;

public class SinglyLinkedList implements Iterable<Integer> {

    public Node head;


    /**
     * 链表的节点类
     * 定义为内部类，是为了对外隐藏细节，没必要让类的使用者关心Node结构
     * 定义为static内部类，是因为Node不需要与SinglyLinkedList实例相关，多个SinglyLinkedList实例能公用Node类定义
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value) {
            this.value = value;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Itr();
    }


    /**
     * 头插法添加节点
     * <p>
     * * 如果 this.head == null，新增节点指向 null，并作为新的 this.head
     * * 如果 this.head != null，新增节点指向原来的 this.head，并作为新的 this.head
     * 赋值的顺序是从右到左
     *
     * @param value 待添加值
     */
    public void addFirst(int value) {
        this.head = new Node(value, this.head);
    }

    /**
     * 尾插法
     *
     * @param value
     */
    public void addLast(int value) {
        Node last = findLast();
        if (last == null) {
            addFirst(value);
        } else {
            last.next = new Node(value, null);
        }

    }

    /**
     * 找到链表的最后一个节点
     *
     * @return 尾节点
     */
    private Node findLast() {
        if (this.head == null) {
            return null;
        }
        Node curr;
        for (curr = this.head; curr.next != null; ) {
            curr = curr.next;
        }
        return curr;
    }

    /**
     * 根据索引值添加节点
     *
     * @param index 索引位置
     * @param value 要添加的值
     */
    public void addByIndex(int index, int value) {
        if(index == 0){
            addFirst(value);
            return;
        }

        Node node = findNode(index-1);      // 插入的时候要找索引的前驱节点
        if (node == null){
            throw illegalIndex(index);
        }

        node.next = new Node(value,node.next);
    }

    /**
     * 根据索引找节点（供内部使用
     *
     * @param index
     * @return
     */
    private Node findNode(int index) {
        int i = 0;
        for (Node cur = head; cur != null; cur = cur.next, i++) {
            if (index == i) {
                return cur;
            }
        }
        return null;
    }

    /**
     * 根据索引获取节点值（供外界调用
     * @param index
     * @return
     */
    public int get(int index){
        Node node = findNode(index);
        if (node == null){
            throw illegalIndex(index);
        }
        return node.value;
    }


    /**
     * 删除指定索引位置的节点
     * @param index 索引
     * @return 被删除节点的值
     * @throws Exception
     */
    public int remove(int index){
        if(head == null){
            throw illegalIndex(0);
        }

        int value;
        if (index == 0){           // 删除的是头节点
            value = head.value;
            head = head.next;
            return value;
        }

        Node pre = findNode(index-1);   // 删除也要找前驱
        Node deleted = pre.next;
        if (deleted == null) {
            throw illegalIndex(index);
        }
        value = deleted.value;
        pre.next = deleted.next;
        return value;
    }

    /**
     * 遍历链表
     *
     * @param consumer 函数式接口，具体的操作由调用者决定
     */
    public void loop(Consumer<Integer> consumer) {
        // 1、while循环遍历
        Node curr = this.head;
        while (curr != null) {
            // 做一些事
            curr = curr.next;
        }
    }


    /**
     * 匿名内部类 -> 带名字内部类
     */
    private class Itr implements Iterator<Integer> {
        Node p = head;

        @Override
        public boolean hasNext() {  // 判断是否有下一个元素
            return p.next != null;
        }

        @Override
        public Integer next() {     // 返回当前元素，并指向下一个元素
            int value = p.value;
            p = p.next;
            return value;
        }
    }

    private IllegalArgumentException illegalIndex(int index) {
        return new IllegalArgumentException(String.format("index [%d] 不合法%n", index));
    }




















}
