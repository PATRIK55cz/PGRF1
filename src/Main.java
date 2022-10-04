import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Canvas canvas = new Canvas();
        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> {
                SwingUtilities.invokeLater(() -> {
                    SwingUtilities.invokeLater(() -> {
                        canvas.start();
                    });
                });
            });
        });
    }
}