package tetris;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Rect;
import model.TetrisShape;
import tetris.block.Tetromino;

import java.util.ArrayList;
import java.util.List;


public class Convert {


    public static Tetromino getTetromino(TetrisShape shape) {
        Rectangle a = new Rectangle(shape.getA().getWidth(), shape.getA().getHeight());
        Rectangle b = new Rectangle(shape.getB().getWidth(), shape.getB().getHeight());
        Rectangle c = new Rectangle(shape.getC().getWidth(), shape.getC().getHeight());
        Rectangle d = new Rectangle(shape.getD().getWidth(), shape.getD().getHeight());

        a.setX(shape.getA().getX());
        b.setX(shape.getB().getX());
        c.setX(shape.getC().getX());
        d.setX(shape.getD().getX());

        a.setY(shape.getA().getY());
        b.setY(shape.getB().getY());
        c.setY(shape.getC().getY());
        d.setY(shape.getD().getY());

        Tetromino tetromino = new Tetromino(a, b, c, d, shape.getName());
        return tetromino;
    }

    public static TetrisShape getTetrisShape(Tetromino block) {
        Rect a = new Rect((int) block.a.getWidth(), (int) block.a.getHeight());
        Rect b = new Rect((int) block.b.getWidth(), (int) block.b.getHeight());
        Rect c = new Rect((int) block.c.getWidth(), (int) block.c.getHeight());
        Rect d = new Rect((int) block.d.getWidth(), (int) block.d.getHeight());

        a.setX((int) block.a.getX());
        b.setX((int) block.b.getX());
        c.setX((int) block.c.getX());
        d.setX((int) block.d.getX());

        a.setY((int)block.a.getY());
        b.setY((int)block.b.getY());
        c.setY((int)block.c.getY());
        d.setY((int)block.d.getY());

        TetrisShape shape = new TetrisShape(a, b, c, d, block.getName());
        shape.setForm(block.form);
        return shape;
    }

    public static void setValues(TetrisShape newBlock, Tetromino oldBlock) {
        oldBlock.a.setX(newBlock.getA().getX());
        oldBlock.b.setX(newBlock.getB().getX());
        oldBlock.c.setX(newBlock.getC().getX());
        oldBlock.d.setX(newBlock.getD().getX());

        oldBlock.a.setY(newBlock.getA().getY());
        oldBlock.b.setY(newBlock.getB().getY());
        oldBlock.c.setY(newBlock.getC().getY());
        oldBlock.d.setY(newBlock.getD().getY());

        oldBlock.form = newBlock.getForm();
    }

    public static List<Rect> getRectsFromThePane(Pane pane){
        List<Rectangle> blocks = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof Rectangle) {
                blocks.add((Rectangle) node);
            }
        }

        List<Rect> rects = new ArrayList<>();
        for (Rectangle rectangle : blocks){
            Rect a = new Rect((int)rectangle.getWidth(), (int)rectangle.getHeight());
            a.setX((int) rectangle.getX());
            a.setY((int) rectangle.getY());
            rects.add(a);
        }
        return rects;
    }


    public static void setRectsToThePane(Pane group, List<Rect> data) {

        List<Rectangle> rectangles = new ArrayList<>();
        for (Rect rect : data){
            Rectangle rectangle = new Rectangle(rect.getWidth(), rect.getHeight());
            rectangle.setX(rect.getX());
            rectangle.setY(rect.getY());
            rectangles.add(rectangle);
        }
        group.getChildren().removeAll();
        group.getChildren().addAll(rectangles);
    }
}
