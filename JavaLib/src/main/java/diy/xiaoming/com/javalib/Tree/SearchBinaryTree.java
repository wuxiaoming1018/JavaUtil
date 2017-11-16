package diy.xiaoming.com.javalib.Tree;

import java.util.NoSuchElementException;

/**
 * Created by Administrator on 2017-11-15.
 */

public class SearchBinaryTree {
    private Node mRoot;

    public SearchBinaryTree(int data) {
        mRoot = new Node(data);
    }

    /**
     * ��ӽڵ�
     *
     * @param data Ҫ��ӵĽڵ�ֵ
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
                System.out.println("���������Ԫ��:" + data + "���ظ�");
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
     * ��������ݹ�ʵ��
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
     * ���ҽڵ�
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
     * ɾ���ڵ�
     * @param node
     */
    private void delNode(Node node) {
        if (node == null) {
            throw new NoSuchElementException();
        } else {
            Node parent = node.parent;//���ɾ���ڵ��˫�׽ڵ�
            if (node.left == null && node.right == null) {
                //1����ɾ���Ľڵ���Ҷ�ӽڵ��ʱ��
                if (parent == null) {
                    mRoot = null;
                } else {
                    if (parent.right == node) {
                        parent.right = null;
                    } else if (parent.left == node) {
                        parent.left = null;
                    }
                }
                node.parent = null;//����Ҫɾ���Ľڵ�����ĸ��ڵ�Ͽ�
            } else if (node.left != null && node.right == null) {
                //2��ֻ��������
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
                //4������������
                if (node.right.left == null) {
                    //a��ɾ���ڵ�����������������Ƿ�Ϊ�գ����Ϊ�գ����Ҫɾ���ڵ����������Ϊɾ�������������������
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
                    //b����Ϊ�գ���ʹ�õ���������
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
     * ��ȡ��������
     * @param node ���ڵ�
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
        System.out.println("����ǰ:");
        System.out.print(arrys[0] + " ");
        for (int i = 1; i < arrys.length; i++) {
            System.out.print(arrys[i] + " ");
            tree.insert(arrys[i]);
        }
        System.out.println();
        System.out.println("�����:");
        tree.middleOrder(tree.mRoot);
        System.out.println();
        Node result = tree.findNode(7);
        System.out.println(result == null ? null : result.data);
        System.out.println("ɾ����");
        tree.delNode(result);
        tree.middleOrder(tree.mRoot);
    }
}
