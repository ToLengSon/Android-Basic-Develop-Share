package com.example.wsong.demo;

public class MainItemBean {

    public interface MainItemBeanAction {
        void onAction(MainItemBean itemBean);
    }

    private String mTitle;
    private MainItemBeanAction mAction;

    public MainItemBean(String title, MainItemBeanAction action) {
        mTitle = title;
        mAction = action;
    }

    public void executeCallBack() {
        if (mAction != null) {
            mAction.onAction(this);
        }
    }

    public String getTitle() {
        return mTitle;
    }
}
