package diy.xiaoming.com.javalib.Tree;

/**
 * Created by Administrator on 2017-11-15.
 */

public class SearchBinaryTree {
    private Node mRoot;

    public SearchBinaryTree(int data) {
        mRoot = new Node(data);
    }

    /**
     * 添加节点
     *
     * @param data 要添加的节点值
     */
    public void insert(int data) {
        if (mRoot == null) {
            mRoot = new Node(data);
        }
        Node nowNode = mRoot;
        Node newParent = null;
        while (nowNode != null) {
            newParent = nowNode;
            if (nowNode.data > data) {
                nowNode = nowNode.left;
            } else if (nowNode.data < data) {
                nowNode = nowNode.right;
            } else {
                System.out.println("您所输入的元素:" + data + "有重复");
                throw new RuntimeException("this element: " + data + " you input has repeated");
            }
        }
        if (newParent.data > data) {
            newParent.left = new Node(data);
        } else {
            newParent.right = new Node(data);
        }
    }

    /**
     * 中序遍历递归实现
     *
     * @param node
     */
    public void middleOrder(Node node) {
        if (node == null) {
            return;
        }
        middleOrder(node.left);
        System.out.print(node.data + " ");
        middleOrder(node.right);
    }

    public Node findNode(int date) {
        Node node = mRoot;
        while (node != null) {
            if (node.data == date) {
                return node;
            }
            if (node.data > date) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    private class Node {
        Node left;
        Node right;
        Node parent;
        int data;

        public Node(int data) {
            this.left = null;
            this.right = null;
            this.parent = null;
            this.data = data;
        }
    }

    public static void main(String[] args) {
        int[] arrys = {20, 10, 6, 4, 2, 7, 89, 9, 30, 15, 14};
        SearchBinaryTree tree = new SearchBinaryTree(arrys[0]);
        System.out.println("排序前:");
        System.out.print(arrys[0] + " ");
        for (int i = 1; i < arrys.length; i++) {
            System.out.print(arrys[i] + " ");
            tree.insert(arrys[i]);
        }
        System.out.println();
        System.out.println("排序后:");
        tree.middleOrder(tree.mRoot);
        System.out.println();
        Node result = tree.findNode(30);
        System.out.println(result == null ? null : result.data);
    }
}
