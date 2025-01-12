import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DivisionCanvasAnimation extends Application {

    private static int dividend; // Number to divide
    private static int divisor;  // Number of groups
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        // Create a Canvas and GraphicsContext
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Set up the DivisionBoard
        DivisionBoard board = new DivisionBoard(gc, CANVAS_WIDTH, CANVAS_HEIGHT, dividend, divisor);

        // Create the animator and start the animation
        DivisionAnimator animator = new DivisionAnimator(board);
        animator.start();

        // Set up the scene
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.LIGHTGRAY);

        primaryStage.setTitle("Division Animation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Validate input arguments
        if (args.length != 2) {
            System.out.println("Error: You must provide exactly 2 arguments: <dividend> <divisor>");
            System.out.println("Example: java DivisionCanvasAnimation 15 3");
            System.exit(1);
        }

        try {
            dividend = Integer.parseInt(args[0]);
            divisor = Integer.parseInt(args[1]);

            if (dividend <= 0 || divisor <= 0) {
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
