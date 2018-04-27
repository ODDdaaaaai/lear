package sy.soya.lear.ui.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import sy.soya.lear.R;
import sy.soya.lear.data.VolleySingleton;
import sy.soya.lear.models.ToDoItem;
import sy.soya.lear.ui.adapters.ToDoAdapter;
import sy.soya.lear.ui.helpers.SpeedyLinearLayoutManager;
import sy.soya.lear.ui.helpers.ViewModes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    private View progressView;
    private View errorView;

    SwipeRefreshLayout swipeRefreshLayout;
    SpeedyLinearLayoutManager linearLayoutManager;
    ToDoAdapter mAdapter;
    RecyclerView todoListRecyclerView;
    ArrayList<ToDoItem> toDoItems;

    private TextView textViewError;
    private Button buttonErrorAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        loadData(true);
    }

    @Override
    public void onBackPressed() {
        if(linearLayoutManager != null && linearLayoutManager.findFirstCompletelyVisibleItemPosition() != 0){
            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
                @Override protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            smoothScroller.setTargetPosition(0);
            linearLayoutManager.startSmoothScroll(smoothScroller);
        } else
            super.onBackPressed();
    }

    private void loadData(boolean showProgress) {
        if(showProgress)
            showViews(ViewModes.PROGRESS);

        final String url = "https://jsonplaceholder.typicode.com/todos";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                Log.d("wiso", "response from " + url + ": \n" + response);
                Gson gson = new Gson();
                Type dataType = new TypeToken<ArrayList<ToDoItem>>(){}.getType();
                toDoItems = gson.fromJson(response, dataType);
                showData(toDoItems);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                showError(error);
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void showData(ArrayList<ToDoItem> toDoItems) {
        showViews(ViewModes.DATA);
        mAdapter = new ToDoAdapter(this, toDoItems);
        linearLayoutManager = new SpeedyLinearLayoutManager(this, SpeedyLinearLayoutManager.VERTICAL, false);
        todoListRecyclerView.setLayoutManager(linearLayoutManager);
        todoListRecyclerView.setAdapter(mAdapter);
    }

    private void showError(VolleyError volleyError){
        String message = null;
        if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }
        textViewError.setText(message);
        showViews(ViewModes.ERROR);
    }

    private void init() {
        toDoItems = new ArrayList<>();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);

        todoListRecyclerView = findViewById(R.id.todo_list_recycler_view);

        progressView = findViewById(R.id.progress_view);
        errorView = findViewById(R.id.error_view);

        textViewError = findViewById(R.id.text_view_error);
        buttonErrorAction = findViewById(R.id.button_retry);
        buttonErrorAction.setOnClickListener(this);
    }

    private void showViews(ViewModes viewMode){
        switch (viewMode) {
            case DATA:
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case PROGRESS:
                swipeRefreshLayout.setVisibility(View.GONE);
                progressView.setVisibility(View.VISIBLE);
                errorView.setVisibility(View.GONE);
                break;
            case ERROR:
                swipeRefreshLayout.setVisibility(View.GONE);
                progressView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        loadData(true);
    }

    @Override
    public void onRefresh() {
        loadData(false);
    }
}
