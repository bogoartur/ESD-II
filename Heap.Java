public class Heap implements FilaDePrioridade{

    private Nodo heap[];
    private int tamanho;

    public Heap(){
        heap = new Nodo[100];
    }


    @Override
    public int tamanho() {
        return tamanho;
    }

    @Override
    public void add(int elemento, int prioridade) {
        if(tamanho == heap.length) throw new RuntimeException("Heap está cheio");
        heap[tamanho] = new Nodo(elemento,prioridade);
        tamanho++;
        // corrigir debaixo pra cima.
    }

    @Override
    public int remover() {
        if(tamanho < 1) throw new RuntimeException("Heap está vazio");
        Nodo removido = heap[0];
        heap[0] = heap[tamanho-1];
        heap[tamanho-1] = null;
        tamanho--;
        // corrigir de cima para baixo
        return removido.elemento();
    }

    @Override
    public int obter() {
        if(tamanho < 1) throw new RuntimeException("Heap está vazio");
        return heap[0].elemento();
    }
}
