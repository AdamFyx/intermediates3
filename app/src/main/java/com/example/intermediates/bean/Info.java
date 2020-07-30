package com.example.intermediates.bean;

import android.webkit.JavascriptInterface;

public class Info {
    private String name;
    private  String describe;

    public Info(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }
    @JavascriptInterface
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "info{" +
                "name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }

    public Info() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @JavascriptInterface
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
