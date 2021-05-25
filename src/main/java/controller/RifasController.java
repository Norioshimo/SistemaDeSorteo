package controller;

import controller.login.LoginBean;
import entity.Rifas;
import java.util.List;
import facade.RifasFacade;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "rifasController")
@ViewScoped
public class RifasController extends AbstractController<Rifas> {

    private LoginBean loginBean;
    @Inject
    private RifasFacade rifaFacade;

    public RifasController() {
        // Inform the Abstract parent controller of the concrete Rifas Entity
        super(Rifas.class);
    }

    
    @PostConstruct
    public void init() {
        loginBean=new LoginBean();
    }
    @Override
    public List<Rifas> getItems() {
        if (items == null) {
            if (loginBean.getRolUsuario().equals("V")) {
                items = this.ejbFacade.findAll();
            } else {
                items = rifaFacade.findRifasByIdUsuario(loginBean.getIdUsuario());
            }

        }
        return items;
    }
    
    /*public String ganadorRifa(){
        if(this.getSelected().getSorteoid().getEstado().equals("N")){
            return "PENDIENTE";
        }else if(this.getSelected().getSorteoid().getEstado().equals("C")){
            return "";
        }else{
            if(this.getSelected().getPremiosList()!=null){
                return "GANADOR";
            }else{
                return "SUERTE PARA LA SIGUIENTE";
            }
        }
        
    }*/

}
