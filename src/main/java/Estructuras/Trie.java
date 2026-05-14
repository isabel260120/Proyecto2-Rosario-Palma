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
            char c = palabra.charAt(i); //Para cada letra, se obtiene su valor numérico (índice en el arreglo de 256).
            if(actual.getHijos()[c] ==null){ //Revisa si el nodo actual ya tiene un hijo para esa letra
                actual.getHijos()[c]= new NodeTrie<>(); //si no existe se crea un nuevo nodo
            }
            actual=actual.getHijos()[c];
        }
        // Al salir del ciclo for, se encuentra en el nodo de la ultima letra.
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
    // Busca palabras usando un patrón con comodines.
    // '?' representa exactamente un carácter.
    // '*' representa cero, uno o varios caracteres.
    public Lista<T> buscarComodin(String patron){
        Lista<T> resultados= new Lista<>();
        if(patron==null){
            return resultados;
        }
        // Inicia la búsqueda recursiva desde la raíz.
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
        if (c == '?') {
            // ? representa exactamente un carácter
            for (int i = 0; i < 256; i++) {
                if (nodo.getHijos()[i] != null) {
                    buscarComodinRecursivo(
                            nodo.getHijos()[i],
                            patron,
                            indice + 1,
                            resultados
                    );
                }
            }
        }else if (c == '*') {
            // Caso 1: * representa cero caracteres
            buscarComodinRecursivo(
                    nodo,
                    patron,
                    indice + 1,
                    resultados
            );

            // Caso 2: * representa uno o más caracteres
            for (int i = 0; i < 256; i++) {
                if (nodo.getHijos()[i] != null) {
                    buscarComodinRecursivo(
                            nodo.getHijos()[i],
                            patron,
                            indice,
                            resultados
                    );
                }
            }
        }else {
            if (c < 256 && nodo.getHijos()[c] != null) {
                buscarComodinRecursivo(
                        nodo.getHijos()[c],
                        patron,
                        indice + 1,
                        resultados
                );
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
    // Recorre recursivamente el Trie y agrega a la lista todos los datos
    // que estén en nodos marcados como final de palabra.
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
    public void eliminar(String palabra){
        eliminar(raiz,palabra,0);
    }
    private boolean eliminar(NodeTrie actual, String palabra, int indice){
        if (indice == palabra.length()) {
            if (actual.esFinDePalabra()) {
                actual.setEsFinDePalabra(false);
                actual.setDato(null);
            }
            return !actual.tieneHijos();

        }
        char ch = palabra.charAt(indice);
        int posicion =(int)ch;
        if (posicion < 0 || posicion >= 256 || actual.getHijos()[posicion] == null) {
            return false; // La palabra no existe
        }
        NodeTrie<T> nodoHijo = actual.getHijos()[posicion];
        boolean deberiaBorrarHijo = eliminar(nodoHijo, palabra, indice + 1);

        if (deberiaBorrarHijo) {
            actual.getHijos()[posicion] = null; // Se borra la referencia al hijo

            // Si el nodo actual no es fin de otra palabra y ya no tiene más hijos,
            // le decimos al de arriba que también se puede borrar
            return !actual.esFinDePalabra() && !actual.tieneHijos();
        }
        return false;

    }
}


