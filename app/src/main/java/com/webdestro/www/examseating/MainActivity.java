package com.webdestro.www.examseating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unstoppable.submitbuttonview.SubmitButton;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    //String url = "http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/update/v4";
    String Base_url="http://blynk-cloud.com/90b1a2dad1ce4c5b9aad0d0557c1c375/update/v1";
    String url,value,row1,row2;
    //String url="http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/get/v4";
    //Button submitButton;
    SubmitButton submitButton;
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
               // value=a.getText().toString()+"%20"+b.getText().toString()+"%20"+c.getText().toString()+"%20"+d.getText().toString()+
                //        "%20"+e.getText().toString()+"%20"+f.getText().toString()+"%20"+g.getText().toString()+"%20"+h.getText().toString();
                row1=a.getText().toString()+" "+b.getText().toString()+" "+c.getText().toString()+" "+d.getText().toString();
                row2=e.getText().toString()+" "+f.getText().toString()+" "+g.getText().toString()+" "+h.getText().toString();
                url=Base_url;//+value;
                Log.d("URL",url);
                volleyRq();
            }
        });


        submitButton.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {
                submitButton.reset();
            }
        });

    }




    public void volleyRq(){
//        final ProgressDialog loading = ProgressDialog.show(this, "","Checking Details",false,false);

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                      //  loading.dismiss();
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                       // submitButton.reset();
                        submitButton.doResult(true);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        //Log.d("Error.Response", response);
                      //  loading.dismiss();
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        //submitButton.reset();
                        submitButton.doResult(false);
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody()
            {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("name", "Alif");
//                params.put("domain", "http://itsalif.info");
                JSONArray mJSONArray=new JSONArray();

                mJSONArray.put(row1);
                    mJSONArray.put(row2);
                try {
                    Log.i("json", mJSONArray.toString());
                    return mJSONArray.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(this); // this = context
        queue.add(putRequest);

    }
}
