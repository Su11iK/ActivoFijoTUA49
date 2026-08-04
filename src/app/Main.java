package app;

import javax.swing.SwingUtilities;

import ui.theme.Theme;
import vista.Principal;

public class Main {
    public static void main(String[] args) {

        Theme.apply();

        SwingUtilities.invokeLater(() -> {

            new Principal().setVisible(true);

        });

    }
}