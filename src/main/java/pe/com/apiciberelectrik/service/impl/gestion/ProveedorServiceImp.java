/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.apiciberelectrik.service.impl.gestion;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.apiciberelectrik.entity.gestion.ProveedorEntity;
import pe.com.apiciberelectrik.repository.gestion.ProveedorRepository;
import pe.com.apiciberelectrik.service.gestion.ProveedorService;

/**
 *
 * @author user
 */
@Service
public class ProveedorServiceImp implements ProveedorService {

    @Autowired
    private ProveedorRepository repositorio;

    @Override
    public List<ProveedorEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public List<ProveedorEntity> findAllCustom() {
        return repositorio.findAllCustom();
    }

    @Override
    public ProveedorEntity add(ProveedorEntity t) {
        return repositorio.save(t);
    }

    @Override
    public Optional<ProveedorEntity> findById(Long id) {
        return repositorio.findById(id);
    }

    @Override
    public ProveedorEntity update(ProveedorEntity t) {
        ProveedorEntity objProveedor = repositorio.getById(t.getCodigo());
        BeanUtils.copyProperties(t, objProveedor);
        return repositorio.save(objProveedor);
    }

    @Override
    public ProveedorEntity delete(ProveedorEntity t) {
        ProveedorEntity objProveedor = repositorio.getById(t.getCodigo());
        objProveedor.setEstado(false);
        return repositorio.save(objProveedor);
    }

    @Override
    public ProveedorEntity enabled(ProveedorEntity t) {
        ProveedorEntity objProveedor = repositorio.getById(t.getCodigo());
        objProveedor.setEstado(true);
        return repositorio.save(objProveedor);
    }

}
