package mediaplayer;
import java.io.File;
import java.net.*;

import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/* 
 * Copyright (c) 2016 Nishanth Shetty,
 * github.com/NishanthSpShetty
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sub license, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

public class Main extends Application {
	int done = 0;
	int track = 1;
	Player player;
	Stage primaryStage;
	FileChooser filechooser;
	Scene scene;
	File file, fname;
	static List<File> files;
	ObservableMap<String, Object> metaData;
	Menu file_, help;
	MenuBar menu;
	MenuItem open, openMultipleFile, showInfo, helpItem, about, updates,
			online, exit;

	public void start(Stage primaryStage) {
		// create OPen media menu
		this.primaryStage = primaryStage;
		open = new MenuItem(" Open File ");
		openMultipleFile = new MenuItem(" Open Mutltiple File");
		showInfo = new MenuItem(" Show media info");
		online = new MenuItem(" Online Stream");
		file_ = new Menu(" Media File ");
		exit = new MenuItem(" Quit ");
		file_.getItems().addAll(open, openMultipleFile, online, showInfo, exit);
		exit.setOnAction((ae) -> {
			System.exit(0);
		});
		menu = new MenuBar();

		// create help menu
		help = new Menu(" Help ");
		helpItem = new MenuItem("help");
		about = new MenuItem(" About ");
		updates = new MenuItem("Updates");

		help.getItems().addAll(helpItem, updates, about);
		menu.getMenus().addAll(file_, help);
		filechooser = new FileChooser();
		filechooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Video Files", "*.mp3",
						"*.mp4"),
						new FileChooser.ExtensionFilter("Audio Files", "*.wav",
								"*.mp3", "*.aac"),
						new FileChooser.ExtensionFilter("All Files", "*.*"));
		helpItem.setOnAction((ae) -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Media Player");
			alert.setHeaderText("Help ");
			alert.setContentText(" M         : mute or unmute \n "
					+ "SPACE : pause or play \n" + "UP    : Vol+"
					+ "DOWN  : Vol-" + "P     : Previous media"
					+ "N     : Next media"
					+ "-----------------------------------\n"
					+ "Supported files \n\tMP3\n\tMP4");
			alert.showAndWait();
		});

		online.setOnAction((ae) -> {
			Dialog<String> dialog = new TextInputDialog("Add url here");
			dialog.setHeaderText("Online streaming");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				System.out.println(" URL " + result.get());
				// playUrl(result.get());

				try {
					if (player != null)
						player.pause();
					player = new Player(new URL(result.get()).toExternalForm(),
							new File("ONLINE FILE"));
					// System.out.println(view.getChildren().remove(player));
					player.setTop(menu);
					scene = new Scene(player, 720, 520, Color.BLACK);
					System.out.println("Playing " + file.getName());
					primaryStage.setScene(scene);
					scene.setOnKeyPressed((a_e) -> {
						// TODO Auto-generated method stub
						handleKeyEvent(a_e);
					});
				} catch (MalformedURLException e) {
					// TODO: handle exception
				}

				primaryStage.show();

			}

		});

		showInfo.setOnAction((ae) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Media Player");
			alert.setHeaderText("Media Information");

			double milli = player.mplayer.getTotalDuration().toMillis();
			int sec_ = (int) (milli / 1000) % 60;
			int min_ = (int) ((milli / (1000 * 60)) % 60);
			int hr_ = (int) ((milli / (1000 * 60 * 60)) % 24);

			alert.setContentText(" Playing           : " + fname.getName()
					+ "\n" + " Path              : " + fname.getPath() + "\n"
					+ " Total Duration    : "
					+ String.format("%02d:%02d:%02d", hr_, min_, sec_) + "\n"
					+ " Volume            : "
					+ (int) (player.mplayer.getVolume() * 100) + "%\n"
					+ " Spectrum Interval : "
					+ player.mplayer.getAudioSpectrumInterval() + "\n"
					+ " Looping           : " + MediaControl.isLooping());
			alert.showAndWait();

		});
		about.setOnAction((ae) -> {
			Stage aboutWin = new Stage();
			Text text = (new Text(
					"This is a simple media player written using JavaFx library. \n\nDevoloper\n\n"
							+ " Nishanth Shetty \n Rithik Suresh Jain\n Rishav Kumar Saha"));
			HBox alertbox = new HBox();
			alertbox.getChildren().add(text);
			aboutWin.setTitle("Media Player");
			aboutWin.setScene(new Scene(alertbox, 340, 150, Color.BLACK));
			aboutWin.showAndWait();
			aboutWin.close();

		});
		updates.setOnAction((ae) -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Media Player");
			alert.setHeaderText("Updates");
			alert.setContentText("Your MediaPlayer is up to date.\n"
					+ "No updates are available");
			alert.showAndWait();
		});

		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				filechooser.setInitialDirectory(new File("F:\\"));
				if (player != null)
					player.pause();
				file = filechooser.showOpenDialog(primaryStage);
				if (file != null) {
					playMe(file);
				}
			}
		});

		openMultipleFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				filechooser.setInitialDirectory(new File("F:\\"));
				if (player != null)
					player.pause();
				files = filechooser.showOpenMultipleDialog(primaryStage);
				done = 1;
				if (files != null) {
					playMe(files.get(0));
				}
			}
		});

		VBox view = new VBox();
		Image img = new Image("file:///F:/video-player-home.jpg");
		ImageView imgv = new ImageView(img);
		view.getChildren().addAll(menu, imgv);

		scene = new Scene(view, 620, 340, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Media Player");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				// TODO Auto-generated method stub
				handleKeyEvent(arg0);
			}
		});

		primaryStage.show();
	}

	synchronized protected void playMe(File file) {
		// TODO Auto-generated method stub
		this.fname = file;
		try {
			if (player != null)
				player.pause();
			player = new Player(file.toURI().toURL().toExternalForm(), file);
			// System.out.println(view.getChildren().remove(player));
			player.setTop(menu);
			scene = new Scene(player, 720, 520, Color.BLACK);
			System.out.println("Playing " + file.getName());
			primaryStage.setScene(scene);
			scene.setOnKeyPressed((ae) -> {
				// TODO Auto-generated method stub
				handleKeyEvent(ae);
			});
		} catch (MalformedURLException e) {
			// TODO: handle exception
		}

		primaryStage.show();

	}

	protected void handleKeyEvent(KeyEvent ae) {
		// TODO Auto-generated method stub
		KeyCode key = ae.getCode();
		if (key == KeyCode.ESCAPE) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(" EXIT ");
			alert.setContentText("Media player will be closed\n ");
			// using java8 filter(predicate) ifPresent(consumer)
			alert.showAndWait().filter(respons -> respons == ButtonType.OK)
					.ifPresent((r) -> {
						System.exit(0);
					});
		} else if (key == KeyCode.SPACE) {
			player.mControl.play.fire();
		} else if (key == KeyCode.M)
			player.mControl.mute.fire();
		else if (key == KeyCode.UP)
			player.mControl.vol.setValue(player.mControl.vol.getValue() + 5);
		else if (key == KeyCode.DOWN)
			player.mControl.vol.setValue(player.mControl.vol.getValue() - 5);
		else if (done == 1 && key == KeyCode.N) {
			if (track == files.size())
				track = 0;
			track++;
			playMe(files.get(track));
		} else if (done == 1 && key == KeyCode.P) {
			if (track == 0)
				track = files.size();
			track--;
			playMe(files.get(track));
		}
	}

	public static void main(String args[]) {
		launch(args);
	}
}
