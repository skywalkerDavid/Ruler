package com.skywalker.ruler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class RulerActivity extends AppCompatActivity implements AppComponentProvider {

    protected AppComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruler);
    }

    @Override
    public AppComponent getComponent() {
        if (component == null) {
            final AndroidModule androidModule = new AndroidModule();
            component = DaggerAppComponent.builder()
                .androidModule(androidModule)
                .build();
        }

        return component;
    }
}
