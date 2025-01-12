import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Pane;

public class DivisionBoard {
    private final GraphicsContext gc;
    private final Pane root; // Pane to hold the button
    private final int canvasWidth;
    private final int canvasHeight;
    private final int dividend;
    private final int divisor;
    private final int quotient; // Result of the division
    private final int cubeSize = 30;
    private final int textHeight = 150;
    private final int startYOffset = textHeight + 20;

    private int currentGroup = 0;
    private int cubesInCurrentGroup = 0;
    private int totalCubesDistributed = 0;
    private int quotientCounter = 0; // Tracks how many groups are fully formed

    private boolean animationComplete = false; // Tracks whether all cubes are distributed
    private Runnable onReturnToQuiz; // Callback for returning to the quiz

    public DivisionBoard(GraphicsContext gc, Pane root, int canvasWidth, int canvasHeight, int dividend, int divisor) {
        this.gc = gc;
        this.root = root;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.dividend = dividend;
        this.divisor = divisor;
        this.quotient = dividend / divisor;

        drawBackground();
        drawInitialText();
        addReturnToQuizButton(); // Add the button immediately during initialization
    }

    public void setOnReturnToQuiz(Runnable onReturnToQuiz) {
        this.onReturnToQuiz = onReturnToQuiz;
    }

    public boolean distributeNextCube() {
        if (totalCubesDistributed < dividend) {
            int groupXStart = (canvasWidth - (divisor * (cubeSize + 10))) / 2;
            int x = groupXStart + currentGroup * (cubeSize + 10);
            int y = startYOffset + cubesInCurrentGroup * (cubeSize + 5);

            // Draw the cube
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            gc.fillRect(x, y, cubeSize, cubeSize);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, cubeSize, cubeSize);

            totalCubesDistributed++;
            cubesInCurrentGroup++;

            // If a group is fully formed, increment the counter and move to the next group
            if (cubesInCurrentGroup >= quotient) {
                cubesInCurrentGroup = 0;
                currentGroup++;
                quotientCounter++;
                updateQuotientCounter(); // Update the displayed quotient counter
            }

            updateProgress();
            return true;
        } else if (!animationComplete) {
            displayFinalMessage(); // Display the final message when all cubes are distributed
            animationComplete = true; // Ensure this message is only displayed once
        }
        return false; // All cubes are distributed
    }

    private void drawInitialText() {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + dividend + " ÷ " + divisor + " = ?", canvasWidth / 2, 30);
        gc.fillText("Cubes distributed: 0 / " + dividend, canvasWidth / 2, 70);
        gc.fillText("Quotient counter: 0", canvasWidth / 2, 110); // Initial counter value
    }

    private void updateProgress() {
        // Clear only the text area
        gc.clearRect(0, 0, canvasWidth, textHeight);

        // Redraw the updated text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + dividend + " ÷ " + divisor + " = " + quotient, canvasWidth / 2, 30);
        gc.fillText("Cubes distributed: " + totalCubesDistributed + " / " + dividend, canvasWidth / 2, 70);
        gc.fillText("Quotient counter: " + quotientCounter, canvasWidth / 2, 110);
    }

    private void updateQuotientCounter() {
        // Display the updated quotient counter
        gc.setFill(Color.DARKGREEN);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Quotient counter: " + quotientCounter, canvasWidth / 2, 90);
    }

    private void displayFinalMessage() {
        gc.clearRect(0, canvasHeight - 100, canvasWidth, 100);
        // Display the final message at the bottom of the canvas
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("There are " + quotient + " cubes in each group.\nNumber of groups: " + divisor, canvasWidth / 2, canvasHeight - 100);
    }

    private void drawBackground() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    private void addReturnToQuizButton() {
        Button returnButton = new Button("Return to Quiz");
        returnButton.setStyle("-fx-font-size: 16; -fx-padding: 10;");
        returnButton.setLayoutX(canvasWidth / 2 - 60);
        returnButton.setLayoutY(canvasHeight - 50);

        // Add action for the button
        returnButton.setOnAction(event -> {
            System.out.println("Return to Quiz button clicked!"); // Debug message
            if (onReturnToQuiz != null) {
                onReturnToQuiz.run();
            }
        });

        root.getChildren().add(returnButton); // Add the button to the root layout
    }
}
