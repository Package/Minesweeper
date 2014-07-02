import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class IntroInterface extends JPanel {

    /* A reference to the game */
    protected Minesweeper game;

    /* Width and height of the game board in tiles */
    private JSpinner boardWidth, boardHeight;

    /* Number of mines to use in the game */
    private JSpinner numMines;

    public IntroInterface(final Minesweeper game) {
        this.game = game;

        setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                "Game settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(63, 100, 253, 150);
        add(panel);

        JLabel title = new JLabel("Minesweeper in Java");
        title.setFont(new Font("sans-serif", Font.BOLD, 30));
        title.setBounds(40, 34, 377, 44);
        add(title);

        JLabel widthLabel = new JLabel("Board width in tiles:");
        widthLabel.setBounds(30, 31, 119, 14);
        panel.add(widthLabel);

        boardWidth = new JSpinner();
        boardWidth.setModel(new SpinnerNumberModel(9, null, null, 1));
        boardWidth.setBounds(159, 29, 71, 18);
        panel.add(boardWidth);

        JLabel heightLabel = new JLabel("Board height in tiles:");
        heightLabel.setBounds(30, 58, 119, 14);
        panel.add(heightLabel);

        boardHeight = new JSpinner();
        boardHeight.setModel(new SpinnerNumberModel(9, null, null, 1));
        boardHeight.setBounds(159, 56, 71, 18);
        panel.add(boardHeight);

        numMines = new JSpinner();
        numMines.setModel(new SpinnerNumberModel(10, null, null, 1));
        numMines.setBounds(159, 83, 71, 18);
        panel.add(numMines);

        JLabel minesLabel = new JLabel("Number of mines:");
        minesLabel.setBounds(30, 85, 123, 14);
        panel.add(minesLabel);

        JButton button = new JButton("Start");
        button.setBounds(82, 116, 91, 23);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int height = Math.max((Integer) boardHeight.getValue(), 9);
                int width = Math.max((Integer) boardWidth.getValue(), 9);
                int mines = Math.max((Integer) numMines.getValue(), 10);
                game.settings(width, height, mines);
            }
        });
    }
}
