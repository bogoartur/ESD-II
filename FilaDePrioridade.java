public interface FilaDePrioridade {

    int tamanho();

    void add(int elemento, int prioridade);

    int remover();

    int obter();

}