package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

public class viewMessages2 extends Fragment {

    private RecyclerView recycler_for_card_view;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    FloatingActionButton fabDelete2;
    public List<String> shift = new ArrayList<>(), time = new ArrayList<>(), message = new ArrayList<>();
    public List<String> d_shift = new ArrayList<>(), d_time = new ArrayList<>(), d_message = new ArrayList<>();

    public viewMessages2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fetchfromdb fb = new fetchfromdb();
        fb.doInBackground();
        View v =  inflater.inflate(R.layout.view_messages2, container, false);
        d_message.add("Message : All messages were deleted");
        d_shift.add("Shift   : 0");
        d_time.add("Time    : 000000");

        recycler_for_card_view=v.findViewById(R.id.recycler_for_card_view);
        layoutManager = new LinearLayoutManager(getContext());
        recycler_for_card_view.setLayoutManager(layoutManager);

        fabDelete2 = v.findViewById(R.id.fabDelete2);
        fabDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
                SQLiteDatabase sqLiteDatabase = contactDbHelper.getWritableDatabase();
                contactDbHelper.deleteDB(sqLiteDatabase);
                adapter = new RecyclerAdapter(d_time, d_shift, d_message);
                recycler_for_card_view.setAdapter(adapter);
            }
        });

        if (time.size() > 0){
            adapter = new RecyclerAdapter(time, shift, message);
        }
        else{
            adapter = new RecyclerAdapter(d_time, d_shift, d_message);
        }
        recycler_for_card_view.setAdapter(adapter);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideSoftKeyboard(getView());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class fetchfromdb extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
            SQLiteDatabase sqLiteDatabase = contactDbHelper.getWritableDatabase();
            Cursor c = contactDbHelper.readDb(sqLiteDatabase);
            while (c.moveToNext()){
                time.add("Time    : " + Integer.toString((int) c.getLong(c.getColumnIndex(ContactContract.ContactEntry.TIME_DATE))));
                shift.add("Shift   : " + c.getString(c.getColumnIndex(ContactContract.ContactEntry.SHIFT)));
                message.add("Message : " + c.getString(c.getColumnIndex(ContactContract.ContactEntry.MESSAGE)));
                Log.d("DB OPER", "card_view");
            }
            c.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
