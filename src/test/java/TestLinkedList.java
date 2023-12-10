import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import 基础.链表.SinglyLinkedList;

public class TestLinkedList {
    private SinglyLinkedList linkedList;

    @Before
    public void setUp() {
        linkedList = new SinglyLinkedList();
    }

    @Test
    public void testRemoveHeadNode() {
        SinglyLinkedList.Node head = new SinglyLinkedList.Node(1);
        head.next = new SinglyLinkedList.Node(2);
        head.next.next = new SinglyLinkedList.Node(3);
        linkedList.head = head;

        try {
            int result = linkedList.remove(0);
            Assert.assertEquals(1, result);
            Assert.assertEquals(2, linkedList.head.value);
            Assert.assertEquals(2, linkedList.head.next.value);
            Assert.assertEquals(3, linkedList.head.next.next.value);
        } catch (Exception e) {
            Assert.fail("Exception should not be thrown");
        }
    }

    @Test
    public void testRemoveMiddleNode() {
        SinglyLinkedList.Node head = new SinglyLinkedList.Node(1);
        head.next = new SinglyLinkedList.Node(2);
        head.next.next = new SinglyLinkedList.Node(3);
        linkedList.head = head;

        try {
            int result = linkedList.remove(2);
            Assert.assertEquals(3, result);
            Assert.assertEquals(1, linkedList.head.value);
            Assert.assertEquals(2, linkedList.head.next.value);
        } catch (Exception e) {
            Assert.fail("Exception should not be thrown");
        }
    }


}
