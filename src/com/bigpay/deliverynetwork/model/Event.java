package com.bigpay.deliverynetwork.model;

import com.bigpay.deliverynetwork.constant.EventType;

public class Event {
    int time;
    Train train;
    Node node;
    EventType type;

    public Event(int time, Train train, Node node, EventType type) {
        this.time = time;
        this.train = train;
        this.node = node;
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
