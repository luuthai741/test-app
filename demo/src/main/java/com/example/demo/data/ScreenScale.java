package com.example.demo.data;

import com.example.demo.utils.constants.RoleType;

public class ScreenScale {
    private static ScreenScale instance;
    private double width;
    private double height;

    public static ScreenScale getInstance() {
        if (instance == null) {
            instance = new ScreenScale();
        }
        return instance;
    }

    public static void setInstance(ScreenScale instance) {
        ScreenScale.instance = instance;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
