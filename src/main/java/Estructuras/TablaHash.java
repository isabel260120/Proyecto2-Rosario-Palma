package Estructuras;

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

    public void insertar(K llave, V valor) {
        int i=hash(llave);
        while (llaves[i]!=null){
            if(llaves[i].equals(llave)){
                valores[i]=valor;
                return;
            }
            i=(i+1)%capacidad;
        }
        llaves[i]=llave;
        valores[i]=valor;
        tamaño++;
    }

    public V obtener(K llave) {
        int i=hash(llave);
        while (llaves[i]!=null){
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
            i=(i+1)%capacidad;
        }
        tamaño--;
    }
}
