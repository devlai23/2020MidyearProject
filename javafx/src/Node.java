package javafx.src;

import java.util.*;

public class Node implements Comparable{ // maybe need siblings
    double value;
    int maxmin = 0; // 1 means minimum, 2 means maximum
    ArrayList<Node> children;   
    int i;
    int j;
    int direction;

    public Node(){
        this.children = new ArrayList<>();
    }

    public Node(double v){
        this.value = v;
        this.children = new ArrayList<>();
    }

    public Node (double v, int i, int j, int direction){
        this.value = v;
        this.children = new ArrayList<>();
        this.i = i;
        this.j = j;
        this.direction = direction;
    }
    /**
     * Possibly add an additional constructor that takes an arraylist, up to other group members
     * ..and if we think it'll be helpful for the rest of the project (the bot)
     */

    public void addChild(double val, int i, int j, int direction) {
        this.children.add(new Node(val, i, j, direction));
    }

    public void removeChild(double val) {
        // this should work IF AND ONLY IF we implement a "equals" methods for Nodes, because this remove method being called uses it
        this.children.remove(new Node(val));
    }

    public String toString(){
        return "[" + i + " " + j + " " + direction + "] Value:" + String.valueOf(value);
    }

    public int compareTo(Object n){
        int nvalue = (int)(Math.round(((Node)n).value * 100));
        return (int)(Math.round(this.value*100)) - nvalue;
    }

    // as we make more constructors we'll need more addChild, removeChild methods
    // these will make the actual "scoring" method for the bot much easier 
}   