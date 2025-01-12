import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MultiplicationCanvasAnimation extends Application {

    private Runnable onComplete; // Callback to execute after animation
    private static int numRows;    // Number of rows
    private static int numColumns; // Number of columns
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 700;

    public MultiplicationCanvasAnimation(int num1, int num2) {
        this.numRows = num1;
        this.numColumns = num2;
    }

    public void playAnimation(Stage stage, Runnable onComplete) {
        this.onComplete = onComplete; // Store the callback
        start(stage); // Start the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Canvas and GraphicsContext
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a Pane (for dynamic layout)
        Pane root = new Pane();
        root.getChildren().add(canvas);

        // Set up the MultiplicationBoard
        MultiplicationBoard board = new MultiplicationBoard(gc, root, CANVAS_WIDTH, CANVAS_HEIGHT, numRows, numColumns);

        // Pass the onComplete callback to the board
        board.setOnReturnToQuiz(() -> {
            System.out.println("Returning to the quiz...");
            if (onComplete != null) {
                onComplete.run(); // Trigger the callback to return to the quiz
            }
        });

        // Set up the animator
        MultiplicationAnimator animator = new MultiplicationAnimator(board);
        animator.start();

        // Add Pause and Run buttons
        Button pauseButton = new Button("Pause");
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(CANVAS_HEIGHT - 50);
        pauseButton.setStyle("-fx-font-size: 14; -fx-background-color: #FFCC00;");
        pauseButton.setOnAction(e -> animator.pause());

        Button runButton = new Button("Run");
        runButton.setLayoutX(100);
        runButton.setLayoutY(CANVAS_HEIGHT - 50);
        runButton.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        runButton.setOnAction(e -> animator.resume());

        root.getChildren().addAll(pauseButton, runButton);

        // Set up the scene
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.LIGHTGRAY);

        primaryStage.setTitle("Multiplication Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
