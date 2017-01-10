package com.teguholica.samplesqlitedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtPhoneNumber = (TextView) findViewById(R.id.phone_number);

        DatabaseHandler db = new DatabaseHandler(this);

        Contact contact = db.getContact(getIntent().getIntExtra(Const.KEY_NAME, -1));

        txtName.setText(contact.getName());
        txtPhoneNumber.setText(contact.getPhoneNumber());
    }
}
