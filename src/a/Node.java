/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a;

/**
 *
 * @author sengkhunlim
 */
public class Node {

    private final int weight, estimateCost;
    private int cost, f;
    private final String name;
    private Node child;

    public Node(String name, int weight, int estimateCost) {
        this.name = name;
        this.weight = weight;
        this.estimateCost = estimateCost;
        this.cost = this.f = 0;
        this.child = null;
    }

    public String getName() {
        return this.name;
    }

    public void setChild(String name, int weight, int f) {
        this.child = new Node(name, weight, f);
    }

    public Node getChild() {
        return this.child;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getCost() {
        return this.cost;
    }
    
    public int getEstimateCost() {
        return this.estimateCost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public int getF() {
        return this.f;
    }
    
    public void setF(int f) {
        this.f = f;
    }

}
