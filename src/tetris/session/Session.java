package tetris.session;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Session {

    private static Session instance;
    private Map<String, Object> map;
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }


    private Session() {
        map = new HashMap<>();
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
}
