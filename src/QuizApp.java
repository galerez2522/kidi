import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class QuizApp extends Application {
    private Label questionLabel;
    private TextField answerField;
    private Button submitButton;
    private Label feedbackLabel;
    private Button viewAnimationButton;
    private Label timerLabel;
    private Label scoreLabel;

    private String currentQuestion;
    private int correctAnswer;
    private int score = 0; // Keep track of the total score
    private int timeLeft = 10; // Time allowed per question (in seconds)

    private Timeline timer; // Timer for countdown
    private Scene quizScene; // Maintain a single quiz scene

    @Override
    public void start(Stage primaryStage) {
        // Initialize the quiz UI
        quizScene = createQuizScene(primaryStage);

        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(quizScene);
        primaryStage.show();

        // Start the quiz
        generateNewQuestion();
    }

    private Scene createQuizScene(Stage primaryStage) {
        // Initialize UI elements with enhanced styling
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

        // Layout with enhanced visuals
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.setStyle("-fx-background-color: #F0F8FF; -fx-border-color: #D3D3D3; -fx-border-width: 2; -fx-border-radius: 10;");
        layout.getChildren().addAll(questionLabel, timerLabel, scoreLabel, answerField, submitButton, feedbackLabel, viewAnimationButton);

        // Return the new Scene
        return new Scene(layout, 500, 400);
    }

    private void generateNewQuestion() {
        Random random = new Random();
        boolean isMultiplication = random.nextBoolean();

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

        startTimer(); // Start the timer for this question
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = 10; // Reset the timer to 10 seconds
        timerLabel.setText("Time Left: " + timeLeft + " seconds");

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            timerLabel.setText("Time Left: " + timeLeft + " seconds");

            if (timeLeft <= 0) {
                timer.stop();
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
                timer.stop(); // Stop the timer
                int pointsEarned = timeLeft * 10; // Award points based on time remaining
                score += pointsEarned;

                feedbackLabel.setText("Correct! You earned " + pointsEarned + " points.");
                feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                scoreLabel.setText("Score: " + score);

                generateNewQuestion(); // Generate the next question
            } else {
                feedbackLabel.setText("Incorrect. The correct answer is " + correctAnswer + ".");
                feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                viewAnimationButton.setVisible(true);
                timer.stop(); // Stop the timer
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
            feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    private void showAnimation(Stage primaryStage) {
        AnimationHandler animationHandler = new AnimationHandler(currentQuestion, correctAnswer);

        // Pass the callback to continue the quiz after the animation
        animationHandler.startAnimation(primaryStage, () -> {
            System.out.println("Returning to the quiz in QuizApp...");
            primaryStage.setScene(quizScene); // Return to the same scene
            generateNewQuestion(); // Continue with the next question
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
