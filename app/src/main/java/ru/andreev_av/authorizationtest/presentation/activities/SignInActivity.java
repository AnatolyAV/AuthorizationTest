package ru.andreev_av.authorizationtest.presentation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import ru.andreev_av.authorizationtest.R;
import ru.andreev_av.authorizationtest.domain.model.WeatherCurrent;
import ru.andreev_av.authorizationtest.presentation.presenters.SignInPresenter;
import ru.andreev_av.authorizationtest.presentation.views.SignInMvpView;
import ru.andreev_av.authorizationtest.utils.TemperatureUtils;

public class SignInActivity extends BaseActivity implements SignInMvpView {

    private static final int SNACKBAR_DURATION = 5000;

    @Inject
    SignInPresenter mSignInPresenter;

    private Toolbar mToolbar;
    private Button mEnterButton;
    private TextInputLayout mEmailTextInputLayout;
    private EditText mEmailEditText;
    private TextInputLayout mPasswordTextInputLayout;
    private EditText mPasswordEditText;

    private Snackbar mSnackbar;
    private ProgressBar progressBar;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        activityComponent().inject(this);

        findComponents();

        mEmailEditText.requestFocus();

        initToolbar();

        initSnackBar();

        initListeners();

        mSignInPresenter.attachView(this);
    }

    private void findComponents() {
        mEmailTextInputLayout = findViewById(R.id.textInputLayout_email);
        mEmailEditText = findViewById(R.id.et_email);
        mPasswordTextInputLayout = findViewById(R.id.textInputLayout_password);
        mPasswordEditText = findViewById(R.id.et_password);
        mEnterButton = findViewById(R.id.btn_enter);
        progressBar = findViewById(R.id.progressBar);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
    }

    private void initSnackBar() {
        mSnackbar = Snackbar.make(mEnterButton, "", Snackbar.LENGTH_LONG);
        mSnackbar.setDuration(SNACKBAR_DURATION);
        View snackbarView = mSnackbar.getView();
        snackbarView.setBackgroundColor(Color.WHITE);
    }

    private void initListeners() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.move_left_activity_out, R.anim.move_rigth_activity_in);
            }
        });

        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSignInPresenter.onTextChangedEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSignInPresenter.onTextChangedPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mPasswordEditText.getRight() - mPasswordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        mSignInPresenter.onTouchPasswordIconHelp();
                        return true;
                    }
                }
                return false;
            }
        });

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignInPresenter.signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_in_toolbar_create, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignInPresenter.detachView();
    }

    @Override
    public void showEmptyEmailError() {
        mEmailTextInputLayout.setError(getString(R.string.error_empty_email));
        mEmailEditText.requestFocus();
    }

    @Override
    public void showInvalidEmailError() {
        mEmailTextInputLayout.setError(getString(R.string.error_invalid_email));
        mEmailEditText.requestFocus();
    }

    @Override
    public void showEmptyPasswordError() {
        mPasswordTextInputLayout.setError(getString(R.string.error_empty_password));
        mPasswordEditText.requestFocus();
    }

    @Override
    public void showInvalidPasswordError() {
        mPasswordTextInputLayout.setError(getString(R.string.error_invalid_password));
        mPasswordEditText.requestFocus();
    }

    @Override
    public void showHelpPasswordInput() {
        Toast.makeText(SignInActivity.this, getString(R.string.help_password_input), Toast.LENGTH_LONG).show();
        mPasswordEditText.requestFocus();
    }

    @Override
    public void resetErrors() {
        mEmailTextInputLayout.setError(null);
        mPasswordTextInputLayout.setError(null);
    }

    @Override
    public void showNotConnection() {
        Toast.makeText(this,
                R.string.connection_not_found,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showWeather(WeatherCurrent weatherCurrent) {

        String temperature = getString(R.string.temperature_with_degree,
                TemperatureUtils.getFormatTemperature(weatherCurrent.getTemperature()));
        String weatherMessage = getString(R.string.message_weather, weatherCurrent.getCityName(), temperature);

        mSnackbar.setText(weatherMessage);
        mSnackbar.show();
    }

    @Override
    public void showWeatherError() {
        Toast.makeText(this, R.string.error_loading_weather, Toast.LENGTH_LONG).show();
    }
}
