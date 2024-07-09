package com.bigpay.deliverynetwork.model;

public class Edge {
    Node node1;
    Node node2;
    int journeyTime; // in seconds

    public Edge(Node node1, Node node2, int journeyTime) {
        this.node1 = node1;
        this.node2 = node2;
        this.journeyTime = journeyTime;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public int getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(int journeyTime) {
        this.journeyTime = journeyTime;
    }
}
