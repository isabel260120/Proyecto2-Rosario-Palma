package Services;
import Estructuras.*;
import BaseDatos.Palabra;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Scanner;

@Service
public class DiccionarioService {
    private Trie<Palabra> trie=new Trie<>();
    private TablaHash<Integer, Palabra>tabla=new TablaHash<>(100);
    private int contadorId=1;
//6.1 almacenamiento de palabras
    public Palabra agregarPalabra(String texto, String significado){
        Palabra nueva =new Palabra(contadorId++, texto, significado,0);
        trie.insertar(texto, nueva);
        tabla.insertar(nueva.getId(), nueva);

        exportarCSV("Diccionario.csv");
        return  nueva;
    }
//6.2 actualizacion de palabras
    public Palabra actualizarPalabra(int id, String nuevoTexto, String nuevoSignificado, int nuevaFrecuencia){
        Palabra p =tabla.obtener(id);
        if(p!=null){
            p.setSignificado(nuevoSignificado);
            p.setFrecuencia(nuevaFrecuencia);
            return p;
        }
        return null;
    }
//6.3 Busqueda por ID
    public Palabra buscarPorId(int id){
        Palabra p=tabla.obtener(id);
        if(p!=null){
            p.setFrecuencia(p.getFrecuencia()+1);
        }
        return p;
    }
    // 6.4 Búsqueda Exacta
    public Palabra buscarPorTexto(String texto) {
        Palabra p = trie.buscar(texto);
        if (p != null) p.setFrecuencia(p.getFrecuencia() + 1);
        return p;
    }

    // 6.6 Búsqueda por comodín (Adaptado para retornar lista)
    public Lista<Palabra> buscarComodin(String patron) {
        return trie.buscarComodin(patron);
    }

    public Lista<Palabra> obtenerTopK(String criterioBusqueda,String tipo, int limite,String orden, String ordenarPor){
        Lista<Palabra>resultadosIniciales;

        if(tipo.equalsIgnoreCase("prefijo")){
            resultadosIniciales=trie.buscarPorPrefijo(criterioBusqueda);
        }else if(tipo.equalsIgnoreCase("comodin")){
            resultadosIniciales= trie.buscarComodin(criterioBusqueda);
            
        }else{
            resultadosIniciales=new Lista<>();
        }

        Comparator<Palabra> comparador;
        if(ordenarPor.equalsIgnoreCase("Frecuencia")){
            comparador= orden.equalsIgnoreCase("desc") ?
                    (p1,p2)->Integer.compare(p2.getFrecuencia(), p1.getFrecuencia()):
                    (p1,p2)->Integer.compare(p1.getFrecuencia(), p2.getFrecuencia());
        }else{
            comparador= orden.equalsIgnoreCase("desc") ?
                    (p1,p2)->p2.getTexto().compareToIgnoreCase(p1.getTexto()):
                    (p1,p2)->p1.getTexto().compareToIgnoreCase(p2.getTexto());
        }
        int capacidadHeap=resultadosIniciales.getTamaño()>0? resultadosIniciales.getTamaño()+1:10;
        ColaPrioridad<Palabra> heap=new ColaPrioridad<>(capacidadHeap,comparador);

        Lista.NodoLista<Palabra>actual=resultadosIniciales.getPrimero();
        while(actual!=null){
            heap.insertar(actual.getValor());
            actual=actual.getSiguiente();
        }
        Lista<Palabra>listaFinal=new Lista<>();
        for(int i=0;i<limite;i++){
            Palabra p=heap.eliminarMin();
            if(p!=null){
                listaFinal.agregar(p);
            }else{
                break;
            }
        }
        return listaFinal;

    }
    public void eliminar(int id){
        tabla.eliminar(id);
    }

    public boolean existePalabra(String texto){
        return trie.buscar(texto) != null;
    }

    public void exportarCSV(String ruta){
        try(PrintWriter writer=new PrintWriter(new File(ruta))){
            writer.println("id;palabra;significado;frecuencia");
            Lista<Palabra> todas= trie.obtenerTodas();
            Lista.NodoLista<Palabra> actual=todas.getPrimero();

            while(actual!=null){
                Palabra p=actual.getValor();
                writer.println(p.getId()+";"+ p.getTexto() +";"+p.getSignificado()+";"+p.getFrecuencia()+";");
                actual=actual.getSiguiente();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void importarCSV(String ruta){
        try(Scanner scanner=new Scanner(new File(ruta))){
            if(scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[]datos=scanner.nextLine().split(";");
                if(datos.length==4){
                    int id=Integer.parseInt(datos[0]);
                    String texto=datos[1];
                    String significado=datos[2];
                    int frecuencia=Integer.parseInt(datos[3]);

                    Palabra p =new Palabra(id,texto,significado,frecuencia);
                    trie.insertar(texto,p);
                    tabla.insertar(id,p);

                    if(id>=contadorId) contadorId=id+1;
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado");
        }
    }
}


