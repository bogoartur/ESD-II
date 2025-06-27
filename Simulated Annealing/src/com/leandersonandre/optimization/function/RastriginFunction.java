package com.leandersonandre.optimization.function;

/**
 * Rastrigin Function
 * É uma função complexa e multimodal, com muitos mínimos locais. O ótimo global
 * é 0 na origem (0, ..., 0).
 * É um desafio para algoritmos de otimização por causa de sua paisagem de busca
 * acidentada.
 * https://www.sfu.ca/~ssurjano/rastr.html
 */
public class RastriginFunction implements Function {

    private final static double MIN_VALUE = -5.12;
    private final static double MAX_VALUE = 5.12;
    private final static int A = 10;

    /**
     * Avalia a função Rastrigin para um dado vetor de entrada x.
     * A fórmula é 10*n + sum(xi^2 - 10*cos(2*pi*xi))
     * 
     * @param x O vetor de entrada.
     * @return O valor da função.
     */
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

    /**
     * Garante que a solução permaneça dentro do domínio definido [-5.12, 5.12].
     * Se um valor está fora do domínio, ele é ajustado para o limite mais próximo.
     * 
     * @param x O vetor da solução a ser validado.
     */
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

    /**
     * Gera uma solução aleatória dentro do domínio da função.
     * 
     * @param solution O array que conterá a solução gerada.
     */
    @Override
    public void generateRandomSolution(double[] solution) {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = MIN_VALUE + (MAX_VALUE - MIN_VALUE) * Math.random();
        }
    }
}
