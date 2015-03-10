package com.example.conor.beacon_demo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;


public class BeaconID6666 extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    TextView username;
    TextView name1;
    String URLImage = "http://http://140.203.217.200/images/estimotebeacon.jpg";
    ImageView image;
    ProgressDialog mProgressDialog;
    // String youtubeURL="https://www.youtube.com/watch?v=SrsHBjzt2E8";

    //URL to get JSON Array
    private static String urltext = "http://140.203.217.200/beacon6.php";
    //JSON Node Names
    private static final String TAG_START = "beacon6";
    private static final String TAG_NAME = "name";
    private static final String TAG_INFORMATION = "information";

    public static final String API_KEY = "AIzaSyB-t2BcSAeBxRvDCqWs8evOhACcWN3imbM";

    //http://youtu.be/<VIDEO_ID>
    public static final String VIDEO_ID = "SrsHBjzt2E8";


    JSONArray user = null;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_id2222);
        new JSONParse().execute();
        image = (ImageView) findViewById(R.id.imageViewtwo);
        new DownloadImage().execute(URLImage);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }


    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

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

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
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
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();

    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            username = (TextView)findViewById(R.id.description);
            name1 = (TextView)findViewById(R.id.whereyouaretext);

            pDialog = new ProgressDialog(BeaconID6666.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
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

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BeaconID6666.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            image.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mProgressDialog!= null)
            mProgressDialog.dismiss();


    }



}

