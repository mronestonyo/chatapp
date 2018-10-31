package com.mronestonyo.chatapp.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.activity.ChatAppActivity;
import com.mronestonyo.chatapp.common.PreferenceUtils;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context getViewContext() {
        return this;
    }

    public void setupToolbar() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLogout(boolean isShow) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView txvLogout = toolbar.findViewById(R.id.txvLogout);
        txvLogout.setOnClickListener(this);
        if (isShow) {
            txvLogout.setVisibility(View.VISIBLE);
        } else {
            txvLogout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvLogout: {
                PreferenceUtils.removePreference(getViewContext(), "KEY_USERNAME");
                PreferenceUtils.saveBooleanPreference(getViewContext(), "IS_LOGIN", false);
                ((ChatAppActivity) getViewContext()).displayView(2, null);
                break;
            }
        }
    }
}
