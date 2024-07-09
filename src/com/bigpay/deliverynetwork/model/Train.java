package com.bigpay.deliverynetwork.model;

import java.util.ArrayList;
import java.util.List;

public class Train {
    String name;
    int capacity;
    Node currentNode;
    List<Package> packages;

    public Train(String name, int capacity, Node currentNode) {
        this.name = name;
        this.capacity = capacity;
        this.currentNode = currentNode;
        this.packages = new ArrayList<>();
    }

    public boolean canCarry(Package pkg) {
        int currentWeight = packages.stream().mapToInt(p -> p.weight).sum();
        return currentWeight + pkg.weight <= capacity;
    }

    public void pickUpPackage(Package pkg) {
        packages.add(pkg);
    }

    public void dropOffPackage(Package pkg) {
        packages.remove(pkg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }
}
