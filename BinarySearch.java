/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

import java.util.ArrayList;

/**
 *
 * @author luism
 */
public class BinarySearch implements AVLTreeInterface {

    TreeNodeDataType root;

    public BinarySearch(Object root) {
        this.root = new TreeNodeDataType(root, null, null, null);
        
        //this.root = AVLInsert((int)root, null);
        
    }

    public TreeNodeDataType find(int mySearch, TreeNodeDataType r) {

        int rootKey;

        rootKey = (int) r.getKey();

        if (rootKey == mySearch) {
            return r;
        } else if (rootKey > mySearch) {

            if (r.getLeftChild() != null) {
                return find(mySearch, (TreeNodeDataType) r.getLeftChild());
            }
            return r;
        } else if (rootKey < mySearch) {

            if (r.getRightChild() != null) {
                return find(mySearch, (TreeNodeDataType) r.getRightChild());
            }
            return r;
        }
        System.out.println("\nÁrvore vazia!!!\n");
        return null;
    }

    public TreeNodeDataType nextElement(TreeNodeDataType node) {

        if (node != null) {
            
            if(node.getRightChild() == null){
              //Case I: right child is null
                return rightAncestor(node);  
                
                //Case II: left child
            }else{
                return leftDescendant(node.getRightChild());
            } 
        } else {
            return null;
        }
    }

    public TreeNodeDataType rightAncestor(TreeNodeDataType node) {

        if (node != null && node.getParent() != null) {
            
            if ((int) node.getKey() < (int) node.getParent().getKey()) {
                
                return (TreeNodeDataType) node.getParent();
                
            } else {
                
                return rightAncestor(node.getParent());
                
            }
            
        } else {
            
            return null;//null input
            
        }

    }

    //Case II: No right child
    public TreeNodeDataType leftDescendant(TreeNodeDataType node) {

        if (node != null) {

            
            if (node.getLeftChild() == null) {
                return node;
            } else {
                return leftDescendant(node.getLeftChild());
            }

        } else {
            return null;
        }
    }

    public TreeNodeDataType insert(int k, TreeNodeDataType root) {

        TreeNodeDataType p = find(k, root);

        if (p != null) {

            TreeNodeDataType node = new TreeNodeDataType(k, p, null, null);

            return node;
        }

        return null;
    }

    public TreeNodeDataType AVLInsert(int k, TreeNodeDataType root) {

        root = insert(k, root);//novo node

        TreeNodeDataType n = find(k, root);// estimated place for new node

        rebalance(n);

        return n;

    }

    public TreeNodeDataType delete(TreeNodeDataType node) {

        if (node.getRightChild() == null) {
            remove(node);

            if (node.getLeftChild() != null) {
                promote(node.getLeftChild());
            }

        } else {
            TreeNodeDataType x = nextElement(null);

            if (x != null) {
                //x.setLeftChild(null);
                replace(node, x);
                promote(x.getRightChild());
            }

        }
        return node;
    }

    public void AVLDelete(int key, TreeNodeDataType n) {

        n = delete(n);
        
        TreeNodeDataType m = n.getParent();

        rebalance(m);

    }

    public ArrayList<TreeNodeDataType> rangeSearch(int start, int end, TreeNodeDataType root) {

        ArrayList<TreeNodeDataType> l = new ArrayList<>();
        
        TreeNodeDataType n = find(start, root);
        
        while((int)n.getKey() <= end){
            if((int)n.getKey() >= end){
                l.add(n);
            }
            n = nextElement(n);
        }
        
        return l;
    }

    private void remove(TreeNodeDataType node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void promote(TreeNodeDataType leftChild) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void replace(TreeNodeDataType node, TreeNodeDataType x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void nearestNeighbor(int i, TreeNodeDataType root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAVLProperty(TreeNodeDataType node) {

        int a = 1, b = 1;

        if (node != null) {
            if (node.getLeftChild() != null) {
                a = (int) node.getLeftChild().getHeight();
            }
            if (node.getRightChild() != null) {
                b = (int) node.getRightChild().getHeight();
            }
        }

        return Math.abs(a - b) <= 1;
    }

    public void teorema(TreeNodeDataType node) {

        if (isAVLProperty(node)) {

            node.setHeight((int) fibonacci((int) node.getHeight()));

        }

    }

    public int fibonacci(int n) {

        switch (n) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return fibonacci(n - 1) + fibonacci(n - 2);
        }

    }

    public void rebalance(TreeNodeDataType n) {

        if (n != null) {

            TreeNodeDataType p = n.getParent();
            
            int leftHeight = 1;
            
            if(n.getLeftChild() != null){
                leftHeight = (int) n.getLeftChild().getHeight();
            }
            
            int rightHeight = 1;
            
            if(n.getRightChild() != null){
                rightHeight = (int) n.getRightChild().getHeight();
            }

            if ( leftHeight > ( rightHeight + 1)) {
                rebalanceRight(n);
            }
            if ( rightHeight > ( leftHeight + 1)) {
                rebalanceLeft(n);
            }
            adjustHeight(n);

            if (p != null) {
                rebalance(p);
            }

        }

    }

    private void rebalanceRight(TreeNodeDataType n) {

        TreeNodeDataType m = n.getLeftChild();

        if ((int) m.getRightChild().getHeight() > (int) m.getLeftChild().getHeight()) {
            rotateLeft(m);
        }
        rotateRight(n);

        //AdjustHeight on affected nodes?
        adjustHeight(n);
    }

    private void rebalanceLeft(TreeNodeDataType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void adjustHeight(TreeNodeDataType n) {

        int left = 1;
        
        if(n.getLeftChild() != null){
            left = (int) n.getLeftChild().getHeight();
        }
        
        int right = 1;
        
        if(n.getRightChild() != null){
            right = (int) n.getRightChild().getHeight();
        }

        
        
        n.setHeight(1 + (int) Math.max(left, right));

    }

    private void rotateLeft(TreeNodeDataType m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void rotateRight(TreeNodeDataType n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void inOrderTraversal(TreeNodeDataType tree) {

        if (tree == null) {
            return;
        }
        inOrderTraversal(tree.getLeftChild());
        print(tree.getKey());
        inOrderTraversal(tree.getRightChild());

    }

    private void print(Object key) {
        System.out.print(" " + (int) key);
    }

}
