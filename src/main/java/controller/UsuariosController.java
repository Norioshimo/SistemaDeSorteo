package controller;

import entity.Usuarios;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "usuariosController")
@ViewScoped
public class UsuariosController extends AbstractController<Usuarios> {

    private String clave;

    public UsuariosController() {
        // Inform the Abstract parent controller of the concrete Usuarios Entity
        super(Usuarios.class);
        System.out.println("Usuario controller inicializado en eon contructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Usuario controller inicializado");
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void prepareEdit(ActionEvent event) {
        clave = "";
    }

    @Override
    public void save(ActionEvent event) {
        if (!"".equals(clave) && clave != null) {
            this.getSelected().setClave(clave);
        }

        super.save(event);
    }
}
