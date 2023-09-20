package com.example.a_asterix;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class App extends Application {

    private int num_of_Nodes=1000;
    ObservableList<Node> nodes= FXCollections.observableArrayList();

    private void connectNodes(BorderPane root){
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

    private void generateNonOverlapingNodes(BorderPane root) throws InterruptedException {
        int num_of_failed_additions=0;
        for (int i = 0; i < num_of_Nodes; i++) {
            Node n = new Node(root.getScene());
            if(nodes.isEmpty()){
                nodes.add(n);
                addCircle(root,n);
            }else if(checkIfNotOverlaping(n)){
                nodes.add(n);
                addCircle(root,n);
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



    private void addCircle(BorderPane root, Node node) {
        Circle circle = new Circle();
        circle.setCenterX(node.getX());
        circle.setCenterY(node.getY());
        circle.setRadius(Node.radius);
        circle.setVisible(true);
        circle.setStroke(Node.color);
        circle.setStrokeWidth(Node.strokeWidth);
        root.getChildren().add(circle);

    }

    private void addLine(BorderPane root,Node begin,Node end){
        Line line=new Line(begin.getX(),begin.getY(),end.getX(),end.getY());
        line.setStroke(Color.rgb(0,140,0));
        line.setStrokeWidth(1);
        line.setVisible(true);
        root.getChildren().add(line);
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        BorderPane root = new BorderPane();
        root.setBackground(Background.fill(Color.BLACK));
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);
        stage.show();
        long start1=System.currentTimeMillis();
        generateNonOverlapingNodes(root);
        long end1=System.currentTimeMillis();
        System.out.println("generate nodes "+(end1-start1));

        long start2=System.currentTimeMillis();
        connectNodes(root);
        long end2=System.currentTimeMillis();
        System.out.println("connect nodes "+(end2-start2));

        System.out.println(nodes.size());
    }

    public static void main(String[] args) {
        launch();
    }
}