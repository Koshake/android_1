package com.koshake1.lesson1;

public class CityPresenter {
    private static CityPresenter instance = null;
    private boolean showWindSpeed;

    private CityPresenter() {
        this.showWindSpeed = false;
    }

    public void setShowWindSpeed(boolean showWindSpeed) {
        this.showWindSpeed = showWindSpeed;
    }

    public boolean isShowWindSpeed() {
        return showWindSpeed;
    }

    public static CityPresenter getInstance() {
        if (instance == null) {
            instance = new CityPresenter();
        }
        return instance;
    }
}

