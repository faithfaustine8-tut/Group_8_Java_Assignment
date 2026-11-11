package services;

import models.NowShowing;

import javax.swing.Timer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Removes showings from the "now showing" list once they are over.
 * A showing is over when its start time plus the movie's duration has passed.
 * Only the showing is removed. The Movie stays in the movie list, so it can be
 * scheduled again later.
 *
 * Uses a Swing Timer, which runs on the same thread as the screens. That avoids
 * threading problems when the screens read the same list.
 */
public class ShowingCleanupService {

    private final List<NowShowing> showings;
    private final Runnable onChange; // called after showings were removed (may be null)
    private Timer timer;

    /**
     * @param showings the shared list of showings (must be a modifiable list such as ArrayList)
     * @param onChange code to run after something was removed, e.g. refreshing a screen; may be null
     */
    public ShowingCleanupService(List<NowShowing> showings, Runnable onChange) {
        this.showings = Objects.requireNonNull(showings, "Showing list cannot be null");
        this.onChange = onChange;
    }

    /** True if the showing finished before the given moment. */
    public static boolean hasEnded(NowShowing showing, LocalDateTime now) {
        LocalDateTime end = showing.getShowingTime().plus(showing.getMovie().getDuration());
        return end.isBefore(now);
    }

    /** Removes every ended showing and returns how many were removed. */
    public int removeEndedShowings() {
        LocalDateTime now = LocalDateTime.now();
        int before = showings.size();
        showings.removeIf(showing -> hasEnded(showing, now));
        return before - showings.size();
    }

    /** Cleans once straight away, then repeats every intervalSeconds. */
    public void start(int intervalSeconds) {
        if (intervalSeconds <= 0) {
            throw new IllegalArgumentException("Interval must be positive");
        }
        stop(); // never run two timers at once
        cleanAndNotify();
        timer = new Timer(intervalSeconds * 1000, event -> cleanAndNotify());
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    public boolean isRunning() {
        return timer != null && timer.isRunning();
    }

    private void cleanAndNotify() {
        int removed = removeEndedShowings();
        if (removed > 0 && onChange != null) {
            onChange.run();
        }
    }
}