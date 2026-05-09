package BaseDatos;

public class Palabra {
    int id;
    String texto;
    String significado;
    int frecuencia;
    public Palabra(int id, String texto, String significado, int frecuencia) {
        this.id = id;
        this.texto = texto;
        this.significado = significado;
        this.frecuencia = frecuencia;
    }
    //implementacion de comparable
    @Override
    public int compareTo(Palabra otra) {
        //Utilizado para que al escribir las palabras no importe si estan en mayuscula o minuscula
        return this.texto.compareToIgnoreCase(otra.getTexto());
    }

    //getters y setters de los atributos del metodo Palabra
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }


    public String getSignificado() {
        return significado;
    }
    public void setSignificado(String significado) {
        this.significado = significado;
    }


    public int getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

}

