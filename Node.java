import java.util.*;

public class Node{ // maybe need siblings
    int value;
    int maxmin = 0; // 1 means minimum, 2 means maximum
    ArrayList<Node> children;

    public Node(){
        this.children = new ArrayList<>();
    }

    public Node(int v){
        this.value = v;
        this.children = new ArrayList<>();
    }
    /**
     * Possibly add an additional constructor that takes an arraylist, up to other group members
     * ..and if we think it'll be helpful for the rest of the project (the bot)
     */

    public void addChild(int val) {
        this.children.add(new Node(val));
    }

    public void removeChild(int val) {
        // this should work IF AND ONLY IF we implement a "equals" methods for Nodes, because this remove method being called uses it
        this.children.remove(new Node(val));
    }

    // as we make more constructors we'll need more addChild, removeChild methods
    // these will make the actual "scoring" method for the bot much easier
}