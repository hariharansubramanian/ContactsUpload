package com.example.hari.contactsupload;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentResolver content = getContentResolver();
        Cursor c = content.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Log.d(TAG,"Phase 1 started");
        if (c.moveToFirst()) {
            ArrayList<String> contacts = new ArrayList<>();
            do {
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d(TAG,"Got the ID");
                if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor num = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
                    while (num.moveToNext()) {
                        String contactNum = num.getString(num.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contacts.add(contactNum);
                        Log.d(TAG, "Got Phone number and Added to list..");
                    }
                    num.close();
                }
                Log.d(TAG,"Prepare for contact numbers..");
                for (int i = 0; i <contacts.size() ; i++) {
                    Log.d(TAG,(contacts.get(i)));

                }

            }while(c.moveToNext());



        }c.close();
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
