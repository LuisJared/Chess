package Chess;

public class Piece 
{
	private String pieceType = "";
	private int positionX;
	private int positionY;

	public Piece(String pieceType, int column, int row)
	{
		this.pieceType = pieceType;
		this.positionX = column;
		this.positionY = row;
	}

	public String getPieceType() 
	{
		return pieceType;
	}

	public void setPieceType(String pieceType)
	{
		this.pieceType = pieceType;
	}

	public int getPositionX() 
	{
		return positionX;
	}

	public void setPositionX(int positionX) 
	{
		this.positionX = positionX;
	}

	public int getPositionY() 
	{
		return positionY;
	}

	public void setPositionY(int positionY) 
	{
		this.positionY = positionY;
	}
}