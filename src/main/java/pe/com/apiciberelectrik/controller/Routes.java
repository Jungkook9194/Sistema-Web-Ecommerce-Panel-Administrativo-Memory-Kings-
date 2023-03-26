/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.apiciberelectrik.controller;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.Console;

import jakarta.validation.Valid;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.com.apiciberelectrik.entity.gestion.CategoriaEntity;
import pe.com.apiciberelectrik.entity.gestion.ClienteEntity;
import pe.com.apiciberelectrik.entity.gestion.DetalleVentaEntity;
import pe.com.apiciberelectrik.entity.gestion.ProductoEntity;
import pe.com.apiciberelectrik.entity.gestion.ProveedorEntity;
import pe.com.apiciberelectrik.entity.gestion.ReclamoEntity;
import pe.com.apiciberelectrik.entity.gestion.RolEntity;
import pe.com.apiciberelectrik.entity.gestion.UsuarioEntity;
import pe.com.apiciberelectrik.entity.gestion.VentaEntity;
import pe.com.apiciberelectrik.repository.gestion.CategoriaRepository;
import pe.com.apiciberelectrik.repository.gestion.ProductoRepository;
import pe.com.apiciberelectrik.repository.gestion.UsuarioRepository;
import pe.com.apiciberelectrik.seguridad.SeguridadConfig;
import pe.com.apiciberelectrik.service.gestion.CategoriaService;
import pe.com.apiciberelectrik.service.gestion.ClienteService;
import pe.com.apiciberelectrik.service.gestion.DetalleVentaService;
import pe.com.apiciberelectrik.service.gestion.ProductoService;
import pe.com.apiciberelectrik.service.gestion.ProveedorService;
import pe.com.apiciberelectrik.service.gestion.ReclamoService;
import pe.com.apiciberelectrik.service.gestion.RolService;
import pe.com.apiciberelectrik.service.gestion.UsuarioService;
import pe.com.apiciberelectrik.service.gestion.VentaService;

@Controller
public class Routes {

    private static final int MAX_PRODUCTOS_EN_CARRITO = 1;

    @Autowired
    private EntityManager entityMaganer;

    @Autowired
    private SeguridadConfig seguridadConfig;
    @Autowired
    private CategoriaService serviciocategoria;

    @Autowired
    private ProductoService servicioproducto;

    private List<ProductoEntity> Carrito = new ArrayList<>();

    private List<ProductoEntity> venta = new ArrayList<>();

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProveedorService servicioproveedor;

    @Autowired
    private RolService serviciorol;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ClienteService serviciocliente;

    @Autowired
    private UsuarioService serviciousuario;

    @Autowired
    private ReclamoService servicioreclamo;

    @Autowired
    private ProductoRepository productorepositorio;

    @Autowired
    private UsuarioRepository usuariorepositorio;

    @Autowired
    private VentaService servicioventa;

    @Autowired
    private DetalleVentaService serviciodetalle;

    @GetMapping("/login")
    public String MostrarLogin() {
        return "login";
    }

    @GetMapping("/navbar")
    public String MostrarNavbar() {
        return "navbar";
    }

    // * MOSTRAR ENTIDADES --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/mostrarcategoria")
    public String MostrarCategoria(Model modelo) {
        modelo.addAttribute("categoria", serviciocategoria.findAllCustom());
        return "/categoria/mostrarcategoria";
    }

    @GetMapping("/mostrarrol")
    public String MostrarRol(Model modelo) {
        modelo.addAttribute("rol", serviciorol.findAllCustom());
        return "/rol/mostrarrol";
    }

    @GetMapping("/mostrarproveedor")
    public String MostrarProveedor(Model modelo) {
        modelo.addAttribute("proveedor", servicioproveedor.findAllCustom());
        return "/proveedor/mostrarproveedor";
    }

    @GetMapping("/mostrarcliente")
    public String MostrarCliente(Model modelo) {
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        return "/cliente/mostrarcliente";
    }

    @GetMapping("/mostrarusuario")
    public String MostrarUsuario(Model modelo) {
        modelo.addAttribute("usuario", serviciousuario.findAllCustom());
        return "/usuario/mostrarusuario";
    }

    @GetMapping("/mostrarreclamo")
    public String MostrarReclamo(Model modelo) {
        modelo.addAttribute("reclamo", servicioreclamo.findAllCustom());
        return "/reclamo/mostrarreclamo";
    }

    @GetMapping("/mostrarpedidos")
    public String MostrarPedido(Model modelo) {
        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        modelo.addAttribute("carrito", Carrito);
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        modelo.addAttribute("usuario", serviciousuario.findAllCustom());
        modelo.addAttribute("detalle", new DetalleVentaEntity());
        return "/pedidos/mostrarpedidos";
    }

    @GetMapping("/mostrarproducto")
    public String MostrarProducto(Model modelo) {
        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        modelo.addAttribute("carrito", Carrito);
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        modelo.addAttribute("usuario", serviciousuario.findAllCustom());
        return "/producto/mostrarproducto";
    }

    @GetMapping("/mostrarventa")
    public String MostrarVentas(Model modelo) {
        modelo.addAttribute("venta", servicioventa.findAllCustom());
        return "/ventas/mostrarventa";
    }

    @GetMapping("/ver")
    public String verproductos() {
        return null;
    }

    @GetMapping("/agregar/{id}")
    public String agregarcarrito(@PathVariable("id") long id, Model modelo) {

        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        ProductoEntity producto = servicioproducto.findById(id).get();
        boolean itemFound = false;
        for (ProductoEntity cart : Carrito) {
            if (cart.getCodigo() == id) {
                itemFound = true;
                break;
            }
        }
        if (Carrito.size() < MAX_PRODUCTOS_EN_CARRITO) {
            if (!itemFound) {
                Carrito.add(producto);
                return "redirect:/mostrarproducto?agregado";
            }
        }
        return "redirect:/mostrarproducto?noagregado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarcarrito(@PathVariable("id") long id) {
        ProductoEntity producto = servicioproducto.findById(id).get();
        if (producto != null) {
            Carrito.remove(producto);
        }
        return "redirect:/mostrarpedidos?removido";
    }

    @GetMapping("/vaciar")
    public String vaciar(Model modelo, HttpSession session) {
        Carrito.clear();
        return "redirect:/mostrarpedidos?vaciar";
    }

    public enum role {
        serviciorol
    }

    // * MOSTRAR ENTIDADES --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    @GetMapping("/principal")
    public String MostrarPrincipal(Model modelo) {
        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        return "principal";
    }

    // * MOSTRAR ACTUALIZAR --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/mostraractualizarcategoria/{id}")
    public String MostrarActualizarCategoria(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("categoria", serviciocategoria.findById(id).get());
        return "/categoria/actualizarcategoria";
    }

    @GetMapping("/mostraractualizarusuario/{id}")
    public String MostrarActualizarUsuario(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("rol", serviciorol.findAllCustom());
        modelo.addAttribute("usuario", serviciousuario.findById(id).get());
        return "/usuario/actualizarusuario";
    }

    @GetMapping("/mostraractualizarreclamo/{id}")
    public String MostrarActualizarReclamo(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        modelo.addAttribute("reclamo", servicioreclamo.findById(id).get());
        return "/reclamo/actualizarreclamo";
    }

    @GetMapping("/mostraractualizarcliente/{id}")
    public String MostrarActualizarCliente(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("cliente", serviciocliente.findById(id).get());
        return "/cliente/actualizarcliente";
    }

    @GetMapping("/mostrardetalle")
    public String MostrarDetalleVenta(Model modelo) {
        modelo.addAttribute("detalle", serviciodetalle.findAllCustom());
        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        modelo.addAttribute("carrito", Carrito);
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        modelo.addAttribute("usuario", serviciousuario.findAllCustom());
        return "/detalle/mostrardetalle";
    }

    @GetMapping("/mostraractualizarrol/{id}")
    public String MostrarActualizarRol(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("rol", serviciorol.findById(id).get());
        return "/rol/actualizarrol";
    }

    @GetMapping("/mostraractualizarproducto/{id}")
    public String MostrarActualizarProducto(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("categoria", serviciocategoria.findAllCustom());
        modelo.addAttribute("producto", servicioproducto.findById(id).get());
        return "/producto/actualizarproducto";
    }

    // * MOSTRAR ACTUALIZAR --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    // * MOSTRAR HABILITAR --------------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/mostrarhabilitarcategoria")
    public String MostrarHabilitarCategoria(Model modelo) {
        modelo.addAttribute("categoria", serviciocategoria.findAll());
        return "/categoria/habilitarcategoria";
    }

    @GetMapping("/mostrarhabilitarusuario")
    public String MostrarHabilitarUsuario(Model modelo) {
        modelo.addAttribute("usuario", serviciousuario.findAll());
        return "/usuario/habilitarusuario";
    }

    @GetMapping("/mostrarhabilitarreclamo")
    public String MostrarHabilitarReclamo(Model modelo) {
        modelo.addAttribute("reclamo", servicioreclamo.findAll());
        return "/reclamo/habilitarreclamo";
    }

    @GetMapping("/mostrarhabilitarrol")
    public String MostrarHabilitarRol(Model modelo) {
        modelo.addAttribute("rol", serviciorol.findAll());
        return "/rol/habilitarrol";
    }

    @GetMapping("/mostrarhabilitarproducto")
    public String MostrarHabilitarProducto(Model modelo) {
        modelo.addAttribute("producto", servicioproducto.findAll());
        return "/producto/habilitarproducto";
    }

    @GetMapping("/mostrarhabilitarcliente")
    public String MostrarHabilitarCliente(Model modelo) {
        modelo.addAttribute("cliente", serviciocliente.findAll());
        return "/cliente/habilitarcliente";
    }
    // * MOSTRAR HABILITAR --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱

    // * MOSTRAR REGISTRAR --------------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/mostrarresgistrarcategoria")
    public String MostrarRegistrarCategoria() {
        return "/categoria/registrarcategoria";
    }

    @GetMapping("/mostrarresgistrarusuario")
    public String MostrarRegistrarUsuario(Model modelo) {
        modelo.addAttribute("rol", serviciorol.findAllCustom());
        return "/usuario/registrarusuario";
    }

    @GetMapping("/mostrarresgistrarproveedor")
    public String MostrarRegistrarProveedor(Model modelo) {
        modelo.addAttribute("producto", servicioproducto.findAllCustom());
        return "/proveedor/registrarproveedor";
    }

    @GetMapping("/mostrarresgistrarventa")
    public String MostrarRegistrarVenta(Model modelo) {
        modelo.addAttribute("rol", serviciorol.findAllCustom());
        return "/producto/mostrarproducto";
    }

    @GetMapping("/mostrarresgistrarreclamo")
    public String MostrarRegistrarReclamo(Model modelo) {
        modelo.addAttribute("cliente", serviciocliente.findAllCustom());
        return "/reclamo/registrarreclamo";
    }

    @GetMapping("/mostrarresgistrarrol")
    public String MostrarRegistrarRol() {
        return "/rol/registrarrol";
    }

    @GetMapping("/mostrarresgistrarcliente")
    public String MostrarRegistrarCliente() {
        return "/cliente/registrarcliente";
    }

    @GetMapping("/mostrarresgistrarproducto")
    public String MostrarRegistrarProducto(Model modelo) {
        modelo.addAttribute("categoria", serviciocategoria.findAllCustom());
        return "/producto/registrarproducto";
    }
    // * MOSTRAR REGISTRAR --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱

    // * MODELATTRIBUTE ENTIDADES --------------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @ModelAttribute("categoria")
    public CategoriaEntity ModeloCategoria() {
        return new CategoriaEntity();
    }

    @ModelAttribute("reclamo")
    public ReclamoEntity ModeloReclamo() {
        return new ReclamoEntity();
    }

    @ModelAttribute("proveedor")
    public ProveedorEntity ModeloProveedor() {
        return new ProveedorEntity();
    }

    @ModelAttribute("cliente")
    public ClienteEntity ModeloCliente() {
        return new ClienteEntity();
    }

    @ModelAttribute("producto")
    public ProductoEntity ModeloProducto() {
        return new ProductoEntity();
    }

    @ModelAttribute("rol")
    public RolEntity ModeloRol() {
        return new RolEntity();
    }

    @ModelAttribute("usuario")
    public UsuarioEntity ModeloUsurio() {
        return new UsuarioEntity();
    }

    @ModelAttribute("venta")
    public VentaEntity Modeloventa() {
        return new VentaEntity();
    }

    @ModelAttribute("detalle")
    public DetalleVentaEntity ModeloDetalle() {
        return new DetalleVentaEntity();
    }

    // * MODELATTRIBUTE ENTIDADES --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    // * REGISTRAR ACCION --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @PostMapping("/registrarcategoria")
    public String RegistroCategoria(@Valid @ModelAttribute("categoria") CategoriaEntity c, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "categoria/registrarcategoria";
            }
            serviciocategoria.add(c);
            return "redirect:/mostrarcategoria?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarcategoria?incorrecto";
        }
    }

    @PostMapping("/registrarventa")
    @Transactional
    public String RegistroVenta(@Valid @ModelAttribute("venta") VentaEntity v, HttpSession session, BindingResult result, Model model, @ModelAttribute("detalle") DetalleVentaEntity detalleVenta, ProductoEntity producto) {
        model.addAttribute("cliente", serviciocliente.findAllCustom());
        model.addAttribute("usuario", serviciousuario.findAllCustom());
        servicioventa.add(v);
        try {
            if (result.hasErrors()) {
                return "ventas/mostrarventa";
            }
            detalleVenta.setVenta(v);
            serviciodetalle.add(detalleVenta);
            ProductoEntity productovendido = entityMaganer.find(ProductoEntity.class, detalleVenta.getProducto().getCodigo());
            productovendido.setStock(productovendido.getStock() - detalleVenta.getCantidad());
            Carrito.clear();
            return "redirect:/mostrarventa?correcto";
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @PostMapping("/registrarcliente")
    public String RegistroCliente(@Valid @ModelAttribute("cliente") ClienteEntity c, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "cliente/registrarcliente";
            }
            serviciocliente.add(c);
            return "redirect:/mostrarcliente?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarcliente?incorrecto";
        }
    }

    @PostMapping("/registrarproducto")
    public String RegistroProducto(@Valid @ModelAttribute("producto") ProductoEntity p, BindingResult result, Model model) {
        try {
            model.addAttribute("categoria", serviciocategoria.findAllCustom());
            if (result.hasErrors()) {
                return "producto/registrarproducto";
            }
            servicioproducto.add(p);
            return "redirect:/mostrarproducto?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarproducto?incorrecto";

        }
    }

    @PostMapping("/registrarreclamo")
    public String RegistroReclamo(@Valid @ModelAttribute("reclamo") ReclamoEntity r, BindingResult result, Model model) {
        try {
            model.addAttribute("cliente", serviciocliente.findAllCustom());
            if (result.hasErrors()) {
                return "reclamo/registrarreclamo";
            }
            servicioreclamo.add(r);
            return "redirect:/mostrarreclamo?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarreclamo?incorrecto";
        }
    }

    @PostMapping("/registrarrol")
    public String RegistroRol(@Valid @ModelAttribute("rol") RolEntity r, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "rol/registrarrol";
            }
            serviciorol.add(r);
            return "redirect:/mostrarrol?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarrol?incorrecto";
        }
    }

    @PostMapping("/registrarproveedor")
    @Transactional
    public String RegistroProveedor(@Valid @ModelAttribute("proveedor") ProveedorEntity p, BindingResult result, ProductoEntity pro) {
        try {
            if (result.hasErrors()) {
                return "proveedor/registrarproveedor";
            }
            servicioproveedor.add(p);
            ProductoEntity produ = entityMaganer.find(ProductoEntity.class, p.getProducto().getCodigo());
            produ.setStock(produ.getStock() + p.getCantidad());
            return "redirect:/mostrarproveedor?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarproveedor?incorrecto";
        }
    }

    @PostMapping("/registrarusuario")
    public String RegistroUsuario(@Valid @ModelAttribute("usuario") UsuarioEntity u, BindingResult result, Model model) {
        try {
            model.addAttribute("rol", serviciorol.findAllCustom());
            if (result.hasErrors()) {
                return "usuario/registrarusuario";
            }
            String passwordcodificado = seguridadConfig.passwordEncoder().encode(u.getPassword());
            u.setPassword(passwordcodificado);
            serviciousuario.add(u);
            return "redirect:/mostrarusuario?correcto";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?incorrecto";
        }
    }

    // * REGISTRAR ACCION --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    // * ACTUALIZAR ACCION --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @PostMapping("/actualizarcategoria/{id}")
    public String ActualizarCategoria(@PathVariable Long id, @Valid @ModelAttribute("categoria") CategoriaEntity c, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "categoria/actualizarcategoria";
            }
            serviciocategoria.update(c);
            return "redirect:/mostrarcategoria?actualizado";
        } catch (Exception e) {
            return "redirect:/mostrarcategoria?noactualizado";

        }
    }

    @PostMapping("/actualizarcliente/{id}")
    public String ActualizarCliente(@PathVariable Long id, @Valid @ModelAttribute("cliente") ClienteEntity c, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "cliente/actualizarcliente";
            }
            serviciocliente.update(c);
            return "redirect:/mostrarcliente?actualizado";
        } catch (Exception e) {
            return "redirect:/mostrarcliente?noactualizado";
        }
    }

    @PostMapping("/actualizarproducto/{id}")
    public String ActualizarProducto(@PathVariable Long id, @Valid @ModelAttribute("producto") ProductoEntity p, BindingResult result, Model model) {
        try {
            model.addAttribute("categoria", serviciocategoria.findAllCustom());
            if (result.hasErrors()) {
                return "producto/actualizarproducto";
            }
            servicioproducto.update(p);
            return "redirect:/mostrarproducto?actualizado";
        } catch (Exception e) {
            return "redirect:/mostrarproducto?noactualizado";

        }
    }

    @PostMapping("/actualizarrol/{id}")
    public String ActualizarRol(@PathVariable Long id, @Valid @ModelAttribute("rol") RolEntity r, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "rol/actualizarrol";
            }
            serviciorol.update(r);
            return "redirect:/mostrarrol?actualizado";
        } catch (Exception e) {
            return "redirect:/mostrarrol?noactualizado";
        }
    }

    @PostMapping("/actualizarusuario/{id}")
    public String ActualizarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") UsuarioEntity u, BindingResult result, Model model) {
        try {
            model.addAttribute("rol", serviciorol.findAllCustom());
            if (result.hasErrors()) {
                return "usuario/actualizarusuario";
            }
            String passwordcodificado = seguridadConfig.passwordEncoder().encode(u.getPassword());
            u.setPassword(passwordcodificado);
            serviciousuario.update(u);
            return "redirect:/mostrarusuario?actualizado";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?noactualizado";
        }
    }

    // * ACTUALIZAR ACCION--------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    @GetMapping("/eliminarcategoria/{id}")
    public String EliminarCategoria(@PathVariable Long id, Model modelo, RedirectAttributes redirectAttributes) {
        CategoriaEntity objCategoria = serviciocategoria.findById(id).get();
        List<ProductoEntity> product = productorepositorio.findByCategoria(objCategoria);
        if (!product.isEmpty()) {
            return "redirect:/mostrarcategoria?noeliminado";
        }
        serviciocategoria.delete(objCategoria);
        return "redirect:/mostrarcategoria?eliminado";
    }

    @GetMapping("/eliminarusuario/{id}")
    public String EliminarUsuario(@PathVariable Long id, Model modelo) {
        try {
            UsuarioEntity objUsuario = serviciousuario.findById(id).get();
            serviciousuario.delete(objUsuario);
            return "redirect:/mostrarusuario?eliminado";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?noeliminado";
        }
    }

    @GetMapping("/eliminarreclamo/{id}")
    public String EliminarReclamo(@PathVariable Long id, Model modelo) {
        try {
            ReclamoEntity objReclamo = servicioreclamo.findById(id).get();
            servicioreclamo.delete(objReclamo);
            return "redirect:/mostrarusuario?eliminado";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?noeliminado";
        }
    }

    @GetMapping("/eliminarrol/{id}")
    public String EliminarRol(@PathVariable Long id, Model modelo) {
        RolEntity objRol = serviciorol.findById(id).get();
        List<UsuarioEntity> user = usuariorepositorio.findByRol(objRol);
        if (!user.isEmpty()) {
            return "redirect:/mostrarrol?noeliminado";
        }

        serviciorol.delete(objRol);
        return "redirect:/mostrarrol?eliminado";

    }

    // * HABILITAR ACCION --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/habilitarcategoria/{id}")
    public String HabilitarCategoria(@PathVariable Long id, Model modelo) {
        try {
            CategoriaEntity objCategoria = serviciocategoria.findById(id).get();
            serviciocategoria.enabled(objCategoria);
            return "redirect:/mostrarcategoria?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarcategoria?nohabilitado";
        }
    }

    @GetMapping("/habilitarrol/{id}")
    public String HabilitarRol(@PathVariable Long id, Model modelo) {
        try {
            RolEntity objRol = serviciorol.findById(id).get();
            serviciorol.enabled(objRol);
            return "redirect:/mostrarrol?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarrol?nohabilitado";
        }
    }

    @GetMapping("/habilitarusuario/{id}")
    public String HabilitarUsuario(@PathVariable Long id, Model modelo) {
        try {
            UsuarioEntity objUsuario = serviciousuario.findById(id).get();
            serviciousuario.enabled(objUsuario);
            return "redirect:/mostrarusuario?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?nohabilitado";
        }
    }

    @GetMapping("/habilitarreclamo/{id}")
    public String HabilitarReclamo(@PathVariable Long id, Model modelo) {
        try {
            ReclamoEntity objReclamo = servicioreclamo.findById(id).get();
            servicioreclamo.enabled(objReclamo);
            return "redirect:/mostrarreclamo?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarreclamo?nohabilitado";
        }
    }
    // * HABILITAR ACCION --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱

    // * DESHABILITAR ACCION --------------- 🡳🡳🡳🡳🡳🡳🡳🡳
    @GetMapping("/deshabilitarcategoria/{id}")
    public String DesabilitarCategoria(@PathVariable Long id, Model modelo) {
        try {
            CategoriaEntity objCategoria = serviciocategoria.findById(id).get();
            serviciocategoria.delete(objCategoria);
            return "redirect:/mostrarcategoria?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarcategoria?nodeshabilitado";
        }
    }

    @GetMapping("/deshabilitarreclamo/{id}")
    public String DesabilitarReclamo(@PathVariable Long id, Model modelo) {
        try {
            ReclamoEntity objReclamo = servicioreclamo.findById(id).get();
            servicioreclamo.delete(objReclamo);
            return "redirect:/mostrarreclamo?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarreclamo?nodeshabilitado";
        }
    }

    @GetMapping("/deshabilitarusuario/{id}")
    public String DesabilitarUsuario(@PathVariable Long id, Model modelo) {
        try {
            UsuarioEntity objUsuario = serviciousuario.findById(id).get();
            serviciousuario.delete(objUsuario);
            return "redirect:/mostrarusuario?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarusuario?nodeshabilitado";
        }
    }

    @GetMapping("/deshabilitarrol/{id}")
    public String DesabilitarRol(@PathVariable Long id, Model modelo) {
        try {
            RolEntity objRol = serviciorol.findById(id).get();
            serviciorol.delete(objRol);
            return "redirect:/mostrarrol?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarrol?nodeshabilitado";

        }
    }

    // * DESHABILITAR ACCION --------------------- 🡱🡱🡱🡱🡱🡱🡱🡱
    @GetMapping("/habilitarproducto/{id}")
    public String HabilitarProducto(@PathVariable Long id, Model modelo) {
        try {
            ProductoEntity objCategoria = servicioproducto.findById(id).get();
            servicioproducto.enabled(objCategoria);
            return "redirect:/mostrarproducto?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarproducto?nohabilitado";

        }
    }

    @GetMapping("/eliminarproducto/{id}")
    public String EliminarProdcuto(@PathVariable Long id, Model modelo) {
        try {
            ProductoEntity objCategoria = servicioproducto.findById(id).get();
            servicioproducto.delete(objCategoria);
            return "redirect:/mostrarproducto?eliminado";
        } catch (Exception e) {
            return "redirect:/mostrarproducto?noeliminado";

        }
    }

    @GetMapping("/deshabilitarproducto/{id}")
    public String DeshabilitarProducto(@PathVariable Long id, Model modelo) {
        try {
            ProductoEntity objCategoria = servicioproducto.findById(id).get();
            servicioproducto.delete(objCategoria);
            return "redirect:/mostrarproducto?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarproducto?nodeshabilitado";

        }
    }

    ////////
    @GetMapping("/habilitarcliente/{id}")
    public String HabilitarCliente(@PathVariable Long id, Model modelo) {
        try {
            ClienteEntity objCliente = serviciocliente.findById(id).get();
            serviciocliente.enabled(objCliente);
            return "redirect:/mostrarcliente?habilitado";
        } catch (Exception e) {
            return "redirect:/mostrarcliente?nohabilitado";

        }
    }

    @GetMapping("/eliminarcliente/{id}")
    public String EliminarCliente(@PathVariable Long id, Model modelo) {
        try {
            ClienteEntity objCliente = serviciocliente.findById(id).get();
            serviciocliente.delete(objCliente);
            return "redirect:/mostrarcliente?eliminado";
        } catch (Exception e) {
            return "redirect:/mostrarcliente?noeliminado";
        }

    }

    @GetMapping("/deshabilitarcliente/{id}")
    public String DeshabilitarCliente(@PathVariable Long id, Model modelo) {
        try {
            ClienteEntity objCliente = serviciocliente.findById(id).get();
            serviciocliente.delete(objCliente);
            return "redirect:/mostrarcliente?deshabilitado";
        } catch (Exception e) {
            return "redirect:/mostrarcliente?nodeshabilitado";
        }
    }
}
