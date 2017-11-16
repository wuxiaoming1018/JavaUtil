package diy.xiaoming.com.javalib.Tree;

import java.util.NoSuchElementException;

/**
 * 二叉排序树
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
     * @param data 需要添加节点的值
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
                System.out.println("您输入的元素:" + data + "有重复");
                throw new RuntimeException("this element: " + data + " you input has repeated");
            }
        }
        Node curNode = new Node(data);
        if (newParent.data > data) {
            newParent.left = curNode;
        } else {
            newParent.right = curNode;
        }
        curNode.parent = newParent;
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

    /**
     * 查找节点
     *
     * @param date
     * @return
     */
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

    /**
     * 删除节点
     * @param node
     */
    private void delNode(Node node) {
        if (node == null) {
            throw new NoSuchElementException();
        } else {
            Node parent = node.parent;//获得删除节点的双亲节点
            if (node.left == null && node.right == null) {
                //1、删除的节点是叶子结点
                if (parent == null) {
                    mRoot = null;
                } else {
                    if (parent.right == node) {
                        parent.right = null;
                    } else if (parent.left == node) {
                        parent.left = null;
                    }
                }
                node.parent = null;//把需要删除的节点和它的双亲节点断开
            } else if (node.left != null && node.right == null) {
                //2、只有左子树
                if (parent == null) {
                    node.left.parent = null;
                    mRoot = node.left;
                } else {
                    if (parent.left == node) {
                        parent.left = node.left;
                    } else if (parent.right == node) {
                        parent.right = node.left;
                    }
                }
                node.parent = null;
            } else if (node.left == null && node.right != null) {
                //3、只有右子树
                if (parent == null) {
                    node.right.parent = null;
                    mRoot = node.right;
                } else {
                    if (parent.left == node) {
                        parent.left = node.right;
                    } else if (parent.right == node) {
                        parent.right = node.right;
                    }
                }
                node.parent = null;
            } else {
                //4有左右子树
                if (node.right.left == null) {
                    //a、删除节点的右子树的左子树是否为空，如果为空，则把要删除节点的左子树设为删除点的右子树的左子树
                    if (parent == null) {
                        mRoot = node.right;
                    }else{
                        if (parent.left==node) {
                            parent.left = node.right;
                        }else{
                            parent.right = node.right;
                        }
                    }
                    node.parent = null;
                }else{
                    //b、不为空，则使用到最左子树
                    Node leftNode = getMinLeftNode(node.right);
                    leftNode.left = node.left;
                    leftNode.parent.left = leftNode.right;
                    leftNode.right = node.right;
                    if (parent == null) {
                        mRoot=leftNode;
                    }else{
                        parent.right = leftNode;
                    }
                }
            }
        }

    }

    /**
     * 获取最右子树
     * @param node 根节点
     * @return
     */
    private Node getMinLeftNode(Node node) {
        Node curNode = node;
        if (node == null) {
            return null;
        }else{
            while (curNode.left != null) {
                curNode = curNode.left;
            }
        }
        return curNode;
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
        Node result = tree.findNode(7);
        System.out.println(result == null ? null : result.data);
        System.out.println("删除后：");
        tree.delNode(result);
        tree.middleOrder(tree.mRoot);
    }
}
