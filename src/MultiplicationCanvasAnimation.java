import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MultiplicationCanvasAnimation extends Application {

    private Runnable onComplete; // Callback to execute after animation
    private static int numRows;    // Number of rows
    private static int numColumns; // Number of columns
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 700;

    public MultiplicationCanvasAnimation(int dividend, int divisor) {
        this.numRows = dividend;
        this.numColumns = divisor;
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

        // Set up the callback to return to the quiz
        board.setOnReturnToQuiz(() -> {
            System.out.println("Returning to the quiz...");
            if (onComplete != null) {
                onComplete.run(); // Trigger the callback
            }
        });

        // Create the animator and start the animation
        MultiplicationAnimator animator = new MultiplicationAnimator(board);
        animator.start();

        // Set up the scene
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.LIGHTGRAY);

        primaryStage.setTitle("Multiplication Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
