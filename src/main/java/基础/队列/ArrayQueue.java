package 基础.队列;

import java.util.Iterator;

public class ArrayQueue<E> implements Queue<E>, Iterable<E> {


    private E[] array;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private int capacity = Integer.MAX_VALUE;


//    public ArrayQueue() {
//        array = (E[]) new Object[this.capacity];
//    }

    public ArrayQueue(int capacity) {
        // 1、抛异常
        /*if((capacity & capacity - 1) != 0){
            throw new IllegalArgumentException("不是2的幂次方");
        }*/

        // 2、改成2^n 
        // TODO: 2023/12/13 转2^n位运算没咋懂 
        capacity--;
        capacity |= capacity >> 1;
        capacity |= capacity >> 2;
        capacity |= capacity >> 4;
        capacity |= capacity >> 8;
        capacity |= capacity >> 16;
        capacity++;
        this.capacity = capacity;
        array = (E[]) new Object[this.capacity];
    }

    @Override
    public boolean offer(E value) {
        if (isFull()) {
            return false;
        }
//        array[tail++ % capacity] = value;
        array[tail++ & capacity - 1] = value;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        size--;
//        return  array[head++ % capacity];
        return array[head++ & capacity - 1];
    }

    @Override
    public E peek() {
//        return array[head % capacity];
        return array[head & capacity - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                return count <= size;
            }

            @Override
            public E next() {
                return array[count++];
            }
        };
    }
}
