package com.example.yongmu.websocketserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity {

    private final class WebsocketServer extends WebSocketServer
    {

        public WebsocketServer(InetSocketAddress address) {
            super(address);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onError(WebSocket arg0, Exception arg1) {
            // TODO Auto-generated method stub
            System.out.println(arg1.getStackTrace());

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onMessage(WebSocket arg0, String text) {
            Log.i("Websocket", "Receiving text : " + text);
            JSONObject message = null;
            try {
                message = new JSONObject(text);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(message != null) {
                String content = null;
                try {
                    content = (String)message.get("content");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String finalContent = content;
                runOnUiThread(()-> {
                    TextView textView = (TextView)findViewById(R.id.textView);
                    textView.setText(textView.getText() + "\n" + finalContent);
                });
            }
        }

        @Override
        public void onOpen(WebSocket arg0, ClientHandshake arg1) {
            Log.i("Websocket","new connection to " + arg0.getRemoteSocketAddress());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ipAddress = "localhost";
        InetSocketAddress inetSockAddress = new InetSocketAddress(ipAddress, 8025);
        WebsocketServer wsServer = new WebsocketServer(inetSockAddress);
        wsServer.start();
    }
}
