import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MultiplicationBoard {
    private final GraphicsContext gc;
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

    public MultiplicationBoard(GraphicsContext gc, int canvasWidth, int canvasHeight, int numRows, int numColumns) {
        this.gc = gc;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.totalCubes = numRows * numColumns;
        this.horizontalCenter = (canvasWidth - (numColumns * (cubeSize + 5))) / 2;

        drawInitialText();
    }

    public boolean drawNextCube() {
        if (currentRow < numRows) {
            int x = horizontalCenter + currentColumn * (cubeSize + 5);
            int y = startYOffset + currentRow * (cubeSize + 5);

            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            gc.fillRect(x, y, cubeSize, cubeSize);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(x, y, cubeSize, cubeSize);

            cubesDrawn++;
            updateProgress();

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
        gc.setFont(Font.font("Arial", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + numColumns + " × " + numRows + " = ?", canvasWidth / 2, 30);
        gc.fillText("Cubes drawn: 0 / " + totalCubes, canvasWidth / 2, 60);
        gc.fillText("Rows completed: 0 / " + numRows, canvasWidth / 2, 90);
    }

    private void updateProgress() {
        gc.clearRect(0, 0, canvasWidth, textHeight);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + numColumns + " × " + numRows + " = " + (numColumns * numRows),
                canvasWidth / 2, 30);
        gc.fillText("Cubes drawn: " + cubesDrawn + " / " + totalCubes, canvasWidth / 2, 60);
        gc.fillText("Rows completed: " + rowsCompleted + " / " + numRows, canvasWidth / 2, 90);
    }

    private void updateRowCompletion() {
        gc.setFill(Color.DARKGREEN);
        gc.setFont(Font.font("Arial", 18));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Row " + rowsCompleted + " completed!", canvasWidth / 2, 120);
    }
}
