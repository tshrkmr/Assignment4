package edu.depaul.assignment4;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InformationDownloader implements Runnable{

    private static final String TAG = "InformationDownloader";
    private static final String DATA_URL = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyCi_ZZSxgVYv2yjwMcQxJsYkd2zj0NPip0&address=";
    private final MainActivity mainActivity;
    private final String searchTarget;


    public InformationDownloader(MainActivity mainActivity, String searchTarget) {
        this.mainActivity = mainActivity;
        this.searchTarget = searchTarget;
    }

    @Override
    public void run() {
        Uri.Builder uriBuilder = Uri.parse(DATA_URL + searchTarget).buildUpon();
        String urlToUse = uriBuilder.toString();

        //Log.d(TAG, "run: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "run: HTTP ResponseCode NOT OK: " + conn.getResponseCode());
                return;
            }

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            //Log.d(TAG, "run: " + sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
            return;
        }
        process(sb.toString());
    }

    private void process(String s){
        try {
            JSONObject jObjMain = new JSONObject(s);

            JSONObject jNormalInput = jObjMain.getJSONObject("normalizedInput");
            JSONArray jOfficesArray = jObjMain.getJSONArray("offices");
            JSONArray jOfficialsArray = jObjMain.getJSONArray("officials");

            mainActivity.setLocation(jNormalInput.getString("city"), jNormalInput.getString("state"), jNormalInput.get("zip").toString());

            int length = jOfficesArray.length();
            mainActivity.clearOfficialList();

            for (int i = 0; i<length; i++){
                JSONObject jObj = jOfficesArray.getJSONObject(i);
                String officeName = jObj.getString("name");
                //Log.d(TAG, "process: " + officeName);

                JSONArray officialIndices = jObj.getJSONArray("officialIndices");

                for (int j = 0; j<officialIndices.length(); j++){
                    int pos = Integer.parseInt(officialIndices.getString(j));
                    Official official = new Official(officeName);
                    JSONObject jOfficial = jOfficialsArray.getJSONObject(pos);

                    official.setOfficeHolder(jOfficial.getString("name"));

                    if(jOfficial.has("address")) {
                        JSONArray jAddresses = jOfficial.getJSONArray("address");
                        JSONObject jAddress = jAddresses.getJSONObject(0);
                        String address = "";

                        if (jAddress.has("line1"))
                            address += jAddress.getString("line1") + '\n';
                        if (jAddress.has("line2"))
                            address += jAddress.getString("line2") + '\n';
                        if (jAddress.has("line3"))
                            address += jAddress.getString("line3") + '\n';
                        if (jAddress.has("city"))
                            address += jAddress.getString("city") + ", ";
                        if (jAddress.has("state"))
                            address += jAddress.getString("state") + ' ';
                        if (jAddress.has("zip"))
                            address += jAddress.getString("zip");
                        official.setOfficeAddress(address);
                    }

                    if (jOfficial.has("party"))
                        official.setParty(jOfficial.getString("party"));
                    if (jOfficial.has("phones"))
                        official.setOfficePhone(jOfficial.getJSONArray("phones").getString(0));
                    if (jOfficial.has("urls"))
                        official.setWebsiteUrl(jOfficial.getJSONArray("urls").getString(0));
                    if (jOfficial.has("emails"))
                        official.setOfficeEmail(jOfficial.getJSONArray("emails").getString(0));

                    if (jOfficial.has("channels")){
                        SocialMediaChannel channel = new SocialMediaChannel();

                        JSONArray jChannels = jOfficial.getJSONArray("channels");
                        for (int k = 0; k<jChannels.length(); k++){
                            JSONObject jChannel = jChannels.getJSONObject(k);
                            if (jChannel.getString("type").equals("Facebook"))
                                channel.setFacebookUrl(jChannel.getString("id"));
                            if (jChannel.getString("type").equals("Twitter"))
                                channel.setTwitterUrl(jChannel.getString("id"));
                            if (jChannel.getString("type").equals("YouTube"))
                                channel.setYoutubeUrl(jChannel.getString("id"));
                        }
                        official.setSocialMediaChannel(channel);
                    }
                    if (jOfficial.has("photoUrl"))
                        official.setPhotoUrl(jOfficial.getString("photoUrl"));
                    //Log.d(TAG, "process1: " + official.toString());
                    mainActivity.runOnUiThread(() -> mainActivity.updateOfficialList(official));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            return;
        }
    }
}


