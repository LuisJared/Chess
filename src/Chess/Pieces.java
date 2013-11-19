package Chess;

public class Pieces 
{	
	private int boardHeight = 8;
	private int boardWidth = 8;
	private Piece[][] piecesArray = new Piece[boardHeight][boardWidth];

	public Pieces()
	{
		
	}
	
	public Piece getPiece(int x, int y) 
	{
		Piece piece = piecesArray[x][y];
		
		return piece;
	}
}