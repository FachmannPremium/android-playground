package lt.ro.fachmann.lab1;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Float.parseFloat;

public class BmiCalculatorActivity extends AppCompatActivity {
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

    Menu menuUnit;
    MenuItem menuUseMetric;
    MenuItem menuUseImperial;

    private Toast toast;

    private CountBmi countBmi;
    private CountBmiUnit countBmiUnit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        ButterKnife.bind(this);
        editMass.addTextChangedListener(textWatcher);
        editHeight.addTextChangedListener(textWatcher);
        initUnit(CountBmiFactory.getUnit());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        menuUnit = menu;
        menuUseMetric = menu.findItem(R.id.menu_use_metric);
        menuUseImperial = menu.findItem(R.id.menu_use_imperial);
        initUnit(countBmiUnit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                return true;
            case R.id.menu_use_metric:
                initUnit(CountBmiUnit.METRIC);
                return true;
            case R.id.menu_use_imperial:
                initUnit(CountBmiUnit.IMPERIAL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void countBmi() {
        float mass = parseFloatDefault(editMass.getText().toString(), 0);
        float height = parseFloatDefault(editHeight.getText().toString(), 0);

        changeEditTextStyle(editMass, countBmi.isValidMass(mass));
        changeEditTextStyle(editHeight, countBmi.isValidHeight(height));

        if (countBmi.isValidMass(mass) && countBmi.isValidHeight(height)) {
            float bmi = countBmi.countBMI(mass, height);
            textBMI.setText(String.format(Locale.getDefault(), "%.3f", bmi));
            updateBmiColor(bmi);
        }

    }

    private void initUnit(CountBmiUnit unit) {
        countBmiUnit = unit;
        countBmi = CountBmiFactory.getInstance(unit);
        if (unit == CountBmiUnit.METRIC) {
            if (menuUseMetric != null) menuUseMetric.setChecked(true);
            setLabelUnits(R.string.unit_kg, R.string.unit_cm);
        } else if (unit == CountBmiUnit.IMPERIAL) {
            if (menuUseImperial != null) menuUseImperial.setChecked(true);
            setLabelUnits(R.string.unit_lb, R.string.unit_ft);
        }
        countBmi();
    }

    private void setLabelUnits(int massId, int heightId) {
        textMass.setText(Html.fromHtml(getString(R.string.label_ask_mass, getString(massId))));
        textHeight.setText(Html.fromHtml(getString(R.string.label_ask_heigth, getString(heightId))));
    }

    private void changeEditTextStyle(EditText editText, boolean good) {
        if (good || editText.getText().length() == 0) {
            TextViewCompat.setTextAppearance(editText, R.style.GoodInput);
        } else {
            TextViewCompat.setTextAppearance(editText, R.style.WrongInput);
        }
    }

    private void updateBmiColor(float bmi) {
        int color;
        if (bmi < 18.5f) {
            color = ContextCompat.getColor(getApplicationContext(), R.color.bmiUnderweight);
        } else if (bmi < 25f) {
            color = ContextCompat.getColor(getApplicationContext(), R.color.bmiNorm);
        } else if (bmi < 30f) {
            color = ContextCompat.getColor(getApplicationContext(), R.color.bmiOverweight);
        } else {
            color = ContextCompat.getColor(getApplicationContext(), R.color.bmiObesity);
        }
        textBMI.setTextColor(color);
    }

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

    private void showToast(String message) {
        int duration = Toast.LENGTH_SHORT;
        try {
            toast.setText(message);
        } catch (Exception e) {
            toast = Toast.makeText(getApplicationContext(), message, duration);
        }
        if (!toast.getView().isShown()) {
            toast.show();
        }
    }

    private static float parseFloatDefault(String string, float defaultValue) {
        float result = defaultValue;
        try {
            result = parseFloat(string);
        } catch (NumberFormatException ignored) {
        }
        return result;
    }

}
