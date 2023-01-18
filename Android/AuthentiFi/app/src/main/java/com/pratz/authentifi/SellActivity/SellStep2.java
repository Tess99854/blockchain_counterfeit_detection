package com.pratz.authentifi.SellActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.pratz.authentifi.ConnectionManager;
import com.pratz.authentifi.MainActivity;
import com.pratz.authentifi.R;
import com.pratz.authentifi.RetailerActivity.MainRetailerActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellStep2 extends Fragment {


	public SellStep2() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View view = inflater.inflate(R.layout.fragment_sell_step2, container, false);

		final String address,email;
		if(MainActivity.address==null) {
			email = MainRetailerActivity.email;
			address = MainRetailerActivity.address;
		}
		else {
			address = MainActivity.address;
			email = MainActivity.email;
		}
		ImageButton confirm = (ImageButton) view.findViewById(R.id.confirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String URL = address+"/sellerConfirm";
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("email", email);
					jsonObject.put("QRCode", SellActivity.qrcode);
					jsonObject.put("retailer", getArguments().getString("retailer"));
				} catch (JSONException e) {
					e.printStackTrace();
				}

				RequestQueue requestQueue = Volley.newRequestQueue(getContext());

				ConnectionManager.sendData(jsonObject.toString(), requestQueue, URL, new ConnectionManager.VolleyCallback() {
					@Override
					public void onSuccessResponse(String result) {
					}

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast toast = Toast.makeText(getContext(),
								"Could not confirm transaction, please try again.",
								Toast.LENGTH_LONG);

						toast.show();
					}
				});
				getActivity().finish();
			}
		});

		return view;
	}

}
