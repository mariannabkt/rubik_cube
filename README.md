# Rubik Cube Solver

## Overview
The Rubik Cube Solver is a graphical application developed in Java. It simulates a Rubik's Cube and employs the A* algorithm to find optimal solutions for reaching a specified goal state. Users can manipulate the cube and visualize the solving process in real-time.

## Features
- **Interactive UI:** Intuitive controls for rotating the Rubik's Cube.
- **A* Algorithm:** Utilizes the A* search algorithm for efficient pathfinding.
- **Customizable Goal State:** Users can specify K (1 to 6), representing the number of sides that must be a uniform color for the cube to be considered solved.
- **Example Execution:** Demonstrates various initial configurations and the solutions found for different values of K.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/mariannabkt/rubik_cube.git
2. Navigate to the project directory:
   ```bash
   cd rubik_cube
3. Compile and run the application using your preferred Java IDE or build tool.

### Usage
- Start the application and interact with the Rubik's Cube using the provided controls.
- Specify the value of K when prompted to define how many sides should be a single color for the cube to be considered solved.
- Observe the A* algorithm as it calculates the optimal sequence of moves to achieve the target state.
