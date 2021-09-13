package calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainFrameController {

    @FXML
    private Button historyId;

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

    /** номер индекса в массиве значений*/
    private int countElement = 0;
    /** массив хранит введенные значения*/
    private List<String> elements = new ArrayList<>();
    private String text = "";
    private String result = "";

    private boolean sign = false;


    @FXML
    private void initialize() {
        clearId.setOnAction(this::clear);

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

        shareId.setOnAction(this::btncode);
        multiplyId.setOnAction(this::btncode);
        minusId.setOnAction(this::btncode);
        plusId.setOnAction(this::btncode);
    }

    private void clear(ActionEvent e) {

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
        if (e.getSource() == zeroId) buttonNumbers("0");

        if (e.getSource() == minusId) signPut("-");
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
        resultId.setText(decimalFormat.format(Double.parseDouble(parsString(text))));
    }

    /** Парсер строки
     *
     * @param text
     * @return*/
    private String parsString(String text) {
         String count = text.trim();
         System.err.println(count);

         while (true) {
             System.err.println("Начало " + count);
             if (count.contains("/") || count.contains("*")) {
                 if (count.contains("/")) {
                     int tIndex = count.indexOf("/");
                     System.err.println("Индекс знака деления ----- " + tIndex);

                     int tEnd;
                     int tStart;
                     int delitel = 0;

                    //Поиск индекса конца числа после арифметического знака
                     for (tEnd = tIndex + 1; tEnd < count.length(); tEnd++) {
                         System.err.println("Длина строки " + count.length() + " ");

//                         tEnd = tIndex + i + count.substring(tIndex + i).length();
//                         System.err.println(Character.isDigit(Integer.parseInt(String.valueOf((count.charAt(tIndex+i)))))
//                                 + " " +Integer.parseInt(String.valueOf((count.charAt(tIndex+i)))));

//                         if (!Character.isDigit(Integer.parseInt(String.valueOf(count.charAt(tEnd))))) {
//                         if (!Character.isDigit((count.charAt(tEnd)))) {
                         if (!isInteger((String.valueOf((count.charAt(tEnd)))))) {
                             System.err.println("ЧИСЛО " + count.charAt(tEnd-1) );

//                         if ((String.valueOf(count.charAt(tIndex+i))).equals("/") ||
//                                 (String.valueOf(count.charAt(tIndex+i))).equals(System.getProperty("line.separator"))) {

//                         if(!Character.isDigit(Integer.parseInt((count.valueOf(tIndex + i))))){
                             System.err.println("НЕ ЧИСЛО " + count.charAt(tEnd) + " " + tEnd);

//                             tEnd = tIndex + i + count.substring(tIndex + i).length();
//                             tEnd = tIndex + i ;
                             // Индекс конца делителя todo нийти конец делителя

                             System.err.println("Индекс конца делителя " + (tEnd));
//                             System.err.println("Длина делителя " + count.substring(tIndex, tEnd).length());

                             delitel = Integer.parseInt((count.substring(tIndex + 1, tEnd)));
                             System.err.println("Делитель " + delitel);

                             System.err.println("Индекс конца делителя " + tEnd + " " +  delitel);
//                                     + count.substring(tIndex +1, tEnd +count.substring(tIndex + 1).length() - 1));

                             break;
                         }
                         else{
                             delitel = Integer.parseInt((count.substring(tIndex + 1, tEnd+1)));
//                             System.err.println("Конец строки, индекс " + tEnd + " Число "  + count.charAt(tEnd));
                             System.err.println("Конец строки, индекс " + tEnd + " Число "  + count.substring(tIndex + 1, tEnd+1));
                         }
                     }
                     System.err.println("Цикл конец ===============================================");

                     //Поиск индекса начала числа до арифметического знака
                     for (int i = 0;;i++) {
//                         if (!Character.isDigit(Integer.parseInt((count.substring(0, tIndex - i))))) {
                         if(!Character.isDigit((count.charAt(tIndex - i)))){
                             tStart = tIndex - count.substring(0, tIndex - i).length();
                             System.err.println("tStart " + tStart + " " + count.substring(tStart, tIndex));
                             break;
                         }
                     }

                     System.err.println("Индекс начала делимого " + tStart + "   " + count.substring(tStart, tIndex) );


//                     int tEnd = count.substring(tIndex).lastIndexOf("");
//                    int t = count.substring(tIndex).lastIndexOf(" ");
//                    tStart = count.substring(0, tIndex).lastIndexOf(" ");



//                    System.err.println(Integer.parseInt(count.substring(tIndex+1, tEnd)));
                     if(delitel!=0) result = String.valueOf(Double.parseDouble(count.substring(tStart, tIndex)) / delitel);
                     else result = String.valueOf(Double.parseDouble(count.substring(tStart, tIndex)));

                    System.err.println("result " + result);

                     String formattedString = count.replace(count.substring(tStart, tEnd), result);
                     count = formattedString;

//                 System.err.println("count.substring(tStart, tEnd) " + count.substring(tStart, tEnd));
                 System.err.println("Обработка строчки " + count);
                 System.err.println("formattedString " + formattedString);



                    //            String[] words = text.split(" + ");




//                    System.err.println(tDelStart);

//                    String first = count.substring(tDelStart, tIndex);//todo
//                    String second = count.substring(Integer.parseInt(count.substring(tIndex+3, tDelEnd)));

//                    System.err.println("first " + first);
//                    System.err.println("second " + second);
//                    count = "";

//                    System.err.println("Деление " + count);


                } else {

                }
            } else if (this.text.contains("+") || this.text.contains("-")) {

            }
             else {
                System.err.println("конец " + count);
                 System.err.println();
                return count;
            }
        }
    }

    /** Проверка выбора знака */
    private void signPut(String s) {
        if (sign) {
            text = text.substring(0, text.length() - 1);
        }
        text = text + s;
        sign = true;
    }

    /** Проверка является ли элемент строки числом */
    public static boolean isInteger(String s) {

        if(s == null || s.equals("")) {
            return false;
        }

        try {
            int iVal = Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException e) {
             /*ignore, System.out.println(e.getMessage());*/
        }
        return false;
    }

}

