package BaseDatos;

public class Palabra implements Comparable<Palabra> {
    private Integer id;
    private String texto;
    private String significado;
    private Integer frecuencia;
    public Palabra(){}
    public Palabra(Integer id, String texto, String significado, Integer frecuencia) {
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
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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


    public Integer getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

}

