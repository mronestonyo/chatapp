package com.mronestonyo.chatapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.base.BaseActivity;
import com.mronestonyo.chatapp.common.PreferenceUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        findViewById(R.id.txvSignUp).setOnClickListener(this);
        findViewById(R.id.txvLogin).setOnClickListener(this);

        if(PreferenceUtils.getBooleanPreference(getViewContext(),"IS_LOGIN")){
            finish();
            ChatAppActivity.start(getViewContext(), 3, null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvSignUp: {
                finish();
                ChatAppActivity.start(getViewContext(), 2, null);
                break;
            }
            case R.id.txvLogin: {
                finish();
                ChatAppActivity.start(getViewContext(), 1, null);
                break;
            }
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
