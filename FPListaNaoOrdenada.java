import java.util.ArrayList;
import java.util.List;

public class FPListaNaoOrdenada implements FilaDePrioridade{

    private List<Nodo> lista;

    public FPListaNaoOrdenada(){
        lista = new ArrayList<>();
    }

    //O(1)
    @Override
    public int tamanho() {
        return lista.size();
    }

    //O(1) : maioria dos casos
    // O(n) : pior caso, quando o arranjo não tem mais espaço
    @Override
    public void add(int elemento, int prioridade) {
        Nodo nodo = new Nodo(elemento,prioridade);
        // adiciona no final da lista arranjo.
        lista.add(nodo);
    }

    // O(n)
    @Override
    public int remover() {
        if(tamanho() < 1){ throw new RuntimeException("FP está vazia");}
        int maior = 0;
        // tamanho da lista
        for(int i =1; i < lista.size(); i++){
            if(lista.get(i).prioridade() > lista.get(maior).prioridade()){
                maior = i;
            }
        }
        Nodo nodo = lista.remove(maior);
        return nodo.elemento();
    }

    // Complexidade: O(n)
    @Override
    public int obter() {
        if(tamanho() < 1){ throw new RuntimeException("FP está vazia");}
        Nodo maior = lista.get(0);
        // tamanho da lista
        for(Nodo n: lista){
            if(n.prioridade() > maior.prioridade()){
                maior = n;
            }
        }
        return maior.elemento();
    }
}