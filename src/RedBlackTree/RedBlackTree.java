/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RedBlackTree;

/**
 *
 * @author hp
 */
public class RedBlackTree implements TreeData {

    private Node root;

    @Override
    public void traverse() {

        if (root != null) {
            inOrdeTraversal(root);
        }
    }

    private void inOrdeTraversal(Node node) {

        if (node.getLeftChild() != null) {
            inOrdeTraversal(node.getLeftChild());
        }
        System.out.println(node + " = ");

        if (node.getRightChild() != null) {
            inOrdeTraversal(node.getRightChild());
        }

    }

    private void righRotate(Node node) {
        System.out.println("Rotating to the right on Node " + node);

        Node tempLeftNode = node.getLeftChild();
        node.setLeftChild(tempLeftNode.getRightChild());

        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(root);
        }

        if (tempLeftNode.getRightChild() != null) {
            tempLeftNode.getRightChild().setParent(tempLeftNode);
        }

        tempLeftNode.setParent(node.getParent());
        if (tempLeftNode.getParent() == null) {
            root = tempLeftNode;
        } else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(tempLeftNode);
        } else {
            node.getParent().setRightChild(tempLeftNode);
        }
        tempLeftNode.setRightChild(node);
        node.setParent(tempLeftNode);
    }

    private void leftRotate(Node node) {
        System.out.println("Rotating to the left on Node " + node);

        Node tempRightNode = node.getRightChild();
        node.setRightChild(tempRightNode.getLeftChild());

        if (node.getRightChild() != null) {
            node.getRightChild().setParent(root);
        }

        tempRightNode.setParent(node.getParent());
        if (tempRightNode.getParent() == null) {
            root = tempRightNode;
        } else if (node == node.getParent().getLeftChild()) {
            node.getParent().setLeftChild(tempRightNode);
        } else {
            node.getParent().setRightChild(tempRightNode);
        }
        tempRightNode.setLeftChild(node);
        node.setParent(tempRightNode);
    }

    @Override
    public void insert(int data) {

        Node node = new Node(data);

        root = insertIntoTree(root, node);

        fixViolations(node);
    }

    private Node insertIntoTree(Node root, Node node) {

        if (root == null) {
            return node;
        }

        if (node.getData() < root.getData()) {
            root.setLeftChild(insertIntoTree(root.getLeftChild(), node));
            root.getLeftChild().setParent(root);
        } else if (node.getData() > root.getData()) {
            root.setRightChild(insertIntoTree(root.getRightChild(), node));
            root.getRightChild().setParent(root);
        }
        return root;
    }

    private void fixViolations(Node node) {

        Node parentNode = null;
        Node grandParentNode = null;

        while (node != root && node.getColor() != NodeColor.BLACK && node.getParent().getColor() == NodeColor.RED) {

            parentNode = node.getParent();
            grandParentNode = node.getParent().getParent();

            if (parentNode == grandParentNode.getLeftChild()) {

                Node uncel = grandParentNode.getRightChild();

                if (uncel != null && uncel.getColor() == NodeColor.RED) {
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncel.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {

                    if (node == parentNode.getRightChild()) {
                        leftRotate(parentNode);
                        node = parentNode;
                        parentNode = node.getParent();
                    }

                    righRotate(grandParentNode);
                    NodeColor tempColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    node = parentNode;
                }
            } else {

                Node uncel = grandParentNode.getLeftChild();

                if (uncel != null && uncel.getColor() == NodeColor.RED) {
                    grandParentNode.setColor(NodeColor.RED);
                    parentNode.setColor(NodeColor.BLACK);
                    uncel.setColor(NodeColor.BLACK);
                    node = grandParentNode;
                } else {

                    if (node == parentNode.getLeftChild()) {
                        righRotate(parentNode);
                        node = parentNode;
                        parentNode = node.getParent();
                    }

                    leftRotate(grandParentNode);
                    NodeColor tempColor = parentNode.getColor();
                    parentNode.setColor(grandParentNode.getColor());
                    node = parentNode;
                }
            }
        }
        if (root.getColor() == NodeColor.RED) {
            root.setColor(NodeColor.BLACK);
        }
    }

    @Override
    public boolean find(int data) {
        if (root == null) {
            System.out.println("Tree is empty. Cannot find.");
            return false;
        }

        Node foundNode = findNode(data);
        if (foundNode != null) {
            System.out.println("Data " + data + " found. Color: " + foundNode.getColor());
            return true;
        } else {
            System.out.println("Data " + data + " not found.");
            return false;
        }
    }

    private Node findNode(int data) {
        return findNode(root, data);
    }

    private Node findNode(Node root, int data) {
        if (root == null || root.getData() == data) {
            return root;
        }

        if (data < root.getData()) {
            return findNode(root.getLeftChild(), data);
        } else {
            return findNode(root.getRightChild(), data);
        }
    }

    @Override
    public void delete(int data) {
        if (root == null) {
            System.out.println("Tree is empty. Cannot delete.");
            return;
        }
        Node nodeToDelete = findNode(data);
        if (nodeToDelete == null) {
            System.out.println("Node with data " + data + " not found in the tree.");
            return;
        }

        deleteNode(nodeToDelete);
    }

    private void deleteNode(Node nodeToDelete) {
        Node successor = findSuccessor(nodeToDelete);

        System.out.println("Deleting Node: " + nodeToDelete.getData() + ", Color: " + nodeToDelete.getColor());

        performBSTDelete(nodeToDelete, successor);

        NodeColor originalColor = successor.getColor();

        if (originalColor == NodeColor.BLACK) {
            fixDeleteViolations(successor);
        }
    }

    private Node findSuccessor(Node node) {
        Node successor = node.getRightChild();
        while (successor.getLeftChild() != null) {
            successor = successor.getLeftChild();
        }
        return successor;
    }

    private void performBSTDelete(Node nodeToDelete, Node successor) {
        if (successor == null) {
            transplant(nodeToDelete, nodeToDelete.getRightChild());
        } else {
            if (nodeToDelete.getRightChild() != successor) {
                transplant(successor, successor.getRightChild());
                successor.setRightChild(nodeToDelete.getRightChild());
                if (successor.getRightChild() != null) {
                    successor.getRightChild().setParent(successor); // Corrected this line
                }
            }
            transplant(nodeToDelete, successor);
            successor.setLeftChild(nodeToDelete.getLeftChild());
            if (successor.getLeftChild() != null) {
                successor.getLeftChild().setParent(successor);
            }
            // No need to set parent here; it's already done by the transplant method
            successor.setColor(nodeToDelete.getColor());
        }
    }

    private void transplant(Node u, Node v) {
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeftChild()) {
            u.getParent().setLeftChild(v);
        } else {
            u.getParent().setRightChild(v);
        }

        if (v != null) {
            v.setParent(u.getParent()); // Add this line to set the parent for the new subtree root
        }
    }

    private void fixDeleteViolations(Node node) {
        while (node != root && node.getColor() == NodeColor.BLACK) {
            if (node == node.getParent().getLeftChild()) {
                Node sibling = node.getParent().getRightChild();
                if (sibling != null) {
                    if (sibling.getColor() == NodeColor.RED) {
                        sibling.setColor(NodeColor.BLACK);
                        node.getParent().setColor(NodeColor.RED);
                        leftRotate(node.getParent());
                        sibling = node.getParent().getRightChild();
                    }
                    if (sibling != null) {
                        if ((sibling.getLeftChild() == null || sibling.getLeftChild().getColor() == NodeColor.BLACK)
                                && (sibling.getRightChild() == null || sibling.getRightChild().getColor() == NodeColor.BLACK)) {
                            sibling.setColor(NodeColor.RED);
                            node = node.getParent();
                        } else {
                            if (sibling.getRightChild() == null || sibling.getRightChild().getColor() == NodeColor.BLACK) {
                                if (sibling.getLeftChild() != null) {
                                    sibling.getLeftChild().setColor(NodeColor.BLACK);
                                }
                                sibling.setColor(NodeColor.RED);
                                righRotate(sibling);
                                sibling = node.getParent().getRightChild();
                            }
                            if (sibling != null) {
                                sibling.setColor(node.getParent().getColor());
                            }
                            node.getParent().setColor(NodeColor.BLACK);
                            if (sibling != null && sibling.getRightChild() != null) {
                                sibling.getRightChild().setColor(NodeColor.BLACK);
                            }
                            righRotate(node.getParent());
                            node = root;
                        }
                    }
                }
            } else {
                Node sibling = node.getParent().getLeftChild();
                if (sibling != null) {
                    if (sibling.getColor() == NodeColor.RED) {
                        sibling.setColor(NodeColor.BLACK);
                        node.getParent().setColor(NodeColor.RED);
                        righRotate(node.getParent());
                        sibling = node.getParent().getLeftChild();
                    }
                    if (sibling != null) {
                        if ((sibling.getRightChild() == null || sibling.getRightChild().getColor() == NodeColor.BLACK)
                                && (sibling.getLeftChild() == null || sibling.getLeftChild().getColor() == NodeColor.BLACK)) {
                            sibling.setColor(NodeColor.RED);
                            node = node.getParent();
                        } else {
                            if (sibling.getLeftChild() == null || sibling.getLeftChild().getColor() == NodeColor.BLACK) {
                                if (sibling.getRightChild() != null) {
                                    sibling.getRightChild().setColor(NodeColor.BLACK);
                                }
                                sibling.setColor(NodeColor.RED);
                                leftRotate(sibling);
                                sibling = node.getParent().getLeftChild();
                            }
                            if (sibling != null) {
                                sibling.setColor(node.getParent().getColor());
                            }
                            node.getParent().setColor(NodeColor.BLACK);
                            if (sibling != null && sibling.getLeftChild() != null) {
                                sibling.getLeftChild().setColor(NodeColor.BLACK);
                            }
                            leftRotate(node.getParent());
                            node = root;
                        }
                    }
                }
            }
        }
        node.setColor(NodeColor.BLACK);
    }

}
