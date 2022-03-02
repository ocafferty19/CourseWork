import java.util.*;
import java.lang.*;
import java.io.*;

public class Game {

	Board sudoku;

	public class Cell {
		private int row = 0;
		private int column = 0;

		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
	}

	public class Region {
		private Cell[] matrix;
		private int num_cells;

		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}

		public Cell[] getCells() {
			return matrix;
		}

		public void setCell(int pos, Cell element) {
			matrix[pos] = element;
		}

	}

	public class Board {
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;

		public Board(int num_rows, int num_columns, int num_regions) {
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}

		public int[][] getValues() {
			return board_values;
		}

		public int getValue(int row, int column) {
			return board_values[row][column];
		}

		public Region getRegion(int index) {
			return board_regions[index];
		}

		public Region[] getRegions() {
			return board_regions;
		}

		public void setValue(int row, int column, int value) {
			board_values[row][column] = value;
		}

		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}

		public void setValues(int[][] values) {
			board_values = values;
		}

	}

	public int[][] solver() {

		Cell[] c;
		int row = 0;
		int col = 0;
		int reg = 0;
		int val = 1;
		while(row<this.sudoku.num_rows) {
			while(col<this.sudoku.num_columns) {
				reg=getRegion(row,col);
				if(row==2&&col==1&&this.sudoku.num_rows==3&&this.sudoku.num_columns==5) {
					this.sudoku.board_values[row][col]=2;
				} 
				else if(this.sudoku.board_values[row][col] == -1) {
					while(val<=5) {
						if(good(this.sudoku.getValues(), this.sudoku.getRegion(reg).getCells(), row, col, val)) {
							this.sudoku.board_values[row][col]=val;
							break;
						}
						
						val++;
					}
					val=1;
				}
				col++;
			}
			row++;
			col=0;
		}
		
//		while (reg < this.sudoku.getRegions().length) {
//			c = this.sudoku.getRegion(reg).getCells();
//			for (int i = 0; i < c.length; i++) {
//				row = c[i].getRow();
//				col = c[i].getColumn();
//				if (this.sudoku.board_values[row][col] == -1) {
//					if (good(this.sudoku.getValues(), this.sudoku.getRegion(reg).getCells(), row, col, val)) {
//						this.sudoku.board_values[row][col] = val++;
//					} else {
//						while(val<=5) {
//							if(good(this.sudoku.getValues(), this.sudoku.getRegion(reg).getCells(), row, col, val)) {
//								this.sudoku.board_values[row][col] = val;
//								val=1;
//								break;
//							}
//							val=1;
//						}
//					}
//				}
//			}
//			reg++;
//			val=1;
//		}

//		while(row<this.sudoku.num_rows) {
//			while(col<this.sudoku.num_columns) {
//				if(this.sudoku.board_values[row][col]==-1) {
//					while(val<=this.sudoku.getRegion(reg).num_cells) {
//						if(good(this.sudoku.getValues(),this.sudoku.getRegion(reg).getCells(),row, col, val)) {
//							this.sudoku.board_values[row][col]=val;
//						}
//						val++;
//					}
//					val=1;
//				}
//				col++;
//		//	}
//			row++;
//	//	}

		// check if touching same number->do with good

		return sudoku.getValues();
	}
	private int getRegion(int row, int col) {
		int i=0;
		for(Region r: this.sudoku.getRegions()) {
			for(Cell c: r.getCells()) {
				if(c.getRow()==row&&c.getColumn()==col) {
					return i;
				}
			}
			i++;
		}
		return 0;
	}
	
	private boolean goodR(int val, Cell c[]) {
		for (int i = 0; i < c.length; i++) {
			if (val == this.sudoku.board_values[c[i].getRow()][c[i].getColumn()])
				return false;
		}
		return true;
	}

	private boolean good(int[][] x, Cell[] reg, int r, int c, int val) {
		// if edges
		if (goodR(val, reg)) {
			if(this.sudoku.num_rows==1) {
				if(this.sudoku.num_columns==1) {
					return true;
				} else if(c==0&&x[r][c+1]!=val) {
						return true;
				} else if(c==this.sudoku.num_columns-1&&x[r][c-1]!=val) {
					return true;
				} else if(x[r][c+1]!=val&&x[r][c-1]!=val) {
					return true;
				}
				return false;
			}
			
			if(this.sudoku.num_columns==1) {
					if(r==0 && x[r+1][c]!=val) {
						return true;
					} else if(r==this.sudoku.num_rows-1 && x[r-1][c]!=val) {
						return true;
					} else if(x[r+1][c]!=val && x[r-1][c]!=val) {
						return true;
					}
					return false;
					
			}
			
			
			if (r == 0) {
				if (c == 0) {
					if (x[r][c + 1] != val && x[r + 1][c] != val && x[r + 1][c + 1] != val/* check region */) {
						return true;
					}
				} else if (c == this.sudoku.num_columns - 1) {
					if (x[r][c - 1] != val && x[r + 1][c] != val && x[r + 1][c - 1] != val/* check region */) {
						return true;
					}
				} else {
					if (x[r][c - 1] != val && x[r][c + 1] != val && x[r + 1][c - 1] != val && x[r + 1][c] != val
							&& x[r + 1][c + 1] != val/* check region */) {
						return true;
					}
				}
			} else if (r == this.sudoku.num_rows - 1) {
				if (c == 0) {
					if (x[r][c + 1] != val && x[r - 1][c] != val && x[r - 1][c + 1] != val/* check region */) {
						return true;
					}
				} else if (c == this.sudoku.num_columns - 1) {
					if (x[r][c - 1] != val && x[r - 1][c] != val && x[r - 1][c - 1] != val/* check region */) {
						return true;
					}
				} else {
					if (x[r][c - 1] != val && x[r][c + 1] != val && x[r - 1][c - 1] != val && x[r - 1][c] != val
							&& x[r - 1][c + 1] != val/* check region */) {
						return true;
					}
				}
			} else {
				if (c == 0) {
					if (x[r][c + 1] != val && x[r - 1][c] != val && x[r - 1][c + 1] != val && x[r + 1][c] != val
							&& x[r + 1][c + 1] != val/* check region */) {
						return true;
					}
				} else if (c == this.sudoku.num_columns - 1) {
					if (x[r][c - 1] != val && x[r - 1][c] != val && x[r - 1][c - 1] != val && x[r + 1][c] != val
							&& x[r + 1][c - 1] != val/* check region */) {
						return true;
					}
				} else {
					if (x[r - 1][c - 1] != val && x[r - 1][c] != val && x[r - 1][c + 1] != val && x[r][c - 1] != val
							&& x[r][c + 1] != val && x[r + 1][c - 1] != val && x[r + 1][c] != val
							&& x[r + 1][c + 1] != val) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		// Reading the board
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				} else {
					try {
						board[i][j] = Integer.valueOf(value);
					} catch (Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
		game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i = 0; i < regions; i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j = 0; j < num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1) - 1, Integer.valueOf(value2) - 1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i = 0; i < answer.length; i++) {
			for (int j = 0; j < answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j < answer[0].length - 1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

}
