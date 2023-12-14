package 基本数据结构.队列;



import static java.util.Collections.swap;

/**
 * 特征
 *
 *  如果从索引 0 开始存储节点数据
 *    节点 i 的父节点为 floor((i-1)/2)，当 i>0 时
 *    节点 i 的左子节点为 2i+1，右子节点为 2i+2，当然它们得 < size
 *  如果从索引 1 开始存储节点数据
 *    节点 i 的父节点为 floor(i/2)，当 i > 1 时
 *    节点 i 的左子节点为 2i，右子节点为 2i+1，同样得 < size
 * @param <E>
 */
public class PriorityQueue3<E extends Priority> implements Queue<E> {
    Priority[] array;
    int size;

    public PriorityQueue3(int capacity) {
        array = new Priority[capacity];
    }
    @Override
    public boolean offer(E offered) {
        if (isFull()) {
            return false;
        }

        int child = size++;     // 表示要添加的元素位置
        int parent = (size - 1) >> 1;
        while (child>0 && array[parent].priority() < offered.priority()){
            array[child] = array[parent];   // 把parent移下去
            child = parent;
            parent = (child - 1) >> 1;
        }

        array[child] = offered;
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }

        // 1、交换堆底和堆顶元素
        swap(array,0,size-1);

        // 2、删除队底元素
        size--;

        Priority e = array[size];
        array[size] = null;     // help gc

        // 3、堆顶元素下潜
        down(0);

        return (E) e;
    }

    private void swap(Priority[] array, int i, int j) {
        E temp  = (E) array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 下潜
     * @param downed
     */
    private void down(int downed){
        int leftChild = downed << 1 + 1;
        int rightChild = downed << 1 + 2;
        int max = downed;

        if (leftChild < size && array[leftChild].priority() > array[max].priority()){
            max = leftChild;
        }
        if (rightChild < size && array[rightChild].priority() > array[max].priority()){
            max = rightChild;
        }
        if(max != downed){
            swap(array,max,downed);
            down(max);
        }

        /*if(array[leftChild].priority() < array[rightChild].priority()){
            // 跟右边换
            swap(array,rightChild,downed);
            down(rightChild);
        }else {
            // 跟左边换
            swap(array,leftChild,downed);
            down(leftChild);
        }*/
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) array[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == array.length;
    }
}
