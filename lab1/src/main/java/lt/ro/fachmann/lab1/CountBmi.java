package lt.ro.fachmann.lab1;

/**
 * Created by stud on 20.03.2017.
 */

public interface CountBmi {
    boolean isValidMass(float mass);

    boolean isValidHeight(float height);

    float countBMI(float mass, float height);
}
