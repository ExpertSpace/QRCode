package com.example.qrcode;

import android.os.AsyncTask;
import android.provider.Settings;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.example.qrcode.MainActivity.androidID;


public class Server extends AsyncTask <Void, Void, Void> {

    Socket clientSocket;
    OutputStreamWriter outputStreamWriter;

    //public String androidIDFirst = android.provider.Settings.System.getString(MainActivity.getContentResolver(), Settings.Secure.ANDROID_ID);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            clientSocket = new Socket("192.168.0.106", 8080);

            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());

            outputStreamWriter.write(androidID);

            outputStreamWriter.flush();
            outputStreamWriter.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
