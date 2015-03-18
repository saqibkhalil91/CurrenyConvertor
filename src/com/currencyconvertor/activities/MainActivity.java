
package com.currencyconvertor.activities;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import com.currencyconvertor.adopters.RateAdapter;
import com.currencyconvertor.databases.DbHandler;
import com.currencyconvertor.entities.Currency;
import com.currencyconvertor.interfaces.JsonNotification;
import com.currencyconvertor.utilities.ConnectionDetector;
import com.currencyconvertor.utilities.CurrencyConvertorAsyncTask;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {

	private DbHandler db;
	private CurrencyConvertorAsyncTask currencyConvertorApi;
	private List<Currency> currencies;
	public static String PACKAGE_NAME;
	private ListView fromList;
	private ListView toList;
	private BigDecimal fromRate;
	private BigDecimal toRate;
	private int fromSelected;
	private int toSelected;
	private EditText fromInput;
	private Dialog fromDialog;
	private Dialog toDialog;
	private RateAdapter fromAdapter;
	private RateAdapter toAdapter;
	private EditText answerText;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setViews();
		sendCurrencyApiRequest();
		
		fromSelected = 0;
		toSelected = 1;

		final View view = this.findViewById(android.R.id.content);
		final Activity activity = this;
		view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
			    Rect r = new Rect();
			    //r will be populated with the coordinates of your view that area still visible.
			    view.getWindowVisibleDisplayFrame(r);

			    int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
			    if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
			    	fromInput.setCursorVisible(true);
			    }
			 }
			});
			
			// Setup event to dismiss keyboard when background is touched
			view.setOnTouchListener(new View.OnTouchListener() {  
				@Override
			    public boolean onTouch (View v, MotionEvent event)
			    {
			        hideSoftKeyboard(activity);
					EditText fromInput = (EditText) findViewById(R.id.fromInput);
					EditText answer = (EditText) findViewById(R.id.answer);
					fromInput.clearFocus();
					fromInput.setCursorVisible(false);
			        return false;
			    }
			});
			
			// Setup event to pick currency from
			final Button fromButton = (Button) findViewById(R.id.selectConvertFrom);
	        fromButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	showSelectFrom(v);
	            }
	        });
	        
	        // Setup event to pick currency to
	        final Button toButton = (Button) findViewById(R.id.selectConvertTo);
	        toButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	showSelectTo(v);
	            }
	        });
	        
	        // Setup event for swap button
	        final ImageButton swapButton = (ImageButton) findViewById(R.id.swapButton);
	        swapButton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	swapCurrencies(v);
	            }
	        });
	        
	        // Setup on change event for from value
	        fromInput = (EditText)findViewById(R.id.fromInput);
			fromInput.setCursorVisible(false);
	        fromInput.addTextChangedListener(new TextWatcher() {
	        	@Override
	        	public void afterTextChanged(Editable s) {
	        		try {
	        			
	        			calculateExchangeRate();
	        		} catch(NumberFormatException e) {
	        			// TODO Auto-generated catch block
	    				e.printStackTrace();
	        		}
	            }

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
				
				}

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
	        });
	        fromInput.setOnEditorActionListener(new OnEditorActionListener() {        
	            @Override
	            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	                if(actionId==EditorInfo.IME_ACTION_DONE){
	                     fromInput.clearFocus();
	                     fromInput.setCursorVisible(false);
	                }
	                return false;
	            }
	        });

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.refresh:
			sendCurrencyApiRequest();
			
			break;

		default:
		}
		return super.onOptionsItemSelected(item);
	}
	

	private void setViews() {
			db = new DbHandler(this);
			currencies = new ArrayList<Currency>();
			PACKAGE_NAME = getApplicationContext().getPackageName();
		}

		private void sendCurrencyApiRequest() {
			ConnectionDetector cd = new ConnectionDetector(this);
			boolean checkingConnection = cd.isConnectingToInternet();
			if (checkingConnection == true) {
				currencyConvertorApi = new CurrencyConvertorAsyncTask(this);

				currencyConvertorApi.execute(null, null, null);
				
				currencyConvertorApi.setApiResulListener(new JsonNotification() {
					
					@Override
					public void setnotify() {
						// TODO Auto-generated method stub
						currencies = db.getAllCurrencies();
					}
				});
			}
			else
			{
				offlineRates();
			}
		}

		
		
		public void showSelectFrom(View v) {
			if (fromDialog == null) {
				// custom dialog
				fromDialog = new Dialog(getBaseContext());
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Select Currency");
				builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog,int id) {
	                    dialog.dismiss();
	                }
	            });
				
				
				fromList = new ListView(this);
				fromList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    fromAdapter = new RateAdapter(this, R.layout.list_row, currencies, getResources(), fromSelected);
			    fromList.setAdapter(fromAdapter);
				fromList.setItemChecked(fromSelected, true);
				
				// Defining the item click listener for listView
		        OnItemClickListener itemClickListener = new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		            	MainActivity.this.selectNewFrom(position);
		            	fromDialog.dismiss();
		             }
		        };
		        
		        // Setting the item click listener for listView
		        fromList.setOnItemClickListener(itemClickListener);
		        fromList.setBackgroundColor(R.color.almostBlack);
		        
				builder.setView(fromList);
				fromDialog = builder.create();
			}
	 
			fromDialog.show();
		}
		
		
		private void offlineRates() {

			AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
			helpBuilder.setTitle("Internet is not available");
			helpBuilder.setMessage("Rates could not be updated and may be out of date.");

			helpBuilder.setPositiveButton("Thank you",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							currencies = db.getAllCurrencies();
							fromRate = new BigDecimal(currencies.get(fromSelected).getCurrencyvalue());
		       				fromInput.setText("1.00");
		       				toRate = new BigDecimal(currencies.get(toSelected).getCurrencyvalue());
		       				answerText.setText("0.54");
		       				calculateExchangeRate();
						}
					});
			// Remember, create doesn't show the dialog
			AlertDialog helpDialog = helpBuilder.create();
			helpDialog.show();

		}

		public boolean selectNewFrom(int selected) {
		    fromSelected = selected;
		    fromList.setItemChecked(fromSelected, true);
		    
		    fromAdapter.setSelected(fromSelected);
		    
		    Button button = (Button) findViewById(R.id.selectConvertFrom);
		    ImageView flag = (ImageView) findViewById(R.id.fromImage);
		    
		    try {
		    	button.setText(currencies.get(fromSelected).getContryname());
		    	//EditText fromInput = (EditText) findViewById(R.id.fromInput);
			
				fromRate = new BigDecimal(currencies.get(fromSelected).getCurrencyvalue());
				String file = currencies.get(fromSelected).getContryname().toLowerCase() + "_flag";
				int resID = getResources().getIdentifier(file , "drawable", MainActivity.PACKAGE_NAME);
				flag.setImageResource(resID);
				calculateExchangeRate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return true;
		}


		public void calculateExchangeRate() {
			try {
				fromRate = new BigDecimal(currencies.get(fromSelected).getCurrencyvalue());
				toRate = new BigDecimal(currencies.get(toSelected).getCurrencyvalue());
				EditText fromInput = (EditText) findViewById(R.id.fromInput);
				double value = Double.parseDouble(fromInput.getText().toString());
				double exchangeRate =  toRate.doubleValue() /fromRate.doubleValue();
				double answer = value * exchangeRate;
			 answerText = (EditText) findViewById(R.id.answer);
				answerText.setText(String.format("%.2f", answer));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public void showSelectTo(View v) {
			if (toDialog == null) {
				// custom dialog
				toDialog = new Dialog(getBaseContext());
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Select Currency");
				builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog,int id) {
	                    dialog.dismiss();
	                }
	            });
				
				toList = new ListView(this);
				toList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				toAdapter = new RateAdapter(this, R.layout.list_row, currencies, getApplicationContext().getResources(), toSelected);
				toList.setAdapter(toAdapter);
				toList.setItemChecked(toSelected, true);
				
				// Defining the item click listener for listView
		        OnItemClickListener itemClickListener = new OnItemClickListener() {
		            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		            	MainActivity.this.selectNewTo(position);
		            	toDialog.dismiss();
		            }
		        };
		        
		        // Setting the item click listener for listView
		        toList.setOnItemClickListener(itemClickListener);
		        toList.setBackgroundColor(R.color.almostBlack);
		        
				builder.setView(toList);
				toDialog = builder.create();
			}
			
			toDialog.show();
		}
		public boolean selectNewTo(int selected) {
		    toSelected = selected;
		    toList.setItemChecked(toSelected, true);
		    
		    toAdapter.setSelected(toSelected);
		    
		    Button button = (Button) findViewById(R.id.selectConvertTo);
		    ImageView flag = (ImageView) findViewById(R.id.toImage);
		    
		    try {
		    	button.setText(currencies.get(toSelected).getContryname());
				fromRate = new BigDecimal(currencies.get(toSelected).getCurrencyvalue());
				String file = currencies.get(toSelected).getContryname().toLowerCase() + "_flag";
				int resID = getResources().getIdentifier(file , "drawable", MainActivity.PACKAGE_NAME);
				flag.setImageResource(resID);
				calculateExchangeRate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return true;
		}
		public void swapCurrencies(View v) {
			// Back up flag
			ImageView fromFlag = (ImageView) findViewById(R.id.fromImage);
			ImageView toFlag = (ImageView) findViewById(R.id.toImage);
			
			// Swap
			try {
				String file =currencies.get(fromSelected).getContryname().toLowerCase() + "_flag";
				int resID = getResources().getIdentifier(file , "drawable", MainActivity.PACKAGE_NAME);
				toFlag.setImageResource(resID);
				file = currencies.get(toSelected).getContryname().toLowerCase() + "_flag";
				resID = getResources().getIdentifier(file , "drawable", MainActivity.PACKAGE_NAME);
				fromFlag.setImageResource(resID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int tempSelected = toSelected;
			toSelected = fromSelected;
			fromSelected = tempSelected;
			
			if (fromList != null) {
				fromList.setItemChecked(fromSelected, true);
			}
			if (toList != null) {
				toList.setItemChecked(toSelected, true);
			}
			
			Button selectFromButton = (Button) findViewById(R.id.selectConvertFrom);
			String labelTo = (String) selectFromButton.getText();
		    
		    Button selectToButton = (Button) findViewById(R.id.selectConvertTo);
		    String labelFrom = (String) selectToButton.getText();
		    
		    selectFromButton.setText(labelFrom);
		    selectToButton.setText(labelTo);
			
			calculateExchangeRate();
		}
		private static void hideSoftKeyboard(Activity activity) {
		    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
		 @Override
		    public void onConfigurationChanged(Configuration newConfig) {
		        super.onConfigurationChanged(newConfig);
		        // Checks whether a hardware keyboard is available
		        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
		        	fromInput.setCursorVisible(true);
		        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
		        	
		        }
		    }
}
