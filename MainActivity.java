package com.example.dbphp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText edName,edMobile,edEmail;
    Button inputindatabase;
    ListView listView;

    HashMap<String,String> hashMap; // HashMap<Key,Value> // HashMap<Table Header name,Value> //1 row Hashmap
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    //ArrayList এর ভিতরে HashMap রাখি । আর Hashmap  এর ভিতর Key,Value রাখি
    // HashMap এর সুবিধা key টা Unique থাকে value duplicate push করা যায় । কিন্তু key বার বার থাকলেও একবারই লেখা থাকবে

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        edName =findViewById(R.id.edName);
        edMobile =findViewById(R.id.edMobile);
        edEmail =findViewById(R.id.edEmail);
        inputindatabase=findViewById(R.id.inputindatabase);
        listView =findViewById(R.id.listView);
        loadData();



        //.......................................................................
        //.......................................................................
        //.......................................................................

// inputindatabase বাটনে setOnClickListener চালু করলাম
        inputindatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edName.getText().toString();
                String mobile = edMobile.getText().toString();
                String email = edEmail.getText().toString();
                //Input text গুলো get করে string এ ধরলাম

                //https://nubsoft.xyz/data.php?n=shahrear&m=01872000&e=arshahrear30
                //লক্ষ্য করো n এবং m এবং e এগুলো key । আর shahrear , 01872000 , arshahrear30 এগুলো হলো Values ।
                //তাহলে .php এর  পর key=values এভাবে data input দিতে পারি ।
                //এখন এই link এ value গুলো update করলে আরেকটা database এ row create হয়ে data গুলো input হবে ।

                String url = "https://nubsoft.xyz/data.php?n=" +name+ "&m=" +mobile+ "&e=" +email ;

                //object / array request করলে এখানে.Get,url এর পর  একটা null দিতাম . কিন্তু  এটা string তাই Response.Listener নিলাম

                progressBar.setVisibility(View.VISIBLE); //বাটলে click করলে progress bar আসবে এবং load হওয়া দেখাবে

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    //একটা stringRequest দিয়ে url get করলাম এবং একটা Listener নিলাম
                    @Override
                    public void onResponse(String response) {
                        // যখন কাজ হবে তখন progressBar GONE হবে
                        progressBar.setVisibility(View.GONE);
                        //AlertDialog হলো screen এর উপর box message আকারে দেখাবে
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Your Server Response")
                                .setMessage(response)
                                .show();
                        loadData();
                    }
                    //.show(); line 1
                    // } line 2
                    //});  line 3
                    // line 3 এ 2nd bracket এর পর new Response.ErrorListener call করবো
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });



//==================================================
                if(name.length()>0) {

                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(stringRequest);




                    // এখানে name দেওয়া ফরজ করে দিয়েছি তা না হয় setError হবে । আর input  না দিলে RequestQueue কাজ করবে না
                }else edName.setError("input your name");
//==================================================
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //.......................................................................
    //.......................................................................
    //.......................................................................




    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//OnCreate bundle এর আগে / শেষে public ধরে adapter বানাবো
    //public  class MyAdapter extends BaseAdapter লিখলে red bulb এ click করে ok করে দিবো
// তাহলে getCount,getItem,getItemId,getView আসবে
    public  class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

 ///////////////////////////////////////////////////////////////////
            LayoutInflater layoutInflater =getLayoutInflater();
            View myview = layoutInflater.inflate(R.layout.item,null);
 ///////////////////////////////////////////////////////////////////

            TextView tvId = myview.findViewById(R.id.tvId);
            TextView tvName = myview.findViewById(R.id.tvName);
            TextView tvMobile = myview.findViewById(R.id.tvMobile);
            TextView tvEmail = myview.findViewById(R.id.tvEmail);
            Button buttonUpdate = myview.findViewById(R.id.buttonUpdate);
            Button buttonDelete = myview.findViewById(R.id.buttonDelete);

            hashMap = arrayList.get(position);
            String name = hashMap.get("name");
            String id = hashMap.get("id");
            String mobile = hashMap.get("mobile");
            String email = hashMap.get("email");

            tvId.setText(id);
            tvEmail.setText(email);
            tvMobile.setText(mobile);
            tvName.setText(name);
//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

            //লেখা input করে update এ click করলে যেটার update এ click করছি ঐটা update হয়ে যাবে 
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = edName.getText().toString();
                    String mobile = edMobile.getText().toString();
                    String email = edEmail.getText().toString();
                    //"websitelink?id=" +id+
                    //"&n=" +name+
                    //"&m=" +mobile+
                    //"&e=" +email ;
                    progressBar.setVisibility(View.VISIBLE);
                    String url = "https://nubsoft.xyz/data.php?id="+id+"&name"+name+"&mobile"+mobile+"&email"+email ;
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Server Response")
                                    .setMessage("Response")
                                    .show();
                            loadData();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    requestQueue.add(request);
                }
            });
//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

            return myview;
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA


    private void loadData(){
        arrayList =new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        // এই requestQueue কে Globally Set করলাম যাতে বার বার না লিখতে হয় ।

        //server a hit করে volly library মাধ্যমে সব নিয়ে আসবো

        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://nubsoft.xyz/view.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);

                for(int x=0;x<response.length();x++) {

                    try {
                        JSONObject jsonObject=response.getJSONObject(x);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String mobile = jsonObject.getString("mobile");
                        String email = jsonObject.getString("email");

                        hashMap = new HashMap<>(); // এক রো বিশিষ্ট একটা টেবিল তৈরি হলো হ্যাস ম্যাপ এর মাধ্যমে
                        hashMap.put("id",id);
                        hashMap.put("name",name);
                        hashMap.put("mobile",mobile);
                        hashMap.put("email",email);
                        arrayList.add(hashMap); //

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
//------------------------------
//for loop এর শেষে কাজটা করবো । server থেকে ত বিনা তথ্যও আসতে পারে

                if(arrayList.size()>0){
                    MyAdapter myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
                }
                //arrayList.size 0 থেকে বড় হলে listView ভিতরে Adapter কল হবে এবং কাজ করবে

//----------------------------
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);


    }
 //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
}
