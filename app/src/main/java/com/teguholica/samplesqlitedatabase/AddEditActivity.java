package com.teguholica.samplesqlitedatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddEditActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private TextView txtName, txtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        txtName = (TextView) findViewById(R.id.name);
        txtPhoneNumber = (TextView) findViewById(R.id.phone_number);
        Button btnSave = (Button) findViewById(R.id.save);

        db = new DatabaseHandler(this);

        if (getIntent().getStringExtra(Const.ACTION).equals(Const.ACTION_EDIT)) {
            txtName.setText(getIntent().getStringExtra(Const.KEY_NAME));
            txtPhoneNumber.setText(getIntent().getStringExtra(Const.KEY_PHONE_NUMBER));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra(Const.ACTION).equals(Const.ACTION_ADD)) {
                    db.addContact(new Contact(
                            -1,
                            txtName.getText().toString(),
                            txtPhoneNumber.getText().toString()
                    ));
                } else if (getIntent().getStringExtra(Const.ACTION).equals(Const.ACTION_EDIT)) {
                    db.updateContact(new Contact(
                            getIntent().getIntExtra(Const.KEY_ID, -1),
                            txtName.getText().toString(),
                            txtPhoneNumber.getText().toString()
                    ));
                }
                setResult(Const.RESULT_OK);
                finish();
            }
        });
    }
}
