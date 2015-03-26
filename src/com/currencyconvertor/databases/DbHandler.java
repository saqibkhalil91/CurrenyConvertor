package com.currencyconvertor.databases;

import java.util.ArrayList;
import java.util.List;

import com.currencyconvertor.entities.Currency;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "currency.db";
	public static final String TABLE_CURRENCY_CONVERTER = "currency_convertor";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COUNTRY_NAME = "Country_Name";
	public static final String COLUMN_CURRENCY_VALUE = "Country_value";

	private SQLiteDatabase database;
	private Context context;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_CURRENCY_CONVERTER + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_COUNTRY_NAME
			+ " text not null," + COLUMN_CURRENCY_VALUE + " text not null);";

	public DbHandler(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this.context = context;

		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		// initializing the values
		for (int i = 0; i < 8; i++) {
			ContentValues cv = new ContentValues();

			cv.put(COLUMN_COUNTRY_NAME, "countryname");
			cv.put(COLUMN_CURRENCY_VALUE, "0");

			long result = db.insert(TABLE_CURRENCY_CONVERTER, null, cv);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCY_CONVERTER);
		onCreate(db);

	}

	private void openConnection() {
		database = this.getWritableDatabase();
	}

	private void closeConnection() {
		database.close();
	}

	public void updatedatemydatabase(Currency c) {
		openConnection();
		ContentValues update = new ContentValues();
		update.put(COLUMN_COUNTRY_NAME, c.getContryName());
		update.put(COLUMN_CURRENCY_VALUE, c.getCurrencyValue());

		database.update(TABLE_CURRENCY_CONVERTER, update,
				COLUMN_ID + "=" + c.getId(), null);
		closeConnection();

	}
	
	public List<Currency> getAllCurrencies()
	{
		openConnection();
		List <Currency> currencies = new ArrayList<Currency>();
		
		Currency mCurrency;
		
		//String col[] = new String[]{colId,colfirstName,collastName,colEmail,colDOB,colAddress};
		Cursor c = database.query(TABLE_CURRENCY_CONVERTER, null, null, null, null, null, null);
		int colid = c.getColumnIndex(COLUMN_ID);
		int mCOLUMN_COUNTRY_NAME=c.getColumnIndex(COLUMN_COUNTRY_NAME);
		int mCOLUMN_CURRENCY_VALUE=c.getColumnIndex(COLUMN_CURRENCY_VALUE);

	
		while(c.moveToNext())
		{
			mCurrency = new  Currency();//(c.getInt(colid),c.getString(mCOLUMN_COUNTRY_NAME),c.getString(mCOLUMN_CURRENCY_VALUE),context );
			mCurrency.setId(c.getInt(colid));
			mCurrency.setContryName(c.getString(mCOLUMN_COUNTRY_NAME));
			mCurrency.setCurrencyValue(c.getString(mCOLUMN_CURRENCY_VALUE));
			currencies.add(mCurrency);	
		}
		closeConnection();
		return currencies;
		
	}

}
