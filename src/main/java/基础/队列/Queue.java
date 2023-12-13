package 基础.队列;

public interface Queue<E> {

    /**
     * 像队尾插入元素
     * @param value 待插入值
     * @return 插入成功返货true，否则返回false
     */
    boolean offer(E value);


    /**
     * 获取队头元素，并移除
     * @return 如果队列非空返回队头值，否则返回null
     */
    E poll();


    /**
     * 获取队头元素，不移除
     * @return 如果队列非空返回队头值，否则返回null
     */
    E peek();

    /**
     * 判断对列是否为空
     * @return 空返回true，反之false
     */
    boolean isEmpty();
}
