package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    EditText e2;
    TextView textResult;
    TextView tempoResultText;
    private static Socket s;
    private static PrintWriter pw;
    String message = "";
    String message2 = "tt";
    long tempoFinal;
    long tempoInicial;
    long tempoResultado;
    private static String ip = "192.168.0.6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.editText2);
        textResult = (TextView) findViewById(R.id.textView);
        e2 = (EditText) findViewById(R.id.editText3);
        tempoResultText = (TextView) findViewById(R.id.textView4);

    }

    static long fibonacci(long n)
    {
        if(n==1 || n==2)
        {
            return 1;
        }
        else
        {
            return fibonacci(n-1) + fibonacci(n-2);
        }
    }

    public void doFib (View v) {
        message = e1.getText().toString();
        myTask2 mt2 = new myTask2();
        mt2.execute();

        Toast.makeText(getApplicationContext(), "Doing process", Toast.LENGTH_LONG).show();
    }

    public void send_text (View v) {
        message = e1.getText().toString();
        ip = e2.getText().toString();
        myTask mt = new myTask();
        mt.execute();

        Toast.makeText(getApplicationContext(), "Data Send", Toast.LENGTH_LONG).show();
    }

    class myTask extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint({"SimpleDateFormat", "WrongThread"})
        @Override
        protected String doInBackground(String... params) {
            /*try {
                s = new Socket(ip,1000);
                pw = new PrintWriter(s.getOutputStream());
                pw.write(message);
                pw.flush();

                pw.close();
                s.close();

                ServerSocket ss = new ServerSocket(2000);
                s = ss.accept();
                InputStreamReader isr = new InputStreamReader(s.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                message2 = br.readLine();
                System.out.println(message2);


                isr.close();
                br.close();
                s.close();

            }catch (IOException e) {
                e.printStackTrace();
            }*/
            DatagramSocket aScoket = null;
            try {
                aScoket = new DatagramSocket();
                byte[] m = message.getBytes();
                InetAddress aHost = InetAddress.getByName(ip);
                DatagramPacket request = new DatagramPacket(m, message.length(), aHost, 1000);
                tempoInicial = System.currentTimeMillis();
                aScoket.send(request);

                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                aScoket.receive(reply);
                tempoFinal = System.currentTimeMillis();
                tempoResultado = tempoFinal - tempoInicial;
                message2 = new String(reply.getData());

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return message2;
            //return null;
        }

        protected void onPostExecute (String result) {
            String t = String.valueOf(tempoResultado);
            tempoResultText.setText(t);
            textResult.setText(result);
        }
    }
    class myTask2 extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint({"SimpleDateFormat", "WrongThread"})
        @Override
        protected String doInBackground(String... params) {
            long s = Long.parseLong(message);
            tempoInicial = System.currentTimeMillis();
            s = fibonacci(s);
            tempoFinal = System.currentTimeMillis();
            tempoResultado = tempoFinal - tempoInicial ;
            System.out.print(new SimpleDateFormat("mm:ss").format(new Date(tempoResultado)));
            message2 = String.valueOf(s);

            return message2;
            //return null;
        }

        protected void onPostExecute (String result) {
            String t = String.valueOf(tempoResultado);
            tempoResultText.setText(t);
            textResult.setText(result);
        }
    }
}