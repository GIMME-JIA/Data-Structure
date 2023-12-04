package Tree树;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 表达式与树的转换
 */
public class ExpressionTree {
    public static void main(String[] args) {

    }


    /**
     * 根据后缀表达式创建二叉树
     * @param postExpression
     * @return
     */
    public static TreeNode<String> constructPostExpressionTree(String[] postExpression){
        LinkedList<TreeNode<String>> stack = new LinkedList<>();
        for (String s : postExpression) {
            // TODO: 2023/11/26  java: -source 11 中不支持 多个 case 标签
            /*switch (s) {
                case "*","-","+","/"->{
                    // 遇到标点符号，弹出栈顶两个元素建立树
                    TreeNode<String> root = new TreeNode<>(s);
                    // 左右子节点不能反
                    root.right = stack.poll();
                    root.left = stack.poll();
                    // 再将整个符号压入栈
                    stack.push(root);
                }
                default -> {
                    // 遇到数字，直接进栈
                    stack.push(new TreeNode<>(s));
                }
            }*/
        }
        return stack.peek();    // 返回栈顶元素
    }



    /*
        preOrder = {1,2,4,3,6,7}
        inOrder = {4,2,1,6,3,7}

        根 1
            pre         in
        左  2,4         4,2
        右  3,6,7       6,3,7


        根 2
        左 4

        根 3
        左 6
        右 7
     */

    /**
     * 根据前序遍历和中序遍历创建二叉树
     * @param preOrder
     * @param inOrder
     * @return
     */
    public static TreeNode<Integer> buildTreeByPreAndIn(int[] preOrder,int[] inOrder){
        if(preOrder.length == 0 || inOrder.length == 0){
            return null;        // 递归的终止条件
        }
        
        // 创建根节点
        int rootvalue = preOrder[0];
        TreeNode<Integer> root = new TreeNode<>(rootvalue);

        // 区分左右子树,
        // 通过中序遍历找根节点所在位置，将左右子树分开
        for (int i = 0; i < inOrder.length; i++) {
            if(inOrder[i] == rootvalue){
                // 划分中序遍历的左右子树
                int[] inLeft = Arrays.copyOfRange(inOrder, 0, i);   // 拷贝0~i-1位置的元素，为左子树
                int[] inRight = Arrays.copyOfRange(inOrder, i + 1, inLeft.length);  // 拷贝i+1到length-1位置的元素为右子树

                // 划分前序遍历的左右子树
                int[] preleft = Arrays.copyOfRange(preOrder, 1, i + 1);
                int[] preRight = Arrays.copyOfRange(preOrder, i + 1, preOrder.length);

                // 将左子树部分和右子树部分再次递归
                TreeNode<Integer> leftTree = buildTreeByPreAndIn(preleft, inLeft);
                TreeNode<Integer> rightTree = buildTreeByPreAndIn(preRight, inRight);

                root.right = rightTree;
                root.left = leftTree;

                break;
            }
        }

        return root;
    }


    /**
     * 根据中序和后序遍历构建二叉树
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode<Integer> buildTreeByInAndPost(int[] inorder, int[] postorder) {
        // 3、确定递归中止条件
        if(inorder.length == 0 || postorder.length == 0){
            return null;
        }
        // 1、确定根节点
        int rootValue = postorder[postorder.length - 1];
        TreeNode root = new TreeNode(rootValue);

        // 2、区分左右子树
        for (int i = 0; i < inorder.length; i++) {
            // 找中序根
            if(inorder[i] == rootValue){
                // 划分中序左右子树
                int[] inLeft = Arrays.copyOfRange(inorder, 0, i);
                int[] inRight = Arrays.copyOfRange(inorder, i + 1, inorder.length);

                // 划分后续左右子树
                int[] postLeft = Arrays.copyOfRange(postorder, 0, i);
                int[] postRight = Arrays.copyOfRange(postorder, i, postorder.length - 1);    // 最后一个是根节点

                // 继续递归
                TreeNode<Integer> leftTree = buildTreeByInAndPost(inLeft, postLeft);
                TreeNode<Integer> rightTree = buildTreeByInAndPost(inRight, postRight);

                root.left = leftTree;
                root.right = rightTree;

                break;
            }
        }
        return root;
    }

}
