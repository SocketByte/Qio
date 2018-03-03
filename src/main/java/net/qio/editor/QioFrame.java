package net.qio.editor;

import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rsyntaxtextarea.templates.CodeTemplate;
import org.fife.ui.rsyntaxtextarea.templates.StaticCodeTemplate;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class QioFrame extends JFrame implements ActionListener {

    private static RSyntaxTextArea textArea;

    public static RSyntaxTextArea getTextArea() {
        return textArea;
    }

    public QioFrame() {
        JPanel panel = new JPanel(new BorderLayout());

        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);

        RTextScrollPane sp = new RTextScrollPane(textArea);
        panel.add(sp);

        // Apply basic settings
        applySyntax();
        applyTheme();
        applyFont();
        applyTemplates();

        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Run");
        mb.add(menu);
        JMenuItem compile = new JMenuItem(
                "Run 'Qio' Program");
        compile.setActionCommand("Compile");
        compile.addActionListener(this);
        menu.add(compile);
        setJMenuBar(mb);

        setContentPane(panel);
        setTitle("Qio Editor 2.8");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void applyTemplates() {
        RSyntaxTextArea.setTemplatesEnabled(true);

        CodeTemplateManager manager = RSyntaxTextArea.getCodeTemplateManager();
        CodeTemplate template = new StaticCodeTemplate("VARIABLE", "PUSH 'value' INTO variable", null);
        manager.addTemplate(template);
    }

    private void applySyntax() {
        AbstractTokenMakerFactory token = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
        token.putMapping("text/qio", "net.qio.editor.QioToken");
        textArea.setSyntaxEditingStyle("text/qio");
    }

    private void applyTheme() {
        try {
            Theme theme = Theme.load(getClass().getClassLoader().getResourceAsStream(
                    "theme.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void applyFont() {
        Font font = new Font("Consolas", Font.PLAIN, 14);
        SyntaxScheme ss = textArea.getSyntaxScheme();
        ss = (SyntaxScheme) ss.clone();
        for (int i = 0; i < ss.getStyleCount(); i++) {
            if (ss.getStyle(i) != null) {
                ss.getStyle(i).font = font;
            }
        }
        textArea.setSyntaxScheme(ss);
        textArea.setFont(font);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Compile")) {
            String[] lines = textArea.getText().split("\n");

            QioInterpreter.interpret(lines);
        }
    }
}
