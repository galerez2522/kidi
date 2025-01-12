import javafx.animation.AnimationTimer;

public class DivisionAnimator {
    private final DivisionBoard board;
    private static final long FRAME_DELAY = 1_000_000_000; // 1 second delay
    private AnimationTimer timer;
    private boolean isPaused = false; // Pause state

    public DivisionAnimator(DivisionBoard board) {
        this.board = board;
    }

    public void start() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (isPaused) return; // Skip updates if paused
                if (now - lastUpdate >= FRAME_DELAY) {
                    if (!board.distributeNextCube()) {
                        stop(); // Stop the animation when all cubes are distributed
                    }
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }
}
