package sample;

import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.application.*;
import javafx.scene.image.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Quiz extends Application {
    private GridPane root;
    private Label questionLabel;
    private Label countryLabel;
    private Button answerButton;
    private Font textFont;
    private HBox hbox;
    private TextField answerField;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ImageView imageView;
    private Label scoreLabel;
    private Label endLabel;

    // Question index, used to pull out questions and answers.
    private int index = 0;
    private int score = 0;
    public Quiz()
    {

        root          = new GridPane();
        questionLabel = new Label("What is the capital in:");
        countryLabel  = new Label("Norway?");
        answerButton  = new Button("Submit");
        answerField   = new TextField();
        textFont      = new Font("Arial", 24);
        hbox          = new HBox(10);

        answerField.setMaxWidth(Double.MAX_VALUE);
        answerField.setPrefWidth(10.0);
        answerButton.setMaxWidth(Double.MAX_VALUE);
        hbox.setHgrow(answerField, Priority.ALWAYS);
        hbox.setHgrow(answerButton, Priority.ALWAYS);
        hbox.getChildren().addAll(answerField, answerButton);

        questions     = new ArrayList<>();
        answers       = new ArrayList<>();
        setupQuiz();

        imageView     = new ImageView(new Image("resources/" + questions.get(index) + ".png", 400, 300, true, true));
        scoreLabel    = new Label(index + " / " + questions.size());
        endLabel      = new Label("");
        fitImageSize(400, 300);
        setFont();
    }

    @Override
    public void start(Stage primaryStage)
    {
        Quiz program = new Quiz();
        program.getGridPane().setAlignment(Pos.CENTER);
        program.getGridPane().setHgap(10);
        program.getGridPane().setVgap(10);

        primaryStage.setTitle("Quiz");
        primaryStage.setScene(new Scene(program.getGridPane(), 550, 550));
        primaryStage.getIcons().add(new Image("resources/icon.png"));

        program.addToGrid();
        program.getAnswerButton().setDefaultButton(true);
        program.getAnswerButton().setOnAction(e -> program.buttonAction());

        primaryStage .show();
    }

    public void buttonAction()
    {
        if (quizEnd()) {
            return;
        }

        checkAnswer();
        clearTextField();
        nextQuestion();
    }

    private void disableButton()
    {
        answerButton.setDisable(true);
    }

    // Returns true if index has reached quiz array size
    // otherwise returns false.
    private boolean quizEnd()
    {
        if (index == questions.size())
            return true;
        else
            return false;
    }

    private void changeEndLabel()
    {
        endLabel.setText("Thanks for participating!");
    }

    private void clearTextField()
    {
        answerField.clear();
    }

    private void checkAnswer()
    {
        if (isAnswerCorrect())
            updateScore();
    }

    private void updateScore()
    {
        scoreLabel.setText(++score + " / " + questions.size());
    }

    private boolean isAnswerCorrect()
    {
        if (answerField.getText().toLowerCase().equals(answers.get(index)))
            return true;
        else
            return false;
    }

    private void nextQuestion()
    {
        incrementIndex();

        if (quizEnd()) {
            changeEndLabel();
            disableButton();
            return;
        }

        changeCountryLabel();
        nextImage();
    }

    private void changeCountryLabel()
    {
        String text = questions.get(index);
        text        = firstLetterToUpper(text);
        countryLabel.setText(text + "?");
    }

    // Returns string where the first letter is made uppercase
    private String firstLetterToUpper(String text)
    {
        if (text.isEmpty() || text == null)
            return text;

        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private void incrementIndex()
    {
        ++index;
    }

    private void nextImage()
    {
        imageView.setImage(new Image("resources/" + questions.get(index) + ".png", 400, 300, true, true));
    }

    public void addToGrid()
    {
        root.add(questionLabel, 0, 0, 2, 1);
        root.add(imageView,     0, 1, 2, 1);
        root.add(countryLabel,  0, 2, 1, 1);
        root.add(hbox,          0, 3, 2, 1);
        root.add(scoreLabel,    0, 4, 1, 1);
        root.add(endLabel,      1, 4, 1, 1);
    }

    private void fitImageSize(int width, int height)
    {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    private void setFont()
    {
        questionLabel.setFont(textFont);
        countryLabel.setFont(textFont);
        scoreLabel.setFont(textFont);
        endLabel.setFont(textFont);
    }
    String w1="";

    private void setupQuiz()
    {	try {
        File myObj = new File("C:\\Users\\User\\Desktop\\words.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
             w1 = w1+myReader.nextLine();

        }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
        String[] words=w1.split("\\s");
        for(String w:words){
            questions.add(w);
        }
        answers.add("oslo");
        answers.add("stockholm");
        answers.add("copenhagen");
        answers.add("helsinki");
        answers.add("paris");
        answers.add("berlin");
        answers.add("reykjavik");
        answers.add("rome");
    }

    public GridPane getGridPane()   { return root; }
    public Label getQuestionLabel() { return questionLabel; }
    public Label getCountryLabel()  { return countryLabel; }
    public Button getAnswerButton() { return answerButton; }

    public static void main(String[] args)
    {
        launch(args);
    }
}