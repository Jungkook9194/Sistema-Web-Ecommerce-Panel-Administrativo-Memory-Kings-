/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.com.apiciberelectrik.repository.gestion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.apiciberelectrik.entity.gestion.ClienteEntity;
import pe.com.apiciberelectrik.entity.gestion.RolEntity;

/**
 *
 * @author user
 */
@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    @Query("select c from ClienteEntity c where c.estado=1")
    List<ClienteEntity> findAllCustom();
}
