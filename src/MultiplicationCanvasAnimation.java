import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MultiplicationCanvasAnimation extends Application {

    private static int numRows;    // Number of rows
    private static int numColumns; // Number of columns
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        // Create a Canvas and GraphicsContext
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set up the MultiplicationBoard
        MultiplicationBoard board = new MultiplicationBoard(gc, CANVAS_WIDTH, CANVAS_HEIGHT, numRows, numColumns);

        // Create the animator and start the animation
        MultiplicationAnimator animator = new MultiplicationAnimator(board);
        animator.start();

        // Set up the scene
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.LIGHTGRAY);

        primaryStage.setTitle("Multiplication Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Validate input arguments
        if (args.length != 2) {
            System.out.println("Error: You must provide exactly 2 arguments: <numRows> <numColumns>");
            System.out.println("Example: java MultiplicationCanvasAnimation 5 3");
            System.exit(1);
        }

        try {
            numRows = Integer.parseInt(args[0]);
            numColumns = Integer.parseInt(args[1]);

            if (numRows <= 0 || numColumns <= 0) {
                System.out.println("Error: Both arguments must be positive integers.");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Both arguments must be valid integers.");
            System.exit(1);
        }

        launch(args);
    }
}
