



import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;


public class Controller {

    private JButton clear, submit;

    public Controller(JButton clear, JButton submit) {
        this.clear = clear;
        this.submit = submit;

        // one listener for two Components(clear/submit Buttons)
        ButtonsListeners listeners = new ButtonsListeners();
        clear.addMouseListener(listeners);
        submit.addMouseListener(listeners);
    }

    private class ButtonsListeners extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            if (e.getSource().equals(clear)){ clear.setBackground(Color.WHITE);}
            else {submit.setBackground(Color.WHITE);}
        }
        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            if (e.getSource().equals(clear)){ clear.setBackground(Model.CLEAR_BUTTON_COLOR);}
            else{ submit.setBackground(Model.SUBMIT_BUTTON_COLOR);}
        }
    }



    public Controller(JSlider heightSlider, JLabel heightLabel) {
        heightSlider.addChangeListener(e -> heightLabel.setText("Height: " + heightSlider.getValue()));

    }

    public Controller(JButton clear, JButton submit, JTextField firstNameTextField, JTextField lastNameTextField,
                      JTextField ageTextField, JTextField weightTextField, JSlider heightSlider,
                      ButtonGroup genderGroup, ButtonGroup frameBodyGroup, JPanel resultPanel,
                      JLabel bmiLabel, JLabel bmiResult, JLabel weightStatusLabel, JLabel idealWeightLabel,
                      JLabel idealWeightResult, JLabel userActualWeightLabel,
                      JRadioButton smallRadioButton,JRadioButton mediumRadioButton,JRadioButton largeRadioButton) {
        clear.addActionListener(e -> {
            //clear user input
            firstNameTextField.setText("");
            lastNameTextField.setText("");
            ageTextField.setText("");
            weightTextField.setText("");
            heightSlider.setValue(140);
            genderGroup.clearSelection();
            frameBodyGroup.clearSelection();

            //ResultPanel And Labels:
            resultPanel.setBackground(Color.WHITE);
            //first row
            bmiLabel.setText("");
            bmiResult.setText("");
            weightStatusLabel.setText("");
            //second row
            idealWeightLabel.setText("");
            idealWeightResult.setText("");
            userActualWeightLabel.setText("");
        });

        submit.addActionListener(e->{

            boolean allowToCalcBmi = !ageTextField.getText().equals("")
                    && !weightTextField.getText().equals("")
                    && frameBodyGroup.getSelection() != null;
//             Did we get the important data to calculate?
            if (allowToCalcBmi) {
                try {
//                    try to calc if we got normal data.
                    float userHeight = ((heightSlider.getValue() / 100.f) * ((float) heightSlider.getValue() / 100.f)); // 1.6 ^ 2
                    float userWeight = Float.parseFloat(weightTextField.getText());
                    float userBmi = userWeight / userHeight;
                    bmiResult.setText(Float.toString(Float.parseFloat(new DecimalFormat("##.##").format(userBmi))));

                    resultPanel.setBackground(Model.RESULT_PANEL_COLOR);
                    bmiLabel.setText("BMI:");

                    if (userBmi < Model.ANOREXIC) weightStatusLabel.setText("Anorexic");
                    else if ( Model.ANOREXIC < userBmi  && userBmi < Model.UNDER_WEIGHT)  weightStatusLabel.setText("Underweight");
                    else if (Model.UNDER_WEIGHT < userBmi && userBmi < Model.NORMAL) weightStatusLabel.setText("Normal");
                    else if (Model.NORMAL < userBmi && userBmi < Model.OVER_WEIGHT)   weightStatusLabel.setText("Overweight");
                    else if (Model.OVER_WEIGHT < userBmi && userBmi < Model.EXTREME_OBESE){   weightStatusLabel.setText("Obese");}
                    else{weightStatusLabel.setText("Extreme Obese");}

                    float userAge = Float.parseFloat(ageTextField.getText());
                    float slimness = 0.f;
                    if (smallRadioButton.isSelected()){ slimness = Model.BODY_FRAME_SMALL;}
                    if (mediumRadioButton.isSelected()){ slimness = Model.BODY_FRAME_MEDIUM;}
                    if (largeRadioButton.isSelected()){ slimness = Model.BODY_FRAME_LARGE;}

                    float idealWeight = ((heightSlider.getValue() - 100.0f + (userAge / 10)) * 0.9f * slimness);

                    idealWeightLabel.setText("Ideal Weight:");
                    idealWeightResult.setText(Float.toString(Float.parseFloat(new DecimalFormat("##.##").format(idealWeight))));
                    userActualWeightLabel.setText("Actual weight: "+weightTextField.getText());

                } catch (NumberFormatException exception) {
                    Font oldFont = bmiLabel.getFont();
                    Border oldBorder = ageTextField.getBorder();

                    bmiResult.setText("INPUT ERROR");
                    bmiResult.setForeground(Color.red);
                    bmiResult.setFont(new Font ("Times New Roman", Font.BOLD | Font.ITALIC, 22));

                    // we rest here because the user may edit the data and submit without clearing
                    resultPanel.setBackground(Color.WHITE);
                    bmiLabel.setText("");
                    weightStatusLabel.setText("");
                    userActualWeightLabel.setText("");
                    idealWeightLabel.setText("");
                    idealWeightResult.setText("");

                    ageTextField.setBorder(new BevelBorder(BevelBorder.LOWERED,  Model.ERROR_COLOR,  Model.ERROR_COLOR));
                    weightTextField.setBorder(new BevelBorder(BevelBorder.LOWERED,  Model.ERROR_COLOR,  Model.ERROR_COLOR));

                    Timer timer = new Timer(1000, event -> {
                        bmiResult.setText("");
                        bmiResult.setForeground(Color.BLACK);
                        bmiResult.setFont(oldFont);

                        ageTextField.setBorder(oldBorder);
                        weightTextField.setBorder(oldBorder);
                    });
                    timer.setRepeats(false);
                    timer.start();

                }




            } else {
                Font oldFont = bmiLabel.getFont();
                Border oldBorder = ageTextField.getBorder();
                Color oldColor = smallRadioButton.getBackground();
                Color oldForegroundColor = smallRadioButton.getForeground();

                bmiResult.setText("Missing Important data");
                bmiResult.setForeground(Color.red);
                bmiResult.setFont(new Font ("Times New Roman", Font.BOLD | Font.ITALIC, 22));
                // we rest here because the user may edit the data and submit without clearing
                resultPanel.setBackground(Color.WHITE);
                bmiLabel.setText("");
                weightStatusLabel.setText("");
                userActualWeightLabel.setText("");
                idealWeightLabel.setText("");



                ageTextField.setBorder(new BevelBorder(BevelBorder.LOWERED, Model.ERROR_COLOR,  Model.ERROR_COLOR));
                weightTextField.setBorder(new BevelBorder(BevelBorder.LOWERED,  Model.ERROR_COLOR,  Model.ERROR_COLOR));

                smallRadioButton.setBackground(Model.ERROR_COLOR);
                smallRadioButton.setForeground(Color.WHITE);

                mediumRadioButton.setBackground(Model.ERROR_COLOR);
                mediumRadioButton.setForeground(Color.WHITE);

                largeRadioButton.setBackground(Model.ERROR_COLOR);
                largeRadioButton.setForeground(Color.WHITE);



                Timer timer = new Timer(1000, event -> {
                    bmiResult.setText("");
                    bmiResult.setForeground(Color.BLACK);
                    bmiResult.setFont(oldFont);

                    ageTextField.setBorder(oldBorder);
                    weightTextField.setBorder(oldBorder);

                    smallRadioButton.setBackground(oldColor);
                    smallRadioButton.setForeground(oldForegroundColor);

                    mediumRadioButton.setBackground(oldColor);
                    mediumRadioButton.setForeground(oldForegroundColor);

                    largeRadioButton.setBackground(oldColor);
                    largeRadioButton.setForeground(oldForegroundColor);

                });
                timer.setRepeats(false);
                timer.start();


            }

        });
    }




}
