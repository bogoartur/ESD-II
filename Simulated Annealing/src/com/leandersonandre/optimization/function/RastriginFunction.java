package com.leandersonandre.optimization.function;

//Rastrigin Function
//https://www.sfu.ca/~ssurjano/rastr.html

public class RastriginFunction implements Function {

    private final static double MIN_VALUE = -5.12;
    private final static double MAX_VALUE = 5.12;
    private final static int A = 10;

    // A formula é 10*n + sum(xi^2 - 10*cos(2*pi*xi))

    @Override
    public double evaluate(double[] x) {
        validateAndFixDomain(x);
        double sum = 0;
        int n = x.length;
        for (double xi : x) {
            sum += (xi * xi) - (A * Math.cos(2 * Math.PI * xi));
        }
        return (A * n) + sum;
    }

    @Override
    public void validateAndFixDomain(double[] x) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] < MIN_VALUE) {
                x[i] = MIN_VALUE;
            }
            if (x[i] > MAX_VALUE) {
                x[i] = MAX_VALUE;
            }
        }
    }

    @Override
    public void generateRandomSolution(double[] solution) {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * Math.random();
        }
    }
}
