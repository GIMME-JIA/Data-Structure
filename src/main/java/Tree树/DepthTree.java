package Tree树;

import java.util.*;

public class DepthTree {
    public static void main(String[] args) {

    }

    /*
    思路：
    1. 得到左子树深度, 得到右子树深度, 二者最大者加一, 就是本节点深度
    2. 因为需要先得到左右子树深度, 很显然是后序遍历典型应用
    3. 关于深度的定义：从根出发, 离根最远的节点总边数

        深度2         深度3         深度1
        1            1            1
       / \          / \
      2   3        2   3
                        \
                         4
           本质：后序遍历
     */


    // 求二叉树最大深度=========================================================================================
    /**
     * 递归实现求树最大深度
     *
     * @param root
     * @return
     */
    static int maxDepthByRecursive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int dl = maxDepthByRecursive(root.left);    // 左子树深度
        int dr = maxDepthByRecursive(root.right);   // 右子树深度
        return Integer.max(dl, dr) + 1;  // 加上当前层并返回
    }

    /**
     * 非递归后序遍历求二叉树最大深度
     *
     * @param root
     * @return
     */
    static int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }

        TreeNode curr = root;
        TreeNode pre = null;    // 记录上一个弹栈的元素
        LinkedList<TreeNode> stack = new LinkedList<>();   // 创建栈

        int max = 0;    // 记录栈中的最大长度，即树的最大高度

        while (curr != null || !stack.isEmpty()) {
            // 遍历左子树
            if (curr != null) {
                // 进栈
                stack.push(curr);
                if (stack.size() > max) {       // 更新树的最大深度
                    max = stack.size();
                }
                // 指向左子树
                curr = curr.left;
            } else {
                // 当前节点为空时，说明左子树遍历到头了，查看栈顶，查看右子树
                TreeNode top = stack.peek();    // 获取栈顶元素
                if (top.right == null || pre == top) {
                    // 如果右子树为空或者右子树已经遍历完成，可以弹出该元素,并记录弹出的节点
                    pre = stack.pop();
                } else {
                    // 右子树不为空，且未遍历过，先遍历右子树，再回来处理根节点
                    curr = top.right;
                }
            }
            // 判断右子树

        }
        return max;
    }

    /**
     * 使用队列层序遍历实现树的最大深度
     *
     * @param root
     * @return
     */
    static int maxDepthByQueue(TreeNode root) {
        if(root == null){
            return 0;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        int max = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                TreeNode poll = queue.poll();
                if (poll.left != null)
                    queue.add(poll.left);
                if (poll.right != null)
                    queue.add(poll.right);
            }
            max++;
        }
        return max;
    }


    // 求二叉树最小深度=========================================================================================

    /**
     * 递归实现求树最大深度
     *
     * @param root
     * @return
     */
    static int minDepthByRecursive(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int dl = minDepthByRecursive(root.left);    // 左子树深度
        int dr = minDepthByRecursive(root.right);   // 右子树深度
        if (dl == 0 || dr == 0) {
            return dl + dr + 1;
        }
        return Integer.min(dl, dr) + 1;  // 加上当前层并返回
    }

    /**
     * 使用队列层序遍历实现树的最大深度
     *
     * @param root
     * @return
     */
    static int minDepthByQueue(TreeNode root) {
        if(root == null){
            return 0;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        int min = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            min++;  //
            for (int i = 0; i < queue.size(); i++) {
                TreeNode poll = queue.poll();
                // 发现第一个叶子节点就返回
                if(poll.right == null && poll.left == null){
                    return min;
                }
                if (poll.left != null)
                    queue.add(poll.left);
                if (poll.right != null)
                    queue.add(poll.right);
            }

        }
        return min;
    }

}
