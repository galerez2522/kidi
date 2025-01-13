import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class QuizApp {
    private int timerDuration;
    private boolean allowMultiplication;
    private boolean allowDivision;

    private Label questionLabel;
    private TextField answerField;
    private Button submitButton;
    private Label feedbackLabel;
    private Button viewAnimationButton;
    private Label timerLabel;
    private Label scoreLabel;

    private String currentQuestion;
    private int correctAnswer;
    private int score = 0;
    private int timeLeft;

    private boolean animationAvailable = false; // Tracks if animation is available
    private String savedQuestion; // Save the last question for animation
    private int savedCorrectAnswer; // Save the last correct answer for animation

    private Timeline timer;
    private Stage primaryStage;
    private Scene quizScene;

    public void setParameters(int timerDuration, boolean allowMultiplication, boolean allowDivision) {
        this.timerDuration = timerDuration;
        this.allowMultiplication = allowMultiplication;
        this.allowDivision = allowDivision;
    }

    public void start(Stage stage) {
        this.primaryStage = stage;

        // Initialize the quiz UI
        quizScene = createQuizScene(stage);

        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(quizScene);
        primaryStage.show();

        // Start the quiz
        generateNewQuestion();
    }

    private Scene createQuizScene(Stage primaryStage) {
        // Initialize UI elements
        questionLabel = new Label("Press 'Start Quiz' to begin.");
        questionLabel.setFont(Font.font("Futura", 24));
        questionLabel.setTextFill(Color.DARKBLUE);
        questionLabel.setEffect(new DropShadow(2, Color.GRAY));

        answerField = new TextField();
        answerField.setPromptText("Enter your answer");
        answerField.setStyle("-fx-font-size: 16; -fx-border-radius: 5; -fx-background-radius: 5;");

        submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Futura", 16));
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #45A049; -fx-text-fill: white;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        submitButton.setOnAction(e -> checkAnswer());

        feedbackLabel = new Label("");
        feedbackLabel.setFont(Font.font("Futura", 18));
        feedbackLabel.setTextFill(Color.BLACK);

        timerLabel = new Label("Time Left: " + timeLeft + " seconds");
        timerLabel.setFont(Font.font("Futura", 16));
        timerLabel.setTextFill(Color.RED);

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(Font.font("Futura", 16));
        scoreLabel.setTextFill(Color.DARKGREEN);

        viewAnimationButton = new Button("View Animation");
        viewAnimationButton.setFont(Font.font("Futura", 16));
        viewAnimationButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-background-radius: 10;");
        viewAnimationButton.setOnMouseEntered(e -> viewAnimationButton.setStyle("-fx-background-color: #E64A19; -fx-text-fill: white;"));
        viewAnimationButton.setOnMouseExited(e -> viewAnimationButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;"));
        viewAnimationButton.setVisible(false);
        viewAnimationButton.setOnAction(e -> showAnimation(primaryStage));

        // Layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                questionLabel, timerLabel, scoreLabel,
                answerField, submitButton, feedbackLabel, viewAnimationButton
        );

        return new Scene(layout, 500, 400);
    }

    private void generateNewQuestion() {
        Random random = new Random();

        // Choose the question type
        boolean isMultiplication = allowMultiplication && (!allowDivision || random.nextBoolean());

        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;

        if (isMultiplication) {
            currentQuestion = num1 + " × " + num2;
            correctAnswer = num1 * num2;
        } else {
            correctAnswer = num1;
            currentQuestion = (num1 * num2) + " ÷ " + num2;
        }

        questionLabel.setText("Question: " + currentQuestion);
        feedbackLabel.setText("");
        viewAnimationButton.setVisible(false);
        answerField.clear();
        animationAvailable = false; // Reset animation state

        startTimer();
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = timerDuration;
        timerLabel.setText("Time Left: " + timeLeft + " seconds");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + " seconds");

            if (timeLeft <= 0) {
                timer.stop();
                saveAnimationState();
                feedbackLabel.setText("Time's up! The correct answer was " + correctAnswer + ".");
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                viewAnimationButton.setVisible(true);
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void checkAnswer() {
        String userInput = answerField.getText().trim();

        try {
            int userAnswer = Integer.parseInt(userInput);
            if (userAnswer == correctAnswer) {
                timer.stop();
                score += timeLeft * 10;
                feedbackLabel.setText("Correct! You earned " + (timeLeft * 10) + " points.");
                feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                scoreLabel.setText("Score: " + score);
                generateNewQuestion();
            } else {
                saveAnimationState();
                feedbackLabel.setText("Incorrect. The correct answer is " + correctAnswer + ".");
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                viewAnimationButton.setVisible(true);
                timer.stop();
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
            feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void saveAnimationState() {
        savedQuestion = currentQuestion;
        savedCorrectAnswer = correctAnswer;
        animationAvailable = true;
    }

    private void showAnimation(Stage primaryStage) {
        if (animationAvailable) {
            AnimationHandler animationHandler = new AnimationHandler(savedQuestion, savedCorrectAnswer);

            // Pass callback to return to quiz
            animationHandler.startAnimation(primaryStage, () -> {
                primaryStage.setScene(quizScene);
                generateNewQuestion();
            });
        } else {
            feedbackLabel.setText("No animation available.");
        }
    }
}
