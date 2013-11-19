package Chess;

public class Board 
{
	private final int boardHeight = 8;
	private final int boardWidth = 8;
	
	private Square[][] chessBoard = new Square[boardHeight][boardWidth];
	
	public Board()
	{		
		for(int i = 0; i < boardHeight; i++)
		{
			for(int k = 0; k < boardWidth; k++)
			{
				chessBoard[i][k] = new Square(new Piece("-", i, k), i, k);
			}
		}		
		processSquaresForBoard();
	}
	
	public void processSquaresForBoard()
	{
		for(int i = 0; i < boardHeight; i++)
		{
			for(int k = 0; k < boardWidth; k++)
			{
				chessBoard[i][k].getPiece().setPieceType("z");
			}
		}
	}
	
	public void printBoard()
	{
		for(int i = 0; i < boardHeight; i++)
		{			
			System.out.println();
			
			for(int k = 0; k < boardWidth; k++)
			{
				System.out.print(chessBoard[i][k].getPiece().getPieceType());
			}
		}
	}
}