package Estructuras;

public class NodeTrie<T> {
    private NodeTrie<T>[] hijos;
    private boolean esFinDePalabra;
    private T dato; //guarda el objeto Palabra

    @SuppressWarnings("unchecked")
    public NodeTrie() {
        this.hijos = new NodeTrie[256]; //tamaño arreglo
        this.esFinDePalabra = false;
        this.dato = null;
    }

    public NodeTrie<T>[] getHijos() {
        return hijos;
    }
    public void setHijos(NodeTrie<T>[] hijos) {
        this.hijos = hijos;
    }

    public boolean esFinDePalabra() {
        return esFinDePalabra;
    }
    public void setEsFinDePalabra(boolean esFinDePalabra) {
        this.esFinDePalabra = esFinDePalabra;
    }

    public T getDato() {
        return dato;
    }
    public void setDato(T dato) {
        this.dato = dato;
    }

}
