package com.mronestonyo.chatapp.base;

import android.content.Context;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected Context getViewContext() {
        return mContext;
    }

    public void showLogout(boolean isShow) {
        ((BaseActivity) getViewContext()).showLogout(isShow);
    }
}
