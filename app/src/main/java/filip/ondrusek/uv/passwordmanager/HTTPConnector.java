package filip.ondrusek.uv.passwordmanager;

import android.os.AsyncTask;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

class HTTPConnector extends AsyncTask<String, Void, String> {
    private  String urlString;

    @Override
    protected String doInBackground(String... urlString) {
        JSONObject jsonObject = null;
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(this.urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoOutput(true);
        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        String line = null;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(line + "\n");
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = sb.toString();
        return response;
    }

    @Override
    protected void onPostExecute(String hash) {
    }

    public void setHash(String hash)
    {
        this.urlString =  "https://api.pwnedpasswords.com/range/" + hash;
    }
}
