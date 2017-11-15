package diy.xiaoming.com.javalib.Tree;

import java.util.Stack;

/**
 * Created by Administrator on 2017-11-15.
 */

public class BinaryTree {

    private Node mRoot;

    public Node getRoot() {
        return mRoot;
    }

    public BinaryTree(String root) {
        mRoot = new Node(root);
    }

    public void createTree(Node root) {
        Node node1 = new Node("B");
        Node node2 = new Node("C");
        Node node3 = new Node("D");
        Node node4 = new Node("E");
        Node node5 = new Node("F");
        Node node6 = new Node("G");
        Node node7 = new Node("H");
        Node node8 = new Node("I");
        Node node9 = new Node("J");
        Node node10 = new Node("K");
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        node3.right = node10;
        node4.left = node7;
        node5.right = node8;
        node8.left = node9;
    }

    /**
     * ǰ������ݹ�д��
     * �� �� ��
     *
     * @param node
     */
    public void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * ǰ������ǵݹ�ʵ��
     *
     * @param node
     */
    public void preOrder2(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.pop();
            System.out.println(node.data);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    /**
     * ��������ݹ�ʵ��
     *
     * @param node
     */
    public void middleOrder(Node node) {
        if (node == null) {
            return;
        }
        middleOrder(node.left);
        System.out.println(node.data);
        middleOrder(node.right);
    }

    /**
     * ��������ǵݹ�ʵ��
     *
     * @param node
     */
    public void middleOrder2(Node node) {
        Stack<Node> stack = new Stack<>();
        if (node == null) {
            return;
        }
        while (node != null) {
            while (node != null) {
                if (node.right != null) {
                    stack.push(node.right);
                }
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            while (!stack.isEmpty() && node.right == null) {
                System.out.println(node.data);
                node = stack.pop();
            }
            System.out.println(node.data);
            if (!stack.isEmpty()) {
                node = stack.pop();
            } else {
                node = null;
            }
        }
    }

    /**
     * ��������ݹ�ʵ��
     *
     * @param node
     */
    public void nextOrder(Node node) {
        if (node == null) {
            return;
        }
        nextOrder(node.left);
        nextOrder(node.right);
        System.out.println(node.data);
    }

    public void nextOrder2(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node p = node;
        while (node != null) {
            while (node.left != null) {
                stack.push(node);
                node = node.left;
            }
            while (node != null && (node.right == null || node.right == p)) {
                System.out.println(node.data);
                p = node;
                if (stack.isEmpty()) {
                    return;
                }
                node = stack.pop();
            }
            stack.push(node);
            node = node.right;
        }
    }

    private class Node {
        private Node left;
        private Node right;
        private String data;

        public Node(String data) {
            this.left = null;
            this.right = null;
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree("A");
        binaryTree.createTree(binaryTree.getRoot());
        System.out.println("-------------------------------");
        System.out.println("ǰ������ݹ�д��");
        binaryTree.preOrder(binaryTree.mRoot);
        System.out.println("-------------------------------");
        System.out.println("ǰ������ǵݹ�д��");
        binaryTree.preOrder2(binaryTree.mRoot);
        System.out.println("-------------------------------");
        System.out.println("��������ݹ�д��");
        binaryTree.middleOrder(binaryTree.mRoot);
        System.out.println("-------------------------------");
        System.out.println("��������ǵݹ�д��");
        binaryTree.middleOrder2(binaryTree.mRoot);
        System.out.println("-------------------------------");
        System.out.println("��������ݹ�д��");
        binaryTree.nextOrder(binaryTree.mRoot);
        System.out.println("-------------------------------");
        System.out.println("��������ǵݹ�д��");
        binaryTree.nextOrder2(binaryTree.mRoot);
        System.out.println("-------------------------------");
    }
}
