public class Tree<E>{
    public Node root;

    public Tree(Node root){
        this.root = root;
    }

    public void printTree(){
        System.out.println("Root: " + root);
        System.out.print("Layer 1: ");
        for (int i = 0; i < root.children.size(); i++){
            System.out.print(root.children.get(i) + ", ");
        }
        System.out.println();
    }
}