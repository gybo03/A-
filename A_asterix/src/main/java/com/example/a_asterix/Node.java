package com.example.a_asterix;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Node {

    public static int max_num_of_connections = 5;
    public static int min_num_of_connections = 2;
    public static int radius = 5;
    public static Color color = Color.rgb(0, 255, 0);
    public static Color color_connection;
    public static int strokeWidth = radius/3;
    private static int id;
    private String name;
    private int coords[] = new int[2];
    private ArrayList<Node> connections;
    private int num_of_connections;

    public Node(int x,int y) {
        name = "n" + id++;
        setRandomCoord(x,y);
        connections = new ArrayList<>();
        num_of_connections = 0;
    }

    private void setRandomCoord(int x,int y) {
        coords[0] = (int) (Math.random() * (x - 2 * radius)) + radius;
        coords[1] = (int) (Math.random() * (y - 2 * radius)) + radius;
    }

    public int distanceFrom2Nodes(Node n) {
        return (int) Math.sqrt((coords[0] - n.getCoords()[0]) * (coords[0] - n.getCoords()[0]) + (coords[1] - n.getCoords()[1]) * (coords[1] - n.getCoords()[1]));
    }

    public boolean addNode(Node n) {
        if (num_of_connections < max_num_of_connections && n.getNum_of_connections() < max_num_of_connections && !n.checkIfConnected(this)) {
            connections.add(n);
            n.getConnections().add(this);
            num_of_connections++;
            n.incerementNum_of_connections();
            return true;
        }
        return false;
    }

    public int getX() {
        return coords[0];
    }

    public int getY() {
        return coords[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean checkIfConnected(Node node) {
        return connections.contains(node);
    }

    public void incerementNum_of_connections() {
        this.num_of_connections++;
    }

    public int getNum_of_connections() {
        return num_of_connections;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Node{");
        sb.append("cords=");
        if (coords == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < coords.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(coords[i]);
            sb.append(']');
        }
        sb.append(", name='").append(name).append('\'');
        sb.append(", connections=").append(connections == null ? "null" : Arrays.asList(connections).toString());
        sb.append('}');
        return sb.toString();
    }

    public int[] getCoords() {
        return coords;
    }

    public ArrayList<Node> getConnections() {
        return connections;
    }
}
