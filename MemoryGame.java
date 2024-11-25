import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MemoryGame extends JFrame {

    private int gridSize;
    private int rows;
    private int cols;
    private int timeLimit;
    private ArrayList<String> tileValues;
    private JButton[][] buttons;
    private String firstSelected = null;
    private JButton firstButton = null;
    private int matchedPairs = 0;
    private Timer gameTimer;
    private int timeRemaining;
    private JLabel timerLabel, scoreLabel;
    private int score = 0;
    private ArrayList<Timer> flipTimers = new ArrayList<>();
    private JButton hintButton; // Hint button
    private JButton helpButton; // Help button

    public MemoryGame(int gridSize, int timeLimit) {
        this.gridSize = gridSize;
        this.timeLimit = timeLimit;

        calculateGridDimensions();
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 800);

        buttons = new JButton[rows][cols];
        initializeTiles();

        // Top panel for timer, score, hint, and help buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 6));
        timerLabel = new JLabel("Time Remaining: " + timeLimit + "s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> gameTimer.stop());
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> resetGame());

        // Hint Button
        hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> revealHint());

        // Help Button
        helpButton = new JButton("Help");
        helpButton.addActionListener(e -> showHelp());

        topPanel.add(timerLabel);
        topPanel.add(scoreLabel);
        topPanel.add(pauseButton);
        topPanel.add(restartButton);
        topPanel.add(hintButton);
        topPanel.add(helpButton);

        add(topPanel, BorderLayout.NORTH);

        // Game board panel
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, cols));
        createBoard(gamePanel);
        add(gamePanel, BorderLayout.CENTER);

        startTimer();
        setVisible(true);
    }

    private void calculateGridDimensions() {
        for (int i = (int) Math.sqrt(gridSize); i > 0; i--) {
            if (gridSize % i == 0) {
                rows = i;
                cols = gridSize / i;
                break;
            }
        }
    }

    private void initializeTiles() {
        tileValues = new ArrayList<>();
        int totalTiles = rows * cols;

        for (int i = 1; i <= totalTiles / 2; i++) {
            tileValues.add(String.valueOf(i));
            tileValues.add(String.valueOf(i));
        }
        Collections.shuffle(tileValues);
    }

    private void createBoard(JPanel panel) {
        int index = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton button = new JButton("X");
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLUE);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

                button.addActionListener(new TileClickListener(row, col));
                buttons[row][col] = button;

                String value = tileValues.get(index++);
                button.putClientProperty("value", value);
                panel.add(button);
            }
        }
    }

    private void startTimer() {
        timeRemaining = timeLimit;

        gameTimer = new Timer(1000, e -> {
            timeRemaining--;
            timerLabel.setText("Time Remaining: " + timeRemaining + "s");

            if (timeRemaining == 0) {
                playSound("C:/Users/H.P/OneDrive/Desktop/images/sound/timeup.wav");
                gameTimer.stop();
                JOptionPane.showMessageDialog(null, "Time's up! Your score: " + score);
                resetGame();
            }
        });

        gameTimer.start();
    }

    private class TileClickListener implements ActionListener {
        private final int row;
        private final int col;

        public TileClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = buttons[row][col];
            String value = (String) button.getClientProperty("value");

            if (button == null || !button.getText().equals("X")) {
                return;
            }

            if (firstSelected == null) {
                revealTile(button, value);
                firstSelected = value;
                firstButton = button;
            } else {
                revealTile(button, value);

                if (value.equals(firstSelected)) {
                    playSound("C:/Users/H.P/OneDrive/Desktop/images/sound/correct.wav");
                    score += 10;
                    scoreLabel.setText("Score: " + score);
                    matchedPairs++;
                    firstSelected = null;
                    firstButton = null;

                    if (matchedPairs == (rows * cols) / 2) {
                        gameTimer.stop();
                        JOptionPane.showMessageDialog(null, "Congratulations! You matched all tiles! Your score: " + score);
                        resetGame();
                    }
                } else {
                    playSound("C:/Users/H.P/OneDrive/Desktop/images/sound/failure.wav");
                    score -= 5;
                    scoreLabel.setText("Score: " + score);
                    Timer flipBackTimer = new Timer(1000, ev -> {
                        hideTile(button);
                        hideTile(firstButton);
                        firstSelected = null;
                        firstButton = null;
                    });
                    flipBackTimer.setRepeats(false);
                    flipTimers.add(flipBackTimer);
                    flipBackTimer.start();
                }
            }
        }
    }

    private void revealTile(JButton button, String value) {
        if (button != null) {
            button.setText(value);
        }
    }

    private void hideTile(JButton button) {
        if (button != null) {
            button.setText("X");
        }
    }

    private void resetGame() {
        gameTimer.stop();
        matchedPairs = 0;
        firstSelected = null;
        firstButton = null;
        score = 0;
        scoreLabel.setText("Score: 0");
        tileValues.clear();
        initializeTiles();

        for (Timer timer : flipTimers) {
            timer.stop();
        }
        flipTimers.clear();

        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                buttons[row][col].setText("X");
                buttons[row][col].putClientProperty("value", tileValues.get(index++));
            }
        }

        timeRemaining = timeLimit;
        timerLabel.setText("Time Remaining: " + timeLimit + "s");
        gameTimer.start();
    }

    private void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.err.println("Error playing sound: " + ex.getMessage());
        }
    }

    private void revealHint() {
        if (score >= 5) {
            ArrayList<JButton> unmatchedButtons = new ArrayList<>();
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    JButton button = buttons[row][col];
                    if (button.getText().equals("X")) {
                        unmatchedButtons.add(button);
                    }
                }
            }

            if (unmatchedButtons.size() >= 2) {
                Random rand = new Random();
                JButton firstHintButton = unmatchedButtons.get(rand.nextInt(unmatchedButtons.size()));
                JButton secondHintButton = unmatchedButtons.get(rand.nextInt(unmatchedButtons.size()));

                revealTile(firstHintButton, (String) firstHintButton.getClientProperty("value"));
                revealTile(secondHintButton, (String) secondHintButton.getClientProperty("value"));

                Timer hideHintTimer = new Timer(2000, ev -> {
                    hideTile(firstHintButton);
                    hideTile(secondHintButton);
                });
                hideHintTimer.setRepeats(false);
                hideHintTimer.start();

                score -= 5;
                scoreLabel.setText("Score: " + score);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You need at least 5 points to use a hint.");
        }
    }

    private void showHelp() {
        String message = """
            Welcome to the Memory Game!
            
            Rules:
            - Click on tiles to reveal their values.
            - Match pairs of tiles with the same value to score points.
            - Each correct match earns you 10 points.
            - Each incorrect match deducts 5 points.
            - Use the Hint button to reveal two tiles for 5 points.
            
            Controls:
            - Pause: Stop the timer temporarily.
            - Restart: Reset the game to the beginning.
            - Help: View this tutorial.
            
            Try to match all tiles before the timer runs out!
            Good luck!
        """;
        JOptionPane.showMessageDialog(this, message, "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose Difficulty Level:",
                "Difficulty Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        int gridSize;
        int timeLimit;

        switch (choice) {
            case 0: // Easy
                gridSize = 16; // 4x4
                timeLimit = 45;
                break;
            case 1: // Medium
                gridSize = 36; // 6x6
                timeLimit = 60;
                break;
            case 2: // Hard
                gridSize = 64; // 8x8
                timeLimit = 90;
                break;
            default:
                return;
        }

        SwingUtilities.invokeLater(() -> new MemoryGame(gridSize, timeLimit));
    }
}
