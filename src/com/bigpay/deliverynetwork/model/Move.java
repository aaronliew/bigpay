package com.bigpay.deliverynetwork.model;

import java.util.List;

public class Move {
    int time;
    String trainName;
    Node startNode;
    List<String> pickedPackages;
    List<String> droppedPackages;

    public Move(int time, String trainName, Node startNode, List<String> pickedPackages, List<String> droppedPackages) {
        this.time = time;
        this.trainName = trainName;
        this.startNode = startNode;
        this.pickedPackages = pickedPackages;
        this.droppedPackages = droppedPackages;
    }

    @Override
    public String toString() {
        return "Time: " + time + ", Train: " + trainName + ", Start: " + startNode.name +
                ", Picked: " + pickedPackages + ", Dropped: " + droppedPackages;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public List<String> getPickedPackages() {
        return pickedPackages;
    }

    public void setPickedPackages(List<String> pickedPackages) {
        this.pickedPackages = pickedPackages;
    }

    public List<String> getDroppedPackages() {
        return droppedPackages;
    }

    public void setDroppedPackages(List<String> droppedPackages) {
        this.droppedPackages = droppedPackages;
    }
}
