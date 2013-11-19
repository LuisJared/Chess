package Chess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileInputReader 
{		 
	private final Pattern CASTLING_VERIFIER = Pattern.compile("(?<kingCastle>[Ee][18]) ([CcGg][18]) (?<rookCastle>[AaHh][18]) ([DdFf][18])");
	private final Pattern PIECE_PLACE_FINDER = Pattern.compile("[\\w]{2}(?<position>[A-Ha-h][1-8])");
	private final Pattern PIECE_MOVEMENT_CAPTURING = Pattern.compile("(?<startingPosition>[A-Ha-h][1-8]) (?<capturePosition>[A-Ha-h][1-8])(\\*)");
	private final Pattern PIECE_PLACING_VERIFIER = Pattern.compile("(?<pieceType>[KkQqBbNnRrPp])(?<pieceColor>[LlDd])(?<columnPosition>[A-Ha-h])(?<rowPosition>[1-8])");
	private final Pattern PIECE_MOVEMENT_VERIFIER = Pattern.compile("(?<startingPosition>[A-Ha-h][1-8]) (?<endPosition>[A-Ha-h][1-8])");
	
//	private static int boardWidth = 8;
//	private static int boardHeight = 8;
//	private Square[][] boardSquares = new Square[boardWidth][boardHeight];
//	private Board board = new Board();
//	
//	private void boardSquaresFiller()
//	{
//		for(int i = 0; i < boardHeight; i++)
//		{
//			for(int k = 0; k < boardWidth; k++)
//			{
//				boardSquares[i][k] = new Square("-", i, k);
//			}
//		}
//	}
//
//	public void getBoardSquares()
//	{		
//		for(int i = 0; i < boardHeight; i++)
//		{
//			System.out.println();
//			for(int k = 0; k < boardWidth; k++)
//			{
//				System.out.print(boardSquares[i][k].getPieceName());
//			}
//		}
//	}
	
	public void readFile(String file)
	{	
		findPieces(file);
	}
	
	public void findPieces(String fileName)
	{
		File file = new File(fileName);
		BufferedReader buffer = null;
		
		try
		{
			try
			{
				buffer = new BufferedReader(new FileReader(file));
			}
			catch(FileNotFoundException f)
			{
				System.out.println("The command line has an invalid file name!  Please input a proper file arguement!");
			}
			
			while(buffer.ready())
			{						
				String piece = "";
				String color = "";
				String placement = "";
				String line = buffer.readLine();

				Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
				Matcher movementCapture = PIECE_MOVEMENT_CAPTURING.matcher(line);
				Matcher movementVerifier = PIECE_MOVEMENT_VERIFIER.matcher(line);
				Matcher castleMatcher = CASTLING_VERIFIER.matcher(line);
				
				piece = pieceType(line);					
				color = pieceColor(line);
				placement = piecePosition(line);
				
				int column = 0;
				int row = 0;				
					
				if(placeVerifier.matches())
				{
					String pieceType = placeVerifier.group("pieceType");
					
					placePiece(piece, color, placement, line);
					
					char columnLetter = placeVerifier.group("columnPosition").charAt(0);
					char rowLetter = placeVerifier.group("rowPosition").charAt(0);
					
					column = columnLetter - 'a';
					row = rowLetter - '1';
						
					pieceType = (color == "White") ? pieceType.toLowerCase() : pieceType.toUpperCase();
					
					Piece newPiece = new Piece(pieceType, column, row);
					Square square = new Square(newPiece, column, row);
					square.setPiece(newPiece);
//					Square square = new Square(pieceType, row, column);
//					boardSquares[column][row] = square;
//					board.processSquaresForBoard(square);
				}					
				else if(movementVerifier.matches())
				{
					movePiece(line);
				}
				else if(movementCapture.matches())
				{
					capturePiece(line);
				}
				else if(castleMatcher.matches())
				{
					notifyCastle(line);
				}
				else
				{
					System.out.println(line + " is an incorrect input!  Please revise it!");
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				buffer.close();
			}
			catch(IOException e)
			{
				System.out.println("Buffer hasn't closed");
			}
		}
	}
	
	private String pieceType(String line) 
	{
		String piece = "";
		
		Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
		
		if(placeVerifier.matches())
		{					
			String pieceType = placeVerifier.group("pieceType").toLowerCase();
			
			if(pieceType.equals("k"))
			{
				piece = "King";
			}
			else if(pieceType.equals("q"))
			{
				piece = "Queen";
			}
			else if(pieceType.equals("b"))
			{
				piece = "Bishop";
			}
			else if(pieceType.equals("n"))
			{
				piece = "Knight";
			}
			else if(pieceType.equals("r"))
			{
				piece = "Rook";
			}
			else if(pieceType.equals("p"))
			{
				piece = "Pawn";
			}
		}		
		return piece;
	}
	
	private String pieceColor(String line)
	{
		Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
		
		String color = "";

		if(placeVerifier.matches())
		{			
			color = (placeVerifier.group("pieceColor").equals("l") || placeVerifier.group("pieceColor").equals("L")) ? "White" : "Black";
		}
		
		return color;
	}
	
	private String piecePosition(String line)
	{
		String position = "";
		Matcher pieceFinder = PIECE_PLACE_FINDER.matcher(line);
		
		if(pieceFinder.matches())
		{
			position = pieceFinder.group("position");			
		}
		
		return position;
	}
	
	private void placePiece(String piece, String color, String placement, String line)
	{
		Matcher pieceFinder = PIECE_PLACE_FINDER.matcher(line);	
		
		if(pieceFinder.matches())
		{
			System.out.println("The " + color + " " + piece + " has been placed at " + pieceFinder.group("position"));
		}
	}

	private void capturePiece(String line)
	{		
		Matcher movementCapture = PIECE_MOVEMENT_CAPTURING.matcher(line);
		
		if(movementCapture.matches())
		{
			System.out.println("The piece at " + movementCapture.group("startingPosition") + " has moved to " + movementCapture.group("capturePosition") + " and captured the piece at " + movementCapture.group("capturePosition"));
		}
	}
	
	private void movePiece(String line)
	{
		Matcher movementVerifier = PIECE_MOVEMENT_VERIFIER.matcher(line);
		
		if(movementVerifier.matches())
		{
			System.out.println("The piece at " + movementVerifier.group("startingPosition") + " has moved to " + movementVerifier.group("endPosition"));
		}
	}
	
	private void notifyCastle(String line) 
	{
		Matcher castleMatcher = CASTLING_VERIFIER.matcher(line);
		
		if(castleMatcher.matches())
		{
			System.out.println("You have just performed Castling with " + castleMatcher.group("kingCastle") + " and " + castleMatcher.group("rookCastle"));
		}
	}
}