
import java.io.File;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class ImagePanelBuilder<B extends ImagePanelBuilder<B>> extends HBoxBuilder<B> {
	private Paint colourG = Color.web("000000");
	private Group graphics = new Group();
	private Boolean dropShadow = false;
	private Boolean gaussianBlur = false;
	private Boolean reflection = false;

	public ImagePanelBuilder(int width, int height) {
		imageSize(width, height);
	}

	private void imageSize(int width, int height) {
		this.maxWidth(width);
		this.maxHeight(height);
		this.children(graphics);
		//this.setMaxSize(width, height);
		//this.getChildren().add(graphic);
		clear(Color.WHITE);
	}

	public void backgroundColour(Color colour) {
		this.style("-fx-fill:#" + colour.toString() + ";");
	}

	public void clear(Color colour) {
		backgroundColour(colour);
	}

	public void setColour(Color colour) {
		this.colourG = colour;
	}

	public void blur(boolean on) {
		gaussianBlur = on;
	}

	public void reflection(boolean on) {
		reflection = on;
	}

	public void dropShadow(boolean on) {
		dropShadow = on;
	}

	public void gradient(Color colourStart, Color colourEnd) {
		Stop[] stops = new Stop[] { new Stop(0, colourStart),
				new Stop(1, colourEnd) };
		LinearGradient lg = new LinearGradient(0, 0, 1, 0, true,
				CycleMethod.NO_CYCLE, stops);
		colourG = lg;
	}

	public void line(int x1, int y1, int x2, int y2) {
		Line line = new Line(x1, y1, x2, y2);
		line.setStroke(colourG);
		colourG = Color.BLACK;
		if (gaussianBlur) {
			GaussianBlur gaussianBlurE = new GaussianBlur();
			line.setEffect(gaussianBlurE);
		}
		if (reflection) {
			Reflection reflectionE = new Reflection();
			line.setEffect(reflectionE);
		}
		graphics.getChildren().add(line);

	}

	public void ect(int x1, int y1, int x2, int y2) {
		Rectangle rect = new Rectangle(x1, y1, x2, y2);
		rect.setStroke(Paint.valueOf("000000"));
		rect.setFill(Paint.valueOf("00000000"));
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			rect.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			rect.setEffect(reflectionE);
		}
		graphics.getChildren().add(rect);

	}

	public void rectFill(int x1, int y1, int x2, int y2) {
		Rectangle rectFill = new Rectangle(x1, y1, x2, y2);
		rectFill.setFill(colourG);
		colourG = Color.BLACK;
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			rectFill.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			rectFill.setEffect(reflectionE);
		}
		graphics.getChildren().add(rectFill);

	}

	public void drawString(int x, int y, String s) {
		Text t = new Text(x, y, s);
		if (dropShadow == true) {
			final DropShadow dropShadowE = new DropShadow();
			t.setEffect(dropShadowE);
		}
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			t.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			t.setEffect(reflectionE);
		}
		graphics.getChildren().add(t);

	}

	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		Arc arc = new Arc(x, y, width / 2, height / 2, startAngle, arcAngle);
		arc.setStroke(colourG);
		arc.setFill(Paint.valueOf("00000000"));
		colourG = Color.web("000000");
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			arc.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			arc.setEffect(reflectionE);
		}
		graphics.getChildren().add(arc);
	}

	public void drawOval(int x, int y, int width, int height) {
		Ellipse oval = new Ellipse(x, y, width, height);
		oval.setStroke(colourG);
		oval.setFill(Paint.valueOf("00000000"));
		colourG = Color.web("000000");
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			oval.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			oval.setEffect(reflectionE);
		}
		graphics.getChildren().add(oval);
	}

	public void drawImage(int x, int y, int width, int height, String path) {
		File file = new File(path);
		Image image = new Image(file.toURI().toString());
		ImageView iv = new ImageView(image);
		iv.setFitWidth(width);
		iv.setFitHeight(height);
		iv.setPreserveRatio(true);
		iv.setSmooth(true);
		iv.setCache(true);
		if (gaussianBlur == true) {
			final GaussianBlur gaussianBlurE = new GaussianBlur();
			iv.setEffect(gaussianBlurE);
		}
		if (reflection == true) {
			final Reflection reflectionE = new Reflection();
			iv.setEffect(reflectionE);
		}
		graphics.getChildren().add(iv);
	}
}
