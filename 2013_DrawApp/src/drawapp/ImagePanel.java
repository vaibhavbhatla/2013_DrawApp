package drawapp;

import java.io.File;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ImagePanel extends HBox
{
	private Group graphic = new Group();
	private Paint colour = Color.BLACK;
	private Boolean shadowDrop = false;
	private Boolean reflection = false;
	private Boolean blurGaussian = false;

	public ImagePanel(int panelWidth, int panelHeight)
	{
		setImageSize(panelWidth, panelHeight);
	}

	private void setImageSize(int imageWidth, int imageHeight)
	{
		this.setMaxSize(imageWidth, imageHeight);
		this.getChildren().add(graphic);
		clear(Color.WHITE);
	}

	public void setBackgroundColour(Color colour)
	{	
		this.setStyle("-fx-background-color: rgb(" + (int) (colour.getRed()*255) + "," + (int) (colour.getGreen()*255) + "," + (int) (colour.getBlue()*255)  + ");");
	}

	public void clear(Color colour)
	{
		setBackgroundColour(colour);
	}

	public void setColour(Color colour)
	{
		this.colour = colour;
	}

	public void setReflection(boolean on)
	{
		this.reflection = true;
	}

	public void setDropShadow(boolean on)
	{
		this.shadowDrop = true;
	}
	
	public void setGaussianBlur(boolean on)
	{
		this.blurGaussian = on;
	}

	public void setGradient(Color colourStart, Color colourEnd)
	{
		LinearGradient gradientLinear = LinearGradientBuilder.create()
				.stops(new Stop(0, colourStart),
				new Stop(1, colourEnd))
				.cycleMethod(CycleMethod.NO_CYCLE)
				.startX(0)
				.startY(0)
				.endX(1)
				.endY(0)
				.proportional(true)
				.build();
		this.colour = gradientLinear;
	}

	public void drawLine(int x1, int y1, int x2, int y2)
	{
		Line line = new Line(x1, y1, x2, y2);
		line.setStroke(this.colour);
		this.colour = Color.BLACK;
		
		if (blurGaussian)
		{
			final GaussianBlur gb = new GaussianBlur();
			line.setEffect(gb);
		}
		
		if (reflection)
		{
			final Reflection refl = new Reflection();
			line.setEffect(refl);
		}
		
		graphic.getChildren().add(line);
	}

	public void drawRect(int x1, int y1, int x2, int y2)
	{
		Rectangle rect = new Rectangle(x1, y1, x2, y2);
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.BLACK);
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			rect.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection reflect = new Reflection();
			rect.setEffect(reflect);
		}
		
		graphic.getChildren().add(rect);
	}

	public void fillRect(int x1, int y1, int x2, int y2)
	{
		Rectangle rectFill = new Rectangle(x1, y1, x2, y2);
		rectFill.setFill(this.colour);
		this.colour = Color.BLACK;
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			rectFill.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection refl = new Reflection();
			rectFill.setEffect(refl);
		}
		
		graphic.getChildren().add(rectFill);
	}

	public void drawString(int x, int y, String s)
	{
		Text t = new Text(x, y, s);
		if (shadowDrop)
		{
			DropShadow shadow = new DropShadow();
			t.setEffect(shadow);
		}
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			t.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection reflect = new Reflection();
			t.setEffect(reflect);
		}
		
		graphic.getChildren().add(t);
	}

	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		Arc arc = new Arc(x, y, width / 2, height / 2, startAngle, arcAngle);
		arc.setStroke(this.colour);
		arc.setFill(Color.BLACK);
		this.colour = Color.BLACK;
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			arc.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection reflect = new Reflection();
			arc.setEffect(reflect);
		}
		
		graphic.getChildren().add(arc);
	}

	public void drawOval(int x, int y, int width, int height)
	{
		Ellipse oval = new Ellipse(x, y, width, height);
		oval.setStroke(this.colour);
		oval.setFill(Color.BLACK);
		this.colour = Color.BLACK;
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			oval.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection reflect = new Reflection();
			oval.setEffect(reflect);
		}
		
		graphic.getChildren().add(oval);
	}

	public void drawImage(int x, int y, int width, int height, String path)
	{
		File file = new File(path);
		Image image = new Image(file.toURI().toString());
		ImageView img = new ImageView(image);
		img.setFitWidth(width);
		img.setFitHeight(height);
		img.setPreserveRatio(true);
		img.setSmooth(true);
		img.setCache(true);
		
		if (blurGaussian)
		{
			GaussianBlur blurGauss = new GaussianBlur();
			img.setEffect(blurGauss);
		}
		
		if (reflection)
		{
			Reflection reflect = new Reflection();
			img.setEffect(reflect);
		}
		
		graphic.getChildren().add(img);
	}
	
	//TO-DO: FIX!
	public Node setEffects(Node node)
	{
		if (blurGaussian)
		{
			GaussianBlur blurGaussE = new GaussianBlur();
			node.setEffect(blurGaussE);
		}
		
		if (reflection)
		{
			Reflection reflectE = new Reflection();
			node.setEffect(reflectE);
		}
		
		return node;
	}
}
