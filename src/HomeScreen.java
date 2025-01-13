import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScreen extends Application {
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;

        // Title Label
        Label titleLabel = new Label("Welcome to the Math Quiz!");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        // Timer Configuration
        Label timerLabel = new Label("Set Timer (seconds per question):");
        TextField timerField = new TextField();
        timerField.setPromptText("Enter seconds (e.g., 10)");

        // Question Type Selection
        Label questionTypeLabel = new Label("Select Question Types:");
        CheckBox multiplicationBox = new CheckBox("Multiplication");
        CheckBox divisionBox = new CheckBox("Division");

        // Start Quiz Button
        Button startQuizButton = new Button("Start Quiz");
        startQuizButton.setStyle("-fx-font-size: 16; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        startQuizButton.setOnAction(e -> {
            String timerInput = timerField.getText().trim();
            int timerDuration = 10; // Default timer duration

            try {
                timerDuration = Integer.parseInt(timerInput);
            } catch (NumberFormatException ex) {
                showAlert("Invalid Timer", "Please enter a valid number for the timer.");
                return;
            }

            boolean isMultiplication = multiplicationBox.isSelected();
            boolean isDivision = divisionBox.isSelected();

            if (!isMultiplication && !isDivision) {
                showAlert("No Question Types Selected", "Please select at least one question type.");
                return;
            }

            // Launch the Quiz
            QuizApp quizApp = new QuizApp();
            quizApp.setParameters(timerDuration, isMultiplication, isDivision);
            quizApp.start(mainStage);
        });

        // Layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                titleLabel,
                timerLabel, timerField,
                questionTypeLabel, multiplicationBox, divisionBox,
                startQuizButton
        );

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Screen");
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
