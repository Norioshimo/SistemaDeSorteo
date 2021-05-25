package controller;

import entity.Usuarios;
import entity.Rifas;
import java.util.List;
import facade.UsuariosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "usuariosController")
@ViewScoped
public class UsuariosController extends AbstractController<Usuarios> {

    public UsuariosController() {
        // Inform the Abstract parent controller of the concrete Usuarios Entity
        super(Usuarios.class);
    }

}
