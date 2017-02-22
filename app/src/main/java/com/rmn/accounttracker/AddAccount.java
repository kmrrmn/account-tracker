package com.rmn.accounttracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccount extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addaccount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        TextView header=(TextView)findViewById(R.id.header);
        header.setText("Add Account");

        setTitle("");
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return Utils.inflateMenu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            NavUtils.navigateUpTo(this,new Intent(this,ListAccounts.class));
        }
        return Utils.handleMenuOption(this, item);
    }

    public void addAccount(View v) {
        // get access to views

        TextInputEditText editAcno = (TextInputEditText) this.findViewById(R.id.editAcno);
        TextInputEditText editCno = (TextInputEditText) this.findViewById(R.id.editCno);
        TextInputEditText editHolders = (TextInputEditText) this.findViewById(R.id.editHolders);
        TextInputEditText editBankName = (TextInputEditText) this.findViewById(R.id.editBankName);
        TextInputEditText editBranchName = (TextInputEditText) this.findViewById(R.id.editBranchName);
        TextInputEditText editAddress = (TextInputEditText) this.findViewById(R.id.editAddress);
        TextInputEditText editIFSC = (TextInputEditText) this.findViewById(R.id.editIFSC);
        TextInputEditText editMICR = (TextInputEditText) this.findViewById(R.id.editMICR);
        TextInputEditText editBalance = (TextInputEditText) this.findViewById(R.id.editBalance);
        TextInputEditText editRemarks = (TextInputEditText) this.findViewById(R.id.editRemarks);

        try {
            DBHelper dbhelper = new DBHelper(this);
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            Log.d("Account", "Got Writable database");
            // execute insert command

            ContentValues values = new ContentValues();
            values.put(Database.ACCOUNTS_ACNO, editAcno.getText().toString());
            values.put(Database.ACCOUNTS_CNO, editCno.getText().toString());
            values.put(Database.ACCOUNTS_HOLDERS, editHolders.getText().toString());
            values.put(Database.ACCOUNTS_BANK, editBankName.getText().toString());
            values.put(Database.ACCOUNTS_BRANCH, editBranchName.getText().toString());
            values.put(Database.ACCOUNTS_ADDRESS, editAddress.getText().toString());
            values.put(Database.ACCOUNTS_IFSC, editIFSC.getText().toString());
            values.put(Database.ACCOUNTS_MICR, editMICR.getText().toString());
            values.put(Database.ACCOUNTS_BALANCE, editBalance.getText().toString());
            values.put(Database.ACCOUNTS_REMARKS, editRemarks.getText().toString());


            long rows = db.insert(Database.ACCOUNTS_TABLE_NAME, null, values);
            db.close();
            if (rows > 0) {
                Toast.makeText(this, "Added Account Successfully!", Toast.LENGTH_LONG).show();
                this.finish();
            } else
                Toast.makeText(this, "Sorry! Could not add account!", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

}
