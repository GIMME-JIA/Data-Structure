package Tree树;


import java.util.Base64;

import static Tree树.RedBlackTree.Color.BLACK;
import static Tree树.RedBlackTree.Color.RED;

/**
 * 红黑树特性：
 * 1、节点只有红色或黑色
 * 2、所有叶子节点是为黑色
 * 3、根节点为黑色
 * 4、***红色节点不能相邻
 * 5、***从根到任意一个叶子节点，路径中的黑色节点数一样
 *
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    /**
     * 枚举类，红黑树的节点颜色
     */
    enum Color {
        RED, BLACK;
    }


    /**
     * 节点类，红黑树的节点
     *
     * @param <K>
     * @param <V>
     */
    static class Node<K extends Comparable<K>, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;       // 父节点
        Color color = RED;    // 颜色，初始为红色

//        Node<K,V> isParent


        public Node(K key) {
            this.key = key;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, Color color) {
            this.key = key;
            this.color = color;
        }

        public Node(K key, Node<K, V> left, Node<K, V> right, Color color) {
            this.key = key;
            this.color = color;
            this.left = left;
            this.right = right;
            // 初始化父节点
            if (left != null) {
                left.parent = this;
            }
            if (right != null) {
                right.parent = this;
            }

        }

        /**
         * 判断该节点是否为左孩子
         *
         * @return
         */
        boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        /**
         * 找叔叔节点，爸爸的兄弟
         *
         * @return
         */
        Node<K, V> uncle() {
            // 爸爸和爷爷必须存在
            if (parent == null || parent.parent == null) {
                return null;
            }

            if (parent == parent.left) {
                // 如果爸爸是左孩子,那么爸爸的兄弟是爷爷的右孩子
                return parent.parent.right;
            } else /*if (parent == parent.right)*/ {
                // 反之，叔叔是左孩子
                return parent.parent.left;
            }
        }

        /**
         * 找兄弟节点
         *
         * @return
         */
        Node<K, V> sibling() {
            if (parent == null) {
                return null;
            }
            if (this == parent.left) {
                return parent.right;
            } else {
                return parent.left;
            }
        }

    }

    // 根节点
    Node<K, V> root;


    /**
     * 判断节点是否为黑色
     *
     * @param node
     * @return
     */
    boolean isBlack(Node<K, V> node) {
        return node != null && node.color == BLACK;
    }


    /**
     * 判断节点是否为红色
     *
     * @param node
     * @return
     */
    boolean isRed(Node<K, V> node) {
        return node != null && node.color == RED;
    }


    /**
     * 右旋
     *
     * @param pink
     */
    private void rightRotate(Node<K, V> pink) {
        Node<K, V> parent = pink.parent;
        Node<K, V> yellow = pink.left;
        Node<K, V> green = yellow.right;

        // 处理green和pink的关系
        if (green != null) {
            green.parent = pink;        // 防止空指针
        }
        pink.left = green;

        // 处理yellow和pink的关系
        pink.parent = yellow;
        yellow.right = pink;

        // 处理yellow
        yellow.parent = parent;

        // 处理新根父亲与yellow的关系
        if (parent == null) {
            root = yellow;
        } else if (parent.left == pink) {
            parent.left = yellow;
        } else if (parent.right == pink) {
            parent.right = yellow;
        }
    }


    /**
     * 左旋
     *
     * @param yellow
     */
    private void leftRotate(Node<K, V> yellow) {
        Node<K, V> parent = yellow.parent;
        Node<K, V> pink = yellow.right;
        Node<K, V> green = pink.left;

        // 先处理最下面的green节点
        if (green != null) {
            green.parent = yellow;
        }
        yellow.right = green;

        // 再处理yellow（向下的节点
        yellow.parent = pink;

        // 再处理pink节点（向上的节点
        pink.left = yellow;
        pink.parent = parent;

        // 最后处理向上的pink节点作为父节点的左右孩子还是根
        if (parent == null) {
            root = pink;
        } else if (parent.left == yellow) {
            parent.left = pink;
        } else if (parent.right == yellow) {
            parent.right = pink;
        }
    }

    public void put(K key,V value){
        Node<K, V> cur = root;
        Node<K, V> pre = null;
        while(cur!=null){
            pre = cur;      // 记录前驱节点
            if (cur.key.compareTo(key) < 0) {
                cur = cur.right;        // cur向右找
            }else if(cur.key.compareTo(key) > 0){
                cur = cur.left;
            }else {
                cur.value = value;      // 更新值
                return;
            }
        }
        // 循环结束，说明该key不存在
        // 创建新节点并插入
        Node<K, V> newNode = new Node<>(key,value);
        if (pre == null) {  // 说明根节点为空
            root = newNode;
        } else if (pre.key.compareTo(key) > 0) {
            pre.left = newNode;
            newNode.parent = pre;
        }else {
            pre.right = newNode;
            newNode.parent = pre;
        }
        fixRedRed(newNode);
    }

    /**
     * 对该节点和父节点是红色的重新修正
     * @param node
     */
    private void fixRedRed(Node<K,V> node) {
        if(node == root){
            root.color = BLACK;
            return;
        }

        Node<K, V> parent = node.parent;    // 父节点
        Node<K, V> uncle = node.uncle();    // 叔叔节点
        Node<K, V> grandpa = node.parent.parent;

        if(isRed(parent)){      // 父节点为红色

            if(isRed(uncle)){   // 叔叔红色
                // 1、将父节点设为黑色，叔叔设为红色
                parent.color = BLACK;
                uncle.color = RED;
                // 2、将祖父设为红色
                grandpa.color = RED;
                // 3、如果祖父为根，再变回黑色
                if(grandpa == root){
                    grandpa.color = BLACK;
                }else {
                    // 4、祖父不为根，将祖父节点设为当前节点进行递归
                    fixRedRed(grandpa);
                }
            }else {     // 叔叔为黑色
                // 判断当前节点是父的左孩子还是右孩子
                if (node == parent.left) {      // 左孩子
                    // 1、将父节点设为黑色
                    parent.color = BLACK;
                    // 2、将祖父设为红色
                    grandpa.color = RED;
                    // 3、以祖父为根右旋
                    rightRotate(grandpa);
                }else { // 右孩子
                    // 将父亲作为当前节点并左旋，在进行递归判断
                    leftRotate(parent);
                    fixRedRed(parent);
                }
            }
        }
        // 父节点为黑色，不需要进行任何操作
    }

    public void remove(K key){
        Node<K, V> deleted = find(key);     // 找到要删除的节点
        if(deleted == null){
            return;
        }
        doRemove(deleted);

    }

    /**
     * 递归完成删除节点操作
     * @param deleted
     */
    // TODO: 2023/11/22 红黑树的删除没学懂 
    private void doRemove(Node<K,V> deleted) {
        Node<K,V> replace = findReplace(deleted); // 找到要替代该位置的节点
        Node<K, V> parent = deleted.parent;
        // 1、如果没有替代节点，则直接删除该节点。
        if(replace == null){
            // 1.1 删除的是根节点
            if(deleted == root){
                root = null;
            }else {
                if(isBlack(deleted)){
                    // 删除黑色节点时，需要进行修正
                    fixDoubleBlack(deleted);
                }else {
                    // 删除红色节点时，无需任何处理
                }

                // 1.2 删除的是叶子节点
                if (deleted.isLeftChild()) {
                    parent.left = null;
                }else {
                    parent.right = null;
                }
                deleted.parent = null;  // 将父节点设为null，有助于垃圾回收
            }
            return;
        }

        // 2、有替代节点，左孩子或者右孩子
        if(deleted.left == null || deleted.right == null){
            // 2.1 删除的是根节点
            if(deleted == root){
                // 将代替节点直接赋值于根节点
                root.key = replace.key;
                root.value = replace.value;
                // TODO: 2023/11/22 没懂这句
                root.left = root.right = null;
            }else {
                // 2.2 删除的不是根节点

                if(deleted.isLeftChild()){
                    parent.left = replace;
                }else {
                    parent.right = replace;
                }
                // 将替代节点的父节点指向被删除节点的父节点
                replace.parent = parent;
                // 将被删除节点指针都指向null，有助于垃圾回收
                deleted.parent = deleted.right = deleted.left = null;

                // 2.3 删除完节点后，判断是否需要修正双黑
                if(isBlack(replace) && isBlack(deleted)){
                    //
                    fixDoubleBlack(replace);
                }else {
                    // 删除的是黑色，剩下的是红色节点
                    replace.color = BLACK;
                }

            }
            return;
        }

        // 3、有左右子节点 =》有一个孩子或者没有孩子
        // 此时可以直接将复杂问题简单化
        // 直接交换两节点的key和value
        K key = deleted.key;
        deleted.key = replace.key;
        replace.key = key;

        V value = deleted.value;
        deleted.value = replace.value;
        replace.value = value;

        // 交换后，删除的节点变为替代节点
        doRemove(replace);  // 递归删除替代节点
    }

    /**
     * 解决删除中的双黑情况
     * @param node
     */
    private void fixDoubleBlack(Node<K,V> node){
        if(node == root){
            return;
        }
        Node<K, V> parent = node.parent;
        Node<K, V> sibling = node.sibling();
        // 判断兄弟颜色

        if(isRed(sibling)){       // 兄弟节点为红色
            if (node.isLeftChild()) {
                leftRotate(parent);
            }else {
                rightRotate(parent);
            }
            parent.color = RED;
            sibling.color = BLACK;
            fixDoubleBlack(node);
            return;
        }else {             // 兄弟节点为黑色
            // 判断兄弟节点是否为空
            if(sibling != null){    // 兄弟节点不为空
                // 兄弟是黑色 ，两个侄子也是黑色
                if(isBlack(sibling.left) && isBlack(sibling.right)){
                    sibling.color = RED;
                    if (isRed(parent)) {
                        parent.color = BLACK;
                    } else {
                        fixDoubleBlack(parent);
                    }
                }else {     // 兄弟节点是黑色,侄子有红色
                    // LL
                    if (sibling.isLeftChild() && isRed(sibling.left)) {
                        rightRotate(parent);
                        sibling.left.color = BLACK;
                        sibling.color = parent.color;
                    }
                    // LR
                    else if (sibling.isLeftChild() && isRed(sibling.right)) {
                        sibling.right.color = parent.color;
                        leftRotate(sibling);
                        rightRotate(parent);
                    }
                    // RL
                    else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                        sibling.left.color = parent.color;
                        rightRotate(sibling);
                        leftRotate(parent);
                    }
                    // RR
                    else {
                        leftRotate(parent);
                        sibling.right.color = BLACK;
                        sibling.color = parent.color;
                    }
                    parent.color = BLACK;

                }

            }else {     // 兄弟节点为null
                fixDoubleBlack(parent);
            }
        }
    }


    /**
     * 根据key查找节点
     * @param key
     * @return
     */
    Node<K,V> find(K key){
        Node<K, V> cur = root;
        while(cur!=null){
            if (cur.key.compareTo(key) < 0) {
                cur = cur.right;        // cur向右找
            }else if(cur.key.compareTo(key) > 0){
                cur = cur.left;
            }else {
                return cur;
            }
        }
        return null;
    }


    /**
     * 当一个节点被删除时，需要找到并替换删除的节点。
     * @param deleted
     * @return
     */
    Node<K,V> findReplace(Node<K,V> deleted){
        // 1、如果被删除的节点没有左右子节点，直接返回null。
        if (deleted.left == null && deleted.right == null) {
            return null;
        }
        // 2、如果被删除的节点没有左子节点，直接返回右子节点。
        if (deleted.left == null){
            return deleted.right;
        }
        // 3、如果被删除的节点没有右子节点，直接返回左子节点。
        if (deleted.right == null) {
            return deleted.left;
        }
//        4、如果被删除的节点既有左子节点又有右子节点，需要找到被删除节点的后继节点。
//         后继节点是指右子树中的最小节点。可以通过将指针指向右子节点开始，一直向右子树移动，
//         直到找到一个没有右子节点的节点为止。返回后继节点作为替换删除节点的结果。
        Node<K, V> successor = deleted.right;
        if (successor.left !=null) {
            successor = successor.left;
        }
        return successor;

    }


}
