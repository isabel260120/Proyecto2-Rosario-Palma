package Estructuras;

import java.util.Comparator;

public class ColaPrioridad<T> {
    // Arreglo que almacena los elementos del heap.
    private T[] heap;
    private int tamaño;
    // Cantidad máxima de elementos que puede almacenar el heap.
    private int capacidad;
    private Comparator<T> comparador;


    @SuppressWarnings("unchecked")
    public ColaPrioridad(int capacidad, Comparator<T> comparador) {
        this.capacidad = capacidad;
        this.tamaño=0;
        this. comparador = comparador;
        // Se crea el arreglo con una posición extra porque el heap inicia en índice 1.
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
    // Elimina y retorna el elemento con mayor prioridad.
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
    // Reacomoda un elemento hacia arriba mientras tenga mayor prioridad que su padre.
    private void subir(int k){
        while(k>1&& comparador.compare(heap[k],heap[k/2]) < 0){
            intercambiar(k,k/2);
            k=k/2;
        }
    }
    // Reacomoda un elemento hacia abajo mientras alguno de sus hijos tenga mayor prioridad.
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
    // Intercambia dos posiciones dentro del arreglo del heap.
    private void intercambiar(int i, int j){
        T temp=heap[i];
        heap[i]=heap[j];
        heap[j]=temp;
    }
    public int getTamaño(){
        return tamaño;
    }

}
