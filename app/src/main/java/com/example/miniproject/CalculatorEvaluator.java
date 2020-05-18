package com.example.miniproject;

import java.util.Iterator;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;

public class CalculatorEvaluator extends DoubleEvaluator{
    private static final Function SQRT = new Function("sqrt", 1);


    private static final Parameters PARAMS;

    static {
        PARAMS = DoubleEvaluator.getDefaultParameters();
        PARAMS.add(SQRT);

    }

    public CalculatorEvaluator() {
        super(PARAMS);
    }

    @Override
    public Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
        if (function == SQRT) {
            return Math.sqrt(arguments.next());
        }

        else {
            return super.evaluate(function, arguments, evaluationContext);
        }
    }
}
