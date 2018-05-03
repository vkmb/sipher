package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class decrypt extends Fragment {


    private EditText d_message, d_shift;
    private TextView decrypt_text;
    FloatingActionButton fabDecrypt;
    int selection ;
    Spinner spinner2;
    FragmentInteraction fi;



    public decrypt() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.decrypt, container, false);
        d_message = v.findViewById(R.id.d_message);
        d_shift = v.findViewById(R.id.d_shift);
        decrypt_text = v.findViewById(R.id.decrypt_text);
        spinner2 = v.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection = i;
                checkSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fabDecrypt = v.findViewById(R.id.fabDecrypt);
        fabDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(d_message.getText().toString().length() > 0 && d_shift.getText().toString().length()>0 && selection < 2){
                int key = Integer.parseInt(d_shift.getText().toString());
                key = checkKey(key);
                decrypt_text.setText(fi.decrypt(d_message.getText().toString(), Integer.toString(key), selection));

                }
                else if (d_message.getText().toString().length() > 0 && d_shift.getText().toString().length()>0 && selection >= 2){
                    if (selection > 2) {
                        if (d_message.getText().toString().length() == d_shift.getText().toString().length())
                        { decrypt_text.setText(fi.decrypt(d_message.getText().toString(), d_shift.getText().toString(), selection));
                        }
                        else {
                            Toast.makeText(getContext(),"Both the words should be of same length", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        decrypt_text.setText(fi.decrypt(d_message.getText().toString(), d_message.getText().toString(), selection));

                    }
                }
                else{
                    Toast.makeText(getContext(),"Pls enter something", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fabDecrypt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                d_message.setText("");
                decrypt_text.setText(R.string.decrypt);
                checkSelection();
                return false;
            }
        });
        return v;
    }

    private int checkKey(int key) {
        if (key < 0){
            key = key * -1;
        }
        if (key > 26){
            while (key > 26){
                key = key % 26;
            }
        }
        return key;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a =(Activity)context;
        fi = (FragmentInteraction)a;
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    public interface FragmentInteraction{

        String decrypt(String data, String shift, int type);
    }

    private void checkSelection(){
        if (selection == 0){
            d_shift.setVisibility(View.INVISIBLE);
            d_shift.setText(R.string.rot13);
        }
        else if (selection == 1 ){
            d_shift.setInputType(InputType.TYPE_CLASS_NUMBER);
            d_shift.setVisibility(View.VISIBLE);
            d_shift.setText("");
        }
        else if (selection >= 2){
            d_shift.setInputType(InputType.TYPE_CLASS_TEXT);
            d_shift.setVisibility(View.VISIBLE);
            d_shift.setText("");
        }
    }

}
