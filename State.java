import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
import java.util.Random;

public class State implements Comparable<State>
{
	private String[][][] tiles;
	private int faces_num;

	//heuristic score
	private int score;

	private State father = null;


	State(int k, boolean randomized)
	{
		this.faces_num = k;
		this.tiles = new String[3][3][6];
		
		if(randomized) 
		{
			// Solved Rubik's Cube
			// White Side
			this.tiles[0][0][0] = "W";
			this.tiles[0][1][0] = "W";
			this.tiles[0][2][0] = "W";
			this.tiles[1][0][0] = "W";
			this.tiles[1][1][0] = "W";
			this.tiles[1][2][0] = "W";
			this.tiles[2][0][0] = "W";
			this.tiles[2][1][0] = "W";
			this.tiles[2][2][0] = "W";
			// Red Side
			this.tiles[0][0][1] = "R";
			this.tiles[0][1][1] = "R";
			this.tiles[0][2][1] = "R";
			this.tiles[1][0][1] = "R";
			this.tiles[1][1][1] = "R";
			this.tiles[1][2][1] = "R";
			this.tiles[2][0][1] = "R";
			this.tiles[2][1][1] = "R";
			this.tiles[2][2][1] = "R";
			// Yellow Side
			this.tiles[0][0][2] = "Y";
			this.tiles[0][1][2] = "Y";
			this.tiles[0][2][2] = "Y";
			this.tiles[1][0][2] = "Y";
			this.tiles[1][1][2] = "Y";
			this.tiles[1][2][2] = "Y";
			this.tiles[2][0][2] = "Y";
			this.tiles[2][1][2] = "Y";
			this.tiles[2][2][2] = "Y";
			// Orange Side
			this.tiles[0][0][3] = "O";
			this.tiles[0][1][3] = "O";
			this.tiles[0][2][3] = "O";
			this.tiles[1][0][3] = "O";
			this.tiles[1][1][3] = "O";
			this.tiles[1][2][3] = "O";
			this.tiles[2][0][3] = "O";
			this.tiles[2][1][3] = "O";
			this.tiles[2][2][3] = "O";
			// Green Side
			this.tiles[0][0][4] = "G";
			this.tiles[0][1][4] = "G";
			this.tiles[0][2][4] = "G";
			this.tiles[1][0][4] = "G";
			this.tiles[1][1][4] = "G";
			this.tiles[1][2][4] = "G";
			this.tiles[2][0][4] = "G";
			this.tiles[2][1][4] = "G";
			this.tiles[2][2][4] = "G";
			// Blue Side
			this.tiles[0][0][5] = "B";
			this.tiles[0][1][5] = "B";
			this.tiles[0][2][5] = "B";
			this.tiles[1][0][5] = "B";
			this.tiles[1][1][5] = "B";
			this.tiles[1][2][5] = "B";
			this.tiles[2][0][5] = "B";
			this.tiles[2][1][5] = "B";
			this.tiles[2][2][5] = "B";
			
			// Scramble the Cube
			Random rand = new Random();
			int new_rand;
			for (int i = 0; i < 20; i++)
			{
				new_rand = rand.nextInt(12);
				if(new_rand == 1)
				{
					this.moveUp_W(0);
				}
				else if(new_rand == 2)
				{
					this.moveUp_W(2);
				}
				else if(new_rand == 3)
				{
					this.moveDown_W(0);
				}
				else if(new_rand == 4)
				{
					this.moveDown_W(2);
				}
				else if(new_rand == 5)
				{
					this.moveLeft_W(0);
				}
				else if(new_rand == 6)
				{
					this.moveLeft_W(2);
				}
				else if(new_rand == 7)
				{
					this.moveRight_W(0);
				}
				else if(new_rand == 8)
				{
					this.moveRight_W(2);
				}
				else if(new_rand == 9)
				{
					this.moveLeft_G(0);
				}
				else if(new_rand == 10)
				{
					this.moveLeft_G(2);
				}
				else if(new_rand == 11)
				{
					this.moveRight_G(0);
				}
				else
				{
					this.moveRight_G(2);
				}
			}
			
		}
		else
		{
			// White Side
			this.tiles[0][0][0] = "Y";
			this.tiles[0][1][0] = "G";
			this.tiles[0][2][0] = "Y";
			this.tiles[1][0][0] = "Y";
			this.tiles[1][1][0] = "W";
			this.tiles[1][2][0] = "B";
			this.tiles[2][0][0] = "W";
			this.tiles[2][1][0] = "Y";
			this.tiles[2][2][0] = "G";
			// Red Side
			this.tiles[0][0][1] = "O";
			this.tiles[0][1][1] = "B";
			this.tiles[0][2][1] = "Y";
			this.tiles[1][0][1] = "R";
			this.tiles[1][1][1] = "R";
			this.tiles[1][2][1] = "O";
			this.tiles[2][0][1] = "Y";
			this.tiles[2][1][1] = "O";
			this.tiles[2][2][1] = "B";
			// Yellow Side
			this.tiles[0][0][2] = "O";
			this.tiles[0][1][2] = "G";
			this.tiles[0][2][2] = "W";
			this.tiles[1][0][2] = "R";
			this.tiles[1][1][2] = "Y";
			this.tiles[1][2][2] = "Y";
			this.tiles[2][0][2] = "O";
			this.tiles[2][1][2] = "W";
			this.tiles[2][2][2] = "G";
			// Orange Side
			this.tiles[0][0][3] = "W";
			this.tiles[0][1][3] = "G";
			this.tiles[0][2][3] = "R";
			this.tiles[1][0][3] = "O";
			this.tiles[1][1][3] = "O";
			this.tiles[1][2][3] = "O";
			this.tiles[2][0][3] = "B";
			this.tiles[2][1][3] = "R";
			this.tiles[2][2][3] = "B";
			// Green Side
			this.tiles[0][0][4] = "G";
			this.tiles[0][1][4] = "B";
			this.tiles[0][2][4] = "R";
			this.tiles[1][0][4] = "B";
			this.tiles[1][1][4] = "G";
			this.tiles[1][2][4] = "R";
			this.tiles[2][0][4] = "G";
			this.tiles[2][1][4] = "W";
			this.tiles[2][2][4] = "B";
			// Blue Side
			this.tiles[0][0][5] = "O";
			this.tiles[0][1][5] = "W";
			this.tiles[0][2][5] = "W";
			this.tiles[1][0][5] = "W";
			this.tiles[1][1][5] = "B";
			this.tiles[1][2][5] = "G";
			this.tiles[2][0][5] = "R";
			this.tiles[2][1][5] = "Y";
			this.tiles[2][2][5] = "R";
		}
	}
	
	// constructor for creating copy of the state.
	State(String[][][] tiles)
	{
		this.setTiles(tiles);
	}

	void print()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(int z = 0; z < 6; z++)
		{
			if(z == 0) 
			{
				System.out.println("White side");
			}
			else if(z == 1)
			{
				System.out.println("Red side");
			}
			else if(z == 2)
			{
				System.out.println("Yellow side");
			}
			else if(z == 3)
			{
				System.out.println("Orange side");
			}
			else if(z == 4)
			{
				System.out.println("Green side");
			}
			else
			{
				System.out.println("Blue side");
			}
			System.out.println("------------ \n");
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					System.out.print(this.tiles[i][j][z]);
					if(j < 2)
					{
						System.out.print('\t');
					}
				}
				System.out.println();
			}
			System.out.println();
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
    String[][][] getTiles() 
	{
        return this.tiles;
    }

	void setTiles(String[][][] tiles) 
	{
		this.tiles = new String[3][3][6];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				for(int z = 0; z < 6; z++)
				{
					this.tiles[i][j][z] = tiles[i][j][z];
				}
			}
		}
	}

	int getSidesNum() 
	{
		return this.faces_num;
	}

	void setSidesNum(int faces_num) 
	{
		this.faces_num = faces_num;
	}

	State getFather()
	{
		return this.father;
	}

	void setFather(State father)
	{
		this.father = father;
	}

	int getScore() 
	{
		return this.score;
	}

	void setScore(int score) 
	{
		this.score = score;
	}

	ArrayList<State> getChildren(int heuristic)
	{
		ArrayList<State> children = new ArrayList<>();
		
		// White Move Up Left Column
		State child = new State(this.tiles);
		child.moveUp_W(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Up Right Column
		child = new State(this.tiles);
		child.moveUp_W(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Down Left Column
		child = new State(this.tiles);
		child.moveDown_W(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Down Right Column
		child = new State(this.tiles);
		child.moveDown_W(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Left Up Row
		child = new State(this.tiles);
		child.moveLeft_W(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Left Down Row
		child = new State(this.tiles);
		child.moveLeft_W(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// White Move Right Up Row
		child = new State(this.tiles);
		child.moveRight_W(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// White Move Left Down Row
		child = new State(this.tiles);
		child.moveRight_W(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		// Green Move Up Left Column
		child = new State(this.tiles);
		child.moveLeft_G(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Up Right Column
		child = new State(this.tiles);
		child.moveLeft_G(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Down Left Column
		child = new State(this.tiles);
		child.moveRight_G(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Down Right Column
		child = new State(this.tiles);
		child.moveRight_G(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
		
		
		return children;
    }

	void moveUp_W(int col)
	{
		if(col != 0 || col != 2)
		{
			return;
		}
		// save White side
		String temp_tile_0 = this.tiles[0][col][0];
		String temp_tile_1 = this.tiles[1][col][0];
		String temp_tile_2 = this.tiles[2][col][0];
		
		// move Red side to White side
		this.tiles[0][col][0] = this.tiles[0][col][1];
		this.tiles[1][col][0] = this.tiles[1][col][1];
		this.tiles[2][col][0] = this.tiles[2][col][1];
		
		// move Yellow side to Red side
		this.tiles[0][col][1] = this.tiles[0][col][2];
		this.tiles[1][col][1] = this.tiles[1][col][2];
		this.tiles[2][col][1] = this.tiles[2][col][2];
		
		// move Orange side to Yellow side
		this.tiles[0][col][2] = this.tiles[0][col][3];
		this.tiles[1][col][2] = this.tiles[1][col][3];
		this.tiles[2][col][2] = this.tiles[2][col][3];		
				
		// move White side to Orange side
		this.tiles[0][col][3] = temp_tile_0;
		this.tiles[1][col][3] = temp_tile_1;
		this.tiles[2][col][3] = temp_tile_2;
		
		// turn side faces
		if(col == 0)
		{
			this.turnFaceLeft(4);
		}
		else
		{
			this.turnFaceRight(5);
		}
	}

	void moveDown_W(int col)
	{
		if(col != 0 || col != 2)
		{
			return;
		}
		// save White side
		String temp_tile_0 = this.tiles[0][col][0];
		String temp_tile_1 = this.tiles[1][col][0];
		String temp_tile_2 = this.tiles[2][col][0];
		
		// move Orange side to White side
		this.tiles[0][col][0] = this.tiles[0][col][3];
		this.tiles[1][col][0] = this.tiles[1][col][3];
		this.tiles[2][col][0] = this.tiles[2][col][3];
		
		// move Yellow side to Orange side
		this.tiles[0][col][3] = this.tiles[0][col][2];
		this.tiles[1][col][3] = this.tiles[1][col][2];
		this.tiles[2][col][3] = this.tiles[2][col][2];
		
		// move Red side to Yellow side
		this.tiles[0][col][2] = this.tiles[0][col][1];
		this.tiles[1][col][2] = this.tiles[1][col][1];
		this.tiles[2][col][2] = this.tiles[2][col][1];		
				
		// move White side to Red side
		this.tiles[0][col][1] = temp_tile_0;
		this.tiles[1][col][1] = temp_tile_1;
		this.tiles[2][col][1] = temp_tile_2;
		
		// turn side faces
		if(col == 0)
		{
			this.turnFaceRight(4);
		}
		else
		{
			this.turnFaceLeft(5);
		}
	}

	void moveLeft_W(int row)
	{
		if(row != 0 || row != 2)
		{
			return;
		}
		
		int temp = (row == 0) ? 2 : 0;
		
		// save White side
		String temp_tile_0 = this.tiles[row][0][0];
		String temp_tile_1 = this.tiles[row][1][0];
		String temp_tile_2 = this.tiles[row][2][0];
		
		// move Blue side to White side
		this.tiles[row][0][0] = this.tiles[0][temp][5];
		this.tiles[row][1][0] = this.tiles[1][temp][5];
		this.tiles[row][2][0] = this.tiles[2][temp][5];
		
		// move Yellow side to Blue side
		this.tiles[0][temp][5] = this.tiles[temp][2][2];
		this.tiles[1][temp][5] = this.tiles[temp][1][2];
		this.tiles[2][temp][5] = this.tiles[temp][0][2];
		
		// move Green side to Yellow side
		this.tiles[temp][2][2] = this.tiles[2][row][4];
		this.tiles[temp][1][2] = this.tiles[1][row][4];
		this.tiles[temp][0][2] = this.tiles[0][row][4];		
				
		// move White side to Green side
		this.tiles[2][row][4] = temp_tile_0;
		this.tiles[1][row][4] = temp_tile_1;
		this.tiles[0][row][4] = temp_tile_2;
		
		// turn side faces
		if(row == 0)
		{
			this.turnFaceRight(3);
		}
		else
		{
			this.turnFaceLeft(1);
		}
	}

    void moveRight_W(int row)
    {
		if(row != 0 || row != 2)
		{
			return;
		}
		
		int temp = (row == 0) ? 2 : 0;
		
		// save White side
		String temp_tile_0 = this.tiles[row][0][0];
		String temp_tile_1 = this.tiles[row][1][0];
		String temp_tile_2 = this.tiles[row][2][0];
		
		// move Green side to White side
		this.tiles[row][0][0] = this.tiles[2][row][4];
		this.tiles[row][1][0] = this.tiles[1][row][4];
		this.tiles[row][2][0] = this.tiles[0][row][4];
		
		// move Yellow side to Green side
		this.tiles[2][row][4] = this.tiles[temp][2][2];
		this.tiles[1][row][4] = this.tiles[temp][1][2];
		this.tiles[0][row][4] = this.tiles[temp][0][2];
		
		// move Blue side to Yellow side
		this.tiles[temp][2][2] = this.tiles[0][temp][5];
		this.tiles[temp][1][2] = this.tiles[1][temp][5];
		this.tiles[temp][0][2] = this.tiles[2][temp][5];		
				
		// move White side to Blue side
		this.tiles[0][temp][5] = temp_tile_0;
		this.tiles[1][temp][5] = temp_tile_1;
		this.tiles[2][temp][5] = temp_tile_2;
		
		// turn side faces
		if(row == 0)
		{
			this.turnFaceLeft(3);
		}
		else
		{
			this.turnFaceRight(1);
		}
    }
    
	void moveLeft_G(int row)
	{
		if(row != 0 || row != 2)
		{
			return;
		}
		
		int temp = (row == 0) ? 2 : 0;
		
		// save Green side
		String temp_tile_0 = this.tiles[row][0][4];
		String temp_tile_1 = this.tiles[row][1][4];
		String temp_tile_2 = this.tiles[row][2][4];
		
		// move Red side to Green side
		this.tiles[row][0][4] = this.tiles[row][0][1];
		this.tiles[row][1][4] = this.tiles[row][1][1];
		this.tiles[row][2][4] = this.tiles[row][2][1];
		
		// move Blue side to Red side
		this.tiles[row][0][1] = this.tiles[row][0][5];
		this.tiles[row][1][1] = this.tiles[row][1][5];
		this.tiles[row][2][1] = this.tiles[row][2][5];
		
		// move Orange side to Blue side
		this.tiles[row][0][5] = this.tiles[temp][2][3];
		this.tiles[row][1][5] = this.tiles[temp][1][3];
		this.tiles[row][2][5] = this.tiles[temp][0][3];		
				
		// move Green side to Orange side
		this.tiles[temp][2][3] = temp_tile_0;
		this.tiles[temp][1][3] = temp_tile_1;
		this.tiles[temp][0][3] = temp_tile_2;
		
		// turn side faces
		if(row == 0)
		{
			this.turnFaceRight(0);
		}
		else
		{
			this.turnFaceLeft(2);
		}		
		
	}
	
	void moveRight_G(int row)
	{
		if(row != 0 || row != 2)
		{
			return;
		}
		
		int temp = (row == 0) ? 2 : 0;
		
		// save Green side
		String temp_tile_0 = this.tiles[row][0][4];
		String temp_tile_1 = this.tiles[row][1][4];
		String temp_tile_2 = this.tiles[row][2][4];
		
		// move Orange side to Green side
		this.tiles[row][0][4] = this.tiles[temp][2][3];
		this.tiles[row][1][4] = this.tiles[temp][1][3];
		this.tiles[row][2][4] = this.tiles[temp][0][3];
		
		// move Blue side to Orange side
		this.tiles[temp][2][3] = this.tiles[row][0][5];
		this.tiles[temp][1][3] = this.tiles[row][1][5];
		this.tiles[temp][0][3] = this.tiles[row][2][5];
		
		// move Red side to Blue side
		this.tiles[row][0][5] = this.tiles[row][0][1];
		this.tiles[row][1][5] = this.tiles[row][1][1];
		this.tiles[row][2][5] = this.tiles[row][2][1];		
				
		// move Green side to Red side
		this.tiles[temp][0][1] = temp_tile_0;
		this.tiles[temp][1][1] = temp_tile_1;
		this.tiles[temp][2][1] = temp_tile_2;
		
		// turn side faces
		if(row == 0)
		{
			this.turnFaceLeft(0);
		}
		else
		{
			this.turnFaceRight(2);
		}
	}
	
	void turnFaceLeft(int face)
	{
		String temp_tile_0 = this.tiles[0][0][face];
		String temp_tile_1 = this.tiles[0][1][face];
    	
		this.tiles[0][0][face] = this.tiles[0][2][face];   	
		this.tiles[0][1][face] = this.tiles[1][2][face];
    	
		this.tiles[0][2][face] = this.tiles[2][2][face];
		this.tiles[1][2][face] = this.tiles[2][1][face];
    	
		this.tiles[2][2][face] = this.tiles[2][0][face];
		this.tiles[2][1][face] = this.tiles[1][0][face];
    	
		this.tiles[2][0][face] = temp_tile_0;
		this.tiles[1][0][face] = temp_tile_1;
	}
    
	void turnFaceRight(int face)
	{
		String temp_tile_0 = this.tiles[0][0][face];
		String temp_tile_1 = this.tiles[0][1][face];
    	
		this.tiles[0][0][face] = this.tiles[2][0][face];   	
		this.tiles[0][1][face] = this.tiles[1][0][face];
    	
		this.tiles[2][0][face] = this.tiles[2][2][face];
		this.tiles[1][0][face] = this.tiles[2][1][face];
    	
		this.tiles[2][2][face] = this.tiles[0][2][face];
		this.tiles[2][1][face] = this.tiles[1][2][face];
    	
		this.tiles[0][2][face] = temp_tile_0;
		this.tiles[1][2][face] = temp_tile_1;
	}
	
	private void countOffPlace()
	{
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j <3; j++)
				{
					if((z == 0 && tiles[i][j][z] != "W") || 
							(z == 1 && tiles[i][j][z] != "R") || 
							(z == 2 && tiles[i][j][z] != "Y") || 
							(z == 3 && tiles[i][j][z] != "O") || 
							(z == 4 && tiles[i][j][z] != "G") || 
							(z == 5 && tiles[i][j][z] != "B"))
					{
						this.score++;
					}
				}
			}
		} 
	}
	
	private void countManhattanDistance()
	{
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if ((!(i == 1 && j == 1)) && 
							((z == 0 && tiles[i][j][z] != "W") || 
							(z == 1 && tiles[i][j][z] != "R") || 
							(z == 2 && tiles[i][j][z] != "Y") || 
							(z == 3 && tiles[i][j][z] != "O") || 
							(z == 4 && tiles[i][j][z] != "G") || 
							(z == 5 && tiles[i][j][z] != "B")))
					{
						if(((tiles[i][j][z] == "G" || tiles[i][j][z] == "B") && (z != 4 && z!= 5)) ||
								((tiles[i][j][z] == "R" || tiles[i][j][z] == "O") && (z == 4 || z == 5)))
						{
							this.score += (j + 1);
						}
						else if(((tiles[i][j][z] == "W" || tiles[i][j][z] == "Y") && (z != 0 && z != 2)) ||
								((tiles[i][j][z] == "R" || tiles[i][j][z] == "O") && (z == 0 || z == 2)))
						{
							this.score += (i + 1);
						}
						else
						{
							this.score += 4;
						}
					}
				}
			}
		}
    }
    
    // evaluate based on the heuristic value
//	private void evaluate(int heuristic)
	private void evaluate(int heuristic)
	{
		this.countOffPlace();
		this.countManhattanDistance();
//        switch(heuristic)
//        {
//            case 1:
//                this.countOffPlace();
//                break;
//            case 2:
//                this.countManhattanDistance();
//                break;
//            default:
//                break;
//        }
	}

	public boolean isFinal()
	{
		int completedFaces = 0;
		boolean correctTiles;
		
		for(int z = 0; z < 6; z++)
		{
			correctTiles = true;
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if (!(i == 1 && j == 1))
					{
						if((z == 0 && tiles[i][j][z] != "W") || 
								(z == 1 && tiles[i][j][z] != "R") || 
								(z == 2 && tiles[i][j][z] != "Y") || 
								(z == 3 && tiles[i][j][z] != "O") || 
								(z == 4 && tiles[i][j][z] != "G") || 
								(z == 5 && tiles[i][j][z] != "B"))
						{
							correctTiles = false;
						}
					}
				}
			}
			if(correctTiles == true)
			{
				completedFaces += 1;
			}
		}
		if(completedFaces >= this.faces_num)
		{
			return true;
		}
		return false;
	}

	// override this for proper hash set comparisons.
	@Override
	public boolean equals(Object obj)
	{
		if((this.tiles.length == 0 || ((State)obj).tiles.length == 0) || (this.tiles.length != 3 || ((State)obj).tiles.length != 3)) return false;
		
		if((this.tiles[0].length == 0 || ((State)obj).tiles[0].length == 0) || (this.tiles[0].length != 3 || ((State)obj).tiles[0].length != 3)) return false;
		
		if(this.tiles[0][0].length != 6 || ((State)obj).tiles[0][0].length != 6) return false;

		// check for equality of colors in the tiles 
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				for(int z = 0; z < 6; z++)
				{
					if(this.tiles[i][j][z] != ((State)obj).tiles[i][j][z])
					{
						return false;
					}
				}
			}
		}
		return true;
	}

    // override this for proper hash set comparisons.
    @Override
    public int hashCode()
    {
    	return this.score + this.identifier();
        //return this.emptyTileRow + this.emptyTileColumn + this.dimension + this.identifier();
    }

	@Override
	public int compareTo(State s)
	{
		return Double.compare(this.score, s.score); // compare based on the heuristic score.
	}

	int identifier()
	{
		int result = 0;
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0 ; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					// a unique sum based on the colors in each state.
					// e.g., for i = j = z = 0 and color = "B" in the fixed initial state --> (3 ^ ((3 * 0) + 0) + 0) * 6 * 6 = 1 * 6 * 6 = 36
					// for another state, this will not be the same
					// min value for result is 6 and max value for result is 1243746
					int color;
					if(this.tiles[i][j][z] == "W")
					{
						color = 1;
					}
					else if(this.tiles[i][j][z] == "R")
					{
						color = 2;
					}
					else if(this.tiles[i][j][z] == "Y")
					{
						color = 3;
					}
					else if(this.tiles[i][j][z] == "O")
					{
						color = 4;
					}
					else if(this.tiles[i][j][z] == "G")
					{
						color = 5;
					}
					else
					{
						color = 6;
					}
					result += Math.pow(6, Math.pow(3, (3 * i) + j) + z) * color;
					
//	                // a unique sum based on the numbers in each state.
//	                // e.g., for i=j=0 in the fixed initial state --> 3^( (0*0) + 0) * 8 = 1 + 8 = 9
//	                // for another state, this will not be the same
//	                result += Math.pow(this.dimension, (this.dimension * i) + j) * this.tiles[i][j];
				}
			}
		}
		return result;
    }
}
