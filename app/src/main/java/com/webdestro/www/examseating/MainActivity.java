package com.webdestro.www.examseating;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //String url = "http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/update/v4";
    String Base_url="http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/update/v2?value=";
    String url,value;
    //String url="http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/get/v4";
    Button submitButton;

    EditText a,b,c,d,e,f,g,h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitButton=findViewById(R.id.button);

        a=findViewById(R.id.editTextA);
        b=findViewById(R.id.editTextB);
        c=findViewById(R.id.editTextC);
        d=findViewById(R.id.editTextD);

        e=findViewById(R.id.editTextE);
        f=findViewById(R.id.editTextF);
        g=findViewById(R.id.editTextG);
        h=findViewById(R.id.editTextH);




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value=a.getText().toString()+"%20"+b.getText().toString()+"%20"+c.getText().toString()+"%20"+d.getText().toString()+
                        "%20"+e.getText().toString()+"%20"+f.getText().toString()+"%20"+g.getText().toString()+"%20"+h.getText().toString();
                url=Base_url+value;
                Log.d("URL",url);
                volleyRq();
            }
        });

    }




    public void volleyRq(){
        final ProgressDialog loading = ProgressDialog.show(this, "","Checking Details",false,false);

        StringRequest putRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                        loading.dismiss();
                    }
                }
        ) {

//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("name", "Alif");
//                params.put("domain", "http://itsalif.info");
//
//                return params;
//            }

        };
        RequestQueue queue = Volley.newRequestQueue(this); // this = context
        queue.add(putRequest);

    }
}
