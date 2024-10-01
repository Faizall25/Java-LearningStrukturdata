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
public interface TreeData {
    public void traverse();
    public void insert(int data);
    public boolean find(int data);
    public void delete(int data);
}
