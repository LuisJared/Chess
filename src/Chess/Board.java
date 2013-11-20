package Chess;

public class Board 
{
	private final int boardHeight = 8;
	private final int boardWidth = 8;
	
	private Square[][] chessBoard = new Square[boardHeight][boardWidth];
	
	public Board()
	{		
		Piece blankPiece = new Piece();
		blankPiece.setPieceType("-");
		
		for(int i = 0; i < boardHeight; i++)
		{
			for(int k = 0; k < boardWidth; k++)
			{
				chessBoard[i][k] = new Square(blankPiece, i, k);
			}
		}
	}
	
	public void addPieceToBoard(Piece piece, int x, int y)
	{
		chessBoard[x][y] = new Square(piece, x, y);
	}
	
	public void movePieceOnBoard(int x1, int y1, int x2, int y2)
	{
		Piece piece = new Piece();
		piece.setPieceType(chessBoard[x1][y1].getPiece().getPieceType());
		chessBoard[x1][y1].getPiece().setPieceType("-");
		chessBoard[x2][y2] = new Square(piece, x2, y2);
	}
	
	public void printBoard()
	{
		for(int i = (boardHeight-1); i >= 0; i--)
		{			
			System.out.println();
			
			for(int k = 0; k < boardWidth; k++)
			{
				System.out.print("[ " + chessBoard[k][i].getPiece().getPieceType() + " ]");
			}
		}
	}
}