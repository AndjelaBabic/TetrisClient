package tetris;

import model.Rect;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void removeRows(List<Integer> rowsToRemoveIndex, Pane group){

        List<Node> blocks = new ArrayList<>();
        List<Node> newBlocks = new ArrayList<>();
        int SIZE = 30; // TODO

        while (rowsToRemoveIndex.size() > 0) {
            // get all blocks
            for (Node node : group.getChildren()) {
                if (node instanceof Rectangle) {
                    blocks.add(node);
                }
            }
            // for current row to delete remove nodes
            for (Node node : blocks) {
                Rectangle rect = (Rectangle) node;
                if (rect.getY() / SIZE == rowsToRemoveIndex.get(0)) {
                    group.getChildren().remove(rect);
                } else {
                    newBlocks.add(node);
                }
            }

            for (Node node : newBlocks) {
                Rectangle rect = (Rectangle) node;
                if (rect.getY() / SIZE < rowsToRemoveIndex.get(0)) {
                    rect.setY(rect.getY() + SIZE);
                }
            }

            blocks.clear();
            newBlocks.clear();
            rowsToRemoveIndex.remove(0);

        }
    }

    private int calculateHowManyRowsToMoveDown(List<Integer> rowsToRemoveIndex, Rect rect) {
        int j = 0;
        for (int i = 0; i < rowsToRemoveIndex.size(); i++){
            if(rect.getY() < rowsToRemoveIndex.get(i)){
                j++;
            }
        }
        return j;
    }
}