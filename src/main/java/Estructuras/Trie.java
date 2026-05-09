package Estructuras;

//arbol de prefijos (Trie)
public class Trie<T> {
    //Raiz como punto de partida del arbol, no contiene caracteres
    private NodeTrie<T> raiz;

    public Trie()
    {
        //incializacion de la raiz como nodo vacio
        this.raiz= new NodeTrie<>();
    }

    //metodos de insercion y busqueda
    public void insertar(String palabra, T objeto){
        NodeTrie<T> actual= raiz; //objeto
        for(int i=0;i<palabra.length();i++){ //recorrido palabra
            char c = palabra.charAt(i); //Para cada letra, obtenemos su valor numérico (índice en el arreglo de 256).
            if(actual.getHijos()[c] ==null){ //Revisa si el nodo actual ya tiene un hijo para esa letra
                actual.getHijos()[c]= new NodeTrie<>(); //si no existe se crea un nuevo nodo
            }
            actual=actual.getHijos()[c];
        }
        // Al salir del ciclo for, se encuentra en el nodo de la ULTIMA letra.
        // Marca que aqui termina una palabra valida.
        actual.setEsFinDePalabra(true);
        actual.setDato(objeto);
    }

    public T buscar(String palabra){
        NodeTrie<T> actual= raiz;
        for(int i=0;i<palabra.length();i++){
            char c = palabra.charAt(i);
            //Se mueve al hijo correspondiente a la letra actual
            actual= actual.getHijos()[c];
            //Si es null el camino no existe y la palabra tampoco
            if(actual==null){
                return null;
            }
        }
        // Retorna el dato solo si es el final de una palabra registrada
        return actual.esFinDePalabra()?actual.getDato():null;
    }
    public Lista<T> buscarComodin(String patron){
        Lista<T> resultados= new Lista<>();
        buscarComodinRecursivo(raiz,patron,0,resultados);
        return resultados;
    }
    private void buscarComodinRecursivo(NodeTrie<T> nodo, String patron, int indice, Lista<T> resultados){
        if(nodo==null){
            return;
        }
        if(indice==patron.length()){
            if(nodo.esFinDePalabra()){
                resultados.agregar(nodo.getDato());
            }
            return;
        }
        char c = patron.charAt(indice);
        if(c=='?'|| c=='*'){
            for(int i=0;i<256;i++){
                if(nodo.getHijos()[i] !=null){
                    buscarComodinRecursivo(nodo.getHijos()[i],patron,indice+1,resultados);
                }
            }
        }else{
            if(c<256&&nodo.getHijos()[c]!=null){
                buscarComodinRecursivo(nodo.getHijos()[c],patron,indice+1,resultados);
            }
        }
    }
    public Lista<T> buscarPorPrefijo(String prefijo){
        Lista<T> resultados= new Lista<>();
        NodeTrie<T> actual= raiz;
        for(int i=0;i<prefijo.length();i++){
            char c = prefijo.charAt(i);
            if(c>=256||actual.getHijos()[c]==null){
                return resultados;
            }
            actual= actual.getHijos()[c];
        }
        recorrerAuxiliar(actual,resultados);
        return resultados;
    }
    public Lista<T>obtenerTodas() {
        Lista<T> listaParaLlenar=new Lista<>();
        this.recorrerAuxiliar(raiz,listaParaLlenar);
        return listaParaLlenar;
    }
    private void recorrerAuxiliar(NodeTrie<T> nodo, Lista<T> lista) {
        if (nodo == null) {
            return;
        }
        if (nodo.esFinDePalabra()) {
            lista.agregar(nodo.getDato());
        }
        for (int i = 0; i < 256; i++) {
            if (nodo.getHijos()[i] != null) {
                recorrerAuxiliar(nodo.getHijos()[i], lista);
            }
        }
    }
}
