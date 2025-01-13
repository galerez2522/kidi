import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MultiplicationBoard {
    private final GraphicsContext gc;
    private final Pane root;
    private final int canvasWidth;
    private final int canvasHeight;
    private final int numRows;
    private final int numColumns;
    private final int totalCubes;
    private final int cubeSize = 22; // Cube size
    private final int textHeight = 150;
    private final int rowSpacing = 15; // Spacing between rows
    private final int cubeSpacing = 6; // Spacing between cubes
    private final int startYOffset = textHeight + 20; // Vertical offset
    private final int horizontalCenter;

    private int currentRow = 0;
    private int currentColumn = 0;
    private int cubesDrawn = 0;
    private int rowsCompleted = 0;

    private Color[][] cubeColors; // 2D array to store cube colors
    private Runnable onReturnToQuiz;

    public MultiplicationBoard(GraphicsContext gc, Pane root, int canvasWidth, int canvasHeight, int numRows, int numColumns) {
        this.gc = gc;
        this.root = root;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.totalCubes = numRows * numColumns;
        this.horizontalCenter = (canvasWidth - (numColumns * (cubeSize + cubeSpacing))) / 2;

        this.cubeColors = new Color[numRows][numColumns];

        drawBackground();
        drawInitialText();
        addReturnToQuizButton();
    }

    public void setOnReturnToQuiz(Runnable onReturnToQuiz) {
        this.onReturnToQuiz = onReturnToQuiz;
    }

    public boolean drawNextCube() {
        if (currentRow < numRows) {
            int x = horizontalCenter + currentColumn * (cubeSize + cubeSpacing);
            int y = startYOffset + currentRow * (cubeSize + cubeSpacing + rowSpacing);

            if (cubeColors[currentRow][currentColumn] == null) {
                cubeColors[currentRow][currentColumn] = Color.color(Math.random(), Math.random(), Math.random());
            }
            Color cubeColor = cubeColors[currentRow][currentColumn];

            gc.setFill(cubeColor);
            gc.fillRect(x, y, cubeSize, cubeSize);

            cubesDrawn++;
            updateProgress();

            currentColumn++;
            if (currentColumn >= numColumns) {
                currentColumn = 0;
                currentRow++;
                rowsCompleted++;
                updateRowCompletion(rowsCompleted - 1);
            }
            if (cubesDrawn == totalCubes) {
                displayFinalMessage();
            }
            return true;
        }
        return false;
    }

    private void updateRowCompletion(int completedRow) {
        int y = startYOffset + completedRow * (cubeSize + cubeSpacing + rowSpacing);
        int ellipseX = horizontalCenter - 5;
        int ellipseWidth = numColumns * (cubeSize + cubeSpacing) + 10;
        int ellipseHeight = cubeSize + 10;

        // Draw the ellipse after the row is completed
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(1000), e -> drawEllipseAroundRow(ellipseX, y, ellipseWidth, ellipseHeight))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void drawEllipseAroundRow(int ellipseX, int y, int ellipseWidth, int ellipseHeight) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeOval(ellipseX, y - 5, ellipseWidth, ellipseHeight);
    }

    private void displayFinalMessage() {
        int messageY = canvasHeight - 100; // Position for the message

        // Draw a background rectangle for the message
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, messageY - 30, canvasWidth, 80);

        // Display the final message
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Futura", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Multiplication Completed!", canvasWidth / 2, messageY);
        gc.fillText("Total Rows: " + numRows + ", Total Columns: " + numColumns, canvasWidth / 2, messageY + 20);
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
        gc.clearRect(0, 0, canvasWidth, textHeight);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + numRows + " × " + numColumns + " = " + (numColumns * numRows),
                canvasWidth / 2, 30);
        gc.fillText("Cubes drawn: " + cubesDrawn + " / " + totalCubes, canvasWidth / 2, 70);
        gc.fillText("Rows completed: " + rowsCompleted + " / " + numRows, canvasWidth / 2, 110);
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

        returnButton.setOnAction(event -> {
            if (onReturnToQuiz != null) {
                onReturnToQuiz.run();
            }
        });

        root.getChildren().add(returnButton);
    }
}
