import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class QuizApp extends Application {
    private Label questionLabel;
    private TextField answerField;
    private Button submitButton;
    private Label feedbackLabel;
    private Button viewAnimationButton;
    private String currentQuestion;
    private int correctAnswer;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the quiz UI
        Scene quizScene = createQuizScene(primaryStage);

        primaryStage.setTitle("Quiz App");
        primaryStage.setScene(quizScene);
        primaryStage.show();

        // Start the quiz
        generateNewQuestion();
    }

    private Scene createQuizScene(Stage primaryStage) {
        // Initialize UI elements
        questionLabel = new Label("Press 'Start Quiz' to begin.");
        questionLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        answerField = new TextField();
        answerField.setPromptText("Enter your answer");

        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> checkAnswer());

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-font-size: 14;");

        viewAnimationButton = new Button("View Animation");
        viewAnimationButton.setVisible(false);
        viewAnimationButton.setOnAction(e -> showAnimation(primaryStage));

        // Layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));
        layout.getChildren().addAll(questionLabel, answerField, submitButton, feedbackLabel, viewAnimationButton);

        // Return the new Scene
        return new Scene(layout, 400, 300);
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
    }

    private void checkAnswer() {
        String userInput = answerField.getText().trim();

        try {
            int userAnswer = Integer.parseInt(userInput);
            if (userAnswer == correctAnswer) {
                feedbackLabel.setText("Correct! Great job!");
                feedbackLabel.setStyle("-fx-text-fill: green;");
                generateNewQuestion();
            } else {
                feedbackLabel.setText("Incorrect. The correct answer is " + correctAnswer + ".");
                feedbackLabel.setStyle("-fx-text-fill: red;");
                viewAnimationButton.setVisible(true);
            }
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Please enter a valid number.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void showAnimation(Stage primaryStage) {
        AnimationHandler animationHandler = new AnimationHandler(currentQuestion, correctAnswer);
    
        // Pass the callback to continue the quiz after the animation
        animationHandler.startAnimation(primaryStage, () -> {
            System.out.println("Returning to the quiz in QuizApp...");
            Scene quizScene = createQuizScene(primaryStage); // Rebuild the quiz UI
            primaryStage.setScene(quizScene); // Update the scene on the same Stage
            generateNewQuestion(); // Continue with the next question
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
