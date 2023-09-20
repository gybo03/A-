package com.example.a_asterix;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.a_asterix.App.addCircle;
import static com.example.a_asterix.App.addLine;

public class Network {
    private int num_of_Nodes=50;
    ObservableList<Node> nodes= FXCollections.observableArrayList();
    private Node origin;
    private Node destination;

    public Network(int num_of_Nodes) {
        this.num_of_Nodes = num_of_Nodes;
    }

    public void setOriginAndDestinationNode(BorderPane root){
        int origin_num=(int)(Math.random()*nodes.size());
        origin=nodes.get(origin_num);
        addCircle(root,origin, Color.RED);
        int destination_num;
        do{
            destination_num=(int)(Math.random()*nodes.size());
        }while(origin_num==destination_num);
        destination=nodes.get(destination_num);
        addCircle(root,destination,Color.RED);
    }

    public void connectNodes(BorderPane root){
        List<Node> xNode= new ArrayList<>(nodes);
        //long start1=System.currentTimeMillis();
        Collections.sort(xNode, Comparator.comparing(Node::getX));
        //long end1=System.currentTimeMillis();
        //System.out.println("sorting xNodes "+(end1-start1));
        List<Node> yNode=new ArrayList<>(nodes);
        Collections.sort(yNode, Comparator.comparing(Node::getY));

        /*for (int i = 0; i < xNode.size(); i++) {
            System.out.print("  "+xNode.get(i).getX());
        }
        System.out.println();
        for (int i = 0; i < yNode.size(); i++) {
            System.out.println(yNode.get(i).getY());
        }*/

        boolean m[][]=new boolean[nodes.size()][nodes.size()];
        for (int i = 0; i < xNode.size(); i++) {
            for (int j = 0; j < yNode.size(); j++) {
                if(xNode.get(i).equals(yNode.get(j))){
                    m[i][j]=true;
                    break;
                }else{
                    m[i][j]=false;
                }
            }
        }
        //long end2=System.currentTimeMillis();
        //System.out.println("making booelan matrix "+(end2-end1));

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if(m[i][j]){
                    makeConnection(m,i,j,xNode,root);
                }
            }
        }
        long end3=System.currentTimeMillis();
        //System.out.println("connecting nodes "+(end3-end2));
    }
    private void makeConnection(boolean m[][],int i,int j,List<Node> xNode,BorderPane root){
        int num_of_connections=0;
        int rand_connections=(int)(Math.random()*(Node.max_num_of_connections-Node.min_num_of_connections))+Node.min_num_of_connections;
        //System.out.println("rand_connections "+rand_connections);

        for (int l = 1; l < m.length/2; l++) {
            for (int n = i-l; n <= i+l; n++) {
                if(!(n<0||n>m.length-1)){
                    for (int o = j-l; o < j+l; o++) {
                        if(!(o<0||o>m.length-1)){
                            if(m[n][o]&&n!=i&&o!=j){
                                if(xNode.get(i).addNode(xNode.get(n))){
                                    num_of_connections++;
                                    addLine(root,xNode.get(i),xNode.get(n));
                                }
                            }
                        }
                    }
                }
            }
            if(xNode.get(i).getConnections().size()==rand_connections){
                break;
            }
        }

        //System.out.println("\tnum_of_connections"+num_of_connections);
    }
    public void generateNonOverlapingNodes(BorderPane root) throws InterruptedException {
        int num_of_failed_additions=0;
        for (int i = 0; i < num_of_Nodes; i++) {
            Node n = new Node((int)root.getScene().getWidth(),(int)root.getScene().getHeight());
            if(nodes.isEmpty()){
                nodes.add(n);
                addCircle(root,n,Node.color);
            }else if(checkIfNotOverlaping(n)){
                nodes.add(n);
                addCircle(root,n,Node.color);
                num_of_failed_additions=0;
            }else{
                num_of_failed_additions++;
                i--;
            }
            if(num_of_failed_additions>100){
                break;
            }
        }
        //System.out.println(num_of_failed_additions);
    }
    private boolean checkIfNotOverlaping(Node n){
        for (Node node:nodes) {
            if(n.distanceFrom2Nodes(node)<2*(Node.radius+Node.strokeWidth)){
                return false;
            }
        }
        return true;
    }
}
