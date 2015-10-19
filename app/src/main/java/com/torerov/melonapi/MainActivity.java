package com.torerov.melonapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter<Song> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        Button button = (Button)findViewById(R.id.btn_melon);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
                new MelonTask().execute(1, 10);
            }
        });


    }

    public static final String MELONURL = "http://apis.skplanetx.com/melon/charts/realtime?count=%s&page=%s&version=1";
    //page, count 받음
    class MelonTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            int page = params[0];
            int count = params[1];
            String urlText = String.format(MELONURL, count, page);
            try {
                URL url = new URL(urlText);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("appKey", "92b4b502-955b-3bd8-bade-c3e9c0f15632");
                int code = conn.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n\r");
                    }
                    return sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
//                Toast.makeText(MainActivity.this, "data : " + s, Toast.LENGTH_SHORT).show();
//                try {
//                    JSONObject obj = new JSONObject(s);
//                    MelonResult result = new MelonResult();
//                    result.parsing(obj);
//                    for (Song songList : result.melon.songs.songList) {
//                        mAdapter.add(songList);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Gson gson = new Gson();
                MelonResult result = gson.fromJson(s, MelonResult.class);
                for (Song song : result.melon.songs.songList) {

                    mAdapter.add(song);
                }
            } else {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
