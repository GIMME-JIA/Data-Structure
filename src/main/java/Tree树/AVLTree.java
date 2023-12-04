package Tree树;

/**
 * 平衡二叉树
 */
public class AVLTree<K extends Comparable<K>, V> {

    // 根节点
    AVLNode<K, V> root;


    /**
     * 节点类
     *
     * @param <K>
     * @param <V>
     */
    static class AVLNode<K extends Comparable<K>, V> {
        K key;
        V value;
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int height = 1;     // 高度

        public AVLNode(K key) {
            this.key = key;
        }

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public AVLNode(K key, V value, AVLNode<K, V> left, AVLNode<K, V> right, int height) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.height = height;
        }

        /*@Override
        public int compareTo(AVLNode<K, V> o) {
            return this.key - o.key;
        }*/
    }

    /**
     * 获取节点的高度值
     *
     * @param node
     * @return
     */
    private int height(AVLNode<K, V> node) {
        return node != null ? node.height : 0;
    }


    /**
     * 更新节点的高度
     *
     * @param node
     */
    private void updateHeight(AVLNode<K, V> node) {
        node.height = Integer.max(height(node.left), height(node.right)) + 1;
    }

    /**
     * 平衡因子：考量以node为根节点的树是否平衡
     * 判别标准：如果返回值的绝对值小于等于1，说明此时平衡
     * 返回值小于-1，说明右子树比左子树高且超过2，需要旋转右子树
     * 返回值大于1，说明左子树比右子树高且超过2，需要旋转左子树
     *
     * @param node
     * @return
     */
    private int balanceFactor(AVLNode<K, V> node) {
        return height(node.left) - height(node.right);
    }


    /**
     * 以node根节点进行右旋转
     *
     * @param node
     * @return 新的根节点
     */
    private AVLNode<K, V> leftRotate(AVLNode<K, V> node) {
        AVLNode<K, V> newRoot = node.left;  // 左子节点将右旋成为新的根节点
        AVLNode<K, V> son = node.left.right;    // 左子节点的右孩子需要换爹
        node.left = son;    // 原来的根节点接上要换爹的节点
        newRoot.right = node;   // 原来的根节点现在变成新根的右孩子，代替要换爹的孩子节点

        // 更新节点和新根的高度
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;     // 返回新的根节点，表示上位
    }

    /**
     * 以node根节点进行左旋转
     *
     * @param node
     * @return 新的根节点
     */
    private AVLNode<K, V> rightRotate(AVLNode<K, V> node) {
        AVLNode<K, V> newRoot = node.right;
        AVLNode<K, V> son = node.right.left;
        newRoot.left = node;
        node.right = son;

        // 更新节点和新根的高度
        updateHeight(node);     // 先更新更新后较低高度节点
        updateHeight(newRoot);     // 再更新新根节点高度
        return newRoot;
    }


    /**
     * 左右旋：因为左子树的右子树问题
     *
     * @param node
     * @return
     */
    private AVLNode<K, V> LRRotate(AVLNode<K, V> node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }


    /**
     * 右左旋：因为右子树的左子树问题，
     *
     * @param node
     * @return
     */
    private AVLNode<K, V> RLRotate(AVLNode<K, V> node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    /**
     * 检查节点是否失衡，重新平衡二叉树
     *
     * @param node
     * @return
     */
    private AVLNode<K, V> balance(AVLNode<K, V> node) {
        if (node == null) {
            return null;
        }
        // 根据树的四种失衡情况来做平衡

        if (balanceFactor(node) > 1 && balanceFactor(node.left) >= 0) {               // 1、平衡因子大于1，说明左子树比右子树高
            // 1。1 当左子树的左子树更高时，LL
            // 此时只需以根节点右旋一次即可
            return rightRotate(node);
        } else if (balanceFactor(node) > 1 && balanceFactor(node.left) < 0) {
            // 1.2 当左子树的右子树更高时，LR
            // 此时需要先将左子树左旋再将根右旋
            return LRRotate(node);
        } else if (balanceFactor(node) < -1 && balanceFactor(node.right) > 0) {      // 2、平衡因子小于-1，说明右子树比左子树高
            // 2.1 当右子树的左子树更高时，RL
            // 此时需要先将右子树右旋再将根左旋
            return RLRotate(node);
        } else if (balanceFactor(node) < -1 && balanceFactor(node.right) <= 0) {
            // 2.2 当右子树的右子树更高时，RR
            // 此时只需将根节点左旋一次即可
            return leftRotate(node);
        }
        // 注明：当左子树或右子树的平衡因子为零时候，即有节点删除之后，此时失衡也要进行一次左旋或者右旋

        // 当该节点为根的树没有失衡的时候，直接返回
        return node;
    }


    /**
     * 添加新节点
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        doPut(root, key, value);
    }

    /**
     * 递归方式实现添加节点
     *
     * @param node
     * @param key
     * @param value
     */
    private AVLNode<K, V> doPut(AVLNode<K, V> node, K key, V value) {
        // 1、找到空位创建新节点插入
        if (node == null) {
            return new AVLNode<>(key, value);
        }
        // 2、key已存在，更新value
        if (key == node.key) {
            node.value = value;
            return node;
        }
        // 3、继续查找
        if (node.key.compareTo(key) < 0) {        // key比节点大时，向右找
            node.right = doPut(node.right, key, value);
        } else {                                 // key比节点小时，向左找
            node.left = doPut(node.left, key, value);
        }

        // 判断添加节点后是否造成树的失衡，递归回来的时候会对每个节点(父节点以上）的高度进行更新和判断
        updateHeight(node);
        return balance(node);
    }


    /**
     * 删除节点
     *
     * @param key
     */
    public void removeByRecursive(K key) {
        root = doRemove(root, key);
    }

    /**
     * 递归实现删除操作
     *
     * @param node
     * @param key
     * @return
     */
    private AVLNode<K, V> doRemove(AVLNode<K, V> node, K key) {
        // 1、节点key不存在
        if (node == null) {
            return null;
        }
        // 2、没找到key,继续向左或向右找
        if (node.key.compareTo(key) < 0) {        // 向右找
            node.right = doRemove(node.right, key);
        } else if (node.key.compareTo(key) > 0) {   // 向左找
            node.left = doRemove(node.left, key);
        } else {                 // 找到了
            // 3、找到key 1）没有孩子  2）只有一个孩子  3）有两个孩子
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {     //只有右孩子
                node = node.right;
            } else if (node.right == null) {    // 只有左孩子
                node = node.left;
            } else {             // 有两孩子
                // 1、找要删除节点的后继
                AVLNode<K, V> successor = node.right;
                while (successor.left != null){
                    successor = successor.left;
                }
                // 2、处理后继节点
                successor.right = doRemove(node.right,successor.key);
                successor.left = node.left;
                node = successor;
            }

        }

        // 更新高度
        updateHeight(node);
        // 调节树的平衡
        return balance(node);

        // 4、更新高度

        // 5、重新平衡树
    }

}
