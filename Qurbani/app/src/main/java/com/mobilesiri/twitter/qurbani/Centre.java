package com.mobilesiri.twitter.qurbani;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class Centre extends AppCompatActivity {
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centre);
        String strUrl = "http://qurbaniapp.icoderslab.com/getQurbaniCenter.php";
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(strUrl);
        mListView = (ListView) findViewById(R.id.lv_info);


    }
    //This method downloading the data from URL
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        //Input Stream writer for reading data
        InputStream iStream = null;
        try {
            URL url = new URL(strUrl);
            //Obtain a new HttpURLConnection by calling URL.openConnection() and casting the result to HttpURLConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "" + e, Toast.LENGTH_SHORT).show();
        } finally {
            iStream.close();
        }

        return data;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
            listViewLoaderTask.execute(result);
        }
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

        JSONObject jObject;

        @Override
        protected SimpleAdapter doInBackground(String... strJson) {
            try {
                jObject = new JSONObject(strJson[0]);
                JsonParser JsonParser = new JsonParser();
                JsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("JSON Exception1", e.toString());
            }

            JsonParser JsonParser = new JsonParser();

            List<HashMap<String, Object>> details = null;

            try {
                details = JsonParser.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }

            String[] from = {"data","details"};
            int[] to = {R.id.tv_information, R.id.tv_details};
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), details, R.layout.lv_layout, from, to);

            return adapter;
        }

        @Override
        protected void onPostExecute(final SimpleAdapter adapter) {
            mListView.setAdapter(adapter);


        }
    }

}
