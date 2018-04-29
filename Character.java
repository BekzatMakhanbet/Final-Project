package sample;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;



public class Character extends Pane{

    Image marioImg = new Image(getClass().getResourceAsStream("batman.gif"));

    ImageView imageView = new ImageView(marioImg);

    int count = 9;

    int columns = 9;

    int offsetX = 0;

    int offsetY = 375;

    int width = 67;

    int height = 60;

    int counterForHitAnimation=0;

    public SpriteAnimation animation;

    public SpriteAnimation animationForStand;

    public Point2D playerVelocity = new Point2D(0,0);

    boolean canJump = true;

    SpriteAnimation animationHit1=new  SpriteAnimation(this.imageView,Duration.millis(300),2,2, 45,440,55,57,1);

    SpriteAnimation animationKick=new SpriteAnimation(this.imageView,Duration.millis(500),5,5,23,550,70,height,1);;

    public Character(){
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));

        animation = new SpriteAnimation(this.imageView,Duration.millis(400),count,columns,offsetX,offsetY,width,height,-1);

        getChildren().addAll(this.imageView);

    }

    public void moveX(int value){

        boolean movingRight = value > 0;

        for(int i = 0; i<Math.abs(value); i++) {

            for (Node platform : Game.platforms) {

                if(this.getBoundsInParent().intersects(platform.getBoundsInParent())) {

                    if (movingRight) {

                        if (this.getTranslateX() + Game.BAT_SIZE == platform.getTranslateX()){

                            this.setTranslateX(this.getTranslateX() - 1);

                            return;

                        }
                    } else {

                        if (this.getTranslateX() == platform.getTranslateX() + Game.BLOCK_SIZE) {
                            this.setTranslateX(this.getTranslateX() + 1);
                            return;
                        }
                    }
                }
            }
            this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    public void moveY(int value){

        boolean movingDown = value >0;

        for(int i = 0; i < Math.abs(value); i++){
            for(Block platform :Game.platforms){
                if(getBoundsInParent().intersects(platform.getBoundsInParent())){

                    if(movingDown){
                        if(this.getTranslateY()+ Game.BAT_SIZE == platform.getTranslateY()){

                            this.setTranslateY(this.getTranslateY()-1);

                            canJump = true;
                            return;
                        }
                    }
                    else{

                        if(this.getTranslateY() == platform.getTranslateY()+ Game.BLOCK_SIZE){
                            this.setTranslateY(this.getTranslateY()+1);
                            playerVelocity = new Point2D(0,10);
                            return;
                        }
                    }
                }
            }

            this.setTranslateY(this.getTranslateY() + (movingDown?1:-1));

            if(this.getTranslateY()>640){
                this.setTranslateX(0);

                this.setTranslateY(400);

                Game.gameRoot.setLayoutX(0);
            }
        }
    }

    public void jumpPlayer(){

        if(canJump){

            playerVelocity = playerVelocity.add(0,-30);

            canJump = false;

        }

    }
}
