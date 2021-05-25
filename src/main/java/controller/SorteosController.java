package controller;

import controller.login.LoginBean;
import controller.util.JsfUtil;
import entity.Premios;
import entity.Rifas;
import entity.Sorteos;
import entity.Usuarios;
import facade.PremiosFacade;
import facade.RifasFacade;
import facade.SorteosFacade;
import facade.UsuariosFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "sorteosController")
@ViewScoped
public class SorteosController extends AbstractController<Sorteos> {

    private LoginBean loginBean;

    @Inject
    private PremiosFacade premioFacade;
    @Inject
    private RifasFacade rifaFacade;
    @Inject
    private SorteosFacade sorteoFacade;
    @Inject
    private UsuariosFacade usuarioFacade;

    private List<Premios> listaPremiosEliminados;
    private List<Usuarios> listaClientes;

    private Premios detallePremio;
    private Rifas rifa;
    private Usuarios cliente;

    private String nroDocumentoAbuscar;
    private Integer conteo;
    private BigDecimal cantidadComprar, total;
    private boolean sorteado;

    public SorteosController() {
        // Inform the Abstract parent controller of the concrete Sorteos Entity
        super(Sorteos.class);
    }

    @PostConstruct
    public void init() {
        conteo = 0;
        listaClientes = usuarioFacade.findUsuarioByTipoUsuario("C");
        loginBean = new LoginBean();
    }

    public boolean isSorteado() {
        return sorteado;
    }

    public void setSorteado(boolean sorteado) {
        this.sorteado = sorteado;
    }

    public String getNroDocumentoAbuscar() {
        return nroDocumentoAbuscar;
    }

    public void setNroDocumentoAbuscar(String nroDocumentoAbuscar) {
        this.nroDocumentoAbuscar = nroDocumentoAbuscar;
    }

    public Usuarios getCliente() {
        return cliente;
    }

    public void setCliente(Usuarios cliente) {
        this.cliente = cliente;
    }

    public List<Usuarios> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Usuarios> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public BigDecimal getCantidadComprar() {
        return cantidadComprar;
    }

    public void setCantidadComprar(BigDecimal cantidadComprar) {
        this.cantidadComprar = cantidadComprar;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Rifas getRifa() {
        return rifa;
    }

    public void setRifa(Rifas rifa) {
        this.rifa = rifa;
    }

    public List<Premios> getListaPremiosEliminados() {
        return listaPremiosEliminados;
    }

    public void setListaPremiosEliminados(List<Premios> listaPremiosEliminados) {
        this.listaPremiosEliminados = listaPremiosEliminados;
    }

    public Premios getDetallePremio() {
        return detallePremio;
    }

    public void setDetallePremio(Premios detallePremio) {
        this.detallePremio = detallePremio;
    }

    public Integer getConteo() {
        return conteo;
    }

    public void setConteo(Integer conteo) {
        this.conteo = conteo;
    }

    @Override
    public Sorteos prepareCreate(ActionEvent event) {

        Sorteos s = super.prepareCreate(event);
        s.setEstado("N");
        s.setTotalventa(BigDecimal.ZERO);
        detallePremio = new Premios();
        listaPremiosEliminados = new ArrayList<Premios>();

        return s;
    }

    public void prepareEdit(ActionEvent event) {
        listaPremiosEliminados = new ArrayList<Premios>();
        detallePremio = new Premios();
        prepareView();
    }

    public void prepareView() {
        this.getSelected().setPremiosList(premioFacade.findPremiosBySorteoId(this.getSelected().getSorteoid()));
    }

    public void prepareSorteo() {
        this.getSelected().setRifasList(rifaFacade.findRifasBySorteo(this.getSelected().getSorteoid()));
        this.getSelected().setPremiosList(premioFacade.findPremiosBySorteoId(this.getSelected().getSorteoid()));
        sorteado = false;
    }

    public void prepareRifa() {
        rifa = new Rifas();

        rifa.setFechacompra(new Date());
        rifa.setPrecio(this.getSelected().getPreciorifa());
        cantidadComprar = BigDecimal.ONE;
        total = rifa.getPrecio();
        nroDocumentoAbuscar = "";
        rifa.setUsuarioid(new Usuarios());
    }

    @Override
    public void save(ActionEvent event) {

        for (Premios p : listaPremiosEliminados) {
            premioFacade.remove(p);
        }

        for (Premios p : this.getSelected().getPremiosList()) {
            if (p.getPremioid() < 0) {
                p.setPremioid(null);
                p.setSorteoid(this.getSelected());
            }
        }
        super.save(event);
    }

    @Override
    public void saveNew(ActionEvent event) {

        for (Premios p : this.getSelected().getPremiosList()) {
            p.setPremioid(null);
            p.setSorteoid(this.getSelected());
        }

        super.saveNew(event);
    }

    public void addPremio() {
        if (this.getSelected().getPremiosList() == null) {
            this.getSelected().setPremiosList(new ArrayList<Premios>());
        }
        boolean puestoExiste = false;
        for (Premios p : this.getSelected().getPremiosList()) {
            if (p.getPuesto().equals(detallePremio.getPuesto())) {
                if (detallePremio.getPremioid() != null) {//si no es nuevo
                    if (!detallePremio.getPremioid().equals(p.getPremioid())) {
                        puestoExiste = true;
                        break;
                    }
                } else {
                    puestoExiste = true;
                    break;
                }
            }
        }
        if (puestoExiste) {
            JsfUtil.addErrorMessage("El puesto número " + detallePremio.getPuesto() + " ya existe.");
            return;
        } else {
            if (detallePremio.getPremioid() == null) {
                detallePremio.setPremioid(--conteo);
            } else {
                this.getSelected().getPremiosList().remove(detallePremio);
            }
        }

        this.getSelected().getPremiosList().add(detallePremio);
        detallePremio = new Premios();

    }

    public void editPremio(Premios item) {
        detallePremio = item;
    }

    public void deletePremio(Premios item) {
        if (item.getPremioid() > 0) {
            listaPremiosEliminados.add(item);
        }
        this.getSelected().getPremiosList().remove(item);
    }

    public void guardarRifa() {
        if (rifa.getUsuarioid().getNrodocumento() == null) {
            JsfUtil.addErrorMessage("Debes seleccionar un cliente.");
            return;
        }

        try {
            Integer nro = rifaFacade.findLastNroRifaBySorte(rifa.getSorteoid().getSorteoid());
            nro = nro == null ? 1 : nro + 1;

            for (int i = 0; i < Integer.parseInt(String.valueOf(cantidadComprar)); i++) {
                rifa.setNrorifa(nro++);
                rifa.setSorteoid(this.getSelected());
                rifaFacade.create(rifa);
            }

            JsfUtil.addSuccessMessage("Rifa/s guardado con éxito");

            this.getSelected().setTotalventa(this.getSelected().getTotalventa().add(rifa.getPrecio().multiply(cantidadComprar)));
            sorteoFacade.edit(this.getSelected());
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al crear la rifa. " + e.getMessage());
        }
    }

    public void calcularMontoAbonar() {
        if (cantidadComprar == null) {
            JsfUtil.addErrorMessage("Ingrese un cantidad valida");
            return;
        }
        this.total = cantidadComprar.multiply(this.getSelected().getPreciorifa());
    }

    public void buscarCliente() {
        if (nroDocumentoAbuscar == null) {
            JsfUtil.addErrorMessage("Ingrese el numero de documento para la busqueda.");
            return;
        }
        Usuarios usu = usuarioFacade.findUsuarioByNroDocumento(nroDocumentoAbuscar);

        if (usu == null) {
            JsfUtil.addErrorMessage("No existe el cliente con el nro de documento " + nroDocumentoAbuscar);
            rifa.setUsuarioid(new Usuarios());
            return;
        } else {
            rifa.setUsuarioid(usu);
        }

    }

    public void clienteSeleccionado() {
        if (cliente != null) {
            rifa.setUsuarioid(cliente);
            nroDocumentoAbuscar = cliente.getNrodocumento();
        }
    }

    public void setearClienteBuscador() {
        cliente = new Usuarios();
        nroDocumentoAbuscar = "";
    }

    public void guardarResultadoSorteos() {
        try {

            this.getSelected().setEstado("F");

            sorteoFacade.edit(this.getSelected());

            JsfUtil.addSuccessMessage("Sorteado guardado y finalizado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Hubo error al sortear. " + e.getMessage());
        }
    }

    public void sortear() {
        try {
            if (this.getSelected().getPremiosList().size() == 0) {
                JsfUtil.addErrorMessage("No hay premios para el sorteo. Se rechaza el sorteo");
                return;
            }

            if (this.getSelected().getRifasList().size() <= this.getSelected().getPremiosList().size()) {
                JsfUtil.addErrorMessage("Debe haber más rifas vendidas que cantidad de premios. Se rechaza el sorteo");
                return;
            }

            System.out.println("Comenzar el sorteo.");
            for (int i = 0; i < this.getSelected().getPremiosList().size(); i++) {
                //valor aletaorio entre 1 y cantidad de rifas.
                int seleccionarRifaAleatorio = (int) Math.floor(Math.random() * this.getSelected().getRifasList().size());

                this.getSelected().getPremiosList().get(i)
                        .setRifaid(this.getSelected().getRifasList().get(seleccionarRifaAleatorio));
                System.out.println("" + (i + 1) + ")- Puesto:" + this.getSelected().getPremiosList().get(i).getPuesto()
                        + ", Premio:" + this.getSelected().getPremiosList().get(i).getDescripcion()
                        + ", Ganador:" + this.getSelected().getPremiosList().get(i).getRifaid().getUsuarioid().getNombrecompleto());
            }

            JsfUtil.addSuccessMessage("Sorteado con éxito. Felicidades a los beneficiarios.");
            sorteado = true;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Hubo error al sortear. " + e.getMessage());
        }

    }
}
