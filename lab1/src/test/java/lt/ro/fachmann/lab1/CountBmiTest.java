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
        CountBmi countBmi = new CountBmiForKgCm();
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
        CountBmi countBmi = new CountBmiForKgCm();
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
        CountBmi countBmi = new CountBmiForKgCm();
        // THEN
        boolean anchor = countBmi.isValidHeight(textMan);
        assertFalse(anchor);
    }

    @Test
    public void height_isValid() {
        // GIVEN
        float under = 0.1f;
        float inRange = 1.7f;
        float over = 5f;
        // WHEN
        CountBmi countBmi = new CountBmiForKgCm();
        // THEN
        assertFalse(countBmi.isValidHeight(under));
        assertTrue(countBmi.isValidHeight(inRange));
        assertFalse(countBmi.isValidHeight(over));
    }

    @Test
    public void bmiCount_isValid() {
        // GIVEN
        float mass = 78.0f;
        float height = 1.78f;
        // WHEN
        CountBmi countBmi = new CountBmiForKgCm();
        // THEN
        float bmi = countBmi.countBMI(mass, height);
        float actual = 78.0f/1.78f/1.78f;
        assertEquals(bmi, actual, 0.001f);
    }

}
