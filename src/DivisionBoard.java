import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.Pane;

public class DivisionBoard {
    private final GraphicsContext gc;
    private final Pane root;
    private final int canvasWidth;
    private final int canvasHeight;
    private final int dividend;
    private final int divisor;
    private final int quotient;
    private final int cubeSize = 30;
    private final int textHeight = 150;
    private final int startYOffset = textHeight + 20;

    private int currentGroup = 0;
    private int cubesInCurrentGroup = 0;
    private int totalCubesDistributed = 0;
    private int quotientCounter = 0;

    private boolean animationComplete = false;
    private Runnable onReturnToQuiz;

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
        addReturnToQuizButton();
    }

    public void setOnReturnToQuiz(Runnable onReturnToQuiz) {
        this.onReturnToQuiz = onReturnToQuiz;
    }

    public boolean distributeNextCube() {
        if (totalCubesDistributed < dividend) {
            int groupXStart = (canvasWidth - (divisor * (cubeSize + 10))) / 2;
            int x = groupXStart + currentGroup * (cubeSize + 10);
            int y = startYOffset + cubesInCurrentGroup * (cubeSize + 5);

            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            gc.fillRect(x, y, cubeSize, cubeSize);

            totalCubesDistributed++;
            cubesInCurrentGroup++;

            if (cubesInCurrentGroup >= quotient) {
                drawEllipseAroundGroup(currentGroup);
                cubesInCurrentGroup = 0;
                currentGroup++;
                quotientCounter++;
                updateQuotientCounter();
            }

            updateProgress();
            return true;
        } else if (!animationComplete) {
            animationComplete = true;
            displayFinalMessage();
        }
        return false;
    }

    private void drawEllipseAroundGroup(int groupIndex) {
        int groupXStart = (canvasWidth - (divisor * (cubeSize + 10))) / 2;
        int x = groupXStart + groupIndex * (cubeSize + 10);
        int yStart = startYOffset;
        int groupHeight = quotient * (cubeSize + 5);

        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        gc.strokeOval(x - 5, yStart - 5, cubeSize + 10, groupHeight + 10);
    }

    private void displayFinalMessage() {
        int messageY = canvasHeight - 150;

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, messageY - 30, canvasWidth, 80);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 20));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("There are " + quotient + " cubes in each group.", canvasWidth / 2, messageY);
        gc.fillText("Number of groups: " + divisor, canvasWidth / 2, messageY + 20);
    }

    private void drawInitialText() {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + dividend + " ÷ " + divisor + " = ?", canvasWidth / 2, 30);
        gc.fillText("Cubes distributed: 0 / " + dividend, canvasWidth / 2, 70);
        gc.fillText("Quotient counter: 0", canvasWidth / 2, 110);
    }

    private void updateProgress() {
        gc.clearRect(0, 0, canvasWidth, textHeight);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Illustrating: " + dividend + " ÷ " + divisor + " = " + quotient, canvasWidth / 2, 30);
        gc.fillText("Cubes distributed: " + totalCubesDistributed + " / " + dividend, canvasWidth / 2, 70);
        gc.fillText("Quotient counter: " + quotientCounter, canvasWidth / 2, 110);
    }

    private void updateQuotientCounter() {
        gc.setFill(Color.DARKGREEN);
        gc.setFont(Font.font("Futura", 30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Quotient counter: " + quotientCounter, canvasWidth / 2, 90);
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
            System.out.println("Return to Quiz button clicked!");
            if (onReturnToQuiz != null) {
                onReturnToQuiz.run();
            }
        });

        root.getChildren().add(returnButton);
    }
}
