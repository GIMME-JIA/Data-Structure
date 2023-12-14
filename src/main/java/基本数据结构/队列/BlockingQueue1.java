package 基本数据结构.队列;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue1<E> implements BlockingQueue<E> {

    private final E array[];
    private int head;
    private int tail;
    private int size;

    public BlockingQueue1(int capacity) {
        array = (E[]) new Object[capacity];
    }


    private ReentrantLock lock = new ReentrantLock();

    private Condition headWaits = lock.newCondition();
    private Condition tailWaits = lock.newCondition();


    private boolean isEmpty(){
        return size == 0;
    }

    private boolean isFull(){
        return size == array.length;
    }

    @Override
    public void offer(E e) throws InterruptedException {
        lock.lockInterruptibly();       // 获得可中断锁

        try {
            while(isFull()){        // 注意：这里要用while，防止虚假唤醒的情况
                tailWaits.await();
            }

            array[tail] = e;
            if(++tail == array.length){
                tail = 0;
            }
            size++;

            // 添加成功的同时要唤醒在头部等待出队的线程
            headWaits.signal();

        }finally {
            lock.unlock();          // 释放锁
        }
    }

    /**
     * 给等待加上一个时间上限制
     * @param e
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean offer(E e, long timeout) throws InterruptedException {
        lock.lockInterruptibly();       // 获得可中断锁
        long time = TimeUnit.MICROSECONDS.toNanos(timeout);
        try {
            while(isFull()){        // 注意：这里要用while，防止虚假唤醒的情况
                if(time <=0){
                    return false;
                }
                time = tailWaits.awaitNanos(time);      // awaitNanos会返回剩余时间
            }

            array[tail] = e;
            if(++tail == array.length){
                tail = 0;
            }
            size++;

            // 添加成功的同时要唤醒在头部等待出队的线程
            headWaits.signal();

        }finally {
            lock.unlock();          // 释放锁
        }
        return true;
    }

    @Override
    public E poll() throws InterruptedException {
        lock.lockInterruptibly();
        E e;
        try{
            while(isEmpty()){           // 注意：这里要用while，防止虚假唤醒的情况
                headWaits.await();
            }
            e = array[head];

            array[head] = null;     // help gc

            if(++head == array.length){
                head  = 0;
            }
            size--;

            // 出队元素后，唤醒尾部要添加元素的线程
            tailWaits.signal();
            return e;
        }finally {
            lock.unlock();
        }
    }
}
