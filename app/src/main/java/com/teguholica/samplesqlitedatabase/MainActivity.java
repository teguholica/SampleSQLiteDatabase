package com.teguholica.samplesqlitedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ArrayAdapter<Contact> listAdapter;
    private ListView vList;
    private FloatingActionButton fab;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTotal = (TextView) findViewById(R.id.total);
        vList = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        db = new DatabaseHandler(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddEditActivity.class);
                i.putExtra(Const.ACTION, Const.ACTION_ADD);
                startActivityForResult(i, Const.REQUEST_ADD);
            }
        });

        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = listAdapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra(Const.KEY_ID, contact.getId());
                startActivity(intent);
            }
        });
        vList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(null)
                        .setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Contact contact = listAdapter.getItem(i);
                                if (which == 0) {
                                    Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
                                    intent.putExtra(Const.ACTION, Const.ACTION_EDIT);
                                    intent.putExtra(Const.KEY_ID, contact.getId());
                                    intent.putExtra(Const.KEY_NAME, contact.getName());
                                    intent.putExtra(Const.KEY_PHONE_NUMBER, contact.getPhoneNumber());
                                    startActivityForResult(intent, Const.REQUEST_EDIT);
                                } else if (which == 1) {
                                    db.deleteContact(contact);
                                    getData();
                                    Snackbar.make(fab, "Contact Deleted", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                builder.create().show();
                return true;
            }
        });

        getData();
    }

    private void getData() {
        txtTotal.setText(String.format(Locale.ENGLISH, "TOTAL CONTACTS : %1$d", db.getContactsCount()));
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, db.getAllContacts());
        vList.setAdapter(listAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getData();
        if (requestCode == Const.REQUEST_ADD) {
            Snackbar.make(fab, "New Contact Saved", Snackbar.LENGTH_LONG).show();
        } else if (requestCode == Const.REQUEST_EDIT) {
            Snackbar.make(fab, "Contact Edited", Snackbar.LENGTH_LONG).show();
        }
    }
}
