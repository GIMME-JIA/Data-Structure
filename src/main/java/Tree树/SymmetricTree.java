package Tree树;

public class SymmetricTree {
    public static void main(String[] args) {

    }


    /**
     * 判断二叉树是否对称
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root){
        return check(root.left,root.right);
    }

    /**
     * 判断两个节点是否对称
     * @param left
     * @param right
     * @return
     */
    public boolean check(TreeNode left,TreeNode right){
        // 1、两节点都为空的时候对称
        if(left == null && right == null){
            return true;
        }
        // 2、两节点一空一不空时不对称
        if(left == null || right == null){
            return false;
        }
        // 3、两节点值不相等时不对称
        if (right.val != left.val){
            return false;
        }
        // 4、两节点值相同，递归 再判断其子节点
        // 左左对右右，左右对右左
        return check(left.left,right.right) && check(left.right,right.left);
    }
}
