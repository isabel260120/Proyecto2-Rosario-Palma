package Estructuras;

import java.util.Comparator;

public class ColaPrioridad<T> {
    private T[] heap;
    private int tamaño;
    private int capacidad;
    private Comparator<T> comparador;

    @SuppressWarnings("unchecked")
    public ColaPrioridad(int capacidad, Comparator<T> comparador) {
        this.capacidad = capacidad;
        this.tamaño=0;
        this. comparador = comparador;
        this.heap = (T[]) new Object[capacidad +1];
    }
    public void insertar(T elemento) {
        if(tamaño==capacidad){
            return;
        }
        tamaño++;
        heap[tamaño] = elemento;
        subir(tamaño);
    }
    public T eliminarMin(){
        if(tamaño==0){
            return null;
        }
        T min = heap[1];
        heap[1] = heap[tamaño];
        heap[tamaño] = null;
        tamaño--;
        bajar(1);
        return min;
    }
    private void subir(int k){
        while(k>1&& comparador.compare(heap[k],heap[k/2]) < 0){
            intercambiar(k,k/2);
            k=k/2;
        }
    }
    private void bajar(int k){
        while(2*k<= tamaño){
            int j=2*k;
            if(j<tamaño&&comparador.compare(heap[j],heap[j+1])>0){
                j++;
            }
            if(comparador.compare(heap[k],heap[j])<=0){
                break;
            }
            intercambiar(k,j);
            k=j;
        }
    }
    private void intercambiar(int i, int j){
        T temp=heap[i];
        heap[i]=heap[j];
        heap[j]=temp;
    }
    public int getTamaño(){
        return tamaño;
    }

}
