/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.apiciberelectrik.restController;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.apiciberelectrik.entity.gestion.ProveedorEntity;
import pe.com.apiciberelectrik.service.gestion.ProveedorService;

/**
 *
 * @author user
 */
@RestController
@RequestMapping("/proveedor")
public class ProveedorRestController {
     @Autowired
    private ProveedorService servicio;
    
    @GetMapping
    public List<ProveedorEntity> findAll(){
        return servicio.findAll();
    }
    @GetMapping("/custom")
    public List<ProveedorEntity>findAllCustom(){
        return servicio.findAllCustom();
    }
    @PostMapping
    public ProveedorEntity add(@RequestBody ProveedorEntity p){
        return servicio.add(p);
    }
    @GetMapping("/{id}")
    public Optional<ProveedorEntity>findById(@PathVariable long id){
        return servicio.findById(id);
    }
    @PutMapping("/{id}")
    public ProveedorEntity update(@PathVariable long id,@RequestBody ProveedorEntity p){
        p.getCodigo();
        return servicio.update(p);
    }
    @DeleteMapping("/{id}")
    public ProveedorEntity delete(@PathVariable long id){
        ProveedorEntity objProveedor = new ProveedorEntity();
        objProveedor.setCodigo(id);
        return servicio.delete(ProveedorEntity.builder().codigo(id).build());
    }
}
