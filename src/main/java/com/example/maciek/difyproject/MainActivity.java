package com.example.maciek.difyproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//jakis komenatzr

public class MainActivity extends AppCompatActivity
{
    List<String> lstring;
    AlertDialog.Builder builder;
    ArrayAdapter<String> modeAdapter;
    ListView modeList;
    EditText inputSearch;
    String tempo;
    String genre;
    String city;
    String country;

    LayoutInflater layoutInflater;

    MainActivity activity;

    private Context mainActivityContext;

    private MainActivity mainActivity;

    private android.support.v7.app.ActionBar myActionBar;

    private final String myActionBarTitle = "Dify";

    private Activity myCurrentActivity;

    private Dialog myDialog;

    Dialog dial;
    Dialog dialog;

    // < BUTTONS > //
    private Button genreButton;
    private Button countryButton;
    private Button cityButton;
    private Button searchButton;
    // </ BUTTONS > //

    // < EDITTEXT >
    private EditText myEditText;
    // < CONSTRUCTOR > //
    public MainActivity()
    {

    }
    // </ CONSTRUCTOR > //

    public void setMyEditText(EditText myEditText)
    {
        this.myEditText = myEditText;
    }

    public EditText getMyEditText()
    {
        return myEditText;
    }

    public void setGenreButton(Button genreButton)
    {
        this.genreButton = genreButton;
    }

    public Button getGenreButton()
    {
        return genreButton;
    }

    public void setCountryButton(Button countryButton)
    {
        this.countryButton = countryButton;
    }

    public Button getcountryButton()
    {
        return countryButton;
    }

    public void setCityButton(Button cityButton)
    {
        this.cityButton = cityButton;
    }

    public Button getCityButton()
    {
        return cityButton;
    }

    public void setSearchButton(Button searchButton)
    {
        this.searchButton = searchButton;
    }

    public Button getSearchButton()
    {
        return searchButton;
    }

    public Dialog getMyDialog()
    {
        return myDialog;
    }

    public void setMyDialog(Dialog myDialogialog)
    {
        this.myDialog = myDialog;
    }

    public void setMyCurrentActivity(Activity currentActivity)
    {
        this.myCurrentActivity = currentActivity;
    }

    public Activity getMyCurrentActivity()
    {
        return myCurrentActivity;
    }

    public void setMainActivityActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public MainActivity getMainActivity()
    {
        return mainActivity;
    }

    public Context getMainActivityContext()
    {
        return mainActivityContext;
    }

    public void setMainActivityContext()
    {
        this.mainActivityContext = getApplicationContext();
    }

    public void setMyActionBar()
    {
        this.myActionBar = getSupportActionBar();
    }

    public android.support.v7.app.ActionBar getMyActionBar()
    {
        return myActionBar;
    }

    public void configureMyActionBar(android.support.v7.app.ActionBar myActionBar)
    {
        myActionBar.setTitle(getMyActionBarTitle());

    }

    public String getMyActionBarTitle()
    {
        return myActionBarTitle;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMyActionBar();

        android.support.v7.app.ActionBar myActionBar = getMyActionBar();

        configureMyActionBar(myActionBar);

        setMyActionBar();

        setMainActivityContext();

        Context mainActivityContext = getMainActivityContext();

        activity = MainActivity.this;

        Dialog dialog = new Dialog(mainActivityContext);

        setMyDialog(dialog);

        Button genreButton = (Button) findViewById(R.id.genreButton);
        setGenreButton(genreButton);

        Button countryButton = (Button) findViewById(R.id.countryButton);
        setCountryButton(countryButton);

        Button cityButton = (Button) findViewById(R.id.cityButton);
        setCityButton(cityButton);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        setSearchButton(searchButton);

        EditText editText = (EditText) findViewById(R.id.editTextDialog);
        setMyEditText(editText);

        genreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getGenres();
                prepareDialog();
                showDialog(getGenreButton());
            }
        });

        countryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialogWithEditTextOnly(getcountryButton(), v);
            }
        });

        cityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialogWithEditTextOnly(getCityButton(), v);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(activity, SearchedArtists.class);
                intent.setClass(MainActivity.this, SearchedArtists.class);
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra("COUNTRY", country);
                intent.putExtra("CITY", city);
                intent.putExtra("GENRE", genre);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });
    }

    public void dialogWithEditTextOnly(final Button tempButton, final View v)
    {
        final AlertDialog.Builder cityDialog = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);
        final LayoutInflater cityLayout = activity.getLayoutInflater(); //TODO getActivity().getLayoutInflater();
        final View dialogview = cityLayout.inflate(R.layout.dialog_edit_text, null);
        cityDialog.setView(dialogview);
        cityDialog.setTitle("Select");
        tempo = null;

        cityDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                EditText editText = (EditText) dial.findViewById(R.id.editTextDialog);

                switch (v.getId())
                {
                    case R.id.countryButton:
                        country = editText.getText().toString();
                        tempButton.setText(country);
                        break;

                    case R.id.cityButton:
                        city = editText.getText().toString();
                        tempButton.setText(city);
                        break;
                }
            }
        });

        cityDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });

        dial = cityDialog.create();
        dial.show();
    }

    public void prepareDialog()
    {
        builder = new AlertDialog.Builder(activity, R.style.AlertDialogCustom);

        layoutInflater = activity.getLayoutInflater();

        final View dialogView = layoutInflater.inflate(R.layout.list_view, null);

        builder.setView(dialogView);
        builder.setTitle("Genres");

        modeList = (ListView) dialogView.findViewById(R.id.listView);

        ColorDrawable sage = new ColorDrawable(ContextCompat.getColor(activity, R.color.colorDivider));
        modeList.setDivider(sage);
        modeList.setDividerHeight(1);

        if(lstring == null)
        {
            lstring = new ArrayList<>();
        }
        else
        {
            lstring.clear();
        }

        modeAdapter = new ArrayAdapter<String>(activity, R.layout.list_view_custom, lstring);

        modeList.setAdapter(modeAdapter);

        dialogView.setBackgroundColor(Color.parseColor("#181818"));

        dialog = builder.create();

        inputSearch = (EditText) dialogView.findViewById(R.id.editText2);

    }

    public void showDialog(final Button myButton)
    {

        dialog.show();

        inputSearch.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                // When user changed the Text
                //Main3Activity.this.arraylist.getFilter().filter(cs);
                activity.modeAdapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3)
            {
                // TODO Auto-generated method stub

            }

        });

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                TextView temp = (TextView) view;
                // Toast.makeText(activity, temp.getText(), Toast.LENGTH_SHORT).show();
                genre = temp.getText().toString();
                myButton.setText(genre);
                dialog.dismiss();
            }
        });
    }

    void getGenres()
    {
        String url = "http://developer.echonest.com/api/v4/genre/list?api_key=SXZNUZ73HVWJ7T5V1&format=json";

        RequestQueue rQ = Volley.newRequestQueue(this);

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONObject j = response.getJSONObject("response");
                            JSONArray jsonArray = j.getJSONArray("genres");

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String genre = object.getString("name");
                                lstring.add(genre);
                            }
                        } catch (JSONException e)
                        {

                        }
                    }
                }, new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // TODO Auto-generated method stub

                    }
                });

        rQ.add(jsObjRequest);
    }
}
