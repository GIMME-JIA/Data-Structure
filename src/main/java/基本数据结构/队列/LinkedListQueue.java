package 基本数据结构.队列;

import java.util.Iterator;

public class LinkedListQueue<E> implements Queue<E>, Iterable<E> {


    /**
     * 节点类
     *
     * @param <E>
     */
    private static class Node<E> {
        E value;
        Node<E> next;

        public Node(E value) {
            this.value = value;
        }

        public Node(E value, Node<E> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<E> head = new Node<>(null, null);       // 首元节点
    private Node<E> tail;

    private int size = 0;       // 队列大小
    private int capacity = Integer.MAX_VALUE;       // 队列容量

    {
        tail = head;        // 初始化语句块，提取于构造方法
    }

    public LinkedListQueue() {
    }

    public LinkedListQueue(int capacity) {
        this.capacity = capacity;

    }


    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }
        Node<E> newNode = new Node<>(value, head);
        tail.next = newNode;
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        Node<E> deleted = head.next;    // 要删除元素
        head.next = deleted.next;
        if (deleted == tail) {
            tail = head;
        }
        size--;
        return deleted.value;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return head.next.value;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != head;
            }

            @Override
            public E next() {
                E value = p.value;
                p = p.next;
                return value;
            }
        };
    }
}
