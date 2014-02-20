/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//package vancho;
import java.lang.Math;
/**
 *
 * @author sdjshanky
 */


class Node12{
    int data=-1;
    Node12 right=null;
    Node12 left=null;
}

class Tree12{
    Node12 root=null;   /* root
                      /   \
                    left  right
                    / \*/

    void AddNode(int d){
        
        if(root==null){
            root=new Node12();
            root.data=d;
            //            root.left=new Node();
            System.out.println("Root added");
        }
        else{

            getAddNode(root,d);
            }
         
           
    }

    void getAddNode(Node12 root,int d){
        int level=0;
        double counter=0;
        int boolif = 0, boolelse =0;
        //boolean check=true;
            //System.out.println("gett: "+root.data);
            if(root.left==null||root.right==null){
                //System.out.println(root.data);
                if(root.left==null){
                    root.left=new Node12();
                    root.left.data=d;
                    System.out.println("Node added-L:"+root.data+"--->"+d);
                }else{
                    root.right=new Node12();
                    root.right.data=d;
                    System.out.println("Node added-R:"+root.data+"-->"+d);
                }
                //return root



            }else{

                if(root.left!=null){//||root.left.right==null)
                  getAddNode(root.left,d);counter++;
                  boolif = 1;
                }
                else//if(root.right.left != null || root.right.right != null)
                {
                 getAddNode(root.right,d);
                 counter++;
                 boolelse = 1;
                }

                if(boolif==1 && boolelse==1){
                  level = (int) Math.sqrt(counter);
                }

            }
        //return root;
    }
    void displayTree(Node root){
        System.out.println("Root: "+root.data);
        //if(root.left.data!=-1)
        System.out.println("left: "+root.left.data);
        //if(root.right.data!=-1)
        System.out.println("Right: "+root.right.data);

        if(root.left!=null)
            displayTree(root.left);
        if(root.right!=null)
            displayTree(root.right);
    }
}
public class TeachTree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Tree12 t=new Tree12();
        for(int i=0;i<8;i++){
            t.AddNode(i);
        }
       // t.displayTree(t.root);
    }

}
