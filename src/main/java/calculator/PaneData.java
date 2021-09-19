package calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PaneData extends AnchorPane {

    public PaneData(List<String> stringList){

        Collections.reverse(stringList);

        Operation[] d = new Operation[5];
        for (String s : stringList)
            d[stringList.indexOf(s)] = new Operation(s);
        ObservableList<Operation> observableList = FXCollections.observableArrayList(d);

        TableView<Operation> table = new TableView<Operation>(observableList);

        TableColumn<Operation, String> tableColumn = new TableColumn<Operation, String>("OPERATION");
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("OPERATION"));
        tableColumn.setSortable(false);

        table.getColumns().add(tableColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);

        getChildren().add(table);
    }

    public PaneData show() {
        return this;
    }

    public static class Operation {

        private String OPERATION;

        public String getOPERATION() {
            return OPERATION;
        }

        public void setOPERATION(String OPERATION) {
            this.OPERATION = OPERATION;
        }

        public Operation(String OPERATION) {
            this.OPERATION = OPERATION;
        }

    }
}
