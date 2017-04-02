package lt.ro.fachmann.lab1;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by bartl on 02.04.2017.
 */

public final class CountBmiFactory {
    private CountBmiFactory() {
    }

    private static Map<CountBmiUnit, CountBmi> bmiMap;

    static {
        bmiMap = new HashMap<>(2);
        bmiMap.put(CountBmiUnit.METRIC, new CountBmiForKgCm());
        bmiMap.put(CountBmiUnit.IMPERIAL, new CountBmiForLbFt());
    }

    public static CountBmi getInstance() {
        return getInstance(getUnit());
    }

    public static CountBmi getInstance(CountBmiUnit unit) {
        return bmiMap.get(unit);
    }

    public static CountBmiUnit getUnit() {
        return getUnit(Locale.getDefault());
    }

    public static CountBmiUnit getUnit(Locale locale) {
        switch (locale.getCountry()) {
            case "US":
            case "LR":
            case "MM":
                return CountBmiUnit.IMPERIAL;
            default:
                return CountBmiUnit.METRIC;
        }
    }
}
