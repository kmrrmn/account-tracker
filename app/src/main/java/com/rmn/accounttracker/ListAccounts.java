package com.rmn.accounttracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListAccounts extends AppCompatActivity {
	ListView listAccounts;
	DrawerLayout drawer;
	ActionBarDrawerToggle drawerToggle;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listaccounts);
		Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
		setSupportActionBar(toolbar);
        ImageView imageView=(ImageView)findViewById(R.id.img);
        imageView.setVisibility(View.VISIBLE);
		TextView header=(TextView)findViewById(R.id.header);
		header.setVisibility(View.GONE);
		listAccounts = (ListView) this.findViewById(R.id.listAccounts);
		listAccounts.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View selectedView, int arg2,long arg3) {
				TextView  textAccountId = (TextView) selectedView.findViewById(R.id.textAccountId);
				Log.d("Accounts", "Selected Account Id : " + textAccountId.getText().toString());
				Intent intent = new Intent(ListAccounts.this, UpdateAccount.class);
				intent.putExtra("accountid", textAccountId.getText().toString());
				startActivity(intent);
			}
		});



		drawer = (DrawerLayout) findViewById(R.id.drawer);

		drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.openDrawer, R.string.closeDrawer) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		drawer.setDrawerListener(drawerToggle);
		drawerToggle.syncState();
//
//		drawerToggle.setDrawerIndicatorEnabled(true);
//		drawer.setDrawerListener(drawerToggle);
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//		getSupportActionBar().setHomeButtonEnabled(true);


		setTitle("");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return Utils.inflateMenu(this,menu);
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.optListAccounts){
            if (drawer.isDrawerOpen(GravityCompat.END)){
                drawer.closeDrawer(GravityCompat.END);
            }else
            drawer.openDrawer(GravityCompat.END);
        }
		return  Utils.handleMenuOption(this,item);
	}
	
	
	@Override 
	public void onStart() {
		super.onStart();
		try {
			DBHelper dbhelper = new DBHelper(this);
			SQLiteDatabase db = dbhelper.getReadableDatabase();
			Cursor  accounts = db.query( Database.ACCOUNTS_TABLE_NAME,null,null,null,null,null,null);
			
			String from [] = { Database.ACCOUNTS_ID, Database.ACCOUNTS_BANK, Database.ACCOUNTS_HOLDERS, Database.ACCOUNTS_BALANCE };
			int to [] = { R.id.textAccountId,R.id.textBank, R.id.textHolder, R.id.textBalance};
			
			SimpleCursorAdapter ca  = new SimpleCursorAdapter(this,R.layout.account, accounts,from,to);
			
		    ListView listAccounts = (ListView) this.findViewById( R.id.listAccounts);
		    listAccounts.setAdapter(ca);
		    dbhelper.close();
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	public void addAccount(View v)
	{
		Intent intent = new Intent(this,AddAccount.class);
    	startActivity(intent);
	}
	
	public void addTransaction(View v)
	{
		Intent intent = new Intent(this,AddTransaction.class);
    	startActivity(intent);
	}
	
	
	public void recentTransactions(View v)
	{
		Intent intent = new Intent(this,ListRecentTransactions.class);
    	startActivity(intent);
	}
}

