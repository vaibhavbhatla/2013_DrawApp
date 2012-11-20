package drawapp;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class MainWindow
{
	public static final int SCENEWIDTH = 500;
	public static final int SCENEHEIGHT = 500;

	private ImagePanel drawingArea;
	private TextArea messageViewer;
	private Button btExit;
	private Button btNext;
	private Button btFinish;
	private Button btSave;

	public MainWindow(Stage stage)
	{
		this(stage, SCENEWIDTH, SCENEHEIGHT);
	}

	public MainWindow(final Stage stage, int width, int height)
	{
		stage.setTitle("DrawApp");
		final Parent root = GroupBuilder.create()
				.children(
						buildGUI(stage, width, height)
				)
				.build();
		Scene scene = new Scene(root, width, height);
		//scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
	}

	private GridPane buildGUI(final Stage stage, final int width, final int height)
	{
		GridPane gridpane = new GridPane();
		
		messageViewer = TextAreaBuilder.create()
				.wrapText(true)
				.prefRowCount(6)
				.prefWidth(width)
				.editable(false)
				.build();
		
		drawingArea = new ImagePanel(width, height);
		drawingArea.setPrefWidth(width);
		drawingArea.setPrefHeight(height-200);
		
		btNext = ButtonBuilder.create()
				.text("Next Step")
				.styleClass("first")
				.build();
		
		btFinish = ButtonBuilder.create()
				.text("Finish Drawing")
				.build();
		
		btExit = ButtonBuilder.create()
				.text("Exit")
				.styleClass("last")
				.onAction(new EventHandler<ActionEvent>()
					{
						@Override
						public void handle(ActionEvent event)
						{
							Platform.exit();
						}
					})
				.build();
		
		btSave = ButtonBuilder.create()
				.text("Save Image...")
				.onAction(new EventHandler<ActionEvent>()
					{
						@Override
						public void handle(ActionEvent event)
						{
							try
							{
								FileChooser imageLocation = new FileChooser();
								FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
								imageLocation.getExtensionFilters().add(pngFilter);
								
								File file = imageLocation.showSaveDialog(stage);
								if(file != null)
								{
									if(!file.toString().endsWith(".png"))
									{
										file = new File(file.getAbsolutePath().toString() + ".png");
									}
									ImageIO.write(SwingFXUtils.fromFXImage(drawingArea.snapshot(null, null), null), "png", file);
					            }
							}
							catch (IOException ex)
							{
								messagePost("Saving Failed. Please choose another file");
							}
						}
					})
				.build();
		
		Region spacer = new Region();
        spacer.getStyleClass().setAll("spacer");
        
        ToolBar buttonBar = ToolBarBuilder.create()
        		.items(
        				spacer,
        				HBoxBuilder.create()
        				.children(
        						btNext,
        						btFinish,
        						btSave,
        						btExit
        						)
        				.padding(new Insets(5, 0, 5, 0))
        				.styleClass("segmented-button-bar")
        				.build()
        				)
        		.build();
		
		gridpane.add(drawingArea, 0, 0);
		gridpane.add(messageViewer, 0, 1);
		gridpane.add(buttonBar, 0, 2);

		return gridpane;
	}

	public ImagePanel getImagePanel()
	{
		return drawingArea;
	}

	public Button buttonNextStep()
	{
		return btNext;
	}

	public Button buttonFinishDrawing()
	{
		return btFinish;
	}

	public void messagePost(String s)
	{
		messageViewer.setText(s);
	}

	public void resizeDrawingArea(int width, int height)
	{
		drawingArea.setPrefHeight(height - 200);
		drawingArea.setPrefWidth(width);
		messageViewer.setPrefWidth(width);
	}
}