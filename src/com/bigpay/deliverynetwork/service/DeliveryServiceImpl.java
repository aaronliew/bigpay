package com.bigpay.deliverynetwork.service;

import com.bigpay.deliverynetwork.constant.EventType;
import com.bigpay.deliverynetwork.model.*;
import com.bigpay.deliverynetwork.model.Package;

import java.util.*;
/*
Assumption:
1. The train can only carry multiples packages only if packages all located at a same node.
2. The train can carry the packages only if the total weight of the packages is less than or equal to the capacity of the train.
3. Djiakstra's algorithm is used to find the shortest path between two nodes.
4. The train will stop moving if there are no packages to deliver and no packages to pick up.
5. The train will pick up the heaviest package first if there are multiple packages at the same node.
 */
public class DeliveryServiceImpl implements DeliveryService{
    List<Node> nodes;
    List<Edge> edges;
    List<Train> trains;
    List<Package> packages;

    Map<Package, Boolean> completedPackages;
    List<Move> moves;
    Map<Node, List<Edge>> adjacencyList;

    public DeliveryServiceImpl(List<Node> nodes, List<Edge> edges, List<Train> trains, List<Package> packages) {
        this.nodes = nodes;
        this.edges = edges;
        this.trains = trains;
        this.packages = packages;
        this.moves = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
        this.completedPackages = new HashMap<>();

        for (Node node : nodes) {
            adjacencyList.put(node, new ArrayList<>());
        }

        for (Package pkg : packages) {
            completedPackages.put(pkg, false);
        }

        for (Edge edge : edges) {
            adjacencyList.get(edge.getNode1()).add(edge);
            adjacencyList.get(edge.getNode2()).add(edge);
        }
    }

    @Override
    public void startDelivery() {
        //Priority queue to store the events
        PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.getTime()));
        Map<Node, List<Package>> packageMap = new HashMap<>();

        for (Package pkg : packages) {
            packageMap.putIfAbsent(pkg.getStartingNode(), new ArrayList<>());
            packageMap.get(pkg.getStartingNode()).add(pkg);
        }

        for (Train train : trains) {
            eventQueue.add(new Event(0, train, train.getCurrentNode(), EventType.PICKUP));
        }

        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            switch (event.getType()) {
                case PICKUP -> handlePickup(event, packageMap, eventQueue);
                case DELIVERY -> handleDelivery(event, eventQueue);
                case MOVE -> handleMove(event, eventQueue);
            }
        }
    }

    private void handlePickup(Event event, Map<Node, List<Package>> packageMap, PriorityQueue<Event> eventQueue) {
        Train train = event.getTrain();
        Node currentNode = event.getNode();
        List<Package> packagesAtNode = packageMap.get(currentNode);
        if (packagesAtNode != null) {
            //sort the package by weight in descending order and pick the heaviest package first
            packagesAtNode.sort(Comparator.comparingInt(Package::getWeight).reversed());
            List<String> pickedPackageNames = new ArrayList<>();
            for (Iterator<Package> iterator = packagesAtNode.iterator(); iterator.hasNext(); ) {
                Package pkg = iterator.next();
                if (train.canCarry(pkg)) {
                    train.pickUpPackage(pkg);
                    pickedPackageNames.add(pkg.getName());
                    completedPackages.put(pkg, true);
                    iterator.remove();
                }
            }
            if (!pickedPackageNames.isEmpty()) {
                moves.add(new Move(event.getTime(), train.getName(), currentNode,
                        pickedPackageNames, Collections.emptyList()));
            }
        }

        eventQueue.add(new Event(event.getTime(), train, currentNode, EventType.MOVE));
    }

    private void handleDelivery(Event event, PriorityQueue<Event> eventQueue) {
        Train train = event.getTrain();
        Node currentNode = event.getNode();

        List<String> droppedPackages = new ArrayList<>();
        List<Package> trainPackages = new ArrayList<>(train.getPackages());
        for (Iterator<Package> iterator = trainPackages.iterator(); iterator.hasNext(); ) {
            Package pkg = iterator.next();
            if (pkg.getDestinationNode().equals(currentNode)) {
                droppedPackages.add(pkg.getName());
                train.dropOffPackage(pkg);
                iterator.remove();
            }
        }

        if (!droppedPackages.isEmpty()) {
            //get list of strings from droppedPackages
            moves.add(new Move(event.getTime(), train.getName(), currentNode, Collections.emptyList(), droppedPackages));
        }

        if (!train.getPackages().isEmpty()) {
            eventQueue.add(new Event(event.getTime(), train, currentNode, EventType.MOVE));
        } else {
            eventQueue.add(new Event(event.getTime(), train, currentNode, EventType.PICKUP));
        }
    }

    private void handleMove(Event event, PriorityQueue<Event> eventQueue) {
        Train train = event.getTrain();
        Node currentNode = event.getNode();

        if (!train.getPackages().isEmpty()) {
            Package packageToDeliver = train.getPackages().stream()
                    .min(Comparator.comparingInt(pkg -> findShortestPathTime(currentNode, pkg.getDestinationNode())))
                    .orElse(null);
            if (packageToDeliver != null) {
                Node destinationNode = packageToDeliver.getDestinationNode();
                int journeyTime = findShortestPathTime(currentNode, destinationNode);

                if (journeyTime > 0) {
                    train.setCurrentNode(destinationNode);
                    eventQueue.add(new Event(event.getTime() + journeyTime, train, destinationNode, EventType.DELIVERY));
                }
            }
        } else {
            //Get the heaviest package to pick up at the current node
            Package packageToPickup = packages.stream()
                    .filter(pkg -> !completedPackages.get(pkg))
                    .filter(train::canCarry)
                    .max(Comparator.comparingInt(Package::getWeight))
                    .orElse(null);

            if (packageToPickup != null && train.canCarry(packageToPickup)) {
                Node destinationNode = packageToPickup.getStartingNode();
                int journeyTime = findShortestPathTime(currentNode, destinationNode);

                if (journeyTime > 0) {
                    train.setCurrentNode(destinationNode);
                    eventQueue.add(new Event(event.getTime() + journeyTime, train, destinationNode, EventType.PICKUP));
                }
            }

        }
    }

    public int findShortestPathTime(Node start, Node end) {
        Map<Node, Integer> distances = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.getDistance()));

        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new NodeDistance(start, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.getNode();

            for (Edge edge : adjacencyList.get(currentNode)) {
                Node neighbor = (edge.getNode1().equals(currentNode)) ? edge.getNode2() : edge.getNode1();
                int newDist = distances.get(currentNode) + edge.getJourneyTime();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.add(new NodeDistance(neighbor, newDist));
                }
            }
        }

        return distances.get(end);
    }

    @Override
    public void printTotalDeliveryTime() {
        System.out.println("Total time to complete delivery: " + moves.get(moves.size()-1).getTime() + " minutes");
    }

    @Override
    public void printMoves() {
        for (Move move : moves) {
            System.out.println(move);
        }
    }
}

