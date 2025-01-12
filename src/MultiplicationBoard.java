import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Pane;

public class MultiplicationBoard {
    private final GraphicsContext gc;
    private final Pane root; // Pane to hold the button
    private final int canvasWidth;
    private final int canvasHeight;
    private final int numRows;
    private final int numColumns;
    private final int totalCubes;
    private final int cubeSize = 30;
    private final int textHeight = 150;
    private final int startYOffset = textHeight + 20;
    private final int horizontalCenter;

    private int currentRow = 0;
    private int currentColumn = 0;
    private int cubesDrawn = 0;
    private int rowsCompleted = 0;

    private Runnable onReturnToQuiz; // Callback to return to quiz

    public MultiplicationBoard(GraphicsContext gc, Pane root, int canvasWidth, int canvasHeight, int numRows, int numColumns) {
        this.gc = gc;
        this.root = root;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.totalCubes = numRows * numColumns;
        this.horizontalCenter = (canvasWidth - (numColumns * (cubeSize + 5))) / 2;

        drawBackground(); // Draw the white background once
        drawInitialText(); // Draw initial instructions
        addReturnToQuizButton(); // Add the button at the start
    }

    public void setOnReturnToQuiz(Runnable onReturnToQuiz) {
        this.onReturnToQuiz = onReturnToQuiz;
    }

    public boolean drawNextCube() {
        if (currentRow < numRows) {
            int x = horizontalCenter + currentColumn * (cubeSize + 5);
            int y = startYOffset + currentRow * (cubeSize + 5);

            // Draw the cube
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            gc.fillRect(x, y, cubeSize, cubeSize);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, cubeSize, cubeSize);

            cubesDrawn++;
            updateProgress(); // Update text only

            // Move to the next position
            currentColumn++;
            if (currentColumn >= numColumns) {
                currentColumn = 0;
                currentRow++;
                rowsCompleted++;
                updateRowCompletion();
            }
            return true;
        }
        return false; // No more cubes to draw
    }

    private void drawInitialText() {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + numColumns + " × " + numRows + " = ?", canvasWidth / 2, 30);
        gc.fillText("Cubes drawn: 0 / " + totalCubes, canvasWidth / 2, 70);
        gc.fillText("Rows completed: 0 / " + numRows, canvasWidth / 2, 110);
    }

    private void updateProgress() {
        // Clear only the text area
        gc.clearRect(0, 0, canvasWidth, textHeight);

        // Redraw the updated text
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + numColumns + " × " + numRows + " = " + (numColumns * numRows),
                canvasWidth / 2, 30);
        gc.fillText("Cubes drawn: " + cubesDrawn + " / " + totalCubes, canvasWidth / 2, 70);
        gc.fillText("Rows completed: " + rowsCompleted + " / " + numRows, canvasWidth / 2, 110);
    }

    private void updateRowCompletion() {
        gc.clearRect(0, canvasHeight - 100, canvasWidth, 50);

        gc.setFill(Color.DARKGREEN);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Group " + rowsCompleted + " completed!", canvasWidth / 2, canvasHeight - 70);
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
            if (onReturnToQuiz != null) {
                onReturnToQuiz.run();
            }
        });

        root.getChildren().add(returnButton);
    }
}
