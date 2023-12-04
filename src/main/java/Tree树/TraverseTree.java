package Tree树;

import java.util.LinkedList;

/**
 * 遍历二叉树
 */
public class TraverseTree {
    public static void main(String[] args) {

    }


    /**
     * 前中后序通用模板
     *
     * @param root
     */
    static void order(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;
        TreeNode pre = null;    // 记录前一个弹栈的元素
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                // 待处理的左子树
                stack.push(curr);
                System.out.println("pre:"+curr.val);        // 前序遍历打印
                curr = curr.left;
            } else {
                TreeNode top = stack.peek();    // 查看栈顶元素
                if (top.right == null) {
                    System.out.println("in:"+top.val);      // 中序遍历
                    // 没有右子树
                    pre = stack.pop();       // 记录弹栈节点
                    System.out.println("post:" + pre.val);      // 后序遍历
                } else if (top.right == pre) {
                    // 右子树处理完成
                    pre = stack.pop();       // 记录弹栈节点
                    System.out.println("post:" + pre.val);      // 后序遍历
                } else {
                    System.out.println("in:" + top.val);        // 中序遍历

                    // 待处理的右子树
                    curr = top.right;

                }
            }
        }
    }


    /*
        非递归实现前中后序遍历
     */

    /**
     * 前序遍历
     *
     * @param root
     */
    static void preOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;

        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);       // 进栈
                System.out.println(curr);       // 访问时打印，前序
                curr = curr.left;       // 找左子
            } else {
                TreeNode pop = stack.pop();     // 出栈
                curr = pop.right;       // 找右子
            }

        }
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    static void inOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode curr = root;
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                TreeNode pop = stack.pop();
                System.out.println(curr);
                curr = pop.right;
            }

        }
    }

    /**
     * 后序遍历
     *
     * @param root
     */
    static void postOrder(TreeNode<Integer> root) {
        LinkedList<TreeNode<Integer>> stack = new LinkedList<>();
        TreeNode curr = root;
        TreeNode pre = null;    // 记录前一个弹栈的元素
        while (!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                TreeNode top = stack.peek();    // 查看栈顶元素
                if (top.right == null || top.right == pre) {
                    // 当右子树为空或者右子树已经处理完成（因为前一个弹栈的是右子树
                    // 该树对应的根节点也能出栈了
                    pre = stack.pop();       // 记录弹栈节点
                    System.out.println(pre.val);    // 输出相应的值
                } else {
                    // 如果右子树还未处理过，则作为当前节点进行处理
                    curr = top.right;
                }
            }
        }
    }


    /*
        递归实现前中后序遍历
     */

    /**
     * 前序遍历
     *
     * @param node 节点
     */
    static void preOrderByRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + "\t"); // 值
        preOrderByRecursive(node.left); // 左
        preOrderByRecursive(node.right); // 右
    }

    /**
     * 中序遍历
     *
     * @param node 节点
     */
    static void inOrderByRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderByRecursive(node.left); // 左
        System.out.print(node.val + "\t"); // 值
        inOrderByRecursive(node.right); // 右
    }

    /**
     * 后序遍历
     *
     * @param node 节点
     */
    static void postOrderByRecursive(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderByRecursive(node.left); // 左
        postOrderByRecursive(node.right); // 右
        System.out.print(node.val + "\t"); // 值
    }

}
