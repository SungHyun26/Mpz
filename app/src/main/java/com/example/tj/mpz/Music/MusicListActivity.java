package com.example.tj.mpz.Music;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tj.mpz.R;
import com.example.tj.mpz.SplashActivity;

public class MusicListActivity extends AppCompatActivity {

    private final String TAG = "MusicListActivity";

    private Button startButton;
    ContentResolver contentResolver;
    Cursor cursor;
    ListView musicList;

    private int forwordPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

         /* 뷰 셋팅 */
        startButton = (Button)findViewById(R.id.startButton);

         /* 리스트 셋팅 */
        musicList = (ListView)findViewById(R.id.musicList);
        contentResolver = getContentResolver();
        cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        final MusicListAdapter musicListAdapter = new MusicListAdapter(this , cursor);
        musicListAdapter.showList();
        musicList.setAdapter(musicListAdapter);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG,"선택된 아이디"+musicListAdapter.getItemId(position));

                    setPosition(position);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forwordPosition != -1) {
                    Intent musicIntent = new Intent(getApplicationContext(), MusicDataActivity.class);
                    musicIntent.putExtra("MUSIC_ID",musicListAdapter.getItemId(getForwordPosition()));
                    startActivity(musicIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"음악을 선택해 주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setPosition(int p){
        forwordPosition = p;
    }

    public int getForwordPosition() {
        return forwordPosition;
    }
}
