package mediaplayer;

import java.awt.Robot;
import javafx.util.Duration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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

public class MediaControl extends HBox implements EventHandler<ActionEvent> {
	Button play, stop, loop;
	Button forward, backwrd, prev, next, mute;
	Player mPlayer;
	Slider vol;
	Label label = new Label(" Volume : ");

	public MediaControl(Player player) {
		// TODO Auto-generated constructor stub
		this.mPlayer = player;
		vol = new Slider();
		vol.setPrefWidth(70);
		vol.setMinWidth(30);
		vol.setValue(100);

		play = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images/pause-icon.jpg")));
		play.setId("0");
		stop = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images/stop-button2.jpg")));
		stop.setId("1");
		prev = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images/prev.jpg")));
		prev.setId("3");
		next = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images/next.jpg")));
		next.setId("4");
		mute = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images/mute-icon.jpg")));
		mute.setId("5");
		loop = new Button(
				"",
				new ImageView(
						new Image(
								"file:///D:/eclipse-luna-java/MediaPlayer/src/images//loop-off.jpg")));
		loop.setId("6");
		setSpacing(3);
		HBox.setHgrow(play, Priority.ALWAYS);
		HBox.setHgrow(prev, Priority.ALWAYS);
		HBox.setHgrow(mute, Priority.ALWAYS);
		HBox.setMargin(vol, new Insets(6, 12, 0, 10));
		HBox.setHgrow(stop, Priority.ALWAYS);
		HBox.setHgrow(next, Priority.ALWAYS);

		HBox.setHgrow(loop, Priority.ALWAYS);
		loop.setMaxWidth(Double.MAX_VALUE);
		play.setMaxWidth(Double.MAX_VALUE);
		stop.setMaxWidth(Double.MAX_VALUE);

		mute.setMaxWidth(Double.MAX_VALUE);
		prev.setMaxWidth(Double.MAX_VALUE);
		next.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(vol, Priority.ALWAYS);
		label.setFont(Font.font("Verdana", FontWeight.BLACK, 20));
		play.setOnAction(this);
		stop.setOnAction(this);
		prev.setOnAction(this);
		next.setOnAction(this);
		mute.setOnAction(this);
		loop.setOnAction(this);
		getChildren().add(play);
		getChildren().add(mute);
		getChildren().add(prev);
		getChildren().add(stop);
		getChildren().add(next);
		getChildren().add(loop);
		getChildren().add(label);
		getChildren().add(vol);
		vol.valueProperty().addListener(new InvalidationListener() {

			public void invalidated(Observable ov) {
				// if(vol.isPressed()){
				mPlayer.mplayer.setVolume(vol.getValue() / 100);
			}
		});

	}

	Status status;
	Duration prevSeek;
	private static boolean looping = false;

	static String isLooping() {
		if (looping)
			return "Enabled";
		else
			return "Disabled";
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handle(ActionEvent ae) {
		// TODO Auto-generated method stub
		Button event = (Button) ae.getSource();
		try {
			switch (event.getId()) {
			// handle play | pause
			case "0":
				status = mPlayer.mplayer.getStatus();
				if (status == Status.PLAYING) {
					if (mPlayer.mplayer.getCurrentTime().greaterThanOrEqualTo(
							mPlayer.mplayer.getTotalDuration())) {
						// mPlayer.mplayer.seek(mPlayer.mplayer.getStartTime());
						mPlayer.mplayer.play();
						prevSeek = new Duration(0);
						// System.out.println("Seek reset...");

					} else {

						prevSeek = mPlayer.mplayer.getCurrentTime();

						// System.out.println("Pausing2");
						mPlayer.mplayer.pause();

						play.setGraphic(new ImageView(
								new Image(
										"file:///D:/eclipse-luna-java/MediaPlayer/src/images//play-icon1.png")));
					}
				}
				if (status == Status.PAUSED || status == Status.HALTED
						|| status == Status.STOPPED) {
					System.out.println(" Seeking " + prevSeek);

					mPlayer.mplayer.play();
					// mPlayer.mplayer.seek(mPlayer.mplayer.getMedia().getDuration().multiply(prevSeek.toMillis()));
					mPlayer.bar.time.setValue(prevSeek.toMillis() / 1000);
					// mPlayer.mplayer.seek(prevSeek);
					prevSeek = new Duration(0);
					play.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//pause-icon.jpg")));
				}
				// handle stop here
			case "1":
				status = mPlayer.mplayer.getStatus();
				if (status == Status.PLAYING) {
					mPlayer.mplayer.stop();
					play.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//play-icon1.png")));
				}
				break;
			case "3":
				// prev
				Robot r = new Robot();
				r.keyPress(KeyCode.P.impl_getCode());
				break;
			case "4":
				// next
				Robot r1 = new Robot();
				r1.keyPress(KeyCode.N.impl_getCode());

				break;
			case "5":

				if (!mPlayer.mplayer.isMute()) {
					mPlayer.mplayer.setMute(true);
					mute.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//unmute-icon.jpg")));
				} else {
					mPlayer.mplayer.setMute(false);
					mute.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//mute-icon.jpg")));

				}
			case "6":
				if (!looping) {
					looping = true;
					loop.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//loop-on.jpg")));
					mPlayer.mplayer.setCycleCount(MediaPlayer.INDEFINITE);
				} else {
					looping = false;

					loop.setGraphic(new ImageView(
							new Image(
									"file:///D:/eclipse-luna-java/MediaPlayer/src/images//loop-off.jpg")));
					mPlayer.mplayer.setCycleCount(0);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
