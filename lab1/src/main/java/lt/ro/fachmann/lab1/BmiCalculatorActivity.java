package lt.ro.fachmann.lab1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Float.parseFloat;

public class BmiCalculatorActivity extends AppCompatActivity {
    private static final float BMI_INCORRECT = -1.0f;
    private static final String BMI_UNIT = "bmi_unit";
    private static final String BMI_MASS = "bmi_mass";
    private static final String BMI_HEIGHT = "bmi_height";

    private static final float UNDERWEIGHT_PEAK = 18.5f;
    private static final float NORM_PEAK = 25.0f;
    private static final float OVERWEIGHT_PEAK = 30.0f;

    @BindView(R.id.text_ask_mass)
    TextView textMass;

    @BindView(R.id.edit_mass)
    EditText editMass;

    @BindView(R.id.text_ask_height)
    TextView textHeight;

    @BindView(R.id.edit_height)
    EditText editHeight;

    @BindView(R.id.text_bmi)
    TextView textBMI;

    @BindView(R.id.button_clear)
    FloatingActionButton buttonClear;

    Menu menuUnit;
    MenuItem menuUseMetric;
    MenuItem menuUseImperial;
    MenuItem menuShare;
    MenuItem menuSave;

    private SharedPreferences sharedPref;
    private ShareActionProvider shareActionProvider;

    private CountBmi bmiEvaluator;
    private CountBmiUnit bmiUnit;
    private ActionBar actionBar;
    private Intent intent;
    private boolean inputGood;
    private boolean restore = false;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            countBmi();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private static float parseFloatWithDefault(String string, float defaultValue) {
        float result = defaultValue;
        try {
            result = parseFloat(string);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bmi_calculator);
        ButterKnife.bind(this);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        actionBar = getSupportActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuUnit = menu;
        initMenu();
        if (!restore) {
            load();
        }
        editMass.addTextChangedListener(textWatcher);
        editHeight.addTextChangedListener(textWatcher);
        changeUnit(bmiUnit);
        countBmi();
        return true;
    }

    private void initMenu() {
        menuUseMetric = menuUnit.findItem(R.id.menu_use_metric);
        menuUseImperial = menuUnit.findItem(R.id.menu_use_imperial);
        menuShare = menuUnit.findItem(R.id.menu_share);
        menuSave = menuUnit.findItem(R.id.menu_save);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuShare);

        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.setType("text/plain");
        setShareIntent(intent);

    }

    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                save();
                return true;
            case R.id.menu_about:
                about();
                return true;
            case R.id.menu_use_metric:
                changeUnit(CountBmiUnit.METRIC);
                countBmi();
                return true;
            case R.id.menu_use_imperial:
                changeUnit(CountBmiUnit.IMPERIAL);
                countBmi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clear(View view) {
        editHeight.setText("");
        editMass.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BMI_UNIT, bmiUnit.toString());
        outState.putString(BMI_MASS, editMass.getText().toString());
        outState.putString(BMI_HEIGHT, editHeight.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restore = true;
        String bmiUnitString = savedInstanceState.getString(BMI_UNIT, sharedPref.getString(BMI_UNIT, CountBmiFactory.getUnit().toString()));
        String bmiMass = savedInstanceState.getString(BMI_MASS, "");
        String bmiHeight = savedInstanceState.getString(BMI_HEIGHT, "");

        bmiUnit = CountBmiUnit.valueOf(bmiUnitString);
        editMass.setText(bmiMass);
        editHeight.setText(bmiHeight);
    }

    private void countBmi() {
        float mass = parseFloatWithDefault(editMass.getText().toString(), 0);
        float height = parseFloatWithDefault(editHeight.getText().toString(), 0);

        changeEditTextStyle(editMass, bmiEvaluator.isValidMass(mass));
        changeEditTextStyle(editHeight, bmiEvaluator.isValidHeight(height));
        boolean previousInputGood = inputGood;
        inputGood = bmiEvaluator.isValidMass(mass) && bmiEvaluator.isValidHeight(height);
        float bmi;
        if (inputGood) {
            bmi = bmiEvaluator.countBMI(mass, height);
            textBMI.setText(String.format(Locale.getDefault(), "%.2f", bmi));
            String content = getString(R.string.share_content, textBMI.getText().toString(), getBmiName(bmi));
            intent.putExtra(Intent.EXTRA_TEXT, content);
            menuShare.setVisible(true);
        } else {
            bmi = BMI_INCORRECT;
        }
        updateBmiColor(bmi);
        menuSave.setVisible(inputGood);
        buttonClear.setVisibility(inputGood ? View.VISIBLE : View.INVISIBLE);
        if (previousInputGood != inputGood) {
            animate(textBMI, inputGood, 400);
            animate(menuShare.getActionView(), inputGood, 400);
        }
    }

    private void animate(final View view, final boolean visible, int duration) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(!visible ? 1.0f : 0.0f);

        view.setTranslationX(visible ? 30.0f : 0.0f);

        view.animate()
                .alpha(visible ? 1.0f : 0.0f)
                .setInterpolator(new AccelerateInterpolator())
                .translationX(visible ? 0.0f : -30.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                        view.setTranslationX(-view.getTranslationX());
                    }
                });
    }

    public void hideKeyboard(View view) {
        final View currentFocus = getWindow().getCurrentFocus();
        if (currentFocus != null) {
            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void changeUnit(CountBmiUnit unit) {
        bmiUnit = unit;
        bmiEvaluator = CountBmiFactory.getInstance(unit);
        if (unit == CountBmiUnit.METRIC) {
            setLabelUnits(R.string.unit_kg, R.string.unit_cm);
            menuUseMetric.setChecked(true);
        } else if (unit == CountBmiUnit.IMPERIAL) {
            setLabelUnits(R.string.unit_lb, R.string.unit_ft);
            menuUseImperial.setChecked(true);
        }
    }

    private void setLabelUnits(int massId, int heightId) {
        textMass.setText(Html.fromHtml(getString(R.string.intro_label_ask_mass, getString(massId))));
        textHeight.setText(Html.fromHtml(getString(R.string.intro_label_ask_heigth, getString(heightId))));
    }

    private void changeEditTextStyle(EditText editText, boolean good) {
        if (good || editText.getText().length() == 0) {
            TextViewCompat.setTextAppearance(editText, R.style.GoodInput);
            editText.setError(null);
        } else {
            TextViewCompat.setTextAppearance(editText, R.style.WrongInput);
            editText.setError(getString(R.string.intro_wrong_value));
        }
    }

    private void updateBmiColor(float bmi) {
        int colorResource;
        if (bmi == BMI_INCORRECT) {
            colorResource = R.color.colorPrimaryDark;
        } else if (bmi < UNDERWEIGHT_PEAK) {
            colorResource = R.color.bmiUnderweight;
        } else if (bmi < NORM_PEAK) {
            colorResource = R.color.bmiNorm;
        } else if (bmi < OVERWEIGHT_PEAK) {
            colorResource = R.color.bmiOverweight;
        } else {
            colorResource = R.color.bmiObesity;
        }

        final int color = ContextCompat.getColor(getApplicationContext(), colorResource);
        textBMI.setTextColor(color);
        final ColorDrawable d = new ColorDrawable(color + 0x00111111);
        actionBar.setBackgroundDrawable(d);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(color);
        }
    }

    private String getBmiName(float bmi) {
        if (bmi < UNDERWEIGHT_PEAK) {
            return getString(R.string.bmi_underweight_word);
        } else if (bmi < NORM_PEAK) {
            return getString(R.string.bmi_norm_word);
        } else if (bmi < OVERWEIGHT_PEAK) {
            return getString(R.string.bmi_overweight_word);
        } else {
            return getString(R.string.bmi_obesity_word);
        }
    }

    private void load() {
        String bmiUnitString = sharedPref.getString(BMI_UNIT, CountBmiFactory.getUnit().toString());
        String bmiMass = sharedPref.getString(BMI_MASS, "");
        String bmiHeight = sharedPref.getString(BMI_HEIGHT, "");

        bmiUnit = CountBmiUnit.valueOf(bmiUnitString);
        editMass.setText(bmiMass);
        editHeight.setText(bmiHeight);
    }

    private void save() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(BMI_UNIT, bmiUnit.toString());
        editor.putString(BMI_MASS, editMass.getText().toString());
        editor.putString(BMI_HEIGHT, editHeight.getText().toString());
        editor.apply();
    }

    private void about() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
