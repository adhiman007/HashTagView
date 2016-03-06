package com.view.hashtag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lib.hashtag.callbacks.OnHashTagClickListener;
import com.lib.hashtag.views.HashTagEditText;
import com.lib.hashtag.views.HashTagView;

public class MainActivity extends AppCompatActivity implements OnHashTagClickListener, View.OnClickListener {
    private HashTagView hashTagView;
    private HashTagEditText hashTagEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hashTagView = (HashTagView) findViewById(R.id.tag_text);
        hashTagEditText = (HashTagEditText) findViewById(R.id.tag_edit);
        hashTagView.setOnHashTagClickListener(this);

        findViewById(R.id.btn_tags).setOnClickListener(this);
        findViewById(R.id.btn_ats).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tags:
                Toast.makeText(getApplicationContext(), TextUtils.join(",", hashTagEditText.getHashTags()), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_ats:
                Toast.makeText(getApplicationContext(), TextUtils.join(",", hashTagEditText.getAts()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(HashTagView v, char c, String tag) {
        Toast.makeText(getApplicationContext(), tag + " is " + c, Toast.LENGTH_LONG).show();
    }
}
