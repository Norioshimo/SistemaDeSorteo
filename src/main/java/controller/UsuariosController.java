package controller;

import entity.Premios;
import entity.Usuarios;
import entity.Rifas;
import java.util.List;
import facade.UsuariosFacade;
import java.util.ArrayList;
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
        if (!"".equals(clave) && clave!=null) {
            this.getSelected().setClave(clave);
        }

        super.save(event);
    }
}
