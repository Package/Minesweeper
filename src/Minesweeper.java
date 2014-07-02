import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Copyright (C) <2014>
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE
 * SOFTWARE.
 */
public class Minesweeper {

    /* The game frame */
    protected JFrame frame;

    /* The graphics panel */
    protected GridInterface gui;

    /* Construct the window to hold a new game of minesweeper */
    public Minesweeper() {
        frame = new JFrame("Minesweeper");
        frame.setSize(460, 480);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (confirmLeave())
                    System.exit(1);
            }
        });
        frame.setContentPane(gui = new GridInterface());
        frame.setVisible(true);
    }

    /**
     * Prompt for confirmation that you want to quit
     *
     * @return - a boolean representing if you want to quit
     */
    private boolean confirmLeave() {
        int option = JOptionPane.showConfirmDialog(Minesweeper.this.frame,
                "Are you sure you want to leave? You will lose your game progress.",
                "Minesweeper", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    /**
     * Application entry point
     *
     * @param args
     *         - command line args
     */
    public static void main(String[] args) {
        new Minesweeper();
    }
}
