/*You are tasked with implementing a basic calculator with a graphical user interface (GUI) in Java. The calculator
should be able to evaluate valid mathematical expressions entered by the user and display the result on the GUI.*/



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Qno2a extends JFrame {

    private JTextField expressionField;
    private JLabel resultLabel;

    public Qno2a() {
        // Set up the frame
        setTitle("Basic Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        expressionField = new JTextField();
        JButton calculateButton = new JButton("Calculate");
        resultLabel = new JLabel("Result: ");

        // Set up the layout
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Enter Expression:"));
        panel.add(expressionField);
        panel.add(calculateButton);

        add(panel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = expressionField.getText();
                try {
                    int result = evaluateExpression(expression);
                    resultLabel.setText("Result: " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: Invalid Expression");
                }
            }
        });
    }

    // Method to evaluate the mathematical expression
    private int evaluateExpression(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int len = expression.length();

        for (int i = 0; i < len; i++) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue; // Ignore whitespaces
            }

            if (Character.isDigit(ch)) {
                int val = 0;
                while (i < len && Character.isDigit(expression.charAt(i))) {
                    val = val * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                values.push(val);
                i--; // Adjust i as it will be incremented in the next iteration
            } else if (ch == '(') {
                ops.push(ch);
            } else if (ch == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Remove '(' from stack
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!ops.isEmpty() && precedence(ch) <= precedence(ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(ch);
            }
        }

        while (!ops.isEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Method to apply an operator to two values
    private int applyOp(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    // Method to determine the precedence of operators
    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    public static void main(String[] args) {
        // Run the calculator GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Qno2a().setVisible(true);
            }
        });
    }
}
