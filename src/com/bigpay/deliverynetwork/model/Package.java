package com.bigpay.deliverynetwork.model;

public class Package {
    String name;
    int weight;
    Node startingNode;
    Node destinationNode;

    public Package(String name, int weight, Node startingNode, Node destinationNode) {
        this.name = name;
        this.weight = weight;
        this.startingNode = startingNode;
        this.destinationNode = destinationNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }
}
