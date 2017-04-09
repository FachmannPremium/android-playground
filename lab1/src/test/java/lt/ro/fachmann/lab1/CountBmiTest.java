package lt.ro.fachmann.lab1;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by stud on 20.03.2017.
 */

public class CountBmiTest {
    @Test
    public void massUnderZero_isValid() {
        // GIVEN
        float textMan = -1.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        boolean anchor = countBmi.isValidMass(textMan);
        assertFalse(anchor);
    }

    @Test
    public void mass_isValid() {
        // GIVEN
        float under = 5f;
        float inRange = 75f;
        float over = 3000f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        assertFalse(countBmi.isValidMass(under));
        assertTrue(countBmi.isValidMass(inRange));
        assertFalse(countBmi.isValidMass(over));
    }

    @Test
    public void heightUnderZero_isValid() {
        // GIVEN
        float textMan = -1.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        boolean anchor = countBmi.isValidHeight(textMan);
        assertFalse(anchor);
    }

    @Test
    public void height_isValid() {
        // GIVEN
        float under = 10.0f;
        float inRange = 170.0f;
        float over = 500.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        assertFalse(countBmi.isValidHeight(under));
        assertTrue(countBmi.isValidHeight(inRange));
        assertFalse(countBmi.isValidHeight(over));
    }

    @Test
    public void bmiCountMetric_isValid() {
        // GIVEN
        float mass = 78.0f;
        float height = 178.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        float bmi = countBmi.countBMI(mass, height);
        float actual = 78.0f / 1.78f / 1.78f;
        assertEquals(bmi, actual, 0.001f);
    }

    @Test
    public void bmiCountImperial_isValid() {
        // GIVEN
        float mass = 170.0f;
        float height = 70.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForImperial();
        // THEN
        float bmi = countBmi.countBMI(mass, height);
        float actual = 703.0f * 170.0f / 70.0f / 70.0f;
        assertEquals(bmi, actual, 0.00001f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bmiCountMetric_testException() {
        // GIVEN
        float mass = 0.0f;
        float height = 0.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForMetric();
        // THEN
        float bmi = countBmi.countBMI(mass, height);
    }


    @Test(expected = IllegalArgumentException.class)
    public void bmiCountImperial_testException() {
        // GIVEN
        float mass = 0.0f;
        float height = 0.0f;
        // WHEN
        CountBmi countBmi = new CountBmiForImperial();
        // THEN
        float bmi = countBmi.countBMI(mass, height);
    }

}
