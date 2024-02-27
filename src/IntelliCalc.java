import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class IntelliCalc extends JFrame implements ActionListener {
    private JTextField displayField;
    private double memoryValue = 0;
    private char lastOperation = ' ';
    private boolean isNewNumber = true;

    public IntelliCalc() {
        setTitle("IntelliCalc");
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));

        String[] buttonLabels = {
                "MC", "MR", "MS", "M+",
                "√", "x²", "1/x", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "±", "0", ".", "=", "C"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "MC":
                memoryValue = 0;
                break;
            case "MR":
                displayField.setText(formatNumber(memoryValue));
                isNewNumber = true;
                break;
            case "MS":
                memoryValue = Double.parseDouble(displayField.getText());
                isNewNumber = true;
                break;
            case "M+":
                memoryValue += Double.parseDouble(displayField.getText());
                isNewNumber = true;
                break;
            case "√":
                displayField.setText(formatNumber(Math.sqrt(Double.parseDouble(displayField.getText()))));
                isNewNumber = true;
                break;
            case "x²":
                displayField.setText(formatNumber(Math.pow(Double.parseDouble(displayField.getText()), 2)));
                isNewNumber = true;
                break;
            case "1/x":
                double value = Double.parseDouble(displayField.getText());
                if (value != 0)
                    displayField.setText(formatNumber(1 / value));
                else
                    displayField.setText("Error: Division by zero");
                isNewNumber = true;
                break;
            case "+":
            case "-":
            case "×":
            case "÷":
                lastOperation = command.charAt(0);
                memoryValue = Double.parseDouble(displayField.getText());
                isNewNumber = true;
                break;
            case "=":
                double result = performOperation();
                displayField.setText(formatNumber(result));
                isNewNumber = true;
                break;
            case "±":
                double currentValue = Double.parseDouble(displayField.getText());
                displayField.setText(formatNumber(-currentValue));
                break;
            case ".":
                if (!displayField.getText().contains(".")) {
                    displayField.setText(displayField.getText() + ".");
                }
                break;
            case "C":
                displayField.setText("");
                isNewNumber = true;
                break;
            default:
                if (isNewNumber) {
                    displayField.setText(command);
                    isNewNumber = false;
                } else {
                    displayField.setText(displayField.getText() + command);
                }
        }
    }

    private double performOperation() {
        double currentValue = Double.parseDouble(displayField.getText());
        switch (lastOperation) {
            case '+':
                return memoryValue + currentValue;
            case '-':
                return memoryValue - currentValue;
            case '×':
                return memoryValue * currentValue;
            case '÷':
                if (currentValue != 0)
                    return memoryValue / currentValue;
                else {
                    displayField.setText("Error: Division by zero");
                    return 0;
                }
        }
        return 0;
    }

    private String formatNumber(double number) {
        DecimalFormat df = new DecimalFormat("0.##########");
        return df.format(number);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(IntelliCalc::new);
    }
}