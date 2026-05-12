package Controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Atributos.Palabra;
import Services.DiccionarioService;
import Estructuras.Lista;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/palabra")
public class DiccionarioController {

    private final DiccionarioService service;
    public DiccionarioController(DiccionarioService service) {
        this.service = service;
    }
    //POST* http://localhost:8080/palabra/Agregarpalabra
    @PostMapping("/Agregarpalabra")
    public ResponseEntity<Palabra> agregarPalabra(@RequestBody Palabra palabra){
        Palabra nueva=service.agregarPalabra(palabra.getTexto(), palabra.getSignificado());
        return ResponseEntity.ok(nueva);
    }
    @PostMapping("/cargar")
    public ResponseEntity<String> cargar()  {
        service.importarCSV("Diccionario.csv");
        return ResponseEntity.ok("OK");
    }
    @PostMapping("/guardar")
    public ResponseEntity<String> guardar() {
        service.exportarCSV("Diccionario.csv");
        return ResponseEntity.ok("Diccionario guardado en CSV");
    }

    //PUT actualiza significado y frecuencia http://localhost:8080/palabra/palabra
    @PutMapping("/palabra")
    public ResponseEntity<Palabra> actualizarPalabra(@RequestBody Palabra palabra){
        Palabra actualizada=service.actualizarPalabra(palabra.getId(), palabra.getTexto(), palabra.getSignificado(), palabra.getFrecuencia());
        if(actualizada != null){
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }
    //Diccionario Completo
    @GetMapping("/diccionario")
    public ResponseEntity<Palabra[]> obtenerDiccionario() {
        Lista<Palabra>todas= service.obtenerTopK("","prefijo",1000,"asc", "alfabeto");
        if(todas==null||todas.getTamaño()==0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(todas.toArray(Palabra.class));
    }
    //GET POR ID* http://localhost:8080/palabra/id/""
    @GetMapping("/id/{id}")
    public ResponseEntity<Palabra> obtenerPorId(@PathVariable int id){
        Palabra p=service.buscarPorId(id);
        return p!=null?ResponseEntity.ok(p):ResponseEntity.notFound().build();
    }
    //GET POR TEXTO (BUSQUEDA EXTACTA)* http://localhost:8080/palabra/""
    @GetMapping("/{texto}")
    public ResponseEntity<Palabra> obtenerPorTexto(@PathVariable String texto){
        Palabra p=service.buscarPorTexto(texto);
        if(p==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }
    //Busqueda por prefijos * http://localhost:8080/palabra/prefijo/gua?limite=4&orden=desc&ordenarPor=frecuencia
    @GetMapping("/prefijo/{texto}")
    public ResponseEntity<Palabra[]> buscarPorPrefijo(@PathVariable String texto,@RequestParam(defaultValue = "10")int limite, @RequestParam(defaultValue = "asc")String orden, @RequestParam(defaultValue = "alfabeto")String ordenarPor){
        Lista<Palabra> resultados=service.obtenerTopK(texto,"Prefijo", limite, orden, ordenarPor);
        return ResponseEntity.ok(resultados.toArray(Palabra.class));
    }

    //Busqueda por comodin* http://localhost:8080/palabra/comodin/gua*?limite=4&ordenarPor=alfabeto&orden=asc
    @GetMapping("/comodin/{patron}")
    public ResponseEntity<Palabra[]> buscarPorComodin(@PathVariable String patron,@RequestParam(defaultValue = "10")int limite, @RequestParam(defaultValue = "asc")String orden, @RequestParam(defaultValue = "frecuencia")String ordenarPor){
        Lista<Palabra> resultados=service.obtenerTopK(patron,"comodin",limite,orden,ordenarPor);
        return ResponseEntity.ok(resultados.toArray(Palabra.class));
    }
    //ELIMINAR
    @DeleteMapping("/palabra/{id}")
    public ResponseEntity<Palabra> eliminar(@PathVariable int id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @PostConstruct
    public void inicializar(){
        String ruta="Diccionario.csv";
        try{
            if(Files.notExists(Paths.get(ruta))){
                PrintWriter writer=new PrintWriter(new File(ruta));
                writer.println("ID;palabra;sigificado;frecuencia");
                writer.close();
            }else{
                service.importarCSV(ruta);
                System.out.println("Datos cargados desde diccionario.csv");
            }

        }catch (IOException e){
            System.err.println("Error al inicializa" +e.getMessage());
        }
    }
    @GetMapping("/exportar")
    public ResponseEntity<String> exportarCSVManual(String ruta){
        service.exportarCSV("Diccionario.csv");
        return ResponseEntity.ok("Archivo exportado exitosamente");
    }



}

