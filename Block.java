package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class Block extends Pane {
    Image blocksImg = new Image(getClass().getResourceAsStream("2.png"));

    ImageView block;

    public enum BlockType {

        PLATFORM, BRICK, BONUS, STAR, STONE

    }

    public Block(BlockType blockType, int x, int y) {

        block = new ImageView(blocksImg);

        block.setFitWidth(Game.BLOCK_SIZE);

        block.setFitHeight(Game.BLOCK_SIZE);

        setTranslateX(x);

        setTranslateY(y);

        switch (blockType) {

            case PLATFORM:

                block.setViewport(new Rectangle2D(4*64, 2*64, 64, 64));

                break;

            case BRICK:

                block.setViewport(new Rectangle2D(0,64,64,64));

                break;

            case STONE:

                block.setViewport(new Rectangle2D(5*64,3*64,64,64));

                break;

            case BONUS:

                block.setViewport(new Rectangle2D(6*64,3*64,64,64));

                break;

            case STAR:

                block.setViewport(new Rectangle2D(8*64,0,64,64));

                break;

        }

        getChildren().add(block);

        Game.platforms.add(this);

        Game.gameRoot.getChildren().add(this);
    }
}