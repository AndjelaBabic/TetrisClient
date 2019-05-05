package tetris.controller;

import javafx.scene.layout.Pane;
import model.TetrisShape;
import tetris.Convert;
import tetris.Utils;
import tetris.block.Tetromino;
import tetris.session.Session;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            request.setOperation(23);
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
            request.setOperation(12);
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
}
