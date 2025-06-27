package com.leandersonandre.optimization;

import com.leandersonandre.optimization.function.Function;
import com.leandersonandre.optimization.function.FunctionFactory;
import com.leandersonandre.optimization.sa.SimulatedAnnealing;
import com.leandersonandre.optimization.sa.operator.GaussianOperator;

public class Main {
    public static void main(String[] args) { // agora o main roda ja as duas funcoes de uma vez

        System.out.println("--- Executando SA para a Função Sphere ---");
        Function sphereFunction = FunctionFactory.getInstance().getFunction("SPHERE");
        SimulatedAnnealing saSphere = new SimulatedAnnealing(
                sphereFunction,
                new GaussianOperator(0.1), // diminuimos p ruido
                0.99, // aumentamos esse
                100, // dimimuimos esse porque a esfera eh mais facil
                100, // cortamos de 100k pra 100
                10 // de 2 pra 10
        );
        saSphere.execute("sphere");

        System.out.println("\n--------------------------------------------\n");

        System.out.println("--- Executando SA para a Função Rastrigin ---");
        Function rastriginFunction = FunctionFactory.getInstance().getFunction("RASTRIGIN");
        SimulatedAnnealing saRastrigin = new SimulatedAnnealing(
                rastriginFunction,
                new GaussianOperator(0.5), // deixamos as variaveis no mesmo nivel que o professor deu
                0.97, // igual
                200, // igual
                10000, // diminuimos so esse porque essa eh mais demorada
                10 // de 2 pra 10
        );
        saRastrigin.execute("rastrigin");
    }
}
