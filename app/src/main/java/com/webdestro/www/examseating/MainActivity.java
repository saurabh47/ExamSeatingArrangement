package com.webdestro.www.examseating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    String Base_url="http://blynk-cloud.com/";
    String End_url="/update/v1";
    String url,value,row1,row2;
    //String url="http://blynk-cloud.com/f391b4bce2804777b46cc3fa1152e818/get/v4";
    //Button submitButton;
    SubmitButton submitButton;
    EditText a,b,c,d,e,f,g,h;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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

                SharedPreferences sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);
                token=sharedPref.getString("Token","90b1a2dad1ce4c5b9aad0d0557c1c375");

                url=Base_url+token+End_url;

                Log.d("URL",url);
                volleyRq();
            }
        });

//        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_settings:
//                        // User chose the "Settings" item, show the app settings UI...
//                        Toast.makeText(getApplicationContext(),"click",Toast.LENGTH_SHORT).show();
//                        return true;
//
//
//                    default:
//                        // If we got here, the user's action was not recognized.
//                        // Invoke the superclass to handle it.
//                        return false;
//
//                }
//            }
//        });


        submitButton.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {
                submitButton.reset();
            }
        });

    }

   private void createPromt(){
        // get prompts.xml view

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.promt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText())
                                if(userInput.getText().toString().isEmpty()){
                                    //Don't save Nothing
                                }else{
                                    saveData(userInput.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                createPromt();
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
    public void saveData(String token){
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Toast.makeText(this,IpAddress,Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPref =getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Token",token);
        editor.apply();

//        savedIP=sharedPref.getString("ServerIP","http://192.168.4.1/");
//        mCurrentIP.setText("Saved IP :"+savedIP);
//        ssidcheck();
        //Toast.makeText(this,serverIP,Toast.LENGTH_LONG).show();
        //Toast.makeText(this,ssid,Toast.LENGTH_LONG).show();
    }
}
