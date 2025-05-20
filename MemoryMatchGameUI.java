import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMatchGameUI extends JFrame {
    private JButton[] buttons = new JButton[10];
    private ImageIcon[] icons = new ImageIcon[5];
    private int[] cardValues = new int[10];
    private boolean[] matched = new boolean[10];
    private int firstCard = -1;
    private int secondCard = -1;
    private boolean isBusy = false;

    public MemoryMatchGameUI() {
        setTitle("Memory Match Game - Dark Theme");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 5, 10, 10));
        panel.setBackground(new Color(30, 30, 30)); // dark background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        // Load original icons (no scaling here)
        for (int i = 1; i <= 5; i++) {
            icons[i - 1] = new ImageIcon("src/images/img" + i + ".png");
        }

        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tempList.add(i);
            tempList.add(i);
        }
        Collections.shuffle(tempList);
        for (int i = 0; i < 10; i++) {
            cardValues[i] = tempList.get(i);
        }

        for (int i = 0; i < 10; i++) {
            buttons[i] = new JButton();
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(new Color(60, 63, 65));
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            buttons[i].setOpaque(true);
            buttons[i].setContentAreaFilled(true);
            final int index = i;

            buttons[i].addActionListener(e -> handleCardClick(index));
            panel.add(buttons[i]);
        }

        // Resize listener to rescale icons dynamically
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                for (int i = 0; i < buttons.length; i++) {
                    if (matched[i] || i == firstCard || i == secondCard) {
                        setButtonIcon(buttons[i], cardValues[i]);
                    }
                }
            }
        });

        setVisible(true);
    }

    private void setButtonIcon(JButton button, int iconIndex) {
        int w = button.getWidth();
        int h = button.getHeight();

        if (w > 0 && h > 0) {
            Image original = icons[iconIndex].getImage();
            Image scaled = original.getScaledInstance(w - 10, h - 10, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaled));
        } else {
            button.setIcon(icons[iconIndex]);
        }
    }

    private void handleCardClick(int index) {
        if (isBusy || matched[index] || index == firstCard || index == secondCard)
            return;

        setButtonIcon(buttons[index], cardValues[index]);

        if (firstCard == -1) {
            firstCard = index;
        } else {
            secondCard = index;
            isBusy = true;

            Timer timer = new Timer(800, e -> {
                if (cardValues[firstCard] == cardValues[secondCard]) {
                    matched[firstCard] = true;
                    matched[secondCard] = true;
                } else {
                    buttons[firstCard].setIcon(null);
                    buttons[secondCard].setIcon(null);
                }

                firstCard = -1;
                secondCard = -1;
                isBusy = false;
                checkWin();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void checkWin() {
        for (boolean b : matched) {
            if (!b) return;
        }
        JOptionPane.showMessageDialog(this, "Congratulations! You matched all the cards!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryMatchGameUI::new);
    }
}