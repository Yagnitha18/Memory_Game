# Memory_Game


# Memory Game

A fun and interactive memory game built using Java Swing! The game challenges players to match pairs of tiles on a grid before the timer runs out. It supports multiple difficulty levels and includes features like scoring, hints, and sound effects.

The Memory Game program demonstrates a comprehensive application of core Java concepts such as object-oriented programming (OOP), event-driven programming, and graphical user interface (GUI) design. Key OOP principles, including encapsulation, abstraction, and polymorphism, are evident in the modular structure of the MemoryGame class, which encapsulates game logic and manages interactions. It leverages inheritance and polymorphism by extending Java's JFrame, JButton, and Timer classes, customizing their behavior. The program employs event-driven programming with ActionListener handling user interactions like flipping tiles, pausing, restarting, and using hints. The Swing framework is used to build the GUI, with components like JPanel, JLabel, and JOptionPane creating a responsive, user-friendly interface. Dynamic layouts with GridLayout enable adaptive grid dimensions based on difficulty levels. Collections such as ArrayList and algorithms like Collections.shuffle are used to manage and randomize tiles effectively. The program also demonstrates exception handling for robust sound playback functionality and incorporates audio feedback for user actions. Together, these concepts create an interactive, extensible, and engaging game application, showcasing practical problem-solving and software development skills.

## Features

- **Dynamic Grid Layout**:
  - Easy: 4x4 grid with 45 seconds
  - Medium: 6x6 grid with 60 seconds
  - Hard: 8x8 grid with 90 seconds
- **Interactive Gameplay**:
  - Match tiles to score points.
  - Incorrect matches deduct points.
  - Game ends when all tiles are matched or the timer runs out.
- **Game Controls**:
  - **Pause**: Pause the game timer.
  - **Restart**: Reset the game to start over.
  - **Hint**: Reveal two random tiles for a 5-point penalty.
  - **Help**: View game instructions.
- **Scoring System**:
  - +10 points for a correct match.
  - -5 points for an incorrect match.
- **Sound Effects**:
  - Plays different sounds for correct matches, incorrect matches, and when the timer runs out.

## How to Play

1. Launch the game and select a difficulty level (Easy, Medium, or Hard).
2. Click on tiles to reveal their values.
3. Match pairs of tiles with the same value to score points.
4. Use the **Hint** button to reveal two tiles temporarily (costs 5 points).
5. Complete all matches before the timer runs out to win the game!

## Requirements

- **Java Version**: Java 8 or later.
- **Sound Files**: Ensure the following sound files exist at the specified paths:
  - `timeup.wav`
  - `correct.wav`
  - `failure.wav`

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Yagnitha18/Memory_Game.git
   ```
2. Navigate to the project directory:
   ```bash
   cd memory-game
   ```
3. Compile the Java program:
   ```bash
   javac MemoryGame.java
   ```
4. Run the game:
   ```bash
   java MemoryGame
   ```

## File Structure

```
memory-game/
├── MemoryGame.java        # Main Java program
├── README.md              # Documentation file
├── sounds/                # Directory for sound files
│   ├── timeup.wav
│   ├── correct.wav
│   └── failure.wav
```

## Controls

- **Pause**: Stop the game timer temporarily.
- **Restart**: Reset the game to its initial state.
- **Hint**: Reveal two tiles for 5 points.
- **Help**: View the game instructions.

## Customization

- Modify the grid size and time limits in the difficulty selection:
  ```java
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
  ```

## License

This project is licensed under the MIT License. Feel free to modify and distribute as needed.

## Contact

For questions or suggestions, feel free to reach out at [pyagnitha@example.com].

---

**Enjoy the game and have fun testing your memory!**
