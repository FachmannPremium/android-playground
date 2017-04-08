package lt.ro.fachmann.lab1;

public interface CountBmi {
    boolean isValidMass(float mass);

    boolean isValidHeight(float height);

    float countBMI(float mass, float height);
}
