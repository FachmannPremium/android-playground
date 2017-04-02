package lt.ro.fachmann.lab1;

/**
 * Created by stud on 20.03.2017.
 */

public class CountBmiForKgCm implements CountBmi {
    public static final float MIN_MASS = 10f;
    public static final float MAX_MASS = 250f;
    public static final float MIN_HEIGHT = 50f;
    public static final float MAX_HEIGHT = 250f;

    @Override
    public boolean isValidMass(float mass) {
        return MIN_MASS <= mass && mass <= MAX_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return MIN_HEIGHT <= height && height <= MAX_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) {
        height /= 100;
        if (isValidMass(mass) || isValidHeight(height)) {
            return mass / (height * height);
        } else {
            throw new IllegalArgumentException("Wrong heigth / mass");
        }
    }
}
