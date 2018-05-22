package ru.andreev_av.authorizationtest.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ru.andreev_av.authorizationtest.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mAuthorizationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findComponents();

        initToolbar();

        initListeners();
    }

    private void findComponents() {
        mAuthorizationButton = findViewById(R.id.btn_authorization);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.title_main_activity));
    }

    private void initListeners() {
        mAuthorizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignInActivity.newIntent(MainActivity.this);
                startActivity(intent);
                overridePendingTransition(R.anim.move_rigth_activity_out, R.anim.move_left_activity_in);
            }
        });
    }
}
