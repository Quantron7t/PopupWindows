package com.amplitude.tron.popupwindows;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;


import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnClosePopup;
    Button btnCreatePopup;
    Button streamThree;
    ImageButton playControlButton;

    MediaPlayer mediaPlayer = new MediaPlayer();
    private PopupWindow pwindo;

    ImageView streamOne,streamTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // myMediaPlayer = new MediaPlayer();
        btnCreatePopup = (Button) findViewById(R.id.btn_create_popup);
        btnCreatePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });

        playControlButton = (ImageButton) findViewById(R.id.playerControl);

        if (!mediaPlayer.isPlaying())
        {
            playControlButton.setImageResource(R.drawable.button_play);
        }
        else
        {
            playControlButton.setImageResource(R.drawable.button_stop);
        }

        playControlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()){
                    playControlButton.setImageResource(R.drawable.button_play);
                    playStream("http://136.243.133.81:8000/live");
                }
                else
                {
                    playControlButton.setImageResource(R.drawable.button_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        });

    }

    public void playStream(String url)
    {
         mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playControlButton = (ImageButton) findViewById(R.id.playerControl);
                playControlButton.setImageResource(R.drawable.button_stop);
                mp.start();
            }
        });
    }

    //POPUP WINDOW
    private void initiatePopupWindow() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w = dm.widthPixels;
        int h = dm.heightPixels;
        int newWidth = w-50;
        int newHeight=h-300;

        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout,newWidth, newHeight, true);

            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setFocusable(true);


            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

            streamThree = (Button) layout.findViewById(R.id.button_stream_three);
            streamThree.setOnClickListener(go_to_stream_three);

            streamOne = (ImageView) layout.findViewById(R.id.button_stream_one);
            streamOne.setOnClickListener(availableStreams);
            streamTwo = (ImageView) layout.findViewById(R.id.button_stream_two);
            streamTwo.setOnClickListener(availableStreams);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            mediaPlayer.reset();
            playStream("http://136.243.133.81:8000/live");
            pwindo.dismiss();
        }
    };


    private View.OnClickListener go_to_stream_three = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();
        }
    };


    /*private View.OnClickListener streamOneClick = new View.OnClickListener() {
        public void onClick(View v) {
            mediaPlayer.reset();
            playStream("http://136.243.133.81:8000/live");
            pwindo.dismiss();
        }
    };

    private View.OnClickListener streamTwoClick = new View.OnClickListener() {
        public void onClick(View v) {
            mediaPlayer.reset();
            playStream("http://mp3channels.webradio.antenne.de/top-40.aac");
        }
    };*/

    private View.OnClickListener availableStreams = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_stream_one:
                    mediaPlayer.reset();
                    playStream("http://136.243.133.81:8000/live");
                    break;
                case R.id.button_stream_two:
                    mediaPlayer.reset();
                    playStream("http://mp3channels.webradio.antenne.de/top-40.aac");
                    break;
            }
        }
    };
}

