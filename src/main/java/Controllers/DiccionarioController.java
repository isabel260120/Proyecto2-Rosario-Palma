package Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import BaseDatos.Palabra;
import Services.DiccionarioService;
import Estructuras.Lista;

@RestController
@RequestMapping("/")
public class DiccionarioController {



    private final DiccionarioService service;
    public DiccionarioController(DiccionarioService service) {
        this.service = service;
    }
    //POST
    @PostMapping("/palabra")
    public Palabra crearPalabra(@RequestBody Palabra palabra){
        return service.agregarPalabra(palabra.getTexto(), palabra.getSignificado());
    }
    //PUT
    @PutMapping("/palabra")
    public ResponseEntity<Palabra> actualizarPalabra(@RequestBody Palabra palabra){
        Palabra actualizada=service.actualizarPalabra(palabra.getId(), palabra.getTexto(), palabra.getSignificado(), palabra.getFrecuencia());
        if(actualizada != null){
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.notFound().build();
    }
    //GET POR ID
    @GetMapping("/palabra/id")
    public ResponseEntity<Palabra> obtenerPorId(@PathVariable int id){
        Palabra p=service.buscarPorId(id);
        return p!=null?ResponseEntity.ok(p):ResponseEntity.notFound().build();
    }
    //GET POR TEXTO (BUSQUEDA EXTACTA)
    @GetMapping("/palabra/buscar/texto")
    public ResponseEntity<Palabra> obtenerPorTexxto(@PathVariable String texto){
        Palabra p=service.buscarPorTexto(texto);
        return p!=null?ResponseEntity.ok(p):ResponseEntity.notFound().build();
    }
    //Busqueda por prefijos
    @GetMapping("/prefijo/texto")
    public ResponseEntity<Lista<Palabra>> buscarPorPrefijo(@PathVariable String texto,@RequestParam(defaultValue = "10")int limite, @RequestParam(defaultValue = "asc")String orden, @RequestParam(defaultValue = "alfabeto")String ordenarPor){
        Lista<Palabra> resultados=service.obtenerTopK(texto,"Prefijo", limite, orden, ordenarPor);
        return ResponseEntity.ok(resultados);
    }

    //Busqueda por comodin
    @GetMapping("/comodin/patron")
    public ResponseEntity<Lista<Palabra>> buscarPorComodin(@PathVariable String patron,@RequestParam(defaultValue = "10")int limite, @RequestParam(defaultValue = "asc")String orden, @RequestParam(defaultValue = "frecuencia")String ordenarPor){
        Lista<Palabra> resultados=service.obtenerTopK(patron,"comodin",limite,orden,ordenarPor);
        return ResponseEntity.ok(resultados);
    }
    //ELIMINAR
    @DeleteMapping("/palabra/id")
    public ResponseEntity<Palabra> eliminar(@PathVariable int id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
