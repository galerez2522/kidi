import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DivisionCanvasAnimation extends Application {

    private Runnable onComplete; // Callback to execute after animation
    private static int dividend; // Number to divide
    private static int divisor;  // Number of groups
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;

    public DivisionCanvasAnimation(int dividend, int divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
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

        // Set up the DivisionBoard
        DivisionBoard board = new DivisionBoard(gc, root, CANVAS_WIDTH, CANVAS_HEIGHT, dividend, divisor);

        // Pass the onComplete callback to the DivisionBoard
        board.setOnReturnToQuiz(() -> {
            System.out.println("Return to Quiz button clicked!"); // Debug message
            if (onComplete != null) {
                onComplete.run(); // Trigger the callback to return to the quiz
            }
        });

        // Set up the animator
        DivisionAnimator animator = new DivisionAnimator(board);
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

        primaryStage.setTitle("Division Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
