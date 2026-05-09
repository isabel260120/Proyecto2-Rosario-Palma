package Estructuras;

public class Lista <T>{
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
}
