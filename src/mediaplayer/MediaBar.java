package mediaplayer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

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
public class MediaBar extends HBox {
	Label playing = new Label(" Playing : ");
	DateFormat df = new SimpleDateFormat("HH:mm:ss");
	Slider time = new Slider();
	Label timeLabel = new Label("");
	Label totalTimeLable = new Label("00:00:00");
	MediaPlayer player;

	public MediaBar(MediaPlayer play) {
		player = play;
		setAlignment(Pos.CENTER);
		setPadding(new Insets(5, 10, 5, 10));

		// VBox sliderBox = new VBox();
		HBox.setHgrow(time, Priority.ALWAYS);
		getChildren().add(timeLabel);
		getChildren().add(time);
		getChildren().add(totalTimeLable);
		player.currentTimeProperty().addListener(
				(ov, od, nd) -> {
					double milli = nd.toMillis();
					int sec_ = (int) (milli / 1000) % 60;
					int min_ = (int) ((milli / (1000 * 60)) % 60);
					int hr_ = (int) ((milli / (1000 * 60 * 60)) % 24);
					timeLabel.setText(String.format("%02d:%02d:%02d", hr_,
							min_, sec_));

					milli = player.getTotalDuration().toMillis();
					sec_ = (int) (milli / 1000) % 60;
					min_ = (int) ((milli / (1000 * 60)) % 60);
					hr_ = (int) ((milli / (1000 * 60 * 60)) % 24);
					totalTimeLable.setText(String.format("%02d:%02d:%02d", hr_,
							min_, sec_));

					updateValues();
				});
		time.valueProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				if (time.isPressed()) {
					player.seek(player.getMedia().getDuration()
							.multiply(time.getValue() / 100));
				}
			}
		});
	}

	protected void updateValues() {
		Platform.runLater(new Runnable() {
			public void run() {
				time.setValue(player.getCurrentTime().toMillis()
						/ player.getTotalDuration().toMillis() * 100);
			}
		});

	}
}
