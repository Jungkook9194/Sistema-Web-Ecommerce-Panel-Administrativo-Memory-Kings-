/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.apiciberelectrik.entity.gestion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pe.com.apiciberelectrik.entity.base.BaseEntity;

/**
 *
 * @author user
 */
@SuperBuilder 
@NoArgsConstructor 
@AllArgsConstructor 
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name="DetalleVentaEntity") 
@Table(name="detalleventa") 
public class DetalleVentaEntity extends BaseEntity implements  Serializable {
    private static final long serialVersionUID=1L;
    @Id 
    @Column(name="coddet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;
    @Column(name="candet", nullable = false)
    private int cantidad;
    @Column(name="preudet", nullable = false)
    private double preciounitario;
    @ManyToOne //relacion de uno a muchos
    @JoinColumn(name="codven",nullable = false)
    private VentaEntity venta;
    @ManyToOne //relacion de uno a muchos
    @JoinColumn(name="codpro",nullable = false)
    private ProductoEntity producto;
}
