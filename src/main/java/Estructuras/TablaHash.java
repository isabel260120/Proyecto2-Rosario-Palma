package Estructuras;

import Atributos.Palabra;

// Permite almacenar pares llave-valor, donde K representa la llave y V el valor.
// En este proyecto se aplico para buscar palabras por ID o por texto.
public class TablaHash<K, V> {
    private int capacidad;
    private int tamaño;
    private K[] llaves;
    private V[] valores;

    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.tamaño = 0;
        //arreglos de objetos y casting de genericos K y V
        this.llaves = (K[]) new Object[capacidad];
        this.valores = (V[]) new Object[capacidad];
    }
    private  int hash(K llave) {
        return Math.abs(llave.hashCode() % capacidad);
    }
    // Inserta un par llave-valor dentro de la tabla hash.
    // Si la llave ya existe, actualiza su valor.
    public void insertar(K llave, V valor) {
        int i=hash(llave);
        //se avanza una posición a la vez hasta encontrar espacio
        // o hasta encontrar la misma llave.
        while (llaves[i]!=null){
            if(llaves[i].equals(llave)){
                valores[i]=valor;
                return;
            } //manejo de colisiones
            i=(i+1)%capacidad;
        }
        // Cuando se encuentra una posición vacía, se guarda la llave y su valor.
        llaves[i]=llave;
        valores[i]=valor;
        tamaño++;
    }
    // Busca el valor asociado a una llave.
    // Si la llave existe, retorna su valor; si no existe, retorna null.

    public V obtener(K llave) {
        int i=hash(llave);
        while (llaves[i]!=null){ //manejo de colisiones
            if(llaves[i].equals(llave)){
                return valores[i];
            }
            i=(i+1)%capacidad;
        }
        return null;
    }


    public boolean contiene(K llave) {
        return obtener(llave) != null;
    }
    public void eliminar(K llave) {
        if(!contiene(llave)) return;
        int i=hash(llave);
        while (!llave.equals(llaves[i])){
            i=(i+1)%capacidad; //manejo colisiones
        }
        llaves[i]=null;
        valores[i]=null;

        i=(i+1)%capacidad;
        while (llaves[i]!=null){
            K llaveParaReinsertar=llaves[i];
            V valorParaReinsertar=valores[i];
            llaves[i]=null;
            valores[i]=null;
            tamaño--;
            insertar(llaveParaReinsertar,valorParaReinsertar);
            i=(i+1)%capacidad; //sondeo lineal: forma de resolver colisiones en una tabla hash. Si la posición calculada por la función hash está ocupada, se revisa la siguiente posición,
            // luego la siguiente, y así hasta encontrar un espacio disponible.
        }
        tamaño--;
    }
    public V buscarPorId(int id) {
        for (int i = 0; i < capacidad; i++) {
            if (valores[i] != null) {
                try {
                    java.lang.reflect.Method getId = valores[i].getClass().getMethod("getId");
                    int valorId = (int) getId.invoke(valores[i]);
                    if (valorId == id) {
                        return valores[i];
                    }
                } catch (Exception e) {
                    // Manejo de error si no es clase Palabra
                }
            }
        }
        return null;
    }

}

