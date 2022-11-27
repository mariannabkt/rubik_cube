import java.util.*;

public class State implements Comparable<State>
{
	// the cube has 6 faces and each face has 3 rows and 3 columns
	// Face 0 : Upper
	// Face 1 : Left
	// Face 2 : Front
	// Face 3 : Right
	// Face 4 : Back
	// Face 5 : Down
	private String[][][] tiles;
	static private int faces_to_solve;

	static private final String[] colors = {"O", "G", "W", "B", "Y", "R"};

	// heuristic score
	private int score;

	private State father = null;

	State(int k, boolean randomized)
	{
		setFacesToSolve(k);
		this.tiles = new String[3][3][6];
		
		if(randomized)
		{
			for(int c = 0; c < 6; c++) {
				for(int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						this.tiles[i][j][c] = colors[c];
					}
				}
			}

			// Scramble the Cube
			Random rand = new Random();
			int new_rand;
			for (int i = 0; i < 25; i++)
			{
				new_rand = rand.nextInt(12);
				switch (new_rand) {
					case 0 -> {
						Up();
						System.out.println("up");
					}
					case 1 -> {
						Down();
						System.out.println("d");
					}
					case 2 -> {
						Front();
						System.out.println("f");
					}
					case 3 -> {
						Back();
						System.out.println("b");
					}
					case 4 -> {
						Left();
						System.out.println("l");
					}
					case 5 -> {
						Right();
						System.out.println("r");
					}
					case 6 -> {
						UpRev();
						System.out.println("upr");
					}
					case 7 -> {
						DownRev();
						System.out.println("dr");
					}
					case 8 -> {
						FrontRev();
						System.out.println("fr");
					}
					case 9 -> {
						BackRev();
						System.out.println("br");
					}
					case 10 -> {
						LeftRev();
						System.out.println("lr");
					}
					case 11 -> {
						{
							RightRev();
							System.out.println("rr");
						}
					}
				}
				print();
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

	/**
	 * PRINTING FORMAT
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 					-----------------
	 * 					|	W	W	W	|
	 * 					|	W	W	W	|
	 * 					|	W	W	W	|
	 * -----------------------------------------------------------------
	 * |	R	R	R	|	G	G	G	|	B	B	B	|	Y	Y	Y	|
	 * |	R	R	R	|	G	G	G	|	B	B	B	|	Y	Y	Y	|
	 * |	R	R	R	|	G	G	G	|	B	B	B	|	Y	Y	Y	|
	 * -----------------------------------------------------------------
	 * 					|	O	O	O	|
	 * 					|	O	O	O	|
	 * 					|	O	O	O	|
	 * 					-----------------
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 */
	void print()
	{
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\t\t\t\t\t-----------------");
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if (j==0)
					System.out.print("\t\t\t\t\t|\t" + this.tiles[i][j][0]);
				else if (j==1)
					System.out.print("\t" + this.tiles[i][j][0]);
				else
					System.out.print("\t" + this.tiles[i][j][0] + "\t|");
			}
			System.out.println();
		}

		System.out.println("\t-----------------------------------------------------------------");

		for(int i = 0; i < 3; i++) {
			for (int z = 1; z < 5; z++) {
				for (int j = 0; j < 3; j++) {
					if (j==0 && z==1)
						System.out.print("\t|\t" + this.tiles[i][j][z]);
					else if (j==2)
						System.out.print("\t" + this.tiles[i][j][z] + "\t|");
					else
						System.out.print("\t" + this.tiles[i][j][z]);
				}
			}
			System.out.println();
		}

		System.out.println("\t-----------------------------------------------------------------");

		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if (j==0)
					System.out.print("\t\t\t\t\t|\t" + this.tiles[i][j][5]);
				else if (j==1)
					System.out.print("\t" + this.tiles[i][j][5]);
				else
					System.out.print("\t" + this.tiles[i][j][5] + "\t|");
			}
			System.out.println();
		}
		System.out.println("\t\t\t\t\t-----------------\n");

	}
	
    String[][][] getTiles() 
	{
        return this.tiles;
    }

	void setTiles(String[][][] tiles) 
	{
		this.tiles = new String[3][3][6];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				System.arraycopy(tiles[i][j], 0, this.tiles[i][j], 0, 6);
	}

	int getFacesToSolve()
	{
		return faces_to_solve;
	}

	void setFacesToSolve(int faces_num)
	{
		faces_to_solve = faces_num;
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

	ArrayList<State> getChildren()
	{
		ArrayList<State> children = new ArrayList<>();
		State child = new State(this.tiles);

		child.Up();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("1");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.Down();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("2");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.Front();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("3");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.Back();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("4");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.Left();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("5");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.Right();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("6");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.UpRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("7");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.DownRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("8");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.FrontRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("9");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.BackRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("10");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.LeftRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("11");
		System.out.println(child.getScore());
		child.print();

		child = new State(this.tiles);
		child.RightRev();
		child.countManhattanDistance();
		child.setFather(this);
		children.add(child);
		System.out.println("12");
		System.out.println(child.getScore());
		child.print();

		return children;
    }

	/**
	 * rotate face clockwise
	 * @param face to be rotated
	 */
	void rotateFace(int face)
	{
		String[][] temp = new String[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				temp[i][j] = this.tiles[i][j][face];

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.tiles[j][2-i][face] = temp[i][j];
	}

	/**
	 * rotate face anticlockwise
	 * @param face to be rotated
	 */
	void rotateFaceRev(int face)
	{
		String[][] temp = new String[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				temp[i][j] = this.tiles[i][j][face];

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.tiles[2-j][i][face] = temp[i][j];
	}

	String[] rowToCol(String[] row, int col, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];
		for (int i = 0; i < 3; i++){
			temp[i] = this.tiles[i][col][side];
			this.tiles[i][col][side] = row[i];
		}
		return temp;
	}

	String[] rowToColRev(String[] row, int col, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];

		for (int i = 0; i < 3; i++){
			temp[i] = this.tiles[i][col][side];
			this.tiles[i][col][side] = row[2-i];
		}
		return temp;
	}

	String[] colToRow(String[] col, int row, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];

		for (int i = 0; i < 3; i++) {
			temp[i] = this.tiles[row][i][side];
			this.tiles[row][i][side] = col[2-i];
		}
		return temp;
	}

	String[] colToRowRev(String[] col, int row, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];

		for (int i = 0; i < 3; i++) {
			temp[i] = this.tiles[row][i][side];
			this.tiles[row][i][side] = col[i];
		}
		return temp;
	}

	String[] rowToRow(String[] rowTemp, int row, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];
		for (int i = 0; i < 3; i++){
			temp[i] = this.tiles[row][i][side];
			this.tiles[row][i][side] = rowTemp[i];
		}
		return temp;
	}

	String[] colToCol(String[] colTemp, int col, int side)
	{
		String[] temp = new String[4];
		temp[3] = colors[side];
		for (int i = 0; i < 3; i++){
			temp[i] = this.tiles[i][col][side];
			if ((colTemp[3].equals("Y") && (this.tiles[i][col][side].equals("R") || this.tiles[i][col][side].equals("O")))
					|| (this.tiles[i][col][side].equals("Y") && (colTemp[3].equals("R")|| colTemp[3].equals("O"))))
				this.tiles[i][col][side] = colTemp[2-i];
			else
				this.tiles[i][col][side] = colTemp[i];
		}
		return temp;
	}

	/**
	 * rotate the upper face clockwise
	 */
	void Up()
	{
		rotateFace(0);
		String[] temp = new String[4];
		temp[3] = colors[0];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[0][i][4];
		rowToRow(rowToRow(rowToRow(rowToRow(temp, 0, 3), 0, 2), 0, 1), 0, 4);

	}

	/**
	 * rotate the upper face anticlockwise
	 */
	void UpRev() {
		rotateFaceRev(0);
		String[] temp = new String[4];
		temp[3] = colors[0];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[0][i][4];
		rowToRow(rowToRow(rowToRow(rowToRow(temp, 0, 1), 0, 2), 0, 3), 0, 4);
	}

	/**
	 * rotate ths down face clockwise
	 */
	void Down()
	{
		rotateFace(5);
		String[] temp = new String[4];
		temp[3] = colors[5];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[2][i][1];
		rowToRow(rowToRow(rowToRow(rowToRow(temp, 2, 2), 2, 3), 2, 4), 2, 1);
	}

	/**
	 * rotate ths down face anticlockwise
	 */
	void DownRev() {
		rotateFaceRev(5);
		String[] temp = new String[4];
		temp[3] = colors[5];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[2][i][1];
		rowToRow(rowToRow(rowToRow(rowToRow(temp, 2, 4), 2, 3), 2, 2), 2, 1);
	}

	/**
	 * rotate front face clockwise
	 */
	void Front() {
		rotateFace(2);
		String[] temp = new String[4];
		temp[3] = colors[2];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[2][i][0];
		colToRow(rowToCol(colToRow(rowToCol(temp, 0, 3), 0, 5), 2, 1), 2, 0);
	}

	/**
	 * rotate front face anticlockwise
	 */
	void FrontRev() {
		rotateFaceRev(2);
		String[] temp = new String[4];
		temp[3] = colors[2];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[2][i][0];
		colToRowRev(rowToColRev(colToRowRev(rowToColRev(temp, 2, 1), 0, 5), 0, 3), 2, 0);
	}

	/**
	 * rotate back face clockwise
	 */
    void Back() {
		rotateFace(4);
		String[] temp = new String[4];
		temp[3] = colors[4];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[0][i][0];
		colToRow(rowToCol(colToRow(rowToCol(temp, 0, 1), 2, 5), 2, 3), 0, 0);
    }

	/**
	 * rotate back face anticlockwise
	 */
	void BackRev() {
		rotateFaceRev(4);
		String[] temp = new String[4];
		temp[3] = colors[4];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[0][i][0];
		colToRowRev(rowToColRev(colToRowRev(rowToColRev(temp, 2, 3), 2, 5), 0, 1), 0, 0);
	}

	/**
	 * rotate left face clockwise
	 */
	void Left() {
		rotateFace(1);
		String[] temp = new String[4];
		temp[3] = colors[1];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[i][0][0];
		colToCol(colToCol(colToCol(colToCol(temp, 0, 2), 0, 5), 2, 4), 0, 0);
	}

	/**
	 * rotate left face anticlockwise
	 */
	void LeftRev() {
		rotateFaceRev(1);
		String[] temp = new String[4];
		temp[3] = colors[1];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[i][0][0];
		colToCol(colToCol(colToCol(colToCol(temp, 2, 4), 0, 5), 0, 2), 0, 0);
	}

	/**
	 * rotate right face clockwise
	 */
	void Right() {
		rotateFace(3);
		String[] temp = new String[4];
		temp[3] = colors[3];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[i][2][0];
		colToCol(colToCol(colToCol(colToCol(temp, 0, 4), 2, 5), 2, 2), 2, 0);
	}

	/**
	 * rotate right face anticlockwise
	 */
	void RightRev() {
		rotateFaceRev(3);
		String[] temp = new String[4];
		temp[3] = colors[3];
		for (int i = 0; i < 3; i++)
			temp[i] = this.tiles[i][2][0];
		colToCol(colToCol(colToCol(colToCol(temp, 2, 2), 2, 5), 0, 4), 2, 0);
	}

	
	private void countOffPlace()
	{
		for(int z = 0; z < 6; z++)
		{
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j <3; j++)
				{
					if (!(i == 1 && j == 1) && !tiles[i][j][z].equals(tiles[1][1][z]))
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
					if (!(i == 1 && j == 1) && !tiles[i][j][z].equals(tiles[1][1][z]))
					{
						if(((tiles[i][j][z].equals("G") || tiles[i][j][z].equals("B")) && (z != 4 && z!= 5)) ||
								((tiles[i][j][z].equals("R") || tiles[i][j][z].equals("O")) && (z == 4 || z == 5)))
						{
							this.score += (j + 1);
						}
						else if(((tiles[i][j][z].equals("W") || tiles[i][j][z].equals("Y")) && (z != 0 && z != 2)) ||
								((tiles[i][j][z].equals("R") || tiles[i][j][z].equals("O")) && (z == 0 || z == 2)))
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


	public boolean isFinal()
	{
		int solvedFaces = 0;
		boolean correctTiles;
		
		for(int z = 0; z < 6; z++)
		{
			correctTiles = true;
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if (!(i == 1 && j == 1) && (!tiles[i][j][z].equals(tiles[1][1][z]))) {
						correctTiles = false;
						break;
					}
				}
				if (!correctTiles) break;
			}
			if (correctTiles) solvedFaces += 1;
		}
		return solvedFaces >= getFacesToSolve();
	}


	// override this for proper hash set comparisons.
	@Override
	public boolean equals(Object obj)
	{
		if(this.tiles.length != ((State)obj).tiles.length) return false;
		
		if(this.tiles[0].length != ((State)obj).tiles[0].length) return false;
		
		if(this.tiles[0][0].length != ((State)obj).tiles[0][0].length) return false;

		// check for equality of colors in the tiles 
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				for(int z = 0; z < 6; z++)
				{
					if(!this.tiles[i][j][z].equals(((State) obj).tiles[i][j][z]))
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
					int color = switch (this.tiles[i][j][z]) {
						case "W" -> 1;
						case "R" -> 2;
						case "Y" -> 3;
						case "O" -> 4;
						case "G" -> 5;
						default -> 6;
					};
					result += Math.pow(6, Math.pow(3, (3 * i) + j) + z) * color;
				}
			}
		}
		return result;
    }
}
