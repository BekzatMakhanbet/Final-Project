package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import  javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Game extends Application {

    public static ArrayList<Block> platforms = new ArrayList<>();    //

    private HashMap<KeyCode,Boolean> keys = new HashMap<>(); //

    Media media=new Media("file:/C:/music.mp3");

    MediaPlayer mediaPlayer=new MediaPlayer(media);

    Image backgroundImg = new Image(getClass().getResourceAsStream("gg.png"));

    Line line=new Line(0,595,1200,595);

    Circle circle=new Circle(10);

    Label loading=new Label("Loading.......");

    PathTransition pathTransition=new PathTransition(Duration.millis(5000),line,circle);

    RotateTransition rotateTransition=new RotateTransition(Duration.millis(1000),loading);

    public static final int BLOCK_SIZE = 45;

    public static final int BAT_SIZE = 60;

    public static Pane appRoot = new Pane();

    public static Pane gameRoot = new Pane();

    public Character player;

    int levelNumber = 0;

    boolean bool=false;

    private int levelWidth;

    ArrayList<Block> ls=new ArrayList<Block>();

    private void initContent(){

        ImageView backgroundIV = new ImageView(backgroundImg);

        backgroundIV.setFitHeight(14*BLOCK_SIZE);

        levelWidth = LevelData.levels[levelNumber][0].length()*BLOCK_SIZE;

        for(int i = 0; i < LevelData.levels[levelNumber].length; i++){

            String line = LevelData.levels[levelNumber][i];

            for(int j = 0; j < line.length();j++){

                switch (line.charAt(j)){

                    case '0':

                        break;

                    case '1':

                        Block platformFloor = new Block(Block.BlockType.PLATFORM, j * BLOCK_SIZE, i * BLOCK_SIZE);

                        break;

                    case '2':

                        Block brick = new Block(Block.BlockType.BRICK,j*BLOCK_SIZE,i*BLOCK_SIZE);

                        break;

                    case '4':

                        Block brick1=new Block(Block.BlockType.BRICK,j*BLOCK_SIZE,i* BLOCK_SIZE);

                    case '9':

                        Block bonus = new Block(Block.BlockType.BONUS,j*BLOCK_SIZE,i*BLOCK_SIZE);


                        break;

                    case '7':

                        Block stone = new Block(Block.BlockType.STONE,j * BLOCK_SIZE, i * BLOCK_SIZE);

                        break;


                    case '*':

                        Block InvisibleBlock = new Block(Block.BlockType.STAR,j * BLOCK_SIZE, i * BLOCK_SIZE);

                        ls.add(InvisibleBlock);

                        break;
                }
            }

        }

        player =new Character();

        player.setTranslateX(0);

        player.setTranslateY(400);

        player.translateXProperty().addListener((obs,old,newValue)->{

            int offset = newValue.intValue();

            if(offset>640 && offset<levelWidth-640){

                gameRoot.setLayoutX(-(offset-640));

                backgroundIV.setLayoutX(-(offset-640));

            }

        });

        gameRoot.getChildren().add(player);

        appRoot.getChildren().addAll(backgroundIV,gameRoot);

    }

    private void update(){

        for (int i = 0; i <ls.size(); i++) {
            if (Math.abs(player.getLayoutX()-ls.get(i).getLayoutX())<60){
                appRoot.getChildren().remove(ls.get(i));
            }
        }

        if(isPressed(KeyCode.UP) && player.getTranslateY()>=5){

            player.jumpPlayer();

        }

        if(isPressed(KeyCode.LEFT) && player.getTranslateX()>=5){

            player.imageView.setFitWidth(60);

            player.setScaleX(-1);

            player.animation.play();

            player.moveX(-5);

            for (int i = 0; i <ls.size(); i++) {
                if (Math.abs(player.getTranslateX()-ls.get(i).getTranslateX())<15){
                    appRoot.getChildren().remove(ls.get(i));
                }
            }

        }
        if(isPressed(KeyCode.RIGHT) && player.getTranslateX()+40 <=levelWidth-5) {

            player.imageView.setFitWidth(60);

            player.setScaleX(1);

            player.animation.play();

            player.moveX(5);

            for (int i = 0; i <ls.size(); i++) {
                if (Math.abs(player.getTranslateX()-ls.get(i).getTranslateX())<15){
                    appRoot.getChildren().remove(ls.get(i));
                }
            }
        }

        if(player.playerVelocity.getY()<10){

            player.playerVelocity = player.playerVelocity.add(0,1);

        }
        player.moveY((int)player.playerVelocity.getY());
    }

    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }

    Connection con;
    Statement statement;

    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("batCon.png")));

        mainMenuScenePane mp=new mainMenuScenePane();

        Scene mainScene=new Scene(mp,1200,620);

        PlayerReg pg=new PlayerReg();

        pg.setAlignment(Pos.CENTER);

        Scene screg=new Scene(pg,1200,620);

        pg.reg.setOnAction(event -> {

            try {
                Class.forName("com.mysql.jdbc.Driver");

                con= DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");

                statement=con.createStatement();

                statement.execute("insert into reg (name) values('"+pg.name.getText()+"')");

                primaryStage.setScene(mainScene);

                System.out.println("succes");
        }

        catch (Exception ex){

            System.out.println("We could not register you, Please check Internet connection and XAMPP Controller");

            }
        });

        mediaPlayer.play();

        initContent();

        startScenePane startPane=new startScenePane();

        Scene startScene=new Scene(startPane,1200,620);

        mainScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Scene scene = new Scene(appRoot,1200,620);

        scene.setOnKeyPressed(event-> keys.put(event.getCode(), true));

        scene.setOnMouseClicked(event -> {

            if (event.getButton()== MouseButton.PRIMARY) {

                player.imageView.setFitWidth(55);

                player.animationHit1.play();

            }

            if (event.getButton()==MouseButton.SECONDARY) {

                player.imageView.setFitWidth(70);

                if (player.canJump) {

                    player.playerVelocity = player.playerVelocity.add(0, -23);

                    player.canJump = false;

                }

                player.animationKick.play();

            }
        });

        player.animationKick.setOnFinished(event -> {

            player.animation.stop();

            player.imageView.setFitWidth(37);

            player.imageView.setViewport(new Rectangle2D(0,305,37,60));

        });

        player.animationHit1.setOnFinished(event -> {

            player.animation.stop();

            player.imageView.setFitWidth(37);

            player.imageView.setViewport(new Rectangle2D(0,305,37,60));

        });

        scene.setOnKeyReleased(event -> {

            keys.put(event.getCode(), false);

            player.animation.stop();

            player.imageView.setFitWidth(37);

            player.animationHit1.stop();

            player.imageView.setViewport(new Rectangle2D(0,305,37,60));

        });

        primaryStage.setTitle("Batman");

        primaryStage.setScene(startScene);
        startScenePane.pt.setOnFinished(event -> {

            primaryStage.setScene(mainScene);

        });

        mainMenuScenePane.btPlay.setOnAction(event -> {

            primaryStage.setScene(scene);

        });

        mainMenuScenePane.mus.setOnAction(event -> {

            if (!bool){
                mainMenuScenePane.mus.setGraphic(mainMenuScenePane.musOff);
                mediaPlayer.pause();
                bool=true;
            }
            else{
                mainMenuScenePane.mus.setGraphic(mainMenuScenePane.musOn);
                mediaPlayer.play();
                bool=false;

            }
        });

        mainMenuScenePane.btExit.setOnAction(event -> {

            primaryStage.close();

        });

        screg.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        mainMenuScenePane.btOptions.setOnAction(event -> {
            primaryStage.setScene(screg);
        });
        pg.back.setOnAction(event -> {
            primaryStage.setScene(mainScene);
        });
        pg.setId("main1");

        primaryStage.show();

        AnimationTimer timer = new AnimationTimer(){

            public void handle(long now) {

                update();

            }
        };

        timer.start();

    }

    public static void main(String[] args) {

        launch(args);
    }

}
