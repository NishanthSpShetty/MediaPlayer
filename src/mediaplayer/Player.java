package mediaplayer;
import com.sun.media.jfxmedia.MediaException;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/* 
 * Copyright (c) 2016 Nishanth Shetty,
 * github.com/NishanthSpShetty
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
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
public class Player extends BorderPane {
	Media media;
	MediaPlayer mplayer;
	MediaView view;
	Pane mpane;
	MediaBar bar;
	MediaControl mControl;
	VBox box;

	Text playing;

	public Player(String file, File file_name) {
		try {
			media = new Media(file);
			// if(media.getError()==null)
			// throw new MediaException("medi_err");

			mplayer = new MediaPlayer(media);
			// if(mplayer.getError()==null){

			// throw new MediaException("medi_err");

		} catch (MediaException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("File error");
			alert.setHeaderText(file_name.getName() + " : ");
			alert.setContentText(" This file is not supported by this player.\n Try using mp4 format");
			alert.showAndWait();
		} // view.autosize();
		playing = new Text(" Playing : " + file_name.getName());
		playing.setFill(Color.RED);
		playing.setFont(Font.font("MonoSpace", 12));
		view = new MediaView(mplayer);
		mpane = new Pane();

		mpane.getChildren().add(view);
		setCenter(mpane);
		bar = new MediaBar(mplayer);
		box = new VBox();
		mControl = new MediaControl(this);
		box.getChildren().addAll(playing, bar, mControl);
		setBottom(box); // bfc2c7
		setStyle("-fx-background-color: #000111");
		mplayer.play();

	}

	public void pause() {
		// TODO Auto-generated method stub
		if (mplayer != null)
			mplayer.pause();
	}
}
