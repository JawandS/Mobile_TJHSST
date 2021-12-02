package com.singhjawand.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    TestVolleyRequest tester;

    Set<String> quotes;

    SharedPreferences.Editor myEdit;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tester = new TestVolleyRequest(this);
        textView = findViewById(R.id.id0);

        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        myEdit = sharedPreferences.edit();

        // get quotes
        quotes = sharedPreferences.getStringSet("key", new HashSet<String>());


    }

    @Override
    protected void onStart() {
        super.onStart();

        quotes = sharedPreferences.getStringSet("key", new HashSet<String>());
    }

    @Override
    protected void onPause() {
        super.onPause();

//        System.out.println(quotes.toString());
        System.out.println("Quotes already seen: " + quotes.size());
        myEdit.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

//        System.out.println(quotes.toString());
        System.out.println("Quotes already seen: " + quotes.size());
        myEdit.commit();
    }

    public void request(View view) {
        tester.volleyGetQuote(textView);
        String value = textView.getText().toString();
        if (quotes.contains(value) && value != "Click to Get Quote") {
            System.out.println("Already there: " + value + " all quotes: " + quotes.toString());
//            request(view);
        }
        else {
            if (value != "Click to Get Quote")
                quotes.add(value);
            myEdit.putStringSet("key", quotes);
        }
    }
}

class TestVolleyRequest {
    Context context;

    public TestVolleyRequest(Context context) {
        this.context = context;
    }

    public String volleyGetQuote(TextView view) {
        final String[] output = {"error"};
        String url = "https://api.quotable.io/random";
        ArrayList<JSONObject> jsonResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String temp = "";
                        try {
                            temp = response.getString("content");
                        } catch (JSONException e) {
                            System.out.println("Error occurred in get quote");
                            e.printStackTrace();
                        }
                        view.setText(temp);
                        output[0] = temp;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // executes request
        requestQueue.add(jsonObjectRequest);
        return output[0];
    }

    public void volleyGetData(TextView view) {
        String url = "https://mw-demo.sites.tjhsst.edu/data";
        ArrayList<JSONObject> jsonResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String category = "";
                        System.out.println("before try");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
//                                System.out.print("Line 63: " + jsonObject);
                                jsonResponses.add(jsonObject);
                            }
                        } catch (JSONException e) {
                            System.out.println("error");
                            e.printStackTrace();
                        }
                        System.out.println("Before for");
                        for (JSONObject elem : jsonResponses) {
                            System.out.println("Line 61 " + elem);
                            try {
                                String id = elem.getString("id");
                                category = elem.getString("category");
                                String increment = elem.getString("increment");
                                String decrement = elem.getString("decrement");

                                System.out.println("category: " + category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        view.setText(category);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // executes request
        System.out.println("before request");
        requestQueue.add(jsonObjectRequest);
        System.out.println("after request");

//        System.out.println("Line 81: " + jsonResponses.toString());
//            System.out.println("Line 89" + jsonResponses.toString());
//        for (JSONObject elem: jsonResponses)
//            System.out.println("Line 61 " + elem);


    }

    public void volleyGetHelloWorld() {
        String url = "https://mw-demo.sites.tjhsst.edu/";
        ArrayList<String> stringResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        stringResponses.add(response);

                        System.out.println("Line 57: " + stringResponses.get(0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        // executes request
        requestQueue.add(stringRequest);
    }
}

class BasicVolleyRequest {
    Context context;

    public BasicVolleyRequest(Context context) {
        this.context = context;
    }

    public void volleyGet() {
        String url = "https://reqres.in/api/users?page=2";
        ArrayList<String> jsonResponses = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String email = jsonObject.getString("email");

                                jsonResponses.add(email);
                            }
                        } catch (JSONException e) {
                            System.out.println("error");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // executes request
        requestQueue.add(jsonObjectRequest);

//            System.out.println("Line 89" + jsonResponses.toString());
        for (String elem : jsonResponses)
            System.out.println("Line 61 " + elem);

    }

    public void volleyPost() {
        String postUrl = "https://reqres.in/api/users";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", "Jonathan");
            postData.put("job", "Software Engineer");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                Overrides the "headers" - data necessary to complete request
//                Normally used for API use cases
                return super.getHeaders();
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}

