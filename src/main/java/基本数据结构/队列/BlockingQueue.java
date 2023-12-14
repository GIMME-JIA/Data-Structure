package 基本数据结构.队列;

/**
 * 阻塞队列
 *
 * 之前的队列在很多场景下都不能很好地工作，例如
 *
 * 1. 大部分场景要求分离向队列放入（生产者）、从队列拿出（消费者）两个角色、它们得由不同的线程来担当，而之前的实现根本没有考虑线程安全问题
 * 2. 队列为空，那么在之前的实现里会返回 null，如果就是硬要拿到一个元素呢？只能不断循环尝试
 * 3. 队列为满，那么再之前的实现里会返回 false，如果就是硬要塞入一个元素呢？只能不断循环尝试
 *
 *  因此需要解决的问题有
 *
 * 1. 用锁保证线程安全
 * 2. 用条件变量让等待非空线程与等待不满线程进入等待状态，而不是不断循环尝试，让 CPU 空转
 *
 * @param <E>
 */
public interface BlockingQueue <E>{
    void offer(E e) throws InterruptedException;
    boolean offer(E e,long timeout) throws InterruptedException;
    E poll() throws InterruptedException;
}
