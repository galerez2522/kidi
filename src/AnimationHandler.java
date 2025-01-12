import javafx.stage.Stage;

public class AnimationHandler {
    private final String question;
    private final int correctAnswer;

    public AnimationHandler(String question, int correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public void startAnimation(Stage primaryStage, Runnable onComplete) {
        try {
            // Extract numbers from the question
            int[] numbers = extractNumbersFromQuestion(question);
    
            int num1 = numbers[0];
            int num2 = numbers[1];
    
            // Determine the type of question and play the appropriate animation
            if (question.contains("×")) {
                MultiplicationCanvasAnimation animation = new MultiplicationCanvasAnimation(num1, num2);
                animation.playAnimation(primaryStage, onComplete);
            } else if (question.contains("÷")) {
                DivisionCanvasAnimation animation = new DivisionCanvasAnimation(num1, num2);
                animation.playAnimation(primaryStage, onComplete);
            } else {
                System.out.println("Error: Unsupported question format.");
            }
        } catch (Exception e) {
            System.out.println("Error: Unable to parse question. Details: " + e.getMessage());
        }
    }
    
    // Helper method to extract numbers from the question
    private int[] extractNumbersFromQuestion(String question) {
        String[] parts;
        if (question.contains("×")) {
            parts = question.split(" × ");
        } else if (question.contains("÷")) {
            parts = question.split(" ÷ ");
        } else {
            throw new IllegalArgumentException("Invalid question format.");
        }
    
        // Parse the numbers
        int num1 = Integer.parseInt(parts[0].trim());
        int num2 = Integer.parseInt(parts[1].trim());
    
        return new int[]{num1, num2};
    }
}