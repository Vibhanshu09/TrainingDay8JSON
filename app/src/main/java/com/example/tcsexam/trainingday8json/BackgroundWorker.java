package com.example.tcsexam.trainingday8json;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Vibhanshu on 4/7/2018.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    Activity activity;

    public BackgroundWorker(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String status = strings[0];
        String loginurl = "http://172.18.41.112/TrainingDay7/loginU.php";
        String registrationurl = "http://172.18.41.112/TrainingDay7/registerU.php";

        if (status.equals("login")){
            String username = strings[1];
            String password = strings[2];
            try {
                URL url = new URL(loginurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String postData = URLEncoder.encode("username","UTF-8")+"="+
                        URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+
                        URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //For Getting server Responce
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result = "";
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (status.equals("registration")){
            String name = strings[1];
            String mobile = strings[2];
            String email = strings[3];
            String username = strings[4];
            String password = strings[5];
            try {
                URL url = new URL(registrationurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("name","UTF-8")+"="+
                        URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("mobile","UTF-8")+"="+
                        URLEncoder.encode(mobile,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+
                        URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("username","UTF-8")+"="+
                        URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+
                        URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //For Getting server Responce
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                String result = "";
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    result +=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Connecting to server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(String json) {
        Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_responce");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            String code = jsonObject1.getString("code");
            String message = jsonObject1.getString("message");
            if (code.equals("register_success") || code.equals("register_false"))
               // Toast.makeText(context, code + " : " + message, Toast.LENGTH_SHORT).show();
                showDialog("Registration Status",code,message);
            else if (code.equals("login_false"))
                //Toast.makeText(context, code + " : " + message, Toast.LENGTH_SHORT).show();
                showDialog("Login Status",code,message);
            else if (code.equals("login_true")){
                //Toast.makeText(context, code + " : " + message, Toast.LENGTH_SHORT).show();
                showDialog("Login Status",code,message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showDialog(String tital,String code, String message){
        builder.setTitle(tital);
        if (code.equals("register_success") || code.equals("register_false")){
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                    Log.e("Error: ","1st");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }
        else if (code.equals("login_false") || code.equals("login_true")){
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Log.e("Error: ","2nd");
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
