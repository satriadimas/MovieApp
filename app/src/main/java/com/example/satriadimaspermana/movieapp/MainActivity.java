package com.example.satriadimaspermana.movieapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import  android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private String TAG = MainActivity.class.getSimpleName();
    private List<Result> data = new ArrayList<>();
    private MyAdapter adapter = new MyAdapter();
    private RecyclerView rec;
    private String jsonRespons;
    private String tag_json_arry = "jarray_req" ;
    private ImageView image;
    private Toolbar tool;
    private Dialog pDialog;
    final Context context = this;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.pbHeaderProgress);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MyAdapter();
        rec  = (RecyclerView) findViewById(R.id.rec);
        rec.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rec.setAdapter(adapter);

        makeJsonObjReq();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()){
            case R.id.mostpopular:

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.piac_sort)
                        .setItems(R.array.filter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        adapter.removeAll(data);
                                        makeJsonObjReq();
                                        break;
                                    case 1:
                                        adapter.removeAll(data);
                                        makeJsonObjReq2();
                                        break;
                                    case 2:
                                        adapter.removeAll(data);
                                        makeJsonObjReq3();
                                        break;
                                    default:
                                }

                            }
                        });
                builder.show();
                break;
        }
        return false;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void makeJsonObjReq() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                Const.URL_API_POPULAR, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        RootObject rootObject = new Gson().fromJson(response.toString(),RootObject.class);
                        //Log.d(TAG, response.toString());

//                        a = rootObject.results.poster_path;
//                        Log.i(TAG, String.valueOf(rootObject.results));
//                        data.add(rootObject.results);

//                    data.clear();
//                        rootObject.results.clear();
                    adapter.removeAll(rootObject.results);
//                    image.setImageResource();
                    adapter.addAll(rootObject.results);
                      //  if (adapter != null){
                          ///  adapter.remove();
                        //}
                    adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req,tag_json_arry);
    }
    private void makeJsonObjReq2() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                Const.URL_API_RATING, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        RootObject rootObject = new Gson().fromJson(response.toString(),RootObject.class);

                       /// data.removeAll(data);
//                        data.clear();
//                        rootObject.results.clear();
                        adapter.removeAll(rootObject.results);
                        adapter.addAll(rootObject.results);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req,tag_json_arry);
    }

    private void makeJsonObjReq3() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                Const.URL_API_UPCOMMING, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        RootObject rootObject = new Gson().fromJson(response.toString(),RootObject.class);

                        /// data.removeAll(data);
//                        data.clear();
//                        rootObject.results.clear();
                        adapter.removeAll(rootObject.results);
                        adapter.addAll(rootObject.results);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req,tag_json_arry);
    }

    @Override
    public void onClick(View view) {

    }
}
