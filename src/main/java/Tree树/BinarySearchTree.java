package Tree树;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉搜索树--二叉排序树
 */
public class BinarySearchTree<T extends Comparable<T>, V> {

    BSTNode<T, V> root;

    /**
     * 查询，根据key找值————非递归
     *
     * @param key
     * @return
     */
    public V get(T key) {
        BSTNode<T, V> node = root;
        int count = 0;      // 比较次数
        while (node != null) {
            int res = key.compareTo(node.key);
            count++;
            // 返回结果小于0说明调用者小于被调用者
            // 返回结果大于0说明调用者大于被调用者
            // 返回结果等于0说明调用者等于被调用者
            if (res < 0) {
                node = node.left;
            } else if (res > 0) {
                node = node.right;
            } else {
                System.out.println("比较次数："+count);
                return node.value;
            }
        }
        System.out.println("比较次数："+count);
        return null;
    }

    /**
     * 根据key插入相应值
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(T key, V value) {
        BSTNode<T, V> p = root;
        BSTNode<T, V> pre = null;

        // key大往左找，key小往右找
        // key存在就更新值，key不存在就插入节点
        while (p != null) {
            pre = p;    // 记录前一个节点
            if (key.compareTo(p.key) > 0) {     // 返回正数，说明key比当前node。key要大
                p = p.right;        // 往右找
            } else if (key.compareTo(p.key) < 0) {             // 返回负数，说明key比当前node。key要小
                p = p.left;         // 往左找
            } else {
                // key相等
                p.value = value;    // 更新值
                return true;
            }
        }
        // 循环完毕，说明没找到，此时新增
        BSTNode<T, V> newNode = new BSTNode<>(key, value);

        // 分为空树和非空树两种情况
        if (root == null) {
            root = newNode;
            return true;
        }

        if (newNode.key.compareTo(pre.key) > 0) {
            pre.right = newNode;
        } else {
            pre.left = newNode;
        }
        return true;

    }

    public void outputTree(BSTNode<T, V> node) {
        if (node == null) {
            return;
        }
        outputTree(node.left);
        System.out.println(node.key + " " + node.value);
        outputTree(node.right);
    }


    /**
     * 根据key删除节点
     *
     * @param key
     * @return 被删除节点对应的值
     */
    public V delete(T key) {
        // 思路：
        // 1、根据key找到该节点
        BSTNode<T, V> p = root;

        BSTNode<T, V> parent = null;

        // key大往左找，key小往右找
        while (p != null) {

            if (key.compareTo(p.key) > 0) {     // 返回正数，说明key比当前node。key要大
                parent = p;     // 记录父节点
                p = p.right;        // 往右找
            } else if (key.compareTo(p.key) < 0) {             // 返回负数，说明key比当前node。key要小
                parent = p;     // 记录父节点
                p = p.left;         // 往左找
            } else {
                break;
            }
        }

        // 判断该节点是否存在
        if (p == null) {
            return null;
        }

        // 2、仅存在左子树或者右子树的情况
        // 都不存在的话随便走一个左空或右空是等效的
        if (p.right == null) {
            // 仅存在左子树的情况
            // 将左子树接到父节点
            shift(parent, p, p.left);
        } else if (p.left == null) {
            // 仅存在右子树的情况
            shift(parent, p, p.right);
        } else {
            //   3、左右子树都存在
            // 3.1、找到被删除节点的后继
            BSTNode<T, V> successor = p.right;   // 后继只会存在于右子树
            BSTNode<T, V> successorParent = p;   // 记录后继的父节点
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            // 3.2 处理后继节点的后事,判断后继节点和当前要删除节点是否相邻

            // 1、后继节点和当前要删除节点不相邻
            if (successorParent != p) {
                // 处理后继节点的后事
                shift(successorParent, successor, successor.right);
                successor.right = p.right;
            }
            // 2、后继节点和当前要删除节点相邻，不需要操作，


            // 3.3 后继节点取代被删除节点
            shift(parent, p, successor);
            successor.left = p.left;
        }

        return p.value;
    }

    /**
     * 查询，根据key找值————递归，java不支持尾递归的自动优化，所以通常将尾递归转化为非递归
     *
     * @param key
     * @return
     */
    public V getByRecursive(T key) {
        return doGet(root, key);
    }

    private V doGet(BSTNode<T, V> node, T key) {
        if (node == null) {
            return null; // 没找到
        }
        int res = key.compareTo(node.key);
        if (res < 0) {
            return doGet(node.left, key); // 向右找
        } else if (res > 0) {
            return doGet(node.right, key); // 向右找
        } else {
            return node.value; // 找到了
        }
    }





    /**
     * 查找最小关键字对应的值，递归版
     *
     * @return
     */
    public V minByRecursive() {
        BSTNode<T, V> p = root;
        return doMin(p);
    }

    private V doMin(BSTNode<T, V> root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.value;
        }
        return doMin(root.left);
    }

    /**
     * 查找最小关键字对应的值，非递归版
     *
     * @return
     */
    public V min() {
        return min(root);
    }

    /**
     * 找某子树最小key节点
     *
     * @param root
     * @return
     */
    private V min(BSTNode<T, V> root) {
        if (root == null) {
            return null;
        }
        BSTNode<T, V> p = root;
        while (p.left != null) {
            p = p.left;
        }
        return p.value;
    }

    /**
     * 查找最大关键字对应的值，非递归版
     *
     * @return
     */
    public V max() {
        return max(root);
    }

    /**
     * 找某子树最大key节点
     *
     * @param root
     * @return
     */
    private V max(BSTNode<T, V> root) {
        if (root == null) {
            return null;
        }
        BSTNode<T, V> p = root;
        while (p.right != null) {
            p = p.right;
        }
        return p.value;
    }



    /**
     * 根据key找到该节点的前驱节点
     *
     * @param key 关键字
     * @return 前驱节点
     */
    public V predecessor(T key) {
        BSTNode<T, V> p = root;

        BSTNode<T, V> ancestorFromLeft = null;   // 记录自左而来的祖先

        // key大往左找，key小往右找
        // key存在就更新值，key不存在就插入节点
        while (p != null) {
            if (key.compareTo(p.key) > 0) {     // 返回正数，说明key比当前node。key要大
                ancestorFromLeft = p;
                p = p.right;        // 往右找
            } else if (key.compareTo(p.key) < 0) {             // 返回负数，说明key比当前node。key要小
                p = p.left;         // 往左找
            } else {
                break;
            }
        }

        // 循环结束，判断该节点是否存在
        if (p == null) {
            return null;
        }

        // 节点存在
        // 情况一：节点有左子树，此时前任就是左子树的最大值
        if (p.left != null) {
            return max(p.left);
        } else {
            // 情况二：节点没有左子树，此时前任就是自左而来的距离最近的祖宗
            return ancestorFromLeft != null ? ancestorFromLeft.value : null;
        }
    }

    /**
     * 根据key找到该节点的后继节点
     *
     * @param key 关键字
     * @return 前驱节点
     */
    public V successor(T key) {
        BSTNode<T, V> p = root;

        BSTNode<T, V> ancestorFromRight = null;   // 记录自左而来的祖先

        // key大往左找，key小往右找
        // key存在就更新值，key不存在就插入节点
        while (p != null) {
            if (key.compareTo(p.key) > 0) {     // 返回正数，说明key比当前node。key要大
                p = p.right;        // 往右找
            } else if (key.compareTo(p.key) < 0) {             // 返回负数，说明key比当前node。key要小
                ancestorFromRight = p;
                p = p.left;         // 往左找
            } else {
                break;
            }
        }

        // 循环结束，判断该节点是否存在
        if (p == null) {
            return null;
        }

        // 节点存在
        // 情况一：节点有右子树，此时后任就是右子树的最小值
        if (p.right != null) {
            return min(p.right);
        } else {
            // 情况二：节点没有右子树，此时前任就是自右向左而来的距离最近的祖宗
            return ancestorFromRight != null ? ancestorFromRight.value : null;
        }
    }



    /**
     * 托孤方法：将要删除节点的孩子节点接到父节点
     *
     * @param parent  父节点
     * @param deleted 要删除的节点
     * @param child   后继节点
     */
    private void shift(BSTNode<T, V> parent, BSTNode<T, V> deleted, BSTNode<T, V> child) {
        if (parent == null) {
            root = child;       // 删除的是根节点时，将子节点当作根节点
        } else if (parent.left == deleted) {
            parent.left = child;        // 要删除节点是父节点的左孩子，那么要删除节点的孩子节点也应该是父节点的左孩子
        } else {
            parent.right = child;
        }

    }

    /**
     * 删除节点————递归版
     * 说明：
     *      1. `ArrayList<Object> result` 用来保存被删除节点的值
     *      2. 第二、第三个 if 对应没找到的情况，继续递归查找和删除，注意后续的 doDelete 返回值代表删剩下的，因此需要更新
     *      3. 最后一个 return 对应删除节点只有一个孩子的情况，返回那个不为空的孩子，待删节点自己因没有返回而被删除
     *      4. 第四个 if 对应删除节点有两个孩子的情况，此时需要找到后继节点，并在待删除节点的右子树中删掉后继节点，最后用后继节点替代掉待删除节点返回，别忘了改变后继节点的左右指针
     * @param key
     * @return
     */
    public V deleteByRecursive(T key) {
        ArrayList<V> result = new ArrayList<>();
        root = doDelete(root, key, result);
        return result.isEmpty() ? null : result.get(0);
    }

    public BSTNode<T,V> doDelete(BSTNode<T,V> node, T key, ArrayList<V> result) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = doDelete(node.left, key, result);
            return node;
        }
        if (key.compareTo(node.key) >0) {
            node.right = doDelete(node.right, key, result);
            return node;
        }
        result.add(node.value);
        if (node.left != null && node.right != null) {
            BSTNode<T,V> s = node.right;
            while (s.left != null) {
                s = s.left;
            }
            s.right = doDelete(node.right, s.key, new ArrayList<>());
            s.left = node.left;
            return s;
        }
        return node.left != null ? node.left : node.right;
    }


    /**
     * 找比key小的key的值
     * @param key
     * @return
     */
    public List<V> less(T key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<T,V> p = root;
        LinkedList<BSTNode<T,V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<T,V> pop = stack.pop();
                if (pop.key.compareTo(key) < 0) {
                    result.add(pop.value);
                } else {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }


    /**
     * 找比key大的key的值,反向中序遍历，
     * @param key
     * @return
     */
    public List<V> greater(T key) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<T,V> p = root;
        LinkedList<BSTNode<T,V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.right;
            } else {
                BSTNode<T,V> pop = stack.pop();
                if (pop.key.compareTo(key) > 0) {
                    result.add(pop.value);
                }else {
                    break;
                }
                p = pop.left;
            }
        }
        return result;
    }

    /**
     * 找key直接的key的值
     * @param key1
     * @param key2
     * @return
     */
    public List<V> between(T key1, T key2) {
        ArrayList<V> result = new ArrayList<>();
        BSTNode<T,V> p = root;
        LinkedList<BSTNode<T,V>> stack = new LinkedList<>();
        while (p != null || !stack.isEmpty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                BSTNode<T,V> pop = stack.pop();
                if (pop.key == key1 && pop.key == key2 && pop.key.compareTo(key1) > 0 && pop.key.compareTo(key2) < 0) {
                    result.add(pop.value);
                } else if (pop.key.compareTo(key2) > 0) {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }



    static class BSTNode<T, V> {

        T key; // 若希望任意类型作为 key, 则后续可以将其设计为 Comparable 接口
        V value;
        BSTNode<T, V> left;
        BSTNode<T, V> right;

        public BSTNode(T key) {
            this.key = key;
        }

        public BSTNode(T key, V value) {
            this.key = key;
            this.value = value;
        }

        public BSTNode(T key, V value, BSTNode<T, V> left, BSTNode<T, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }













    private BinarySearchTree<Integer,String> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree<>();
    }

    @Test
    public void testGet() {
        bst.put(5, "Five");
        bst.put(3, "Three");
        bst.put(7, "Seven");
        bst.put(2, "Two");
        bst.put(4, "Four");

        // Test get values
        Assert.assertEquals("Two", bst.get(2));
        Assert.assertEquals("Three", bst.get(3));
        Assert.assertEquals("Four", bst.get(4));
        Assert.assertEquals("Five", bst.get(5));
        Assert.assertEquals(null, bst.get(6));
    }

    @Test
    public void testPut() {
        bst.put(5, "Five");
        bst.put(3, "Three");
        bst.put(7, "Seven");
        bst.put(2, "Two");
        bst.put(4, "Four");

        // Test put values
        Assert.assertEquals("Five", bst.get(5));
        Assert.assertEquals("Three", bst.get(3));
        Assert.assertEquals("Seven", bst.get(7));
        Assert.assertEquals("Two", bst.get(2));
        Assert.assertEquals("Four", bst.get(4));
    }



    @Test
    public void testDelete() {
        bst.put(5, "Five");
        bst.put(3, "Three");
        bst.put(7, "Seven");
        bst.put(2, "Two");
        bst.put(4, "Four");
        bst.outputTree(bst.root);
        System.out.println();
        // Test delete values
        bst.delete(3);
        bst.outputTree(bst.root);
        System.out.println();
        Assert.assertEquals("Four", bst.get(4));
        Assert.assertEquals("Five", bst.get(5));
        Assert.assertEquals("Two", bst.get(2));
        Assert.assertEquals("Seven", bst.get(7));
        Assert.assertEquals(null, bst.get(3));



        bst.delete(5);
        bst.outputTree(bst.root);
        System.out.println();
        Assert.assertEquals("Four", bst.get(4));
        Assert.assertEquals("Two", bst.get(2));
        Assert.assertEquals(null, bst.get(5));


        bst.delete(2);
        bst.outputTree(bst.root);
        Assert.assertEquals("Four", bst.get(4));
        Assert.assertEquals(null, bst.get(2));
        bst.outputTree(bst.root);

        bst.delete(7);
        System.out.println();
        Assert.assertEquals("Four", bst.get(4));
        Assert.assertEquals(null, bst.get(7));
    }


}
