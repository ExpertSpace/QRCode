package com.example.qrcode;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class YandexAppMetrica extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final String API_KEY = "f28cad09-4349-45b5-8a8f-c5cab91b7b1f";

        YandexMetricaConfig yandexMetricaConfig = YandexMetricaConfig.newConfigBuilder(API_KEY).build();

        YandexMetrica.activate(getApplicationContext(), yandexMetricaConfig);

        YandexMetrica.enableActivityAutoTracking(this);
    }
}
