package Tree树;



import java.util.Arrays;

/**
 * B-树的实现
 * B-树的特性：
 * 1. 每个节点最多有m个孩子，其中m称为B-树的阶；
 * 2·除根节点和叶子节点外,其他每个节点至少有ceil(m/2)个孩子;
 * 3.若根节点不是叶子节点,则至少有两个孩子;
 * 4. 所有叶子节点都在同一层；
 * 5. 每个非叶子节点由n个关键字和n+1个指针组成，其中ceil(m/2）-1<=n<=m-1；
 * 6·关键字按非降序排列,即节点中的第i个关键字大于等于第i-1个关键字;
 * 7.指针P.[i]指向关键字值位于第i个关键字和第i+1个关键字之间的子树。
 */
public class BTree {


    Node root;

    int minDegree;          // 最小度数
    final int MIN_KEY_NUMBER;   // 最小key数目
    final int MAX_KEY_NUMBER;   // 最大key数目

    public BTree(int minDegree) {
        root = new Node(minDegree);
        this.minDegree = minDegree;
        this.MIN_KEY_NUMBER = minDegree - 1;
        this.MAX_KEY_NUMBER = 2 * minDegree - 1;
    }

    /**
     * 判断是否包含key
     *
     * @param key
     * @return
     */
    public boolean contain(int key) {
        return root.get(key) != null;
    }


    /**
     * 新增key
     *
     * @param key
     */
    public void put(int key) {
        doPut(root, key, null, 0);
    }

    /**
     * 新增key——内部方法，递归实现
     * 首先查找本节点中的插入位置 i，如果没有空位（key 被找到），应该走更新的逻辑，目前什么没做接下来分两种情况。
     * 如果节点是叶于节点,可以直接插入了。
     * 如果节点是非叶于节点,需要继续在children[i]处继续递归插入
     * 无论哪种情况，插入完成后都可能超过节点 keys 数目限制，此时应当执行节点分裂
     *
     * @param node
     * @param key
     * @param parent
     * @param index
     */
    private void doPut(Node node, int key, Node parent, int index) {
        int i = 0;
        // 遍历有效关键字数量keyNumber
        while (i < root.keyNumber) {
            if (node.keys[i] == key) {   // key相同于keys[i],返回当前节点
                return;     // 因为这里没有设置value，所以不用更新值，直接return
            }
            if (node.keys[i] > key) {    // key 小于keys[i],此时应该找孩子
                break;
            }
            // key 大于keys[i],此时应该向右边比较
            i++;
        }
        // 循环结束，此时应该返回孩子，但先判断是不是叶子节点
        if (node.isLeaf) {
            node.insertKey(i, key);        // 是叶子节点，返回null
        } else {
            doPut(node.children[i], key, node, i);        // 当前节点不是叶子节点，便找到第i个孩子节点，继续递归
        }

        // 添加完key后判断是否需要分裂
        if (node.keyNumber == MAX_KEY_NUMBER) {
            split(node, parent, index);
        }

    }



    /**
     * 节点分裂
     * ·如果 parent== null 表示要分裂的是根节点，此时需要创建新根，原来的根节点作为新根的 0 孩子
     * 否则直接：
     * 创建right节点(分裂后大于当左前节点的),把t以后的关键和孩子都拷贝过去
     * t - 1处的key插入到parent处的索引,索引指左作为孩子时的索引
     * right节点作为parent的孩子插入到指数+ 1处
     *
     * @param left   要分裂的节点
     * @param parent 其父节点
     * @param index  被分裂节点所在的index
     */
    private void split(Node left, Node parent, int index) {
        if (parent == null) {
            Node newRoot = new Node(minDegree);
            newRoot.insertChild(0, left);    // 把要分裂的节点当作新节点的0索引孩子（第一个孩子
            newRoot.isLeaf = false;
            this.root = newRoot;
            parent = newRoot;
        }

        // 1、创建right节点，把left中的t之后的key和child移动过去
        Node right = new Node(minDegree);   // 树的最小度和节点的最小度保持一致
        right.isLeaf = left.isLeaf;
        System.arraycopy(left.keys, minDegree, right.keys, 0, minDegree - 1);   // 拷贝key到新节点,拷贝个数min-1
        if (!right.isLeaf) {
            System.arraycopy(left.children, minDegree, right.children, 0, minDegree);       // 拷贝children到新节点，拷贝个数min
        }

        right.keyNumber = minDegree - 1;        // 新节点被分到min-1个节点
        left.keyNumber -= minDegree - 1;        // 分裂的节点被分出去min-1个节点
        // 2、中间的key插入到父节点
        parent.insertKey(index, left.keys[minDegree - 1]);
        left.keyNumber--;       // 分裂的节点被分出去1个节点到父节点
        // 3、right节点作为父节点的孩子
        parent.insertChild(index + 1, right);
    }


    /**
     * 删除key
     * @param key 要删除的key
     */
    public void remove(int key){
        doRemove(null,root,0,key);
    }

    /**
     * 删除key——递归实现
     * @param node key可能所在的节点
     * @param key 要删除的key
     */
    private void doRemove(Node parent,Node node,int index, int key) {
        int i = 0;
        // 找该key所在的位置
        while(i < node.keyNumber){
            if( key <= node.keys[i] ){
                break;
            }
            i++;
        }

        if(isFound(node,key,i)){    // i找到：代表待删除key的索引
            if(node.isLeaf){        // case1:找到了，当前节点是叶子节点
                node.removeKey(i);      // 直接删除
            }else {                 // case2:找到了，当前节点不是叶子节点
                // 1、找到后继的key
                Node successor = node.children[i+1];
                while(!successor.isLeaf){
                    successor =successor.children[0];
                }
                // 循环结束，说明找到叶子节点，那么该节点的0号索引就是后继key
                int skey = successor.keys[0];

                // 2、替换待删除的key
//                successor.keys[0] = key;
                node.keys[i] = skey;

                // 3、删除后继的key
                doRemove(node,node.children[i+1],i+1,skey);
            }
        }else {         // i没找到：代表到第i个孩子继续查找
            if(node.isLeaf){        // case3:没找到，当前节点是叶子节点
                return;
            }else {                 // case4:没找到，当前节点不是叶子节点
                doRemove(node,node.children[i],i,key);     // 从第i个孩子继续递归
            }
        }

        // 判断是否需要重新调整平衡
         if(node.keyNumber < MIN_KEY_NUMBER){       // 当有效key数目小于最小有效key数目的时候，需要重新调整
             // case 5，case 6
             balance(parent,node,index);
         }

    }

    /**
     * 调用平衡
     * @param parent 要调整节点的父节点
     * @param node  要调整节点
     * @param index 要调整节点的索引，是第几个孩子
     */
    private void balance(Node parent,Node node,int index){
        // case 6 根节点调整平衡情况
        if (node == root){
            if (root.keyNumber == 0 && root.children[0] != null){
                root = root.children[0];
            }
            return;
        }

        // case 5
        Node left = parent.childLeftSibling(index);
        Node right = parent.childRightSibling(index);

        if(left != null && left.keyNumber > MIN_KEY_NUMBER){    // 左兄弟富裕
            // case 5-1 右旋
            // a）父节点中前驱key旋转下来,插入到要删除的key
            int prekey = parent.keys[index - 1];
            node.insertKey(0,prekey);

            // b）left中最大的孩子换爹
            if (!left.isLeaf){  // 要先判断是不是叶子节点
                //  兄弟最大孩子换到node最小孩子
                Node rightmostChild = left.removeRightmostChild();
                node.insertChild(0,rightmostChild);
            }

            // c）left中最大的key旋转上去
            int rightmostKey = left.removeRightmostKey();
            parent.keys[index-1] =rightmostKey;

            return;
        }

        if(right != null && right.keyNumber > MIN_KEY_NUMBER){    // 右兄弟富裕
            // case 5-2 右边富裕，左旋
            // a） 父节点中后继key旋转下来
            int successorKey = parent.keys[index];
            node.insertKey(node.keyNumber,successorKey);

            // b) right中最小的孩子换爹
            if (right.isLeaf) {
                Node leftmostChild = right.removeLeftmostChild();
                node.insertChild(node.keyNumber+1, leftmostChild);     // TODO: 2023/11/25 没懂这里为什么+1
            }

            // c) right中最小的key旋转上去
            int leftmostKey = right.removeLeftmostKey();
            parent.keys[index] = leftmostKey;
        }

        // 左右兄弟都不富裕的情况下
        if(left != null){   // 左兄弟不为空，合并到左兄弟
            parent.removeChild(index);      // 把当前节点从父节点删掉
            // TODO: 2023/11/25 这句不是很懂
            left.insertKey(parent.removeKey(index-1), left.keyNumber);       // 讲父节点的要删除node对应的前一个key插入到兄弟中
            node.moveToTarget(left);    // 把当前节点合并到左兄弟

        }else {     // 左兄弟为空，把右兄弟合并到该节点
            // 向自己合并
            parent.removeChild(index+1);    // 把右兄弟删掉
            node.insertKey(parent.removeKey(index),node.keyNumber );   // TODO: 2023/11/25 这句不是很懂，index  // 讲父节点的
            right.moveToTarget(node);       // 把右兄弟合并到该节点
        }


    }

    /**
     * 判断该节点中是否存在key
     * @param node
     * @param key
     * @param index 索引
     * @return
     */
    private boolean isFound(Node node,int key, int index){
        return index < node.keyNumber && node.keys[index] == key;   // 要先判断index是否合法，防止索引越界
    }


    static class Node {
        int[] keys;         // 关键字
        Node[] children;        // 孩子
        int keyNumber;          // 有效关键字数量
        boolean isLeaf = true;  // 是否是叶子节点
        int minDegree;          // 最小度数（最小孩子数）


        public Node(int minDegree) {
            this.minDegree = minDegree;
            this.keys = new int[2 * minDegree - 1];         // 特性第五条：关键字个数至少为2*minDegree-1
            this.children = new Node[2 * minDegree];      // 特性第六条：m叉树至少一个节点孩子个数至少为2*minDegree
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOfRange(keys, 0, keyNumber));
        }

        /**
         * 多路查找
         *
         * @param key
         * @return
         */
        Node get(int key) {
            int i = 0;
            // 遍历有效关键字数量keyNumber
            while (i < keyNumber) {
                if (keys[i] == key) {   // key相同于keys[i],返回当前节点
                    return this;
                }
                if (keys[i] > key) {    // key 小于keys[i],此时应该找孩子
                    break;
                }
                // key 大于keys[i],此时应该向右边比较
                i++;
            }
            // 循环结束，此时应该返回孩子，但先判断是不是叶子节点
            if (isLeaf) {
                return null;        // 是叶子节点，返回null
            }
            // 不是叶子节点，返回孩子所在的第i个位置
            return children[i].get(key);        // 通过递归直到找到该key，或者null

        }

        /**
         * 向keys指定索引处插入key
         *
         * @param key   要插入的元素
         * @param index 插入位置的索引
         */
        void insertKey(int index, int key) {
            /*keys是源数组，index是起始位置，keyNumber是需要复制的元素个数，keys是目标数组，index+1是目标位置。
            这个函数将keys数组中的从index开始的keyNumber - index个元素复制到keys数组的从index+1开始的位置。*/
            System.arraycopy(keys, index, keys, index + 1, keyNumber - index);
            keys[index] = key;
            keyNumber++;
        }

        /**
         * 根据索引插入孩子节点
         *
         * @param index
         * @param child
         */
        void insertChild(int index, Node child) {
            System.arraycopy(children, index, children, index + 1, keyNumber - index);
            children[index] = child;
        }


        /**
         * 移除指定index处的key
         *
         * @param index 索引
         * @return 移除的key
         */
        int removeKey(int index) {
            int key = keys[index];
            System.arraycopy(keys, index + 1, keys, index, --keyNumber - index);    // --keyNumber - index因为移除了一个key，所以要先减去1
            return key;
        }

        /**
         * 移除最左边的key
         *
         * @return key
         */
        int removeLeftmostKey() {
            return removeKey(0);
        }

        /**
         * 移除最右边的key
         *
         * @return key
         */
        int removeRightmostKey() {
            return removeKey(keyNumber - 1);
        }

        /**
         * 移除指定index处的child
         *
         * @param index 索引
         * @return 被移除的孩子节点
         */
        Node removeChild(int index) {
            Node child = children[index];
            System.arraycopy(children, index + 1, children, index, keyNumber - index);
            children[keyNumber] = null;     // 删除一个孩子都做左移了，所以最后一个要赋空
            return child;
        }

        /**
         * 移除最右边的孩子
         *
         * @return
         */
        Node removeRightmostChild() {
            return removeChild(keyNumber);
        }

        /**
         * 移除最左边的孩子
         *
         * @return
         */
        Node removeLeftmostChild() {
            return removeChild(0);
        }

        /**
         * index孩子处左边的兄弟
         *
         * @param index 孩子索引
         * @return 兄弟节点
         */
        Node childLeftSibling(int index) {
            return index > 0 ? children[index - 1] : null;        // 教程写的
//            return index - 1 >= 0 ? children[index - 1] : null; // 自己写的
        }


        /**
         * index孩子处右边的兄弟
         *
         * @param index 孩子索引
         * @return 兄弟节点
         */
        Node childRightSibling(int index) {
//            return index + 1 <= keyNumber ? children[index + 1] : null;  // 自己写的
            return index == keyNumber ? null : children[index + 1];      // 教程写的
        }

        /**
         * 复制当前节点所有的key和child到target
         *
         * @param target 目标节点
         */
        void moveToTarget(Node target) {
            int start = target.keyNumber;

            for (int i = 0; i < keyNumber; i++) {
                target.keys[keyNumber++] = keys[i];     // target可能原本就有其keyNumber个key，所以要从keyNumber开始赋值，每次赋值都要加1
            }

            if(!isLeaf){     // 判断当前节点是不是叶子节点
                for (int i = 0; i < keyNumber; i++) {
                    target.children[start+i] = children[i];     // target可能原本就有其keyNumber个孩子，所以要从keyNumber+i开始赋值
                }
            }
        }
    }


}
