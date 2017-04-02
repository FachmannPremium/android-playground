package lt.ro.fachmann.lab1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Float.parseFloat;

public class BmiCalculator extends AppCompatActivity {
    @BindView(R.id.text_bmi)
    TextView textBMI;

    @BindView(R.id.edit_height)
    EditText editHeight;

    @BindView(R.id.edit_mass)
    EditText editMass;

    Menu menuUnit;
    MenuItem menuUseMetric;
    MenuItem menuUseImperial;

    private Toast toast;

    private CountBmi countBmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menuUnit = menu;
        menuUnit.setGroupCheckable(1, true, true);
        menuUseMetric = menu.findItem(R.id.menu_use_metric);
        menuUseImperial = menu.findItem(R.id.menu_use_imperial);
        initUnit(CountBmiFactory.getUnit());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
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

    public void countBMI(View view) {
        try {
            float mass = parseFloat(editMass.getText().toString());
            float height = parseFloat(editHeight.getText().toString());
            if (countBmi.isValidMass(mass) && countBmi.isValidHeight(height)) {
                float bmi = countBmi.countBMI(mass, height);
                textBMI.setText(String.format(Locale.getDefault(), "%.3f", bmi));
            } else {
                showToast(getString(R.string.intro_check_value));
            }
        } catch (NumberFormatException e) {
            showToast(getString(R.string.intro_check_numers));
        }
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        try {
            toast.setText(message);
        } catch (Exception e) {
            toast = Toast.makeText(context, message, duration);
        }
        if (!toast.getView().isShown()) {
            toast.show();
        }
    }

    private void initUnit(CountBmiUnit unit) {
        countBmi = CountBmiFactory.getInstance(unit);
        if (unit == CountBmiUnit.METRIC) {
            menuUseMetric.setChecked(true);
        } else if (unit == CountBmiUnit.IMPERIAL) {
            menuUseImperial.setChecked(true);
        }
    }

}
