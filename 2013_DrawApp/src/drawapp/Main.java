package drawapp;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application
{	
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException
	{
		final MainWindow windowMain = new MainWindow(stage);
		ImagePanel imagePanel = windowMain.getImagePanel();
		InputStream inputFile = new FileInputStream("C:\\Vaibhav\\Work\\Java\\2013_DrawApp\\test.out");
		InputStream streamIn = new ByteArrayInputStream( "TL 30\nFD 100".getBytes() );
		Reader reader = new InputStreamReader(streamIn);//System.in);
		Parser parser = new Parser(reader, imagePanel, windowMain, stage);
		Button nextStep = windowMain.buttonNextStep();
		Button finishDrawing = windowMain.buttonFinishDrawing();
		parser.parseButton(nextStep, finishDrawing);
		stage.show();
	}
}