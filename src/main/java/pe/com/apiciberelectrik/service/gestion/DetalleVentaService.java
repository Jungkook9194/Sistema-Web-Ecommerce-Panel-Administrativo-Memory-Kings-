/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.com.apiciberelectrik.service.gestion;

import java.util.List;
import pe.com.apiciberelectrik.entity.gestion.DetalleVentaEntity;
import pe.com.apiciberelectrik.service.generic.GenericoService;

/**
 *
 * @author user
 */
public interface DetalleVentaService extends GenericoService<DetalleVentaEntity> {

    public void addAll(List<DetalleVentaEntity> detalleVenta);
    
}
