# Space Racer Game

Space Racer is a simple Java game where players control spaceships to avoid obstacles and compete against each other.

## Overview

Space Racer is a 2D game developed in Java using the Swing library. The game features spaceships, balls, and various screens for title, mode selection, gameplay, and ending screens. Players can choose between time-based or score-based gameplay.

## Game Features

- Two-player mode
- Time-based and score-based gameplay options
- Obstacle avoidance gameplay with spaceships and balls
- Title and mode selection screens
- Ending screens for different game outcomes

## Getting Started

### Prerequisites
- Java Development Kit (JDK)
- IDE (Eclipse, IntelliJ, etc.) - Optional but recommended

### Game Controls
- Player 1:
  - Arrow keys for movement
- Player 2:
  - W, A, S, D keys for movement

### Common controls:
'R' key to restart the game (on the ending screen)
'ESC' key to exit the game (on the ending screen)

### Game Architecture
- GameFrame: Main JFrame for the game window.
- GamePanel: JPanel for rendering the game and handling game logic.
- Spaceship: Class representing the player's spaceship.
- Balls: Class representing the balls or obstacles in the game.
- Time: Class representing the time frame in time-based gameplay.
- TitleScreen, ModeScreen, EndScreen: Classes for different game screens.

### How to Run
```
cd src
javac SpaceRace.java
java SpaceRace
```
