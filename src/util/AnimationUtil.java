package util;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * util.AnimationUtil
 * ------------------
 * Small, reusable JavaFX animations used across the app to make interactions
 * feel more alive: content transitions, button feedback, hover effects, and
 * an error shake. Kept framework-agnostic (plain Node) so any Controller can
 * call these on any control without extra wiring.
 */
public final class AnimationUtil {

    private AnimationUtil() {}

    /** Fades a node in from fully transparent while gently sliding it up a few pixels. */
    public static void fadeInSlideUp(Node node, double durationMillis) {
        node.setOpacity(0);
        node.setTranslateY(14);

        FadeTransition fade = new FadeTransition(Duration.millis(durationMillis), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(durationMillis), node);
        slide.setFromY(14);
        slide.setToY(0);

        new ParallelTransition(fade, slide).play();
    }

    /** Simple fade-in, no movement — good for the login card on first launch. */
    public static void fadeIn(Node node, double durationMillis) {
        node.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(durationMillis), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    /** Horizontal shake — use for invalid login / validation errors. */
    public static void shake(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(55), node);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.setOnFinished(e -> node.setTranslateX(0));
        tt.play();
    }

    /** Quick "press" feedback — shrink then bounce back. Great on primary action buttons. */
    public static void pulse(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(90), node);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(0.93);
        st.setToY(0.93);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    /** Attaches a smooth grow-on-hover / shrink-on-exit effect (used for sidebar buttons). */
    public static void attachHoverScale(Node node, double hoverScale) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(120), node);
        grow.setToX(hoverScale);
        grow.setToY(hoverScale);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(120), node);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        node.setOnMouseEntered(e -> { shrink.stop(); grow.playFromStart(); });
        node.setOnMouseExited(e -> { grow.stop(); shrink.playFromStart(); });
    }
}
