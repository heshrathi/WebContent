package com.example.webcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //class for downloading web contect
    public class DownWebContent extends AsyncTask<String, Void, String> { //pass in string, get return string

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data != -1) {
                    char current = (char) data;

                    result += current;
                    data = reader.read();
                }

                return result;

//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return "Failed";
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Failed";
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView;
        EditText editText;
        Button reset, send;

        editText = findViewById(R.id.editView);
        textView = findViewById(R.id.resultTextView);
        reset = findViewById(R.id.resetBtn);
        send = findViewById(R.id.sendBtn);

        DownWebContent downWebContent = new DownWebContent();
        final String[] result = {""};

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                editText.setText("");
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.VISIBLE);

                try {
//                    result[0] = downWebContent.execute(editText.getText().toString()).get();
                    result[0] = downWebContent.execute("https://docs.agora.io/en/All/faq/restful_authentication").get();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

                Log.i("Result: ", result[0]);
                textView.setText(result[0]);
            }
        });
    }
}