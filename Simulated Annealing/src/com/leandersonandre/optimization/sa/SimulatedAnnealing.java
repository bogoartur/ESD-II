package com.leandersonandre.optimization.sa;

import com.leandersonandre.optimization.function.Function;
import com.leandersonandre.optimization.log.Log;
import com.leandersonandre.optimization.sa.operator.NeighborOperator;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulatedAnnealing {

    private final Function function;
    private final double alpha;
    private final double maxIterations;
    private final double tempInitial;
    private final int solutionSize;
    private double[] bestSolution;
    private double bestFitness;
    private double[] currentSolution;
    private double currentFitness;
    private double[] neighbor;
    private double neighborFitness;
    private int iterationsAtTemp;
    private final NeighborOperator neighborOperator;
    private StringBuilder builder;
    private List<Integer> xData;
    private List<Double> bestFitnesses;
    private List<Double> currentFitnesses;
    private List<Double> temperatures;
    private int countIterations;

    public SimulatedAnnealing(Function function, NeighborOperator neighborOperator, double alpha, double maxIterations,
            double tempInitial, int solutionSize) {
        this.function = function;
        this.alpha = alpha;
        this.maxIterations = maxIterations;
        this.tempInitial = tempInitial;
        this.solutionSize = solutionSize;
        this.neighborOperator = neighborOperator;
    }

    /// aaaaaa
    private void generateFirstSolution() {
        Log.info("Generating first Solution...");
        bestSolution = new double[solutionSize];
        function.generateRandomSolution(bestSolution);
        bestFitness = function.evaluate(bestSolution);
        Log.info("First Solution: " + Arrays.toString(bestSolution));
        Log.info("First fitness: " + bestFitness);
    }

    private void copySolution(double[] source, double[] target) {
        System.arraycopy(source, 0, target, 0, target.length);
    }

    private void copyNeighBorToCurrentSolution() {
        copySolution(neighbor, currentSolution);
        currentFitness = neighborFitness;
    }

    private void copyCurrentToBestSolution() {
        copySolution(currentSolution, bestSolution);
        bestFitness = currentFitness;
    }

    public void execute(String filePrefix) {
        builder = new StringBuilder();
        iterationsAtTemp = 0;
        double temperature = tempInitial;
        neighbor = new double[solutionSize];
        currentSolution = new double[solutionSize];
        bestFitnesses = new ArrayList<>();
        currentFitnesses = new ArrayList<>();
        xData = new ArrayList<>();
        temperatures = new ArrayList<>();
        countIterations = 0;

        generateFirstSolution();
        copySolution(bestSolution, currentSolution);
        currentFitness = bestFitness;

        while (temperature > 0.0001) {
            Log.info("Temperature: " + temperature);
            while (iterationsAtTemp < maxIterations) {
                Log.info("Iteration: " + iterationsAtTemp);
                iterationsAtTemp++;
                countIterations++;
                copySolution(currentSolution, neighbor);
                neighborOperator.generateNeighbor(neighbor);
                // Valida e corrige o vizinho para garantir que está no domínio da função
                function.validateAndFixDomain(neighbor);
                neighborFitness = function.evaluate(neighbor);
                double delta = neighborFitness - currentFitness;

                if (delta < 0.0) {
                    copyNeighBorToCurrentSolution();
                    if (currentFitness < bestFitness) {
                        copyCurrentToBestSolution();
                    }
                } else {
                    double r = Math.random();
                    double e = Math.exp(-delta / temperature);
                    if (r < e) {
                        copyNeighBorToCurrentSolution();
                    }
                }
                Log.info("Best: " + bestFitness + " current: " + currentFitness);
                builder.append(countIterations)
                        .append(", ")
                        .append(temperature)
                        .append(", ")
                        .append(bestFitness)
                        .append(", ")
                        .append(currentFitness)
                        .append("\n");

                bestFitnesses.add(bestFitness);
                currentFitnesses.add(currentFitness);
                xData.add(countIterations);
                temperatures.add(temperature);
            }
            temperature *= alpha;
            iterationsAtTemp = 0;
        }
        writeReport(filePrefix);
        generateGraphsFitness(filePrefix);
        generateGraphsTemperature(filePrefix);
        System.out.println("Best Solution: " + Arrays.toString(bestSolution));
        System.out.println("Best fitness: " + bestFitness);
        System.out.println("Total of Iterations: " + countIterations);
    }

    private void writeReport(String filePrefix) {
        try {
            String fileName = "report_" + filePrefix + ".csv";
            Files.write(Paths.get(fileName), builder.toString().getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateGraphsTemperature(String filePrefix) {
        String chartTitle = "Temperatura vs Iteração (" + filePrefix + ")";
        XYChart chart = new XYChartBuilder().width(1200).height(800).title(chartTitle).xAxisTitle("Iteração")
                .yAxisTitle("Temperatura").build();
        chart.addSeries("Temperatura", xData, temperatures);
        chart.getStyler().setLegendVisible(false);

        try {
            String fileName = "grafico_temperatura_" + filePrefix;
            BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateGraphsFitness(String filePrefix) {
        String chartTitle = "Convergência do Fitness vs Iteração (" + filePrefix + ")";
        XYChart chart = new XYChartBuilder().width(1200).height(800).title(chartTitle).xAxisTitle("Iteração")
                .yAxisTitle("Fitness").build();
        chart.addSeries("Melhor Fitness", xData, bestFitnesses);
        chart.addSeries("Fitness Atual", xData, currentFitnesses);

        try {
            String fileName = "grafico_convergencia_" + filePrefix;
            BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
