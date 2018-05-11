/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sengkhunlim
 */
public class A {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Node[] graph = initialGraph(8);
        printGraph(graph);

        String start, goal;
        start = "A";
        goal = "F";

        System.out.printf("Starting Node is %s\n", start);
        System.out.printf("Ending Node is %s\n", goal);

        Node result = AlgorithmAStar(graph, start, goal);

        if (result != null) {
            System.out.printf("SUCCESS! From %s to %s node the min cost search is %d!\n", start, goal, result.getF());
        } else {
            System.out.printf("FAIL! The goal %s is not found!\n", goal);
        }
    }
    
    public static Node AlgorithmAStar(Node[] graph, String start, String goal) {

        Node n, m, tmp;
        List<Node> OPEN = new ArrayList<>();
        List<Node> CLOSED = new ArrayList<>();

        Node s = getNode(graph, start);

        if (s == null) {
            System.out.println("Cannot find the Starting Node!");
        }

        // Set OPEN = { s }
        OPEN.add(s);

        // if OPEN = {}, Terminate with failure
        while (!OPEN.isEmpty()) {

            // Select the minimum f state, n, from OPEN and save n in CLOSED
            n = getMinimunFNode(OPEN);
            OPEN.remove(n);
            CLOSED.add(n);
            
            System.out.printf("current: %s %d %d\n", n.getName(), n.getCost(), n.getF());

            // if n ε G, terminate with success
            if (n.getName().equals(goal)) {
                return n;
            }

            // Generate the successors of n using O
            m = getNode(graph, n.getName());
            int h = m.getEstimateCost(); // estimate cost of the node
            while (m != null) {

                // Skip the first node in the array graph
                if (m.getWeight() == -1) {
                    m = m.getChild();
                    continue;
                }                           
                
                // if m ∉ [OPEN ⋃ CLOSED]
                if (checkElement(OPEN, m.getName()) == null && checkElement(CLOSED, m.getName()) == null) {
                    
                    // Set c(m) = c(n) + c(n, m)
                    m.setCost(n.getCost() + m.getWeight());
                    
                    // Set f(m) = c(m)+h(m)
                    m.setF(m.getCost() + h);
                    
                    // insert m in OPEN
                    OPEN.add(m);
                    
                    System.out.printf("successor: %s %d %d\n", m.getName(), n.getCost(), n.getF());
                                
                } else { // if m ε [OPEN ⋃ CLOSED]

                    // C(n) + C(n, m)
                    int c = n.getCost() + m.getWeight();
                    int f = c + h;
                    
                    tmp = checkElement(CLOSED, m.getName());                                        
                    if (tmp != null) {
                        
                        // Set c(m) = min{ c(m), c(n)+c(n, m)}
                        if (f < tmp.getCost()) {
                            
                            // if f(m) has decreased, move m to OPEN
                            CLOSED.remove(tmp);
                            tmp.setCost(c);
                            tmp.setF(f);
                            OPEN.add(tmp);
                            
                            System.out.printf("successor: %s %d %d\n", tmp.getName(), tmp.getCost(), tmp.getF());
                        }
                    }
                                        
                    tmp = checkElement(OPEN, m.getName());
                    if (tmp != null) {
                        
                        // Set f(m) = min{ C(m), C(n)+C(n, m) }
                        if (f < tmp.getCost()) {
                            
                            // if f(m) has decreased and m є OPEN, replace the current noe in OPEN
                            OPEN.remove(tmp);
                            tmp.setCost(c);
                            tmp.setF(f);
                            OPEN.add(tmp);
                            
                            System.out.printf("successor: %s %d %d\n", tmp.getName(), tmp.getCost(), tmp.getF());
                        }
                    }
                    
                }

                // Next Successor
                m = m.getChild();
            }
        }
        return null;
    }

    public static Node checkElement(List<Node> arr, String name) {

        for (Node node : arr) {

            if (node.getName().equals(name)) {
                return node;
            }

        }
        return null;
    }

    public static Node getMinimunFNode(List<Node> graph) {

        if (graph.size() <= 1) {
            return graph.get(0);
        }

        Node currentNode = graph.get(0);

        for (Node n : graph) {

            if (n.getF() < currentNode.getEstimateCost()) {
                currentNode = n;
            }

        }

        return currentNode;
    }

    public static Node getNode(Node[] graph, String name) {

        for (int i = 0; i < graph.length; i++) {
            if (graph[i].getName().equals(name)) {
                return graph[i];
            }
        }
        return null;
    }

    public static Node[] initialGraph(int size) {

        String name, used;
        Node tmp;
        Node[] graph = new Node[size];

        for (int i = 0; i < size; i++) {

            int length = new Random().nextInt(size - 4) + 1;
            name = Character.toString((char) (i + 65));
            used = name;

            // The root node in each array
            graph[i] = new Node(name, -1, new Random().nextInt(4) + 1);

            for (int j = 0; j < length; j++) {

                tmp = getLastChild(graph[i]);

                for (int k = 0; k < length; k++) {
                    name = Character.toString((char) (new Random().nextInt(size) + 65)); // generate random letter
                    if (!used.contains(name)) {
                        break;
                    }
                }
                
                if (used.contains(name)) {
                    break;
                }
                used += name;
                tmp.setChild(name, new Random().nextInt(size), -1);
            }
        }
        return graph;
    }

    public static Node getLastChild(Node n) {

        Node current = n;

        while (true) {

            if (current.getChild() == null) {
                return current;
            }

            current = current.getChild();
        }
    }

    public static void printGraph(Node[] g) {

        Node current;

        for (int i = 0; i < g.length; i++) {

            current = g[i];

            while (true) {

                if (current != null) {
                    System.out.printf("%s %d %d -> ", current.getName(), current.getWeight(), current.getEstimateCost());
                    current = current.getChild();
                } else {
                    System.out.println("NULL");
                    break;
                }
            }
        }

    }
    
}
