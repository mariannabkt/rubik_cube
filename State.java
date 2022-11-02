import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;

public class State implements Comparable<State>
{
	private String[][][] tiles;
	private int faces_num;

	//heuristic score
	private int score;

	private State father = null;


	State(int k)
	{
		this.faces_num = k;
		this.tiles = new String[3][3][6];
		
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
				System.out.println("------------ \n");
			}
			else if(z == 1)
			{
				System.out.println("Red side");
				System.out.println("------------ \n");
			}
			else if(z == 2)
			{
				System.out.println("Yellow side");
				System.out.println("------------ \n");
			}
			else if(z == 3)
			{
				System.out.println("Orange side");
				System.out.println("------------ \n");
			}
			else if(z == 4)
			{
				System.out.println("Green side");
				System.out.println("------------ \n");
			}
			else
			{
				System.out.println("Blue side");
				System.out.println("------------ \n");
			}
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
		child.moveUp_G(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Up Right Column
		child = new State(this.tiles);
		child.moveUp_G(2);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Down Left Column
		child = new State(this.tiles);
		child.moveDown_G(0);
		if(heuristic > 0) child.evaluate(heuristic);
		child.setFather(this);
		children.add(child);
				
		// Green Move Down Right Column
		child = new State(this.tiles);
		child.moveDown_G(2);
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
	}

	void moveLeft_W(int row)
	{
		if(row != 0 || row != 2)
		{
			return;
		}
		// save White side
		String temp_tile_0 = this.tiles[row][0][0];
		String temp_tile_1 = this.tiles[row][1][0];
		String temp_tile_2 = this.tiles[row][2][0];
		
		// move Blue side to White side
		this.tiles[row][0][0] = this.tiles[row][0][5];
		this.tiles[row][1][0] = this.tiles[row][1][5];
		this.tiles[row][2][0] = this.tiles[row][2][5];
		
		// move Yellow side to Blue side
		this.tiles[row][0][5] = this.tiles[row][0][2];
		this.tiles[row][1][5] = this.tiles[row][1][2];
		this.tiles[row][2][5] = this.tiles[row][2][2];
		
		// move Green side to Yellow side
		this.tiles[row][0][2] = this.tiles[row][0][4];
		this.tiles[row][1][2] = this.tiles[row][1][4];
		this.tiles[row][2][2] = this.tiles[row][2][4];		
				
		// move White side to Green side
		this.tiles[row][0][4] = temp_tile_0;
		this.tiles[row][1][4] = temp_tile_1;
		this.tiles[row][2][4] = temp_tile_2;
	}

    void moveRight_W(int row)
    {
		if(row != 0 || row != 2)
		{
			return;
		}
		// save White side
		String temp_tile_0 = this.tiles[row][0][0];
		String temp_tile_1 = this.tiles[row][1][0];
		String temp_tile_2 = this.tiles[row][2][0];
		
		// move Green side to White side
		this.tiles[row][0][0] = this.tiles[row][0][4];
		this.tiles[row][1][0] = this.tiles[row][1][4];
		this.tiles[row][2][0] = this.tiles[row][2][4];
		
		// move Yellow side to Green side
		this.tiles[row][0][4] = this.tiles[row][0][2];
		this.tiles[row][1][4] = this.tiles[row][1][2];
		this.tiles[row][2][4] = this.tiles[row][2][2];
		
		// move Blue side to Yellow side
		this.tiles[row][0][2] = this.tiles[row][0][5];
		this.tiles[row][1][2] = this.tiles[row][1][5];
		this.tiles[row][2][2] = this.tiles[row][2][5];		
				
		// move White side to Blue side
		this.tiles[row][0][5] = temp_tile_0;
		this.tiles[row][1][5] = temp_tile_1;
		this.tiles[row][2][5] = temp_tile_2;
    }
    
	void moveUp_G(int col)
	{
		if(col != 0 || col != 2)
		{
			return;
		}
		// save Green side
		String temp_tile_0 = this.tiles[0][col][4];
		String temp_tile_1 = this.tiles[1][col][4];
		String temp_tile_2 = this.tiles[2][col][4];
		
		// move Yellow side to Green side
		this.tiles[0][col][4] = this.tiles[0][col][2];
		this.tiles[1][col][4] = this.tiles[1][col][2];
		this.tiles[2][col][4] = this.tiles[2][col][2];
		
		// move Blue side to Yellow side
		this.tiles[0][col][2] = this.tiles[0][col][5];
		this.tiles[1][col][2] = this.tiles[1][col][5];
		this.tiles[2][col][2] = this.tiles[2][col][5];
		
		// move White side to Blue side
		this.tiles[0][col][5] = this.tiles[0][col][0];
		this.tiles[1][col][5] = this.tiles[1][col][0];
		this.tiles[2][col][5] = this.tiles[2][col][0];		
				
		// move Green side to White side
		this.tiles[0][col][0] = temp_tile_0;
		this.tiles[1][col][0] = temp_tile_1;
		this.tiles[2][col][0] = temp_tile_2;
		
	}
	
	void moveDown_G(int col)
	{
		if(col != 0 || col != 2)
		{
			return;
		}
		// save Green side
		String temp_tile_0 = this.tiles[0][col][4];
		String temp_tile_1 = this.tiles[1][col][4];
		String temp_tile_2 = this.tiles[2][col][4];
		
		// move White side to Green side
		this.tiles[0][col][4] = this.tiles[0][col][0];
		this.tiles[1][col][4] = this.tiles[1][col][0];
		this.tiles[2][col][4] = this.tiles[2][col][0];
		
		// move Blue side to White side
		this.tiles[0][col][0] = this.tiles[0][col][5];
		this.tiles[1][col][0] = this.tiles[1][col][5];
		this.tiles[2][col][0] = this.tiles[2][col][5];
		
		// move Yellow side to Blue side
		this.tiles[0][col][5] = this.tiles[0][col][2];
		this.tiles[1][col][5] = this.tiles[1][col][2];
		this.tiles[2][col][5] = this.tiles[2][col][2];		
				
		// move Green side to Yellow side
		this.tiles[0][col][2] = temp_tile_0;
		this.tiles[1][col][2] = temp_tile_1;
		this.tiles[2][col][2] = temp_tile_2;
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

	private void countManhattanDistance()	//CHOOSE A++++++++++++++++++++++++++++++++++++++++++++++
	{
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if ((!(i == 1 && j == 1)) && ((z == 0 && tiles[i][j][z] != "W") || 
							(z == 1 && tiles[i][j][z] != "R") || 
							(z == 2 && tiles[i][j][z] != "Y") || 
							(z == 3 && tiles[i][j][z] != "O") || 
							(z == 4 && tiles[i][j][z] != "G") || 
							(z == 4 && tiles[i][j][z] != "B")))
					{
						if(((tiles[i][j][z] == "W" || tiles[i][j][z] == "Y") && (z == 4 || z == 2)) || 
								((tiles[i][j][z] == "R" || tiles[i][j][z] == "O") && (z == 1 || z == 3)) || 
								((tiles[i][j][z] == "G" || tiles[i][j][z] == "B") && (z == 1 || z == 3)))
						{
							this.score += (i + 1);
						}
						else if(((tiles[i][j][z] == "W" || tiles[i][j][z] == "R" || tiles[i][j][z] == "Y" || tiles[i][j][z] == "O") && (z == 5 || z == 6)) || 
								((tiles[i][j][z] == "G" || tiles[i][j][z] == "B") && (z == 4 || z == 2)))
						{
							this.score += (j + 1);
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

	private void countManhattanDistance2()	//CHOOSE B++++++++++++++++++++++++++++++++++++++++++++++
	{
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if ((!(i == 1 && j == 1)) && ((z == 0 && tiles[i][j][z] != "W") || 
							(z == 1 && tiles[i][j][z] != "R") || 
							(z == 2 && tiles[i][j][z] != "Y") || 
							(z == 3 && tiles[i][j][z] != "O") || 
							(z == 4 && tiles[i][j][z] != "G") || 
							(z == 4 && tiles[i][j][z] != "B")))
					{
						if(tiles[i][j][z] == "G" || tiles[i][j][z] == "B")
						{
							if(z == 1 || z == 3)
							{
								this.score += (i + 1);
							}
							else if(z == 4 || z == 2)
							{
								this.score += (j + 1);
							}
							else
							{
								this.score += 4;
							}
						}
						else
						{
							if(((tiles[i][j][z] == "W" || tiles[i][j][z] == "Y") && (z == 4 || z == 2)) || 
									((tiles[i][j][z] == "R" || tiles[i][j][z] == "O") && (z == 1 || z == 3)))
							{
								this.score += (i + 1);
							}
							else if((z == 5 || z == 6))
							{
								this.score += (j + 1);
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
		if(completedFaces < this.faces_num)
		{
			return false;
		}
		return true;
	}

    // override this for proper hash set comparisons.
//    @Override
//    public boolean equals(Object obj)
//    {
//        if(this.dimension != ((State)obj).dimension) return false;
//        if(this.emptyTileRow != ((State)obj).emptyTileRow) return false;
//        if(this.emptyTileColumn != ((State)obj).emptyTileColumn) return false;
//
//        // check for equality of numbers in the tiles.
//        for(int i = 0; i < this.dimension; i++)
//        {
//            for(int j = 0; j < this.dimension; j++)
//            {
//                if(this.tiles[i][j] != ((State)obj).tiles[i][j])
//                {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }

    // override this for proper hash set comparisons.
//    @Override
//    public int hashCode()
//    {
//        return this.emptyTileRow + this.emptyTileColumn + this.dimension + this.identifier();
//    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.score, s.score); // compare based on the heuristic score.
    }

//	int identifier()
//	{
//		int result = 0;
//		for(int z = 0; z < 6; z++)
//		{
//			for(int i = 0 ; i < 3; i++)
//			{
//				for(int j = 0; j < 3; j++)
//				{
//					// a unique sum based on the numbers in each state.
//					// e.g., for i=j=z=0 in the fixed initial state --> SADD++++++++++++++++++
//					// for another state, this will not be the same
//					
//				}
//			}
//		}
//		for(int i = 0; i < this.dimension; i++)
//        {
//            for(int j = 0; j < this.dimension; j++)
//            {
//                // a unique sum based on the numbers in each state.
//                // e.g., for i=j=0 in the fixed initial state --> 3^( (0*0) + 0) * 8 = 1 + 8 = 9
//                // for another state, this will not be the same
//                result += Math.pow(this.dimension, (this.dimension * i) + j) * this.tiles[i][j];
//            }
//        }
//        return result;
//    }
}
