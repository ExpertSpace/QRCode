package com.example.qrcode;

import android.os.AsyncTask;
import android.provider.Settings;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.example.qrcode.MainActivity.tvRes;


public class Server extends AsyncTask <Void, Void, Void> {

    //private String androidID = android.provider.Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    public static String androidID = "888";

    Socket clientSocket;
    OutputStreamWriter outputStreamWriter;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            clientSocket = new Socket("84.252.131.113", 6868);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());

            outputStreamWriter.write(androidID);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        if(!clientSocket.isConnected())
            tvRes.setText("Не удалось подключиться!");

        try {
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());

            outputStreamWriter.write(androidID);

            outputStreamWriter.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
