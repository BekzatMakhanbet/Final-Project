package sample;

import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


class startScenePane extends Pane{
    Text loading=new Text("Loading......");


    RotateTransition rotateTransition=new RotateTransition();

    static PathTransition pt=new PathTransition();

    Line l=new Line(0,600,1200,600);


    Circle r=new Circle(10);

    public startScenePane(){
        start();
    }


    public void start(){

        loading.setStyle("-fx-font-size:20;");

        loading.setY(580);

        loading.setX(550);

        loading.setFill(Color.YELLOW);

        setStyle("-fx-background-image:url(sample/startScene.jpg);-fx-background-size: 1200 620;");

        r.setFill(Color.YELLOW);

        l.setStroke(Color.YELLOW);

        pt.setPath(l);

        pt.setNode(r);


        pt.setDuration(Duration.seconds(6));

        pt.setCycleCount(1);

        pt.play();

        rotateTransition.setNode(loading);

        rotateTransition.setDuration(Duration.seconds(1.5));


        rotateTransition.setCycleCount(Timeline.INDEFINITE);

        rotateTransition.setAxis(new Point3D(0,1,0));

        rotateTransition.setFromAngle(0);

        rotateTransition.setToAngle(360);

        rotateTransition.play();

        setWidth(800);
        setHeight(480);
        getChildren().addAll(l,r,loading);
    }
}
class mainMenuScenePane extends Pane{

    VBox buttons =new VBox(15);
    static Button btPlay=new Button("■     PLAY");

    static Button btOptions=new Button("■  PLAYER");
    static Button btExit=new Button("■     EXIT");

    static ToggleButton mus=new ToggleButton();

    static ImageView musOn=new ImageView("sample/musOn1.png");

    static ImageView musOff=new ImageView("sample/musOff1.png");

        ImageView question=new ImageView("sample/question.png");


        Button btHelp=new Button();
    Text copyright=new Text("© Copyright by Bekzat");


    Media sound=new Media("file:///C:/Users/бекзат/IdeaProjects/DarkKnight/src/sample/music.mp3");

    MediaPlayer player=new MediaPlayer(sound);
    HBox helpMus=new HBox(5,mus,btHelp);


    public mainMenuScenePane(){
        super();
        start();
    }

    public void start(){

        btPlay.setTextFill(Color.BLACK);

        btOptions.setTextFill(Color.BLACK);

        btExit.setTextFill(Color.BLACK);

        copyright.setFill(Color.WHITE);

        buttons.setLayoutY(300);

        buttons.setLayoutX(230);
        buttons.getChildren().addAll(btPlay,btOptions,btExit);

        question.setFitWidth(35);

        question.setFitHeight(35);

        btHelp.setGraphic(question);

        copyright.setTranslateY(594);

        copyright.setTranslateX(315);

        copyright.setStyle("-fx-font-size:18;");

        musOn.setFitWidth(35);

        musOn.setFitHeight(35);

        musOff.setFitWidth(35);

        musOff.setFitHeight(35);

        mus.setGraphic(musOn);
        mus.setId("mus");


        btHelp.setId("mus");

        helpMus.setTranslateX(20);

        helpMus.setTranslateY(540);

        setId("main");

        getChildren().addAll(helpMus,copyright,buttons,question);
    }

}


class PlayerReg extends HBox{

    static TextField name=new TextField();

    static Label label=new Label("Name");

    static Button back=new Button();

    static Button reg=new Button("Register");

    static ImageView iconBack=new ImageView("sample/goBack.png");

    static int counterPlayer=1;

    public PlayerReg(){

        iconBack.setFitWidth(75);

        iconBack.setFitHeight(75);

        back.setGraphic(iconBack);

        back.setStyle("fx-border-radius:100;");

        getChildren().addAll(label,name,reg,back);

        back.setTranslateX(-750);

        back.setTranslateY(-250);

        setSpacing(40);

        label.setStyle(" -fx-font-size:20px;-fx-font-family:Arial;-fx-font-weight:bold;");

        name.setStyle(" -fx-font-size:20px;-fx-font-family:Arial;-fx-font-weight:bold;");

        setStyle("-fx-background-color: white");

        VBox vBox=new VBox();

        Connection con;
        Statement statement;


        try {

            Class.forName("com.mysql.jdbc.Driver");

            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");

            statement=con.createStatement();

            ResultSet rs=statement.executeQuery("select * from reg");

            while (rs.next()) {

                System.out.println(rs.getString("name"));

                vBox.getChildren().add(new Text(counterPlayer+".  "+rs.getString("name")));

            }

            System.out.println("succes");

        }

        catch (Exception ex){

            ex.printStackTrace();

        }


      //  vBox.setTranslateY(-220);

      //  vBox.getChildren().add(new Label("text"));

       // vBox.setTranslateX();

        vBox.setId("names");

    }
}
