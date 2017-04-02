package lt.ro.fachmann.lab1;

/**
 * Created by bartl on 28.03.2017.
 */

public class CountBmiForImperial implements CountBmi {
    public static final float MIN_MASS = 22.05f;
    public static final float MAX_MASS = 551.16f;
    public static final float MIN_HEIGHT = 1.64f;
    public static final float MAX_HEIGHT = 8.20f;

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
