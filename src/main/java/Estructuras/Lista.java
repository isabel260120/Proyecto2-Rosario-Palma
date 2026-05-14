package Estructuras;
//permite guardar varios elementos uno detrás de otro usando nodos.
public class Lista <T>{
    // Referencia al primer nodo de la lista.
    // Desde este nodo se puede recorrer toda la estructura.
    public NodoLista<T> primero;
    private int tamaño;

    public static class NodoLista<T>{
        T valor;
        NodoLista<T> siguiente;

        NodoLista(T valor){
            this.valor = valor;
            this.siguiente = null;
        }
        public T getValor(){
            return this.valor;
        }
        public NodoLista<T> getSiguiente(){
            return this.siguiente;
        }
    }


    public void agregar(T valor){
        NodoLista<T> nuevo = new NodoLista<>(valor);
        if(primero == null){
            primero=nuevo;
        }else{
            NodoLista<T> aux=primero;
            while(aux.siguiente!=null){
                aux=aux.siguiente;
            }
            aux.siguiente=nuevo;
        }
        tamaño++;
    }
    public int getTamaño() {
        return tamaño;
    }
    public NodoLista<T> getPrimero() {
        return primero;
    }
    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clase){
        T[] arreglo=(T[]) java.lang.reflect.Array.newInstance(clase,tamaño);
        NodoLista<T> aux=primero;
        int i=0;
        while(aux!=null){
            arreglo[i]=aux.getValor();
            aux=aux.getSiguiente();
            i++;
        }
        return arreglo;
    }
}

