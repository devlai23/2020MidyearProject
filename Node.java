import java.util.*;

public class Node{ // maybe need siblings
    int value;
    int maxmin = 0; // 1 means minimum, 2 means maximum
    ArrayList<Node> children;

    public Node(){
        this.children = new ArrayList<>();
    }
}