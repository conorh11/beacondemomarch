package com.example.conor.beacon_demo;



import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class BeaconID2222 extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    //Atari 2600 details and photos, video is of Commercial
    TextView username;
    TextView name1;

    public static ArrayList<Drawable> drawable = new ArrayList<Drawable>();
    ImageView image;
    ImageView image2;
    ProgressDialog mProgressDialog;
    // String youtubeURL="https://www.youtube.com/watch?v=SrsHBjzt2E8";
    public static String[] URLs = {"http://140.203.217.200/images/atari2600.jpg","http://140.203.217.200/images/atari2600cartridge.jpg"};


    //URL to get JSON Array
    private static String urltext = "http://140.203.217.200/beacon2.php";
    //JSON Node Names
    private static final String TAG_START = "beacon2";
    private static final String TAG_NAME = "name";
    private static final String TAG_INFORMATION = "information";

    public static final String API_KEY = "AIzaSyB-t2BcSAeBxRvDCqWs8evOhACcWN3imbM";

    //http://youtu.be/<VIDEO_ID>
    public static final String VIDEO_ID = "913xrM9FYpI";


    JSONArray user = null;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_id2222);
        new JSONParse().execute();
        image = (ImageView) findViewById(R.id.imageViewtwo);
        image2 = (ImageView) findViewById(R.id.imageViewcart);
        new DownloadImage().execute();

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);

        final Button startButton = (Button) findViewById(R.id.StartTour);



        Log.d("NOTIFICATION_TEST", "This will should now send a notification");
        Intent w = new Intent(this, NotificationService.class);
        startService(w);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });





    }



    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {

        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);

        }




    }

    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };


    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();

    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            username = (TextView)findViewById(R.id.description);
            name1 = (TextView)findViewById(R.id.whereyouaretext);


        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(urltext);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            //pDialog.dismiss();
            try {
                // Getting JSON Array
                user = json.getJSONArray(TAG_START);
                JSONObject c = user.getJSONObject(0);
                // Storing  JSON item in a Variable
                String name = c.getString(TAG_NAME);
                String info = c.getString(TAG_INFORMATION);

                //String email = c.getString(TAG_EMAIL);
                //Set JSON Data in TextView
                username.setText(info);
                name1.setText(name);
                //  email1.setText(email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setImage()
    {
        if(drawable.get(0) == null)
        {
            System.out.println("DRAWABLE JEST NULL");
        }
        image.setBackgroundDrawable(drawable.get(0));
        image2.setBackgroundDrawable(drawable.get(1));
    }

    private class DownloadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Create a progressdialog
            mProgressDialog = new ProgressDialog(BeaconID2222.this);
           // Set progressdialog title
           mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
           mProgressDialog.setMessage("Loading...");
          mProgressDialog.setIndeterminate(false);
          // Show progressdialog
           mProgressDialog.show();


        }


        @Override
        protected Void doInBackground(Void... params) {

            downloadImage();
            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {


                setImage();

       }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mProgressDialog!= null)
            mProgressDialog.dismiss();


    }


    @SuppressWarnings("deprecation")
    private void downloadImage()
    {
        //Prepare to download image

        URL url;

        InputStream in;
        BufferedInputStream buf;

        //BufferedInputStream buf;
        for(int i = 0; i<URLs.length; i++)
        {
            try {
                url = new URL(URLs[i]);
                in = url.openStream();

                // Read the inputstream
                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                drawable.add(new BitmapDrawable(bMap));

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

        }

    }


}





