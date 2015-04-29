
package com.example.greendaoexample.activity;

import android.app.Activity;

public abstract class BaseActivity extends Activity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
        setLinstener();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setLinstener();

}
