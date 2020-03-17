/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.jmep;

import java.math.BigDecimal;

import static com.googlecode.jmep.BinaryOperatorType.*;
import static com.googlecode.jmep.UnaryOperatorType.MIN;
import static com.googlecode.jmep.UnaryOperatorType.PLS;

/**
 * FinancialEnvironment assume that the operation on numbers will be only accomplished
 * through Long and BigDecimal. Double is Prohibited.
 *
 * @author jd3714
 */
public class FinancialEnvironment extends Environment {
    protected FinancialEnvironment() {
        super(OperationalMode.FINANCIAL);
        BasicEnvironment.implementDefaultLong(this);
        BasicEnvironment.implementDefaultString(this);

        // Register special Long-Long cases (because they can return Double)
        register(DIV, Long.class, Long.class, (Long t, Long u) -> {
            if (u != 0L && t % u == 0L) return t / u;
            return BigDecimal.valueOf(t).divide(BigDecimal.valueOf(u));
        });
        register(POW, Long.class, Long.class, (t, u) -> {
            if (u == 0) return 1L;
            if (u >= 0 && u < 5) {
                long returnValue = 1;
                for (int i = 0; i < u; i++) {
                    returnValue *= t;
                }
                return returnValue;
            }
            return BigDecimal.valueOf(t).pow(u.intValue());
        });
        register(POW, BigDecimal.class, Long.class, (t, u) -> t.pow(u.intValue()));

        // Register Double cases
        // POW for xxx-Double not allowed.
        register(MUL, BigDecimal.class, BigDecimal.class, BigDecimal::multiply);
        register(DIV, BigDecimal.class, BigDecimal.class, BigDecimal::divide);
        register(ADD, BigDecimal.class, BigDecimal.class, BigDecimal::add);
        register(SUB, BigDecimal.class, BigDecimal.class, BigDecimal::subtract);
        register(LT, BigDecimal.class, BigDecimal.class, (t, u) -> (t.compareTo(u) < 0 ? 1L : 0L));
        register(GT, BigDecimal.class, BigDecimal.class, (t, u) -> (t.compareTo(u) > 0 ? 1L : 0L));
        register(LE, BigDecimal.class, BigDecimal.class, (t, u) -> (t.compareTo(u) <= 0 ? 1L : 0L));
        register(GE, BigDecimal.class, BigDecimal.class, (t, u) -> (t.compareTo(u) >= 0 ? 1L : 0L));
        register(NE, BigDecimal.class, BigDecimal.class, (t, u) -> ((t.equals(u)) ? 0L : 1L));
        register(EQ, BigDecimal.class, BigDecimal.class, (t, u) -> ((t.equals(u)) ? 1L : 0L));

        // Register Unary Operators on Double
        register(PLS, BigDecimal.class, BigDecimal::plus);
        register(MIN, BigDecimal.class, BigDecimal::negate);

        // Register Upgrade Conversions
        register(Long.class, BigDecimal.class, BigDecimal::new);
        // Do not allow Long-to-Double Conversions
    }
}
