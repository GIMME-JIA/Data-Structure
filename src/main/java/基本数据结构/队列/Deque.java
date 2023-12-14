package 基本数据结构.队列;

/**
 * 双端队列
 * @param <E>
 */
public interface Deque<E> {

    /**
     * 队头插入
     * @param e
     * @return
     */
    boolean offerFirst(E e);

    /**
     * 队尾插入
     * @param e
     * @return
     */
    boolean offerLast(E e);

    /**
     * 队头出队
     * @return
     */
    E pollFirst();

    /**
     * 队尾出队
     * @return
     */
    E pollLast();

    /**
     * 获取队头元素
     * @return
     */
    E peekFirst();

    /**
     * 获取队尾
     * @return
     */
    E peekLast();

    /**
     * 判空
     * @return
     */
    boolean isEmpty();

    /**
     * 判满
     * @return
     */
    boolean isFull();
}