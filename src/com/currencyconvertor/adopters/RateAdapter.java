package com.currencyconvertor.adopters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;





import com.currencyconvertor.activities.MainActivity;
import com.currencyconvertor.activities.R;
import com.currencyconvertor.entities.Currency;

@SuppressWarnings("rawtypes")
public class RateAdapter extends ArrayAdapter {
	// we use the constructor allowing to provide a List of objects for the data
	// to be binded.
	private String[] contryFullName;
	Resources res;
	int selected;
	Currency tempValues=null;
	@SuppressWarnings("unchecked")
	public RateAdapter(Context context, int textViewResourceId, List objects,
			Resources res, int selected) {
		super(context, textViewResourceId, objects);
		this.res = res;
		this.selected = selected;
		contryFullName = context.getResources().getStringArray(R.array.contriesName);
	}

	public void setSelected(int selected) {
		this.selected = selected;
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// retrieve the Person object binded at this position
		final Currency c = (Currency) getItem(position);
		// A ViewHolder keeps references to children views to avoid unneccessary
		// calls
		// to findViewById() on each row.
		ViewHolder holder;

		if (convertView == null) {
			convertView = View.inflate(getContext(), R.layout.list_row, null);

			// Creates a ViewHolder and store references to the two children
			// views
			// we want to bind data to.
			holder = new ViewHolder();
			holder.countryTextView = (TextView) convertView.findViewById(R.id.label);
			holder.coutryFlagImage = (ImageView) convertView.findViewById(R.id.flag);
			holder.coutryFlagImage.setFocusable(false);
			holder.coutryFlagImage.setFocusableInTouchMode(false);
			holder.countryCheckBox = (CheckBox) convertView.findViewById(R.id.cbk);
			convertView.setTag(holder);
		} else {
			// Get the ViewHolder back to get fast access to the TextView
			// and the ImageView.
			holder = (ViewHolder) convertView.getTag();
		}
		try{
			
			

			 String mDrawableName = c.getContryName().toLowerCase() + "_flag";
			 
		        int resID = res.getIdentifier(mDrawableName , "drawable", MainActivity.PACKAGE_NAME);
		        holder.coutryFlagImage.setImageResource(resID);
		        holder.countryTextView.setText(contryFullName[position]);
		// Bind the data efficiently with the holder.
		
	
			if (position == selected) {
				holder.countryCheckBox.setChecked(true);
			} else {
				holder.countryCheckBox.setChecked(false);
			}
		}catch(Exception e)
		{
			e.getMessage();
			
		}
		return convertView;
	}

	/**
	 *
	 * Inner holder class for a single row view in the ListView
	 *
	 */
	static class ViewHolder {
		public CheckBox countryCheckBox;
		public TextView countryTextView;
		ImageView coutryFlagImage;
	}

}