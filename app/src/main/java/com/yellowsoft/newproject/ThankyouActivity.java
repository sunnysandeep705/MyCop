package com.yellowsoft.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThankyouActivity extends AppCompatActivity {
	LinearLayout backtohome_ll_btn;
	TextView order_number_tv;

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(intent);
		finish();

	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thankyoupage);

		order_number_tv = (TextView) findViewById(R.id.order_number_tv);

		order_number_tv.setText(getIntent().getStringExtra("id"));

		backtohome_ll_btn = (LinearLayout)findViewById(R.id.backtohome_ll_btn);
		backtohome_ll_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(intent);
				finish();


				}
		});

	}
}
