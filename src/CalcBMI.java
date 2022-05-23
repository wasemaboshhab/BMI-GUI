import javax.swing.*;


public class CalcBMI extends JFrame {
    private JPanel mainPanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton largeRadioButton;
    private JSlider heightSlider;
    private JTextField weightTextField;
    private JButton clear;
    private JButton submit;

    private JLabel heightLabel;
    private JPanel resultPanel;
    private JLabel bmiLabel;
    private JLabel bmiResult;
    private JLabel idealWeightLabel;
    private JLabel weightStatusLabel;
    private JLabel idealWeightResult;
    private JLabel userActualWeightLabel;
    private JTextField ageTextField;

    public CalcBMI(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        ButtonGroup frameBodyGroup = new ButtonGroup();
        frameBodyGroup.add(smallRadioButton);
        frameBodyGroup.add(mediumRadioButton);
        frameBodyGroup.add(largeRadioButton);

        // change the heightLabel with the slider in live
        new Controller(heightSlider, heightLabel);

        //change color when mouse Enter/Exit to the Buttons
        new Controller(clear, submit);

        //rest and submit
        new Controller(clear, submit, firstNameTextField, lastNameTextField, ageTextField, weightTextField,
                heightSlider, genderGroup, frameBodyGroup, resultPanel, bmiLabel, bmiResult, weightStatusLabel,
                idealWeightLabel, idealWeightResult, userActualWeightLabel, smallRadioButton, mediumRadioButton, largeRadioButton);

    }

    public static void main(String[] args) {
         new CalcBMI("BMI");

    }
}
