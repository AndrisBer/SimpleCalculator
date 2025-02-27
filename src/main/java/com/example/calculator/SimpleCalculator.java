package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.util.Stack;

public class SimpleCalculator {
    @FXML
    private TextArea out;
    @FXML
    private TextField inp;

    private String inputExpression = "";

    @FXML
    private void handleButtonClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        inputExpression += clickedButton.getText();
        inp.setText(inputExpression);
    }

    @FXML
    private void ClearInputs(MouseEvent event) {
        inputExpression = "";
        inp.clear();
        out.clear();
    }

    @FXML
    private void getResult() {
        if (inputExpression.isEmpty()) return;

        // Validate input
        if (!inputExpression.matches("[0-9+\\-*/%√^().]+")) {
            out.setText("Invalid Input");
            return;
        }

        try {
            float result = evaluate(inputExpression);
            out.setText(String.format("%.2f", result));
        } catch (ArithmeticException e) {
            out.setText("Math Error");
        } catch (Exception e) {
            out.setText("Invalid Expression");
        }
    }

    // =====================================
    //       Expression (ex. 2-5*7/(3+9)-4 ) Evaluation Logic
    // =====================================
    private static float evaluate(String expression) {
        Stack<Float> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder numStr = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numStr.append(expression.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Float.parseFloat(numStr.toString()));
            } else if (ch == '(') {
                operations.push(ch);
            } else if (ch == ')') {
                while (!operations.isEmpty() && operations.peek() != '(') {
                    processStacks(numbers, operations);
                }
                operations.pop(); // Remove '('
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '√' || ch == '^') {
                while (!operations.isEmpty() && precedence(ch) <= precedence(operations.peek())) {
                    processStacks(numbers, operations);
                }
                operations.push(ch);
            }
        }

        while (!operations.isEmpty()) {
            processStacks(numbers, operations);
        }

        return numbers.pop();
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        if (operator == '%' || operator == '√') return 3;
        if (operator == '^') return 4;
        return 0;
    }

    private static void processStacks(Stack<Float> numbers, Stack<Character> operations) {
        float b = numbers.pop();
        float a = numbers.isEmpty() ? 0 : numbers.pop();
        char op = operations.pop();
        numbers.push(op == '+' ? a + b :
                op == '-' ? a - b :
                        op == '*' ? a * b :
                                op == '/' ? a / b :
                                        op == '%' ? (b / 100) * a :
                                                op == '√' ? (float) Math.sqrt(a) :
                                                        (float) Math.pow(a, b));
    }

    @FXML
    private void convert(MouseEvent event) {
        try {
            float a = Float.parseFloat(inp.getText());
            float resF = ((a * (float) 9 / 5) + 32);
            float resC = ((a-32)*((float) 5 /9));
            out.setText("F° >> " + String.format("%.2f", resF)+"\nC° >> "+String.format("%.2f", resC));
        } catch (NumberFormatException e) {
            out.setText("Invalid Input");
        }
    }
}
