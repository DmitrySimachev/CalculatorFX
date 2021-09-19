package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainFrameController {

    @FXML
    private Button historyId;

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea textId;

    @FXML
    private Label resultId;

    @FXML
    private Button clearId;

    @FXML
    private Button degreeId;

    @FXML
    private Button shareId;

    @FXML
    private Button rootNumberId;

    @FXML
    private Button sevenId;

    @FXML
    private Button eightId;

    @FXML
    private Button nineId;

    @FXML
    private Button multiplyId;

    @FXML
    private Button fourId;

    @FXML
    private Button fiveId;

    @FXML
    private Button sixId;

    @FXML
    private Button minusId;

    @FXML
    private Button oneId;

    @FXML
    private Button twoId;

    @FXML
    private Button threeId;

    @FXML
    private Button plusId;

    @FXML
    private Button zeroId;

    @FXML
    private Button equallyId;

    /**
     * номер индекса в массиве значений
     */
    private int countElement = 0;
    /**
     * массив хранит введенные значения
     */
    private List<String> elements = new ArrayList<>();
    private String text = "";
    private String resultText;
    private String result = "";

    private boolean sign = false;

    int tStart;
    int tEnd;

    DBInitialize db;
    PaneData paneData;

    boolean paneDataVisible = true;




    @FXML
    private void initialize() {
        clearId.setOnAction(e -> {
            clear(e);
            resultId.setText("");
        });

        nineId.setOnAction(this::btncode);
        eightId.setOnAction(this::btncode);
        sevenId.setOnAction(this::btncode);
        sixId.setOnAction(this::btncode);
        fiveId.setOnAction(this::btncode);
        fourId.setOnAction(this::btncode);
        threeId.setOnAction(this::btncode);
        twoId.setOnAction(this::btncode);
        oneId.setOnAction(this::btncode);
        zeroId.setOnAction(this::btncode);

        rootNumberId.setOnAction(this::btncode);

        shareId.setOnAction(this::btncode);
        multiplyId.setOnAction(this::btncode);
        minusId.setOnAction(this::btncode);
        plusId.setOnAction(this::btncode);

        historyId.setOnAction(this::selectTable);
        equallyId.setOnAction(this::addTable);
    }

    private void addTable(ActionEvent actionEvent) {
        root.getChildren().remove(paneData);
        paneDataVisible = true;
        db.insertTable(textId.getText(), resultText);
        clear(actionEvent);
    }

    private void selectTable(ActionEvent actionEvent) {
        if(paneDataVisible){
            List<String> stringList = db.selectTable();

            paneData = new PaneData(stringList);
            AnchorPane anchorPane = paneData.show();
            AnchorPane.setTopAnchor(anchorPane, 125.0);
            AnchorPane.setRightAnchor(anchorPane, 100.0);
            AnchorPane.setLeftAnchor(anchorPane, 0.0);
            AnchorPane.setBottomAnchor(anchorPane, 0.0);
            root.getChildren().add(anchorPane);
            anchorPane.toFront();

//            paneData.show();
            paneDataVisible = false;
        }else {
//            paneData.hide();
            root.getChildren().remove(paneData);
            paneDataVisible = true;
        }
    }

    private void clear(ActionEvent e) {
        textId.clear();
        text = "";
        tStart = 0;
        tEnd = 0;
    }

    private void btncode(ActionEvent e) {
        if (e.getSource() == nineId) buttonNumbers("9");
        if (e.getSource() == eightId) buttonNumbers("8");
        if (e.getSource() == sevenId) buttonNumbers("7");
        if (e.getSource() == sixId) buttonNumbers("6");
        if (e.getSource() == fiveId) buttonNumbers("5");
        if (e.getSource() == fourId) buttonNumbers("4");
        if (e.getSource() == threeId) buttonNumbers("3");
        if (e.getSource() == twoId) buttonNumbers("2");
        if (e.getSource() == oneId) buttonNumbers("1");
        if (e.getSource() == zeroId) { //todo сделать проверку деление на 0 и 0 в начале строки
//            int i = text.indexOf(text.length()-2);
//            String t = String.valueOf(text.charAt(i));
//            if(!t.equals("/"))
                buttonNumbers("0");
        }

        if (e.getSource() == minusId) signPut("-"); //todo минус вначале может означать отрицательное число и после другого знака
        if (!text.isEmpty()) {
            if (e.getSource() == shareId) signPut("/");
            if (e.getSource() == multiplyId) signPut("*");
            if (e.getSource() == plusId) signPut("+");

            textId.setText(text);
        }
    }

    private void buttonNumbers(String s) {
        text = text + s;
        sign = false;

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String str = parsString(text);
        resultText = str;
        resultId.setText(decimalFormat.format(Double.parseDouble(str)));
    }

    /**
     * Парсер строки
     *
     * @param text
     * @return
     */
    private String parsString(String text) {
        String strOperation = text.trim();
        System.err.println(strOperation);

        while (true) {
            System.err.println("Начало " + strOperation);
            if (strOperation.contains("/") || strOperation.contains("*")) {
                if (strOperation.contains("/")) {
                    int tIndex = strOperation.indexOf("/");

                    double delimoe = firstVariable(tIndex, strOperation);
                    double delitel = secondVariable(tIndex, strOperation);

                    if (delitel != 0) result = String.valueOf(delimoe / delitel);
                    else result = String.valueOf(delimoe);

                    strOperation = strOperation.replace(strOperation.substring(tStart + 1, tEnd), result);
                }else{
                    /* Умножение */
                    int tIndex = strOperation.indexOf("*");

                    double value1 = firstVariable(tIndex, strOperation);
                    double value2 = secondVariable(tIndex, strOperation);

                    result = String.valueOf(value1 * value2);

                    strOperation = strOperation.replace(strOperation.substring(tStart + 1, tEnd), result);
                }
            } else if (strOperation.contains("+") || strOperation.contains("-")) {
                if (strOperation.contains("+")) {
                    int tIndex = strOperation.indexOf("+");

                    double value1 = firstVariable(tIndex, strOperation);
                    double value2 = secondVariable(tIndex, strOperation);

                    result = String.valueOf(value1 + value2);

                    strOperation = strOperation.replace(strOperation.substring(tStart + 1, tEnd), result);
                } else{
                    int tIndex = strOperation.indexOf("-");
                    System.err.println("tIndex " + tIndex );

                    if(tIndex == 0){
//                        count = count.replace(count.substring(tStart, tEnd), result);

//                        if(isInteger(count))
//                        double value1 = secondVariable(tIndex, count);
//                        System.err.println(value1);

                            return strOperation;
//                        else{}   //todo -6-3 выдает ошибку если два отрицательных числа, сделать решение
                    }else{
                        double value1 = firstVariable(tIndex, strOperation);
                        double value2 = secondVariable(tIndex, strOperation);

                        System.err.println("            " + value1 + " " + value2);

//                        if(value1 >value2)

                        if(value2<0) result = String.valueOf(value1 - Math.abs(value2));
                        else result = String.valueOf(value1 - value2);

                        System.err.println(tStart + " " + tEnd);
                        strOperation = strOperation.replace(strOperation.substring(tStart + 1, tEnd), result);
                    }
                }
            } else {
                System.err.println("конец " + strOperation);
                System.err.println();
                return strOperation;
            }
        }
    }

    /**
     * Поиск числа после арифметического знака
     *
     * @param tIndex
     * @param count
     */
    private double secondVariable(int tIndex, String count) {
        double variable = 0;
        for (tEnd = tIndex + 1; tEnd < count.length(); tEnd++) {
            System.err.println("Длина строки " + count.length() + " ");


                if (!isInteger((String.valueOf((count.charAt(tEnd)))))) {
                    if(String.valueOf((count.charAt(tIndex + 1))).equals("-")){ // todo Проверить
                        tEnd++;
                        System.err.println("СЛЕДУЮЩИЙ ММИНУС");
                    }else {
                        System.err.println("ЧИСЛО " + count.charAt(tEnd - 1));

                        System.err.println("НЕ ЧИСЛО " + count.charAt(tEnd) + " " + tEnd);

                        System.err.println("Индекс конца делителя " + (tEnd));

                        variable = Double.parseDouble((((count.substring(tIndex + 1, tEnd)))));
                        System.err.println("Делитель " + variable);

                        System.err.println("Индекс конца делителя " + tEnd + " " + variable);

                        break;
                    }
                } else {
                    variable = Double.parseDouble((((count.substring(tIndex + 1, tEnd + 1)))));
                    System.err.println("Конец строки, индекс " + tEnd + " Число " + count.substring(tIndex + 1, tEnd + 1));
                }

        }
        return variable;
    }

    private double firstVariable(int tIndex, String count) {
        double variable = 0;

        //Поиск индекса начала числа до арифметического знака
        System.err.println("-------------------------------- " + tIndex);
        for (tStart = tIndex - 1; tStart >= 0; tStart--) {

            System.err.println("Вход " + String.valueOf(count.charAt(tStart)));

                if (isInteger(String.valueOf(count.charAt(tStart)))) {
                    variable = Double.parseDouble(count.substring(tStart, tIndex));

                } else {
                    if(String.valueOf(count.charAt(tStart)).equals("-")) {
//                        variable = Double.parseDouble(count.substring(tStart, tIndex));
                        variable = Double.parseDouble(count.substring(tStart+1, tIndex)); // todo Проверить
                        System.err.println("Индекс начала ===== " + tStart  + " " + count.substring(tStart, tIndex));

                        break;
                    }else {
                        variable = Double.parseDouble(count.substring(tStart + 1, tIndex));

                        System.err.println("Индекс начала делимого " + tStart + "   " + count.substring(tStart, tIndex));
                        break;
                    }
                }
        }
        return variable;
    }

    /**
     * Проверка выбора знака
     */
    private void signPut(String s) {
        if (sign) {
            text = text.substring(0, text.length() - 1);
        }
        text = text + s;
        sign = true;
    }

    /**
     * Проверка является ли элемент строки числом
     */
    public static boolean isInteger(String s) {

        if (s == null || s.equals("")) return false;

        if (s.equals(".")) return true;

        try {
            int iVal = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            /*ignore, System.out.println(e.getMessage());*/
        }
        return false;
    }

    public void setDB(DBInitialize db) {
        this.db = db;
    }
}

