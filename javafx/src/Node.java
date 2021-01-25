package javafx.src;

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

    public Node(int v, ArrayList<Node> cList){
        this.value = v;
        this.children = cList;
    }

    public void addChild(int val) {
        this.children.add(new Node(val));
    }

    public void removeChild(int val) {
        this.children.remove(new Node(val));
    }

    public void addChild(int val, ArrayList<Node> list) {
        this.children.add(new Node(val, list));
    }

    public void removeChild(int val, ArrayList<Node> list) {
        this.children.remove(new Node(val, list));
    }

    public boolean equals(Node n) {
        if (this.value==n.value && this.children.equals(n.children)) {
            return true;
        }
        return false;
    }
}