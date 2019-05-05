package tetris.form;

import tetris.block.Tetromino;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sun.security.pkcs11.wrapper.Constants;
import tetris.controller.Controller;
import tetris.session.Session;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Tetris extends Application {

    public static final int SIZE = 30;
    public static final int MOVE = 30;
    public static int XMAX = SIZE * 12;
    public static int YMAX = SIZE * 24;
    public static int score = 0;
    private static int linesNo = 0;

    public static int time = 0;
    private static boolean game = true;

    private static Pane group = new Pane();

    private static Scene scene = new Scene(group, XMAX + 150, YMAX);
    /**
     *  Represent current block
     */
    private static Tetromino object;
    /**
     *  Represent next block
     */
    private static Tetromino nextObj;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller.connectWithServer();

        // creating score and level text
        Line line = new Line(XMAX, YMAX, XMAX, 0);
        Text scoreText = new Text("Score: ");
        scoreText.setStyle("-fx-font: 20 arials;");
        scoreText.setX(XMAX + 5);
        scoreText.setY(50);
        Text lines = new Text("Lines: ");
        lines.setStyle("-fx-font: 20 arials;");
        lines.setX(XMAX + 5);
        lines.setY(100);

        group.getChildren().addAll(scoreText, lines, line);

        // Creating first block and adding it to the pane
        nextObj = Controller.getNextRect();
        Tetromino a = nextObj;
        group.getChildren().addAll(a.a, a.b, a.c, a.d);
        moveOnKeyPressed(a);
        object = a;
        nextObj = Controller.getNextRect();
        primaryStage.setScene(scene);
        primaryStage.setTitle("TETRIS");
        primaryStage.getIcons().add(new Image("file:tetris.png"));
        primaryStage.show();

        // Timer
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
            if (object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0) {
                time++;
            } else {
                time = 0;
            }
            // for the 2 seconds the block is on the top
            if (time == 2) {
                Text over = new Text("Game over");
                over.setFill(Color.RED);
                over.setStyle("-fx-font: 70 arial;");
                over.setY(250);
                over.setX(10);
                group.getChildren().add(over);
                game = false;
            }
            if (time == 15) {
                System.exit(0);
            }

            if (game) {
                moveDown(object);
                scoreText.setText("Score: " + score);
                lines.setText("Lines: " + linesNo);
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void moveOnKeyPressed(Tetromino block) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    Controller.moveRight(block);
                    break;
                case LEFT:
                    Controller.moveLeft(block);
                    break;
                case UP:
                    Controller.moveTurn(block);
                    break;
                case DOWN:
                    moveDown(block);
                     score++; //TODO: This should be done on server side when moving down
                    break;
                case SPACE:
                    moveToBottom(block);
                    break;
            }
        });
    }

    private void moveToBottom(Tetromino block) {
    }

    private void moveDown(Tetromino block){
        // moved down is true if the block is still moving
        boolean movingDown = Controller.moveDown(block);
        if(!movingDown){
            int rowsToRemove = Controller.removeRows(group);
            score += rowsToRemove * 50;
            linesNo += rowsToRemove;
            try {
                object = nextObj;
                if(Controller.checkBoundries(object)){
                    moveOnKeyPressed(object);
                    group.getChildren().addAll(object.a, object.b, object.c, object.d);
                }
                nextObj = Controller.getNextRect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
