package 基本数据结构.动态数组;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class DynamicArray implements Iterable<Integer> {
    private int size = 0;   // 逻辑大小
    private int capacity = 10;  // 容量
    private int[] array = {};


//    float loadFactor = 0.75F;


    /**
     * 迭代器遍历
     *
     * @return
     */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i = 0;

            @Override

            public boolean hasNext() {
                return i < size;
            }

            @Override
            public Integer next() {
                return array[i++];
            }
        };
    }


    /**
     * 在数组尾部添加元素
     *
     * @param element 插入的元素
     */
    public void add(int element) {
       /* if(size < capacity){        // 其实这一步可以不用，因为数组会自动扩容
            array[size++] = element;
        }*/
        add(size, element);
    }

    /**
     * 在指定位置插入元素
     *
     * @param index   要插入的索引处
     * @param element 要插入的元素
     */
    public void add(int index, int element) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException();
        }

        checkAndGrow();

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    /**
     * 扩容数组
     */
    public void checkAndGrow() {
        if (size == 0) {
            array = new int[capacity];
        } else if (size == capacity) { // 动态扩容
            capacity += (capacity >> 1); // 扩容1.5倍
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    /**
     * 查询元素
     *
     * @param index 索引
     * @return 值
     */
    public int get(int index) {
        return index >= 0 && index < size ? array[index] : -1;
    }


    /**
     * 获取数组长度
     *
     * @return
     */
    public int size() {
        return size;
    }


    /**
     * 删除元素
     *
     * @param index 被删除元素的索引
     * @return 被删除的值
     */
    public int remove(int index) {
        if (index < 0 && index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int element = array[index];
        if (index < size) {
            System.arraycopy(array, index + 1, array, index, size - index);
        }

        size--;
        return element;
    }


    /**
     * foreach遍历
     * 使用函数式接口，将功能的控制权交给调用者
     *
     * @param consumer
     */
    public void foreach(Consumer<Integer> consumer) {
        for (int i = 0; i < size; i++) {
            consumer.accept(array[i]);
        }
    }

    /**
     * 使用流的方法遍历
     *
     * @return
     */
    public IntStream stream() {
        return IntStream.of(Arrays.copyOfRange(array, 0, size));      // 左闭右开
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Test
    public void testAdd() {
        DynamicArray dynamicArray = new DynamicArray();


        System.out.println(dynamicArray.get(666));

        // Test adding element at the first index
        dynamicArray.add(0, 5);
        Assertions.assertEquals(1, dynamicArray.size());
        Assertions.assertEquals(5, dynamicArray.get(0));

        // Test adding element at the last index
        dynamicArray.add(dynamicArray.size(), 10);
        Assertions.assertEquals(2, dynamicArray.size());
        Assertions.assertEquals(10, dynamicArray.get(dynamicArray.size() - 1));

        // Test adding element in the middle
        dynamicArray.add(1, 7);
        Assertions.assertEquals(3, dynamicArray.size());
        Assertions.assertEquals(7, dynamicArray.get(1));
        dynamicArray.remove(1);
        dynamicArray.foreach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });

        for (Integer i : dynamicArray) {
            System.out.println(i);
        }

        dynamicArray.stream().forEach(e -> System.out.println(e));
    }


    private DynamicArray dynamicArray;

    @BeforeEach
    public void setup() {
        dynamicArray = new DynamicArray();
    }

    @Test
    public void testCheckAndGrow_WhenSizeIsZero_ShouldResizeArrayToCapacity() {
        dynamicArray.setSize(0);
        dynamicArray.setCapacity(10);
        dynamicArray.checkAndGrow();
        Assertions.assertEquals(0, dynamicArray.size());
        Assertions.assertEquals(10, dynamicArray.getCapacity());
    }

    @Test
    public void testCheckAndGrow_WhenSizeEqualsCapacity_ShouldResizeArrayToDoubleCapacity() {
        dynamicArray.setSize(10);
        dynamicArray.setCapacity(10);
        dynamicArray.checkAndGrow();
        Assertions.assertEquals(10, dynamicArray.size());
        Assertions.assertEquals(20, dynamicArray.getCapacity());
    }
}
