import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Main {

    static int nGlobal = 1000; 
    static int[] listaOriginalGlobal;

    public static int[] bubbleSort(int[] lista) {
        int N = lista.length;
        boolean trocou = true;
        while (trocou) {
            trocou = false;
            for (int i = 0; i < N - 1; i++) {
                if (lista[i] > lista[i + 1]) {
                    int temp = lista[i];
                    lista[i] = lista[i + 1];
                    lista[i + 1] = temp;
                    trocou = true;
                }
            }
        }
        return lista;
    }

    public static int[] selectionSort(int[] lista) {
        int N = lista.length;
        for (int i = 0; i < N; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < N; j++) {
                if (lista[j] < lista[indiceMenor]) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                int temp = lista[i];
                lista[i] = lista[indiceMenor];
                lista[indiceMenor] = temp;
            }
        }
        return lista;
    }

    public static int[] insertionSort(int[] lista) {
        int N = lista.length;
        for (int i = 1; i < N; i++) {
            int chave = lista[i];
            int j = i - 1;
            while (j >= 0 && chave < lista[j]) {
                lista[j + 1] = lista[j];
                j -= 1;
            }
            lista[j + 1] = chave;
        }
        return lista;
    }

    public static int[] mergeSort(int[] lista) {
        int N = lista.length;
        if (N <= 1) {
            return lista;
        }
        int meio = N / 2;
        int[] esquerda = Arrays.copyOfRange(lista, 0, meio);
        int[] direita = Arrays.copyOfRange(lista, meio, N);
        int[] esquerdaOrdenada = mergeSort(esquerda);
        int[] direitaOrdenada = mergeSort(direita);
        List<Integer> resultadoList = new ArrayList<>();
        int iEsq = 0, iDir = 0;
        while (iEsq < esquerdaOrdenada.length && iDir < direitaOrdenada.length) {
            if (esquerdaOrdenada[iEsq] <= direitaOrdenada[iDir]) {
                resultadoList.add(esquerdaOrdenada[iEsq]);
                iEsq++;
            } else {
                resultadoList.add(direitaOrdenada[iDir]);
                iDir++;
            }
        }
        while (iEsq < esquerdaOrdenada.length) {
            resultadoList.add(esquerdaOrdenada[iEsq]);
            iEsq++;
        }
        while (iDir < direitaOrdenada.length) {
            resultadoList.add(direitaOrdenada[iDir]);
            iDir++;
        }
        int[] resultado = new int[resultadoList.size()];
        for(int k=0; k < resultadoList.size(); k++) {
            resultado[k] = resultadoList.get(k);
        }
        return resultado;
    }

    public static void quickSortLomuto(int[] lista, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = partitionLomuto(lista, inicio, fim);
            quickSortLomuto(lista, inicio, indicePivo - 1);
            quickSortLomuto(lista, indicePivo + 1, fim);
        }
    }

    public static int partitionLomuto(int[] lista, int inicio, int fim) {
        int pivo = lista[fim];
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (lista[j] <= pivo) {
                i = i + 1;
                int temp = lista[i];
                lista[i] = lista[j];
                lista[j] = temp;
            }
        }
        int temp = lista[i + 1];
        lista[i + 1] = lista[fim];
        lista[fim] = temp;
        return i + 1;
    }
    public static int[] quickSort(int[] lista) {
        int N = lista.length;
        if (N > 0) {
             quickSortLomuto(lista, 0, N - 1);
        }
        return lista;
    }
    
    public static double calcularMedia(List<Double> dados) {
        if (dados == null || dados.isEmpty()) {
            return 0.0;
        }
        double soma = 0;
        for (double valor : dados) {
            soma += valor;
        }
        return soma / dados.size();
    }

    
    public static double calcularDesvioPadrao(List<Double> dados, double media) {
        if (dados == null) {
            return 0.0;
        }
        double somaQuadradosDiferencas = 0;
        for (double valor : dados) {
            somaQuadradosDiferencas += Math.pow(valor - media, 2);
        }
        return Math.sqrt(somaQuadradosDiferencas / (dados.size() - 1));
    }


    public static void main(String[] args) {
        Random random = new Random();
        listaOriginalGlobal = new int[nGlobal];
        for (int i = 0; i < nGlobal; i++) {
            listaOriginalGlobal[i] = random.nextInt(nGlobal * 10) + 1;
        }
        System.out.printf("Lista com %d números aleatórios gerada.%n", nGlobal);

        int contadorTestes = 30;
        System.out.printf("%nIniciando %d rodadas de Testes de Desempenho (N=%d)%n", contadorTestes, nGlobal);

        Map<String, List<Double>> todosOsTempos = new HashMap<>();
        String[] algoritmosParaTestar = {"BubbleSort", "SelectionSort", "InsertionSort", "MergeSort", "QuickSort"};
        for (String nomeAlgoritmo : algoritmosParaTestar) {
            todosOsTempos.put(nomeAlgoritmo, new ArrayList<>());
        }
        for (int i = 0; i < contadorTestes; i++) {
            System.out.printf("\n--- Rodada %d de %d ---%n", i + 1, contadorTestes);
            int[] listaTeste;
            long inicio, fim;
            double tempoSegundos;
            System.out.println("Testando BubbleSort...");
            listaTeste = Arrays.copyOf(listaOriginalGlobal, listaOriginalGlobal.length);
            inicio = System.nanoTime();
            bubbleSort(listaTeste);
            fim = System.nanoTime();
            tempoSegundos = (fim - inicio) / 1_000_000_000.0;
            todosOsTempos.get("BubbleSort").add(tempoSegundos);
            System.out.printf("BubbleSort: %.4f s%n", tempoSegundos);
            System.out.println("Testando SelectionSort...");
            listaTeste = Arrays.copyOf(listaOriginalGlobal, listaOriginalGlobal.length);
            inicio = System.nanoTime();
            selectionSort(listaTeste);
            fim = System.nanoTime();
            tempoSegundos = (fim - inicio) / 1_000_000_000.0;
            todosOsTempos.get("SelectionSort").add(tempoSegundos);
            System.out.printf("SelectionSort: %.4f s%n", tempoSegundos);
            System.out.println("Testando InsertionSort...");
            listaTeste = Arrays.copyOf(listaOriginalGlobal, listaOriginalGlobal.length);
            inicio = System.nanoTime();
            insertionSort(listaTeste);
            fim = System.nanoTime();
            tempoSegundos = (fim - inicio) / 1_000_000_000.0;
            todosOsTempos.get("InsertionSort").add(tempoSegundos);
            System.out.printf("InsertionSort: %.4f s%n", tempoSegundos);
            System.out.println("Testando MergeSort...");
            listaTeste = Arrays.copyOf(listaOriginalGlobal, listaOriginalGlobal.length);
            inicio = System.nanoTime();
            mergeSort(listaTeste); 
            fim = System.nanoTime();
            tempoSegundos = (fim - inicio) / 1_000_000_000.0;
            todosOsTempos.get("MergeSort").add(tempoSegundos);
            System.out.printf("MergeSort: %.4f s%n", tempoSegundos);
            System.out.println("Testando QuickSort...");
            listaTeste = Arrays.copyOf(listaOriginalGlobal, listaOriginalGlobal.length);
            inicio = System.nanoTime();
            quickSort(listaTeste);
            fim = System.nanoTime();
            tempoSegundos = (fim - inicio) / 1_000_000_000.0;
            todosOsTempos.get("QuickSort").add(tempoSegundos);
            System.out.printf("QuickSort: %.4f s%n", tempoSegundos);
        }
        System.out.println("\n--- Resultados Finais (Após " + contadorTestes + " rodadas) ---");
        System.out.println("Algoritmo      | Média (s) | Desvio Padrão (s)");
        System.out.println("--------------------------------------------------");
        for (String nomeAlgoritmo : algoritmosParaTestar) {
            List<Double> temposDoAlgoritmo = todosOsTempos.get(nomeAlgoritmo);
            double media = calcularMedia(temposDoAlgoritmo);
            double desvioPadrao = calcularDesvioPadrao(temposDoAlgoritmo, media);
            System.out.printf("%-15s | %9.4f | %17.4f%n", nomeAlgoritmo, media, desvioPadrao);
        }
    }
}

