import javafx.animation.AnimationTimer;

public class DivisionAnimator {
    private final DivisionBoard board;
    private static final long FRAME_DELAY = 1_000_000_000; // 1 second delay
    private AnimationTimer timer;

    public DivisionAnimator(DivisionBoard board) {
        this.board = board;
    }

    public void start() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= FRAME_DELAY) {
                    if (!board.distributeNextCube()) {
                        timer.stop(); // Stop the animation when all cubes are distributed
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }
}
