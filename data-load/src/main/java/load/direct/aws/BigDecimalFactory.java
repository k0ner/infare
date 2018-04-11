package load.direct.aws;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalFactory {

    private static final MathContext mathContext = new MathContext(120, RoundingMode.CEILING);

    public static BigDecimal newInstance(String unscaledVal) {
        return new BigDecimal(unscaledVal, mathContext);
    }
}
