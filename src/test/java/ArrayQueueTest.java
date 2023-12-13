import org.junit.Before;
import org.junit.jupiter.api.Test;
import 基础.队列.ArrayQueue;

import java.sql.Array;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayQueueTest {
    private ArrayQueue<Integer> queue=  new ArrayQueue<>(3);;

    public ArrayQueueTest() throws Exception {
    }


    @Test
    public void testOfferShouldAddValueToQueue() {
        assertTrue(queue.offer(1));
        assertEquals(1, queue.peek());
    }

    @Test
    public void testOfferShouldReturnFalseWhenQueueIsFull() {
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        assertFalse(queue.offer(4));
    }

    @Test
    public void testPollShouldRemoveHeadOfQueue() {
        queue.offer(1);
        queue.offer(2);
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPollShouldReturnNullWhenQueueIsEmpty() {
        assertNull(queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testPeekShouldReturnHeadOfQueue() {
        queue.offer(1);
        assertEquals(1, queue.peek());
    }

    @Test
    public void testIsEmptyShouldReturnTrueWhenQueueIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testIsEmptyShouldReturnFalseWhenQueueIsNotEmpty() {
        queue.offer(1);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testIsFullShouldReturnTrueWhenQueueIsFull() {

        queue.offer(1);
        queue.offer(2);
        queue.offer(2);
        assertTrue(queue.isFull());
    }

    @Test
    public void testIsFullShouldReturnFalseWhenQueueIsNotFull() {

        assertFalse(queue.isFull());
    }
}
