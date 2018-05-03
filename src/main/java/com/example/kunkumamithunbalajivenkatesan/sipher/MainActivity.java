package com.example.kunkumamithunbalajivenkatesan.sipher;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.system.Os;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, createMessage.OnFragmentInteractionListener, decrypt.FragmentInteraction{
    public FragmentManager fm;
    public CaeserCipher caeserCipher = new CaeserCipher();
    public Rot13 rot13 = new Rot13();
    public OneTimePad oneTimePad = new OneTimePad();
    private String notficationID = "Sipher";
    private int notficationNo = 001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerElevation(1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideSoftKeyboard(drawerView);
        }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fm = getFragmentManager();
        try {
            fm.beginTransaction().replace(R.id.fragment_content, help.class.newInstance()).addToBackStack(null).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(notficationID,"Sipher",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Test");
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this,notficationID);
        notificationCompat.setContentTitle("Test");
        notificationCompat.setContentText("Successful");
        notificationCompat.setSmallIcon(R.drawable.ic_dev_logo);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notficationNo, notificationCompat.build());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean a = fm.popBackStackImmediate("pass", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.d("onOptionsItemSelected: ", String.valueOf(a));
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            try {
                fm.beginTransaction().replace(R.id.fragment_content, change.class.newInstance()).addToBackStack(null).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.action_help) {
            try {
                fm.beginTransaction().replace(R.id.fragment_content, viewMessages.class.newInstance()).addToBackStack(null).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment mFragment = null;
        Class fragmentClass;
        if (id == R.id.nav_en) {
            fragmentClass = createMessage.class;
        } else if (id == R.id.nav_his) {
            sendNotification();
            fragmentClass = login.class;

        } else if (id == R.id.nav_help) {
            fragmentClass = help.class;
        } else if (id == R.id.nav_decrypt) {
            fragmentClass = decrypt.class;
        } else {
            fragmentClass = help.class;

        }
        try {
            mFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean a = fm.popBackStackImmediate("pass", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.d("onNavigation ", String.valueOf(a));
        fm.beginTransaction().replace(R.id.fragment_content, mFragment).addToBackStack(null).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        setTitle(item.getTitle());
        drawer.closeDrawers();
        return true;
    }


    @Override
    public String sendData(String message, String shift, int type) {
        long time = System.currentTimeMillis();
        String message1 = "";
        ContactDbHelper contactDbHelper = new ContactDbHelper(getApplicationContext());
        SQLiteDatabase db = contactDbHelper.getWritableDatabase();
        boolean enc = false;
        switch (type) {
            case 0:
                rot13.rot13(message);
                message1 = rot13.rot();
                contactDbHelper.addMessage(time, Integer.toString(13), message1, db);
                enc = true;
                break;
            case 1:
                caeserCipher.CaserCipher(message, Integer.parseInt(shift));
                if (caeserCipher.isInteger()) {
                    message1 = caeserCipher.encrypt();
                    contactDbHelper.addMessage(time, shift, message1, db);
                    enc = true;
                    break;
                }
            case 2:
                message1 = VigenereCipher.encrypt_vg(message, shift);
                contactDbHelper.addMessage(time, shift, message1, db);
                Log.d("sendData: ", shift);
                enc = true;
                break;
            case 3:
                message1 = oneTimePad.encrypt_otp(message, shift);
                contactDbHelper.addMessage(time, shift, message1, db);
                enc = true;
                break;
        }
        if (enc) {
            ClipboardManager clipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", message1 + "-" + shift);
            if (clipMan != null) {
                clipMan.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Message Copied To ClipBoard", Toast.LENGTH_SHORT).show();
            }
            return message1 + "-" + shift;
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Valid data", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    @Override
    public void shareData(String msg) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(
                Intent.EXTRA_TEXT,
                msg);
        startActivity(Intent.createChooser(send, "Share with"));
    }

    @Override
    public String decrypt(String message, String shift, int type) {
        String message1 = "";
        boolean enc = false;
        switch (type) {
            case 0:
                caeserCipher.CaserCipher(message, Integer.parseInt(shift));
                if (caeserCipher.isInteger()) {
                    message1 = caeserCipher.decrypt();
                    enc = true;
                }
                break;
            case 1:
                rot13.rot13(message);
                message1 = rot13.rot();

                enc = true;
                break;
            case 2:
                message1 = VigenereCipher.decrypt_vg(message, shift);
                enc = true;
                break;
            case 3:
                message1 = oneTimePad.decrypt_otp(message, shift);
                enc = true;
                break;
        }
        if (enc) {
            ClipboardManager clipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", message1 + "-" + shift);
            if (clipMan != null) {
                clipMan.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Message Copied To ClipBoard", Toast.LENGTH_SHORT).show();
            }
            return message1;
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Valid data", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

}

