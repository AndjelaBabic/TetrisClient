package tetris.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.TetrisShape;
import results.Results;
import tetris.Convert;
import tetris.Utils;
import tetris.block.Tetromino;
import tetris.session.Session;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Operation;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Controller {

    static Socket socket = Session.getInstance().getSocket();

    public static void connectWithServer() {
        try {
            socket = new Socket("localhost", 9009);
            Session.getInstance().setSocket(socket);
            System.out.println("Successfully connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Tetromino getNextRect() throws Exception {
        RequestObject request = new RequestObject();
        request.setOperation(Operation.GET_NEXT_RECT);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
        out.flush();

        // Wait for response
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ResponseObject response = (ResponseObject) in.readObject();
        return Convert.getTetromino((TetrisShape) response.getData());
    }

    public static void moveRight(Tetromino block) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.MOVE_RIGHT);
            request.setData(Convert.getTetrisShape(block));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            Convert.setValues((TetrisShape) response.getData(), block);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveLeft(Tetromino block) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.MOVE_LEFT);
            request.setData(Convert.getTetrisShape(block));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            Convert.setValues((TetrisShape) response.getData(), block);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveTurn(Tetromino block) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.MOVE_TURN);
            request.setData(Convert.getTetrisShape(block));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            if (response.getCode() == 1) {
                Convert.setValues((TetrisShape) response.getData(), block);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean moveDown(Tetromino block)  {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.MOVE_DOWN_BLOCK);
            request.setData(Convert.getTetrisShape(block));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            if (response.getCode() == 1) {
                Convert.setValues((TetrisShape) response.getData(), block);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkBoundries(Tetromino object) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.CHECK_BOUNDRIES);
            request.setData(Convert.getTetrisShape(object));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            if (response.getCode() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int removeRows(Pane group) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.REMOVE_ROWS);
            request.setData(Convert.getRectsFromThePane(group));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            List<Integer> rows = (List<Integer>) response.getData();
            int numberOfRows = rows.size();
            Utils.removeRows(rows, group);
            return numberOfRows;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean insertResult(String name, int score) {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.INSERT_IN_SCORES);
            Results result = new Results();
            result.setName(name);
            result.setScore(score);
            result.setDate(new java.util.Date());
            request.setData(result);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            return response.getCode() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Results> getHighScoreList() {
        try {
            RequestObject request = new RequestObject();
            request.setOperation(Operation.GET_HIGH_SCORE_LIST);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(request);
            out.flush();

            // Wait for response
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ResponseObject response = (ResponseObject) in.readObject();
            return (List<Results>) response.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO everything
    public void changeScreen(Stage window) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("results.fxml"));
        Scene scene = new Scene(root);

        window.setScene(scene);
        window.show();

    }


}
