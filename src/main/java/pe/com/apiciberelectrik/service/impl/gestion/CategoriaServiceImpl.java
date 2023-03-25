package pe.com.apiciberelectrik.service.impl.gestion;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.apiciberelectrik.entity.gestion.CategoriaEntity;
import pe.com.apiciberelectrik.repository.gestion.CategoriaRepository;
import pe.com.apiciberelectrik.service.gestion.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    
    @Autowired
    private CategoriaRepository repositorio;

    @Override
    public List<CategoriaEntity> findAll() {
        return repositorio.findAll();
    }

    @Override
    public List<CategoriaEntity> findAllCustom() {
        return repositorio.findAllCustom();
    }

    @Override
    public CategoriaEntity add(CategoriaEntity t) {
        return repositorio.save(t);
    }

    @Override
    public Optional<CategoriaEntity> findById(Long id) {
        return repositorio.findById(id);
    }

    @Override
    public CategoriaEntity update(CategoriaEntity t) {
        CategoriaEntity objCategoria = repositorio.getById(t.getCodigo());
        BeanUtils.copyProperties(t,objCategoria);
        return repositorio.save(objCategoria);
    }

    @Override
    public CategoriaEntity delete(CategoriaEntity t) {
        CategoriaEntity objCategoria = repositorio.getById(t.getCodigo());
        objCategoria.setEstado(false);
        return repositorio.save(objCategoria);
    }

    @Override
    public CategoriaEntity enabled(CategoriaEntity t) {
        CategoriaEntity objCategoria = repositorio.getById(t.getCodigo());
        objCategoria.setEstado(true);
        return repositorio.save(objCategoria);
    }

    
}
