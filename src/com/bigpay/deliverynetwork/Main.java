package com.bigpay.deliverynetwork;

import com.bigpay.deliverynetwork.model.Edge;
import com.bigpay.deliverynetwork.model.Node;
import com.bigpay.deliverynetwork.model.Package;
import com.bigpay.deliverynetwork.model.Train;
import com.bigpay.deliverynetwork.service.DeliveryService;
import com.bigpay.deliverynetwork.service.DeliveryServiceImpl;

import java.util.Arrays;
import java.util.List;

public class Main {

    static DeliveryService deliveryService;

    public static void main(String[] args) {
        testSimpleDeliveryNetwork();
    }

    private static void initiateDelivery(List<Node> nodes, List<Edge> edges, List<Train> trains, List<Package> packages){
        deliveryService = new DeliveryServiceImpl(nodes, edges, trains, packages);
        deliveryService.startDelivery();
        deliveryService.printMoves();
        deliveryService.printTotalDeliveryTime();
    }


    //test delivery network
    private static void testSimpleDeliveryNetwork(){
        /*
            Q1(K1): B -> A -> B -> C total time: 30 + 30 + 10 = 70
        * */
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");

        List<Node> nodes = Arrays.asList(nodeA, nodeB, nodeC);
        List<Edge> edges = Arrays.asList(
                new Edge(nodeA, nodeB, 30),
                new Edge(nodeB, nodeC, 10)
        );

        List<Train> trains = Arrays.asList(
                new Train("Q1", 6, nodeB)
        );

        List<Package> packages = Arrays.asList(
                new Package("K1", 5, nodeA, nodeC)
        );

        initiateDelivery(nodes, edges, trains, packages);
    }

    private static void testDeliveryNetwork2(){
        /*
            T1(K1): A -> B -> D total time: 25
            T2(K2): B-> B -> E total time: 25
            T1(K3): D -> C -> B: 10 + 25 = 35 + 25 = 60
        * */
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");

        List<Node> nodes = Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE);
        List<Edge> edges = Arrays.asList(
                new Edge(nodeA, nodeB, 10),
                new Edge(nodeA, nodeC, 15),
                new Edge(nodeB, nodeD, 20),
                new Edge(nodeC, nodeD, 10),
                new Edge(nodeD, nodeE, 5)
        );

        List<Train> trains = Arrays.asList(
                new Train("Q1", 100, nodeA),
                new Train("Q2", 150, nodeB)
        );

        List<Package> packages = Arrays.asList(
                new Package("K1", 50, nodeA, nodeD),
                new Package("K2", 60, nodeB, nodeE),
                new Package("K3", 30, nodeC, nodeB)
        );

        initiateDelivery(nodes, edges, trains, packages);

    }

    public static void testDeliveryNetworkWithTwoTrainsAnd4Packages() {
        /*
            A --- B --- D
                  C --- E
                        |
                  C --- D
            T1(K1): A -> B -> D total time: 25
            T2(K2): B-> B -> E total time: 25
            T1(K3): D -> C -> B: 10 + 25 = 35 + 25 = 60
        * */
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");

        List<Node> nodes = Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE);
        List<Edge> edges = Arrays.asList(
                new Edge(nodeA, nodeB, 60),
                new Edge(nodeA, nodeC, 30),
                new Edge(nodeB, nodeD, 30),
                new Edge(nodeC, nodeE, 10),
                new Edge(nodeC, nodeD, 80),
                new Edge(nodeE, nodeD, 10)
        );

        List<Train> trains = Arrays.asList(
                new Train("Train1", 100, nodeA),
                new Train("Train2", 150, nodeB)
        );

        List<Package> packages = Arrays.asList(
                new Package("Package1", 50, nodeA, nodeD),
                new Package("Package2", 70, nodeB, nodeE),
                new Package("Package3", 40, nodeC, nodeB),
                new Package("Package4", 90, nodeC, nodeE)
        );

        initiateDelivery(nodes, edges, trains, packages);
    }
}
