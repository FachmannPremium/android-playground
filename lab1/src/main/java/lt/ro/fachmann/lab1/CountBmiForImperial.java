package lt.ro.fachmann.lab1;

public class CountBmiForImperial implements CountBmi {
    public static final float MIN_MASS = 22.05f;
    public static final float MAX_MASS = 551.16f;
    public static final float MIN_HEIGHT = 19.69f;
    public static final float MAX_HEIGHT = 98.43f;
    private static final float FACTOR = 703.0f;

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
        if (isValidMass(mass) || isValidHeight(height)) {
            return mass / (height * height) * FACTOR;
        } else {
            throw new IllegalArgumentException("Wrong heigth / mass");
        }
    }
}
