package com.mronestonyo.chatapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mronestonyo.chatapp.R;
import com.mronestonyo.chatapp.activity.ChatAppActivity;
import com.mronestonyo.chatapp.base.BaseFragment;
import com.mronestonyo.chatapp.common.CommonFunctions;
import com.mronestonyo.chatapp.common.PreferenceUtils;
import com.mronestonyo.chatapp.pojo.Users;

public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    private TextView txvTos;
    private TextView txvLogin;
    private TextView txvSignUp;
    private EditText edtUserName;
    private EditText edtPassword;

    private TextView txvErrorUsername;
    private TextView txvErrorPassword;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        showLogout(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        setHasOptionsMenu(true);

        init(view);

        return view;
    }

    private void init(View view) {
        txvLogin = view.findViewById(R.id.txvLogin);
        SpannableString content = new SpannableString(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", getString(R.string.string_login)));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txvLogin.setText(content);
        txvTos = view.findViewById(R.id.txvTos);
        txvTos.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Medium.ttf", getString(R.string.string_tos)));
        txvSignUp = view.findViewById(R.id.txvSignUp);
        txvSignUp.setOnClickListener(this);
        edtUserName = view.findViewById(R.id.edtUserName);
        edtPassword = view.findViewById(R.id.edtPassword);
        txvErrorUsername = view.findViewById(R.id.txvErrorUsername);
        txvErrorUsername.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "Value is incorrect"));
        txvErrorPassword = view.findViewById(R.id.txvErrorPassword);
        txvErrorPassword.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "Value is incorrect"));

        txvLogin.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvLogin: {
                ((ChatAppActivity) getViewContext()).displayView(1, null);
                break;
            }
            case R.id.txvSignUp: {
                String username = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                evaluateForm(username, password);
                break;
            }
        }
    }

    private void evaluateForm(String username, String password) {
        if (username.length() > 0 && password.length() > 0) {
            if ((username.length() >= 8 && username.length() <= 16) && (password.length() >= 8 && password.length() <= 16)) {
                txvErrorUsername.setVisibility(View.GONE);
                txvErrorPassword.setVisibility(View.GONE);
                checkIfUserExists(username, password);
            } else {
                //show error
                if (!(username.length() >= 8 && username.length() <= 16)) {
                    txvErrorUsername.setVisibility(View.VISIBLE);
                    txvErrorPassword.setVisibility(View.GONE);
                }

                if (!(password.length() >= 8 && password.length() <= 16)) {
                    txvErrorUsername.setVisibility(View.GONE);
                    txvErrorPassword.setVisibility(View.VISIBLE);
                }

                if (!(username.length() >= 8 && username.length() <= 16) && !(password.length() >= 8 && password.length() <= 16)) {
                    txvErrorUsername.setVisibility(View.VISIBLE);
                    txvErrorPassword.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (username.length() == 0) {
                //show error
                txvErrorUsername.setVisibility(View.VISIBLE);
                txvErrorPassword.setVisibility(View.GONE);
            }

            if (password.length() == 0) {
                //show error
                txvErrorUsername.setVisibility(View.GONE);
                txvErrorPassword.setVisibility(View.VISIBLE);
            }

            if (username.length() == 0 && password.length() == 0) {
                //show error
                txvErrorUsername.setVisibility(View.VISIBLE);
                txvErrorPassword.setVisibility(View.VISIBLE);
            }
        }
    }

    private void checkIfUserExists(final String username, final String password) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        Query query = myRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean isNewUser = dataSnapshot.exists();

                if (!isNewUser) {
                    DatabaseReference myRef = database.getReference("users");
                    myRef.child(username).setValue(new Users(username, password), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            if (databaseError != null) {
                                Toast.makeText(getViewContext(), "Something went wrong while saving your data. Please try again later.", Toast.LENGTH_SHORT).show();
                            } else {
                                Bundle args = new Bundle();
                                args.putString("KEY_USERNAME", username);
                                PreferenceUtils.saveStringPreference(getViewContext(), "KEY_USERNAME", username);
                                PreferenceUtils.saveBooleanPreference(getViewContext(), "IS_LOGIN", true);
                                ((ChatAppActivity) getViewContext()).displayView(3, args);
                            }

                        }
                    });
                } else {
                    txvErrorUsername.setVisibility(View.VISIBLE);
                    txvErrorPassword.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (databaseError != null) {
                    Toast.makeText(getViewContext(), "Something went wrong while saving your data. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
