package com.handsome.dat.mp3list;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2;
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    SeekBar seekBar;
    MediaPlayer media;
    ArrayList<Integer> arraySong;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        addArraySong();
        media = MediaPlayer.create(this, arraySong.get(position));

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media.isPlaying()) {
                    media.stop();
                    imageButton2.setImageResource(R.drawable.play);
                } else {
                    media.start();
                    imageButton2.setImageResource(R.drawable.pause);
                }
                TimeSong();
                UpdateTime();
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                imageButton2.setImageResource(R.drawable.play);
                media = MediaPlayer.create(MainActivity.this, arraySong.get(position));
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size() - 1) {
                    position = 0;
                }
                if (media.isPlaying()) {
                    media.stop();
                }
                media = MediaPlayer.create(MainActivity.this, arraySong.get(position));
                media.start();
                TimeSong();
            }
        });
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < 0) {
                    position = arraySong.size() - 1;
                }
                if (media.isPlaying()) {
                    media.stop();
                }
                media = MediaPlayer.create(MainActivity.this, arraySong.get(position));
                media.start();
                TimeSong();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media.seekTo(seekBar.getProgress());
            }
        });
    }
    private void TimeSong() {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss");
        textView2.setText(time.format(media.getDuration()));

        seekBar.setMax(media.getDuration());
    }
    private void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(media.getCurrentPosition());

                SimpleDateFormat time = new SimpleDateFormat("mm:ss");
                textView1.setText(time.format(media.getCurrentPosition()));

                media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1) {
                            position = 0;
                        }
                        if (media.isPlaying()) {
                            media.stop();
                        }
                        media = MediaPlayer.create(MainActivity.this, arraySong.get(position));
                        media.start();
                        TimeSong();
                    }
                });
                handler.postDelayed(this, 1000);
            }
        }, 100);
    }
    private void addArraySong() {
        arraySong = new ArrayList<>();
        arraySong.add(R.raw.beautifulinwhite);
        arraySong.add(R.raw.cryonmyshoulder);
        arraySong.add(R.raw.endless);
        arraySong.add(R.raw.iwillberighthere);
        arraySong.add(R.raw.seeyouagain);

    }

    private void anhxa() {
        textView1 = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }
}
