package com.example.satriadimaspermana.movieapp.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
//import android.widget.ShareActionProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.satriadimaspermana.movieapp.AppController;
import com.example.satriadimaspermana.movieapp.Const;
import com.example.satriadimaspermana.movieapp.DateHelper;
import com.example.satriadimaspermana.movieapp.MainActivity;
import com.example.satriadimaspermana.movieapp.MyAdapter;
import com.example.satriadimaspermana.movieapp.R;
import com.example.satriadimaspermana.movieapp.Result;
import com.example.satriadimaspermana.movieapp.ResultReviews;
import com.example.satriadimaspermana.movieapp.Reviews;
import com.example.satriadimaspermana.movieapp.RootObject;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private List<ResultReviews> ResultListReviews = new ArrayList<>();
    private Result object ;
    ImageView ResultImage;
    ImageView backImage;
    TextView title;
    TextView release;
    TextView rating;
    TextView overview;
    public  String TAG=SampleActivity.class.getSimpleName();
    public final static String KEY_MEMBER = "keymember";
    public boolean mIsMarked;
    Button buttonFav ;
    ShareActionProvider mShareActionProvider;
    private String tag_json_arry = "jarray_req" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setProgressBarIndeterminateVisibility(false);
        buttonFav = (Button) findViewById(R.id.button);
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mIsMarked){
                    mIsMarked = true;
                    buttonFav.setText("UNFAVORITE");
                    Toast.makeText(getApplicationContext(),"Added to favorire",Toast.LENGTH_SHORT).show();
                }else{
                    mIsMarked = false;
                    buttonFav.setText("ADD TO Favorite");
                    Toast.makeText(getApplicationContext(),"Remove from Favorite",Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (getIntent().hasExtra(KEY_MEMBER)) {
            object = (Result) getIntent().getParcelableExtra(KEY_MEMBER);
            Log.e(TAG, "onCreate: "+object.getPoster_path());
            Log.e(TAG, "onCreate: "+object.getBackdrop_path());
            Log.e(TAG, "onCreate: "+object.getOriginal_title());
            Log.e(TAG, "onCreate: "+object.getRelease_date());
            Log.e(TAG, "onCreate: "+object.getVote_average());
            Log.e(TAG, "onCreate: "+object.getOverview());
            Log.e(TAG, "onCreate: "+object.getId());
            getSupportActionBar().setTitle(object.getOriginal_title());

        }else{
            Toast.makeText(getApplicationContext(),"maaf data tidak ditemukan",Toast.LENGTH_SHORT).show();
        }

        ResultImage = (ImageView) findViewById(R.id.imgkecil);
        backImage = (ImageView) findViewById(R.id.backdrop_path);
        title = (TextView) findViewById(R.id.tvTitle);
        release = (TextView) findViewById(R.id.tvRelease);
        rating = (TextView) findViewById(R.id.tvVote);
        overview = (TextView) findViewById(R.id.tvOverview);

        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/" + object.getPoster_path()).into(ResultImage);

        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/" + object.getBackdrop_path()).into(backImage);
        title.setText(object.getOriginal_title());
        String date = DateHelper.dateParseToString(object.getRelease_date(), DateHelper.DATE_DEFAULT, DateHelper.DATE_DDMMMMYYYY);
        release.setText(date);
        rating.setText(object.getVote_average()+"");
        overview.setText(object.getOverview());

        makeJsonObjReq4();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sample,menu);
        MenuItem menuItem = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(doShare());
        return true;
    }

    private Intent doShare() {
        Intent doShare = new Intent(Intent.ACTION_SEND);
        doShare.setType("text/plain");
        doShare.putExtra(Intent.EXTRA_TEXT,object.getOriginal_title());
        return doShare;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    private void makeJsonObjReq4() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
                "http://api.themoviedb.org/3/movie/"+object.getId()+"/reviews?api_key=1b2f29d43bf2e4f3142530bc6929d341", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Reviews rootObject = new Gson().fromJson(response.toString(),Reviews.class);
                        LinearLayout lin = (LinearLayout) findViewById(R.id.linear3);
                        for (ResultReviews r :  rootObject.results) {
                            View v = getLayoutInflater().inflate(R.layout.itemreviews,lin,false);
                            TextView txt = (TextView) v.findViewById(R.id.tvContent);
                            txt.setText(r.getContent());
                            TextView txtDua = (TextView) v.findViewById(R.id.tvAuthor);
                            txtDua.setText(r.getAuthor());
                            lin.addView(v);
                        }
                        /// data.removeAll(data);
//                        data.clear();
//                        rootObject.results.clear();
//                        rootObject.content;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req,tag_json_arry);
    }


}
