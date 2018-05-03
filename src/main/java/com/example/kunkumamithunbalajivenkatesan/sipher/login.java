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


public class login extends Fragment {

    private EditText mpar;
    FloatingActionButton fabLogin;

    public login() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.login, container, false);
        mpar = v.findViewById(R.id.mpar);
        fabLogin = v.findViewById(R.id.fabLogin);
        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m = mpar.getText().toString();
                ContactDbHelper contactDbHelper = new ContactDbHelper(getActivity());
                SQLiteDatabase sqLiteDatabase = contactDbHelper.getReadableDatabase();
                Cursor c = contactDbHelper.readDB(sqLiteDatabase);
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
                    mpar.setText("");
                    FragmentManager fm = getFragmentManager();
                    try {
                        fm.beginTransaction().replace(R.id.fragment_content, viewMessages2.class.newInstance()).addToBackStack("pass").commit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
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
