package exercise;

import java.util.*;

public class BinaryTree
{
    private String name;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(String name, BinaryTree left, BinaryTree right) {
        if(name == null) throw new NullPointerException("Node name must not be null");
        this.name = name;
        this.left = left;
        this.right = right;
    }

    // For unit test only
    void setLeft(BinaryTree left) {
        this.left = left;
    }

    // For unit test only
    void setRight(BinaryTree right) {
        this.right = right;
    }

    public static boolean EQ(BinaryTree tree1, BinaryTree tree2) {
        if(tree1 == null && tree2 == null ) return true;
        if(tree1 == null || tree2 == null ) return false;
        if(!tree1.getName().equals(tree2.getName())) return false;
        return EQ(tree1.getLeft(), tree2.getLeft()) && EQ(tree1.getRight(), tree2.getRight());
    }



    public String getName() {
        return name;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public BinaryTree getRight() {
        return right;
    }

    /**
     *
     * @param tree input tree to serialize
     * @param s stack to hold current thread objects, to validate there are no cycle nodes
     * @return a string representation of BinaryTree
     */
    private static String serializeTreeInternal(BinaryTree tree, Stack<BinaryTree> s){
        if (tree == null || s.contains(tree))
            return "";

        s.add(tree);

        if (tree.left == null && tree.right == null)
            return "{" + tree.getName() + "}";

    	return "{" + tree.getName() + "," + serializeTreeInternal(tree.left, s) + "," + serializeTreeInternal(tree.right, s) + "}";
    }

    /**
     *
     * @return a string representation of BinaryTree
     */
    public String serializeTree() {
        return serializeTreeInternal(this, new Stack<BinaryTree>());
    }


    /**
     * One path on the input string
     * @param str byte array holding the string's bytes
     * @param position current position in the given byte array
     * @return a binaryTree
     */
    private static final int COMA = 44;
    private static final int OPEN = 123;
    private static final int CLOSE = 125;
    private static BinaryTree deserializeTreeInternal(byte[] str, int position){
        if (str == null || position == str.length  -1)
            return null;


//        for (int i= 0;i< str.length;i++){
//            System.out.print(i +" - " + (char) str[i]);
//            System.out.println(" - " + str[i]);
//        }



        int opener = findNextChar(str, position, OPEN);
        int coma = findNextChar(str, position, COMA);
        int closer = findNextChar(str, position, CLOSE);

        BinaryTree root = builder(str, position+1, Math.min(opener, closer));;
        if(opener - coma == 1)
            root.left = deserializeTreeInternal(str, opener);
        if(closer - coma == 1)
            root.right = deserializeTreeInternal(str, closer);

		return root;
    }

    private static int findNextChar(byte[] str, int position, int charValue) {
        int index = position + 1;
        while (index < str.length && str[index] != charValue) {
            index++;
        }

        return index;
    }

    private static BinaryTree builder(byte[] arr, int from , int to) {
        if (arr == null)
            return null;

        String name = new String(Arrays.copyOfRange(arr, from , to));
        name = name.replace("{","").replace("}", "").replace(",", "");
        return new BinaryTree(name, null, null);
      //  root.left = helper(arr, );
      //  root.right = helper();

       // return root;
    }


    /**
     *
     * @param str byte array holding the string's bytes
     * @return a binaryTree
     */
    public static BinaryTree deserializeTree(String str) {
    	return deserializeTreeInternal(str.getBytes(), 0);
    }
}
;