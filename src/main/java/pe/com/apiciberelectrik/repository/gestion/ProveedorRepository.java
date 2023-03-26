/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.apiciberelectrik.repository.gestion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.apiciberelectrik.entity.gestion.ProveedorEntity;

/**
 *
 * @author user
 */
@Repository
public interface ProveedorRepository  extends JpaRepository<ProveedorEntity, Long> {
    @Query("select pr from ProveedorEntity pr where pr.estado=1")
    List<ProveedorEntity> findAllCustom();
}
