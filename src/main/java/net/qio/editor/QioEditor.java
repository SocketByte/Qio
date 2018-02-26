package net.qio.editor;

import net.qio.lang.Qio;

import javax.swing.*;

public class QioEditor {

    public static void main(String[] args) {
        new Qio();

        SwingUtilities.invokeLater(() -> new QioFrame().setVisible(true));
    }

}
