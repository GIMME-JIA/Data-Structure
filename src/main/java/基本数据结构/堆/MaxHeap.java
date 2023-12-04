package 基本数据结构.堆;

import java.util.Arrays;

/**
 * 堆：大顶堆为例
 * 时间复杂度：威廉姆斯：n*log（n）
 * 弗洛伊德：n
 * 2^h - h - 1
 */
public class MaxHeap {

    int[] array;
    int size;

    public MaxHeap(int capacity) {
        this.array = new int[capacity];
    }

    public MaxHeap(int[] array) {
        this.array = array;
        this.size = array.length;
        heapify();
    }

    /**
     * 建堆：
     * 1、找到最后一个非叶子节点
     * 2、从后向前，对每个结点执行下潜
     */
    private void heapify() {
        for (int i = size/2 -1; i >= 0; i--) {
            down(i);
        }
    }


    /**
     * 将parent索引处的元素下潜：与两个孩子较大者交换，直至没孩子或者孩子没他大
     *
     * @param parent
     */
    private void down(int parent) {
        
        int left = 2 * parent + 1;       // 左孩子索引
        int right = 2 * parent + 2;      // 右孩子索引

        int max = parent;       // 记录最大的索引处


        if( left <  size && array[left] > array[max]){     // 左孩子大于max
            // 跟左孩子换
            max = left;
        }
        if ( right < size && array[right] > array[max]){     // 右孩子大于max
            // 跟右孩子换
            max = right;
        }

        if(max != parent){
            swap(max,parent);
            down(max);
        }

    }

    /**
     * 交换两索引处的元素值
     *
     * @param i
     * @param j
     */
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    /**
     * 获取堆顶元素
     * @return
     */
    public Integer peek(){
        return array[0] != 0 ? array[0] : null;
    }

    /**
     * 删除堆顶元素
     * @return 被删除元素
     */
    public Integer poll(){
        Integer top = peek();
        if (top == null) {  // 堆顶元素为空
            return null;
        }

        swap(top,size-1);   // 将堆顶元素交换到子节点
        size--;
        down(0);
        return top;
    }

    /**
     * 删除指定索引元素
     * @param index 索引
     * @return 被删除元素
     */
    public Integer poll(int index){
        if (index > size-1) {
            return null;
        }
        int deleted = array[index];
        swap(index,size-1);
        size--;
        down(index);
        return deleted;
    }

    /**
     * 替换堆顶元素
     * @param replaced
     */
    public void replace(int replaced){
        array[0] = replaced;
        down(0);
    }


    /**
     * 添加新元素
     * @param offered
     */
    public boolean offer(int offered){
        if(size == array.length){
            return false;
        }
//        array[size] = offered;
        up(size,offered);
        size++;
        return true;
    }

    /**
     * 元素上浮
     * @param index 要上浮的索引
     * @param offered 要上浮的元素
     */
    public void up(int index,int offered){
        int child = index;       //
        while(child > 0){
            int parent = 2 * child -1;      // 父索引
            if(offered > array[parent]){
                array[child] = array[parent];
            }else {
                break;
            }
            child = parent;
        }
        array[child] = offered;
    }




    public static void main(String[] args) {

        int[] array = {2, 3, 1, 7, 6, 4, 5};
        MaxHeap heap = new MaxHeap(array);
        System.out.println(Arrays.toString(heap.array));

        while (heap.size > 1) {
            heap.swap(0, heap.size - 1);
            heap.size--;
            heap.down(0);
        }

        System.out.println(Arrays.toString(heap.array));
    }
}


