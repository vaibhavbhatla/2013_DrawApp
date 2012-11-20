package drawapp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Parser
{
	private BufferedReader reader;
	private ImagePanel image;
	private MainWindow frame;
	private Stage primaryStage = new Stage();
	private int i = 0;
	private Turtle turtle;

	public Parser(Reader reader, ImagePanel image, MainWindow frame, Stage primaryStage)
	{
		this.reader = new BufferedReader(reader);
		this.image = image;
		this.frame = frame;
		this.primaryStage = primaryStage;
		this.turtle = new Turtle();
	}

	public void parse()
	{
		try
		{
			String line = reader.readLine();
			while (line != null)
			{
				parseLine(line);
				line = reader.readLine();
			}
		}
		catch (IOException ex)
		{
			frame.messagePost("Parse failed.");
			return;
		}
		catch (ParseException e)
		{
			frame.messagePost("Parse Exception: " + e.getMessage());
			return;
		}
		frame.messagePost("Drawing completed");
	}

	private void parseLine(String line) throws ParseException
	{
		if (line.length() < 2)
		{
			return;
		}
		String command = line.substring(0, 2);
		if (command.equals("SD"))
		{
			setDimension(line.substring(2, line.length()));
			return;
		}
		if (command.equals("DL"))
		{
			drawLine(line.substring(2, line.length()));
			return;
		}
		if (command.equals("DR"))
		{
			drawRect(line.substring(2, line.length()));
			return;
		}
		if (command.equals("FR"))
		{
			fillRect(line.substring(2, line.length()));
			return;
		}
		if (command.equals("SC"))
		{
			setColour(line.substring(3, line.length()));
			return;
		}
		if (command.equals("DS"))
		{
			drawString(line.substring(3, line.length()));
			return;
		}
		if (command.equals("DA"))
		{
			drawArc(line.substring(2, line.length()));
			return;
		}
		if (command.equals("DO"))
		{
			drawOval(line.substring(2, line.length()));
			return;
		}
		if (command.equals("DI"))
		{
			drawImage(line.substring(3, line.length()));
			return;
		}
		if (command.equals("SG"))
		{
			setGradient(line.substring(2, line.length()));
			return;
		}
		if (command.equals("SR"))
		{
			setReflection(line.substring(2, line.length()));
			return;
		}
		if (command.equals("SS"))
		{
			setDropShadow(line.substring(2, line.length()));
			return;
		}
		if (command.equals("SB"))
		{
			setGaussianBlur(line.substring(2, line.length()));
			return;
		}		
		if (command.equals("TL"))
		{
			turtleTurnLeft(line.substring(2, line.length()));
			return;
		}
		if (command.equals("TR"))
		{
			turtleTurnRight(line.substring(2, line.length()));
			return;
		}
		if (command.equals("FD"))
		{
			turtleMoveForward(line.substring(2, line.length()));
			return;
		}
		throw new ParseException("Unknown drawing command");
	}

	private void setDimension(String args) throws ParseException
	{
		int width = -1;
		int height = -1;
		
		StringTokenizer tokenizer = new StringTokenizer(args);
		width = getInteger(tokenizer);
		height = getInteger(tokenizer);
		
		if ((width < 0) || (height < 0))
		{
			throw new ParseException("Invalid values for the scene dimension command.");
		}

		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		frame.resizeDrawingArea(width, height);
	}

	private void drawImage(String args) throws ParseException
	{
		int x = -1;
		int y = -1;
		int width = -1;
		int height = -1;
		String file = "";

		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		width = getInteger(tokenizer);
		height = getInteger(tokenizer);
		int position = args.indexOf("@");
		
		if (position == -1)
		{
			throw new ParseException("DrawString string is missing");
		}
		
		file = args.substring(position + 1, args.length());

		if ((x < 0) || (y < 0) || (width < 0) || (height < 0))
			throw new ParseException("Invalid values for the draw image command.");

		image.drawImage(x, y, width, height, file);
	}

	private void drawLine(String args) throws ParseException
	{
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		x1 = getInteger(tokenizer);
		y1 = getInteger(tokenizer);
		x2 = getInteger(tokenizer);
		y2 = getInteger(tokenizer);

		if ((x1 < 0) || (x2 < 0) || (y1 < 0) || (y2 < 0))
			throw new ParseException("Invalid values for the draw line command.");

		image.drawLine(x1, y1, x2, y2);
	}

	private void drawRect(String args) throws ParseException
	{
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		x1 = getInteger(tokenizer);
		y1 = getInteger(tokenizer);
		x2 = getInteger(tokenizer);
		y2 = getInteger(tokenizer);

		if ((x1 < 0) || (x2 < 0) || (y1 < 0) || (y2 < 0))
			throw new ParseException("Invalid values for the draw rectangles command.");

		image.drawRect(x1, y1, x2, y2);
	}

	private void fillRect(String args) throws ParseException
	{
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		x1 = getInteger(tokenizer);
		y1 = getInteger(tokenizer);
		x2 = getInteger(tokenizer);
		y2 = getInteger(tokenizer);

		if ((x1 < 0) || (x2 < 0) || (y1 < 0) || (y2 < 0))
			throw new ParseException("Invalid values for the draw fill rectangle command.");

		image.fillRect(x1, y1, x2, y2);
	}

	private void drawArc(String args) throws ParseException
	{
		int x = -1;
		int y = -1;
		int width = -1;
		int height = -1;
		int startAngle = -1;
		int arcAngle = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		width = getInteger(tokenizer);
		height = getInteger(tokenizer);
		startAngle = getInteger(tokenizer);
		arcAngle = getInteger(tokenizer);

		if ((x < 0) || (y < 0) || (width < 0) || (height < 0)
				|| (startAngle < 0) || (arcAngle < 0))
			throw new ParseException("Invalid values for the draw arc command.");

		image.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	private void drawOval(String args) throws ParseException
	{
		int x1 = -1;
		int y1 = -1;
		int width = -1;
		int height = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		x1 = getInteger(tokenizer);
		y1 = getInteger(tokenizer);
		width = getInteger(tokenizer);
		height = getInteger(tokenizer);

		if ((x1 < 0) || (y1 < 0) || (width < 0) || (height < 0))
			throw new ParseException("Invalid values for the draw oval command.");

		image.drawOval(x1, y1, width, height);
	}

	private void drawString(String args) throws ParseException
	{
		int x = -1;
		int y = -1;
		String s = "";

		StringTokenizer tokenizer = new StringTokenizer(args);
		x = getInteger(tokenizer);
		y = getInteger(tokenizer);
		int position = args.indexOf("@");
		
		if (position == -1)
		{
			throw new ParseException("DrawString string is missing");
		}
		
		s = args.substring(position + 1, args.length());

		if ((x < 0) || (y < 0))
			throw new ParseException("Invalid values for the draw string command.");

		image.drawString(x, y, s);
	}

	private void setColour(String colourName) throws ParseException
	{
		if (colourName.equals("black"))
		{
			image.setColour(Color.BLACK);
			return;
		}
		if (colourName.equals("blue"))
		{
			image.setColour(Color.BLUE);
			return;
		}
		if (colourName.equals("cyan"))
		{
			image.setColour(Color.CYAN);
			return;
		}
		if (colourName.equals("darkgray"))
		{
			image.setColour(Color.DARKGREY);
			return;
		}
		if (colourName.equals("gray"))
		{
			image.setColour(Color.GRAY);
			return;
		}
		if (colourName.equals("green"))
		{
			image.setColour(Color.GREEN);
			return;
		}
		if (colourName.equals("lightgray"))
		{
			image.setColour(Color.LIGHTGRAY);
			return;
		}
		if (colourName.equals("magenta"))
		{
			image.setColour(Color.MAGENTA);
			return;
		}
		if (colourName.equals("orange"))
		{
			image.setColour(Color.ORANGE);
			return;
		}
		if (colourName.equals("pink"))
		{
			image.setColour(Color.PINK);
			return;
		}
		if (colourName.equals("red"))
		{
			image.setColour(Color.RED);
			return;
		}
		if (colourName.equals("white"))
		{
			image.setColour(Color.WHITE);
			return;
		}
		if (colourName.equals("yellow"))
		{
			image.setColour(Color.YELLOW);
			return;
		}
		throw new ParseException("Invalid colour name");
	}

	private void setReflection(String args) throws ParseException
	{
		StringTokenizer tokenizer = new StringTokenizer(args);
		int setEffect = getInteger(tokenizer);
		
		if(setEffect == 0)
		{
			image.setReflection(false);
		}
		else
		{
			image.setReflection(true);
		}
	}

	private void setDropShadow(String args)  throws ParseException
	{
		StringTokenizer tokenizer = new StringTokenizer(args);
		int setEffect = getInteger(tokenizer);
		
		if(setEffect == 0)
		{
			image.setDropShadow(false);
		}
		else
		{
			image.setDropShadow(true);
		}
	}
	
	private void setGaussianBlur(String args)  throws ParseException
	{
		StringTokenizer tokenizer = new StringTokenizer(args);
		int setEffect = getInteger(tokenizer);
		
		if(setEffect == 0)
		{
			image.setGaussianBlur(false);
		}else{
			image.setGaussianBlur(true);
		}
	}

	private Color getColour(String colourName) throws ParseException
	{
		if (colourName.equals("black"))
		{
			return Color.BLACK;
		}
		if (colourName.equals("blue"))
		{
			return Color.BLUE;
		}
		if (colourName.equals("cyan"))
		{
			return Color.CYAN;
		}
		if (colourName.equals("darkgray"))
		{
			return Color.DARKGREY;
		}
		if (colourName.equals("gray"))
		{
			return Color.GRAY;
		}
		if (colourName.equals("green"))
		{
			return Color.GREEN;
		}
		if (colourName.equals("lightgray"))
		{
			return Color.LIGHTGRAY;
		}
		if (colourName.equals("magenta"))
		{
			return Color.MAGENTA;
		}
		if (colourName.equals("orange"))
		{
			return Color.ORANGE;
		}
		if (colourName.equals("pink"))
		{
			return Color.PINK;
		}
		if (colourName.equals("red"))
		{
			return Color.RED;
		}
		if (colourName.equals("white"))
		{
			return Color.WHITE;
		}
		if (colourName.equals("yellow"))
		{
			return Color.YELLOW;
		}
		throw new ParseException("Invalid colour name");
	}

	private void setGradient(String args) throws ParseException
	{
		String colourStart = "";
		String colourEnd = "";
		int position1 = args.indexOf("@");
		int position2 = args.indexOf("!");
		
		if ((position1 == -1) || (position2 == -1))
		{
			throw new ParseException("DrawString string is missing");
		}
		
		colourStart = args.substring(position1 + 1, position2 - 1);
		colourEnd = args.substring(position2 + 1, args.length());
		image.setGradient(getColour(colourStart), getColour(colourEnd));
	}
	
	private void turtleMoveForward(String args) throws ParseException
	{
		int distance = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		distance = getInteger(tokenizer);

		if (distance < 0)
			throw new ParseException("Invalid values for the draw line command.");

		turtleForward(distance);
	}
	
	private void turtleTurnLeft(String args) throws ParseException
	{
		int angle = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		angle = getInteger(tokenizer);

		if (angle < 0)
			throw new ParseException("Invalid values for the draw line command.");

		turtleLeft(angle);
	}
	
	private void turtleTurnRight(String args) throws ParseException
	{
		int angle = -1;

		StringTokenizer tokenizer = new StringTokenizer(args);
		angle = getInteger(tokenizer);

		if (angle < 0)
			throw new ParseException("Invalid values for the draw line command.");

		turtleRight(angle);
	}

	private void turtleForward(int distance)
	{
		int initialX = (int) turtle.getX();
		int initialY = (int) turtle.getY();
		turtle.moveForward(distance);
		int finalX = (int) turtle.getX();
		int finalY = (int) turtle.getY();
		image.drawLine(initialX, initialY, finalX, finalY);
	}
	
	private void turtleRight(double rotateAngle)
	{
		turtle.turnRight(rotateAngle);
	}
	
	private void turtleLeft(double rotateAngle)
	{
		turtle.turnLeft(rotateAngle);
	}
	
	private int getInteger(StringTokenizer tokenizer) throws ParseException, NumberFormatException
	{
		if (tokenizer.hasMoreTokens())
		{
			try
			{
				return Integer.parseInt(tokenizer.nextToken());
			}
			catch(NumberFormatException ex)
			{
				throw new NumberFormatException("Integer value not proper format.");
			}
		}
		else
		{
			throw new ParseException("Missing Integer value");
		}
	}

	public void parseButton(final Button nextStep, final Button complete) throws IOException
	{
		String line = reader.readLine();
		final ArrayList<String> storeCommads = new ArrayList<String>();
		
		while (line != null)
		{
			storeCommads.add(line);
			line = reader.readLine();
		}
		
		nextStep.setOnAction(new EventHandler<ActionEvent>()
				{
				public void handle(ActionEvent event)
				{
					try
					{
						parseLine(storeCommads.get(i));
						i++;
						frame.messagePost("Next Step completed.");
						if (i == storeCommads.size())
						{
							nextStep.setDisable(true);
							complete.setDisable(true);
							frame.messagePost("Drawing completed.");
						}
					}
					catch (ParseException e)
					{
						frame.messagePost("Parse Exception: " + e.getMessage());
					}
					catch(NumberFormatException e)
					{
					frame.messagePost("Parse Exception: " + e.getMessage());
					}
				}
			});
		complete.setOnAction(new EventHandler<ActionEvent>()
				{
			public void handle(ActionEvent event)
			{
				try
				{
					while (i < storeCommads.size())
					{
						parseLine(storeCommads.get(i));
						i++;
						if (i == storeCommads.size())
						{
							complete.setDisable(true);
							nextStep.setDisable(true);
							frame.messagePost("Drawing completed.");
						}
					}
				}
				catch (ParseException e)
				{
					frame.messagePost("Parse Exception: " + e.getMessage());
				}
				catch(NumberFormatException e)
				{
					frame.messagePost("Parse Exception: " + e.getMessage());
				}
			}
		});
	}
}
