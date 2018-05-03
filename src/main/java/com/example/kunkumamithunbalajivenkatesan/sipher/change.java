package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class change extends Fragment {

    private EditText old, new1, new2;
    FloatingActionButton fabChange;


    public change() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.change, container, false);

        old = v.findViewById(R.id.old);
        new1 = v.findViewById(R.id.new1);
        new2 = v.findViewById(R.id.new2);
        fabChange = v.findViewById(R.id.fabChange);
        fabChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = old.getText().toString();
                ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
                SQLiteDatabase a = contactDbHelper.getReadableDatabase();
                Cursor c = contactDbHelper.readDB(a);
                StringBuilder info = new StringBuilder();
                if(c.getCount()!=0){
                    if(c.moveToFirst()){
                        do{
                            info.append(c.getString(c.getColumnIndex(ContactContract.ContactEntry.lol)));
                        }while(c.moveToNext());
                    }
                    c.close();
                }
                if (m.equals(info.toString())){
                    if (new1.getText().toString().equals(new2.getText().toString())){

                        SQLiteDatabase sqLiteDatabase = contactDbHelper.getWritableDatabase();
                        contactDbHelper.changeLol(sqLiteDatabase, old.getText().toString(), new2.getText().toString());
                        old.setText("");
                        new1.setText("");
                        new2.setText("");
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getFragmentManager();
                        try {
                            fm.beginTransaction().replace(R.id.fragment_content, help.class.newInstance()).commit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        new1.setText("");
                        new2.setText("");
                        Toast.makeText(getContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Old Password is Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
