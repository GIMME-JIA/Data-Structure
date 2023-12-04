package Tree树;

public class InvertTree {
    public static void main(String[] args) {

    }

    /**
     * 反转二叉树，左右倒置
     * @param root
     */
    static TreeNode invertTree(TreeNode root){
        fn(root);
        return root;
    }

    static void fn(TreeNode node){
        if (node == null){
            return;
        }

        // 交换左右子节点
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;

        // 递归反转子树
        fn(node.left);
        fn(node.right);
    }

}
