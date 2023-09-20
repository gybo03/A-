package com.example.a_asterix;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Network network;
    public static void addCircle(BorderPane root, Node node,Color color) {
        Circle circle = new Circle();
        circle.setCenterX(node.getX());
        circle.setCenterY(node.getY());
        circle.setRadius(Node.radius);
        circle.setVisible(true);
        circle.setStroke(color);
        circle.setStrokeWidth(Node.strokeWidth);
        root.getChildren().add(circle);

    }
    public static void addLine(BorderPane root,Node begin,Node end){
        Line line=new Line(begin.getX(),begin.getY(),end.getX(),end.getY());
        line.setStroke(Node.color_connection);
        line.setStrokeWidth(1);
        line.setVisible(true);
        root.getChildren().add(line);
    }
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        BorderPane mainPane=new BorderPane();
        Stage mainStage=new Stage();
        Scene mainScene=new Scene(mainPane,500,220);
        mainStage.setScene(mainScene);

        Button generate=new Button("Generate");

        Label width=new Label("width");
        Label height=new Label("height");
        Label max_num_of_connections=new Label("max_num_of_connections");
        Label min_num_of_connections=new Label("min_num_of_connections");
        Label num_of_nodes=new Label("num_of_nodes");
        Label radius_of_nodes=new Label("radius_of_nodes");
        Label color_of_nodes=new Label("color_of_nodes");
        Label color_of_connections=new Label("color_of_connections");

        TextField width_field=new TextField();
        width_field.setMinSize(40,40);
        width_field.setMaxSize(40,40);
        TextField height_field=new TextField();
        height_field.setMinSize(40,40);
        height_field.setMaxSize(40,40);
        TextField max_field=new TextField();
        max_field.setMinSize(40,40);
        max_field.setMaxSize(40,40);
        TextField min_field=new TextField();
        min_field.setMinSize(40,40);
        min_field.setMaxSize(40,40);
        TextField num_field=new TextField();
        num_field.setMinSize(40,40);
        num_field.setMaxSize(40,40);
        TextField radius_field=new TextField();
        radius_field.setMinSize(40,40);
        radius_field.setMaxSize(40,40);

        VBox vBox1=new VBox(20,width,max_num_of_connections,num_of_nodes);
        vBox1.setAlignment(Pos.BOTTOM_LEFT);
        VBox vBox2=new VBox(20,height,min_num_of_connections,radius_of_nodes);
        vBox2.setAlignment(Pos.BOTTOM_LEFT);
        VBox vBox3=new VBox(height_field,max_field,num_field);
        vBox3.setAlignment(Pos.CENTER_LEFT);
        VBox vBox4=new VBox(width_field,min_field,radius_field);
        vBox4.setAlignment(Pos.CENTER_LEFT);
        HBox hBox1=new HBox(60,vBox1,vBox3,vBox2,vBox4);

        ColorPicker color_nodes=new ColorPicker(Color.rgb(0,255,0));
        ColorPicker color_connections =new ColorPicker(Color.rgb(0,140,0));

        VBox vBox5=new VBox(10,color_of_nodes,color_nodes);
        vBox5.setAlignment(Pos.CENTER);
        VBox vBox6=new VBox(10,color_of_connections, color_connections);
        vBox6.setAlignment(Pos.CENTER);

        HBox hBox2=new HBox(60,vBox5,vBox6);
        hBox2.setAlignment(Pos.CENTER);

        VBox vBox7=new VBox(10,hBox1,hBox2,generate);
        vBox7.setAlignment(Pos.CENTER);

        //generate.setVisible(true);
        mainPane.setCenter(vBox7);
        mainStage.show();
        generate.setOnAction(E->{
            if(width_field.getText().equals("")||
                    height_field.getText().equals("")||
                    max_field.getText().equals("")||
                    min_field.getText().equals("")||
                    num_field.getText().equals("")||
                    radius_field.getText().equals("")){
                BorderPane errorPane=new BorderPane();
                Stage errorStage=new Stage();
                Scene errorScene=new Scene(errorPane,200,100);
                Label errorLabel=new Label("Error");
                errorLabel.setFont(new Font(40));
                errorStage.setScene(errorScene);
                errorPane.setCenter(errorLabel);
                errorStage.show();
            }else{
                network=new Network(Integer.valueOf(num_field.getText()));
                Node.radius=Integer.valueOf(radius_field.getText());
                Node.min_num_of_connections=Integer.valueOf(min_field.getText());
                Node.max_num_of_connections=Integer.valueOf(max_field.getText());
                Node.color=color_nodes.getValue();
                Node.color_connection=color_connections.getValue();

                BorderPane root = new BorderPane();
                root.setBackground(Background.fill(Color.BLACK));
                Scene scene = new Scene(root, Integer.valueOf(height_field.getText()), Integer.valueOf(width_field.getText()));
                Stage stage1=new Stage();
                stage1.setScene(scene);
                try {
                    network.generateNonOverlapingNodes(root);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                network.connectNodes(root);
                network.setOriginAndDestinationNode(root);
                stage1.show();
            }
        });






        //long start1=System.currentTimeMillis();

        //long end1=System.currentTimeMillis();
        //System.out.println("generate nodes "+(end1-start1));

        //long start2=System.currentTimeMillis();

        //long end2=System.currentTimeMillis();
        //System.out.println("connect nodes "+(end2-start2));



        //System.out.println(nodes.size());
    }

    public static void main(String[] args) {
        launch();
    }
}