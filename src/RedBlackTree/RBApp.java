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
public class RBApp {
    
    public static void main(String[] args) {
     
        TreeData redblacktree = new RedBlackTree();
        
        redblacktree.insert(10);
        redblacktree.insert(15);
        redblacktree.insert(33);
        redblacktree.insert(35);
        redblacktree.insert(13);
        redblacktree.insert(40);
        redblacktree.insert(20);

        System.out.println();
        // Menampilkan pohon sebelum penghapusan
        System.out.println("Red-Black Tree before deletion:");
        redblacktree.traverse();

        System.out.println();
        // Mencari dan menampilkan hasil pencarian beberapa data
        int[] dataToFind = {10, 35, 20};
        for (int data : dataToFind) {
            redblacktree.find(data);
        }

        System.out.println();
        // Menghapus beberapa data dari pohon
        int[] dataToDelete = {15, 20 };
        for (int data : dataToDelete) {
            System.out.println("Deleting data: " + data);
            redblacktree.delete(data);
        }

        System.out.println();
        // Menampilkan pohon setelah penghapusan
        System.out.println("Red-Black Tree after deletion:");
        redblacktree.traverse();
    }
}