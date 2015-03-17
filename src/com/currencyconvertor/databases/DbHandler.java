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
	public static final String Table_currency_convertor = "currency_convertor";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_Country_Name = "Country_Name";
	public static final String COLUMN_Currency_Value = "Country_value";

	private SQLiteDatabase database;
	private Context context;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ Table_currency_convertor + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_Country_Name
			+ " text not null," + COLUMN_Currency_Value + " text not null);";

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

			cv.put(COLUMN_Country_Name, "countryname");
			cv.put(COLUMN_Currency_Value, "0");

			long result = db.insert(Table_currency_convertor, null, cv);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + Table_currency_convertor);
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
		update.put(COLUMN_Country_Name, c.getContryname());
		update.put(COLUMN_Currency_Value, c.getCurrencyvalue());

		database.update(Table_currency_convertor, update,
				COLUMN_ID + "=" + c.getId(), null);
		closeConnection();

	}
	
	public List<Currency> getAllCurrencies()
	{
		openConnection();
		List <Currency> currencies = new ArrayList<Currency>();
		
		Currency mCurrency;
		
		//String col[] = new String[]{colId,colfirstName,collastName,colEmail,colDOB,colAddress};
		Cursor c = database.query(Table_currency_convertor, null, null, null, null, null, null);
		int colid = c.getColumnIndex(COLUMN_ID);
		int mCOLUMN_Country_Name=c.getColumnIndex(COLUMN_Country_Name);
		int mCOLUMN_Currency_Value=c.getColumnIndex(COLUMN_Currency_Value);

	
		while(c.moveToNext())
		{
			mCurrency = new  Currency();//(c.getInt(colid),c.getString(mCOLUMN_Country_Name),c.getString(mCOLUMN_Currency_Value),context );
			mCurrency.setId(c.getInt(colid));
			mCurrency.setContryname(c.getString(mCOLUMN_Country_Name));
			mCurrency.setCurrencyvalue(c.getString(mCOLUMN_Currency_Value));
			currencies.add(mCurrency);	
		}
		closeConnection();
		return currencies;
		
	}

}
