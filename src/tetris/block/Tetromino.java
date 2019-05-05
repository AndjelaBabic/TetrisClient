package tetris.block;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tetromino {

    public Rectangle a;
    public Rectangle b;
    public Rectangle c;
    public Rectangle d;
    Color color;
    private String name;
    public int form = 1;

    public Tetromino(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Tetromino(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        // set color of the stones
        switch (name){
            case "j":
                color = Color.SLATEGRAY;
                break;
            case "l":
                color = Color.GOLDENROD;
                break;
            case "o":
                color = Color.INDIANRED;
                break;
            case "s":
                color = Color.FORESTGREEN;
                break;
            case "t":
                color = Color.CADETBLUE;
                break;
            case "z":
                color = Color.HOTPINK;
                break;
            case "i":
                color = Color.SANDYBROWN;
                break;
        }

        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    public void changeForm(){
        if(form!=4){
            form++;
        } else {
            form = 1;
        }
    }

    public String getName() {
        return name;
    }
}
