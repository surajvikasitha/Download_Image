package lk.ac.kln.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void download(View view){
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        imageDownloadTask.execute(getString(R.string.image_url));
    }

    private class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap>{
        private ProgressDialog progressDialog= null;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(1000);
            progressDialog.show();
        }

        protected Bitmap doInBackground(String[] strings) {
            Bitmap bitmap = null;
            try {
                //Download the image
                URL imageURL = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onProgressUpdate(Integer[] integers){
            progressDialog.setProgress(integers[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            ImageView imageView = findViewById(R.id.image);
            if(bitmap !=null){
                progressDialog.hide();
                imageView.setImageBitmap(bitmap);
            }
        }
}}



