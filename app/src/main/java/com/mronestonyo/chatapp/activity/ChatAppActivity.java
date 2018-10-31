package com.mronestonyo.chatapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.base.BaseActivity;
import com.mronestonyo.chatapp.common.CommonFunctions;
import com.mronestonyo.chatapp.fragments.ChatAppMessageFragment;
import com.mronestonyo.chatapp.fragments.SignInFragment;
import com.mronestonyo.chatapp.fragments.SignUpFragment;

public class ChatAppActivity extends BaseActivity {

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private TextView txvToolbarTitle;
    private int mIndexSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_app);

        setupToolbar();

        init();

    }

    private void init() {
        txvToolbarTitle = (TextView) findViewById(R.id.txvToolbarTitle);
        txvToolbarTitle.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", "Chat app"));

        mIndexSelected = getIntent().getIntExtra("index", -1);
        Bundle args = getIntent().getBundleExtra("args");

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        displayView(mIndexSelected, args);
    }

    public void displayView(int id, Bundle args) {
        Fragment fragment = null;
        String title = "";
        mIndexSelected = id;
        switch (id) {
            case 1: {
                fragment = new SignInFragment();
                title = "Login";
                break;
            }
            case 2: {
                fragment = new SignUpFragment();
                title = "Sign Up";
                break;
            }
            case 3: {
                fragment = new ChatAppMessageFragment();
                title = "Sign Up";
                break;
            }
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            if (fragment != null) {
//                fragmentTransaction.replace(R.id.container_body, fragment);
//                fragmentTransaction.addToBackStack(null);
//            } else {
//                fragmentTransaction.add(R.id.container_body, fragment);
//            }
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            setActionBarTitle(title);
        }
    }

    public void setActionBarTitle(String title) {
        setTitle(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", title));
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, ChatAppActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            Log.d("antonhttp", "mIndexSelected: " + String.valueOf(mIndexSelected));
            if (mIndexSelected == 3) {
                finish();
            } else {
                finish();
                MainActivity.start(getViewContext());
            }
        }

    }
}
