package controller.login;

import controller.util.JsfUtil;
import controller.util.SessionUtils;
import entity.Usuarios;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import facade.UsuariosFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private UsuariosFacade usuarioFacade;

    private String pass, user;
    private boolean logeado = false;

    @PreDestroy
    public void cerrarSersion() {
        System.out.println("sesión destruida y cerrada(loginBean)");
    }

    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("Iniciar Sesion(loginBean)");
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getIdUsuario() {
        Integer idUser = Integer.parseInt(SessionUtils.getSessionParameter("idUser").toString());;
        if (SessionUtils.getSessionParameter("idUser") != null) {
            idUser = Integer.parseInt(SessionUtils.getSessionParameter("idUser").toString());
        }
        return idUser;
    }

    public String getUsuario() {
        String usuario = SessionUtils.getSessionParameter("usuario").toString();
        if (SessionUtils.getSessionParameter("usuario") != null) {
            usuario = SessionUtils.getSessionParameter("usuario").toString();
        }
        return usuario;
    }

    public String getRolUsuario() {
        String usuario = SessionUtils.getSessionParameter("rolUsuario").toString();
        if (SessionUtils.getSessionParameter("rolUsuario") != null) {
            usuario = SessionUtils.getSessionParameter("rolUsuario").toString();
        }
        return usuario;
    }

    public String login(ActionEvent event) throws IOException {
        FacesMessage message = null;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        Usuarios usuario = null;
        logeado = false;
        try {
            usuario = usuarioFacade.findByUsuario(user);
        } catch (Exception e) {
            System.out.println(e);
            usuario = null;
        }
        if (usuario != null) {
            if (!usuario.getEstadi().toString().equalsIgnoreCase("A")) {
                JsfUtil.addErrorMessage("Usuario Inactivo");
            } else if (usuario.getClave().compareTo(pass) == 0) {
                logeado = true;
                JsfUtil.addSuccessMessage("Bienvenido " + usuario.getNombrecompleto());
            } else {
                System.out.println("El usuario no puede inicar sesión. Credencial invalida.");
            }
            usuarioFacade.edit(usuario);
        } else {
            JsfUtil.addErrorMessage("No existe el usuario "+user);
            user="";
        }
        

        if (logeado) {

            SessionUtils.addSessionParameter("idUser", usuario.getUsuarioid());
            SessionUtils.addSessionParameter("usuario", usuario.getNrodocumento());
            SessionUtils.addSessionParameter("username", usuario.getNombrecompleto());
            SessionUtils.addSessionParameter("estaLogueado", logeado);
            SessionUtils.addSessionParameter("rolUsuario", usuario.getTipousuario());

            String pagina = SessionUtils.getContextPath() + "/index.xhtml";

            try {
                SessionUtils.redirect(pagina);
            } catch (IOException ex) {
                System.out.println(ex);
                //LOGEO LOG ANTERIOR
                //           Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public boolean estaLogeado() {
        return logeado;
    }

    public void logout() {
        //Se elimina todos los objetos asociados a la sesion
        SessionUtils.invalidarSesion();
        logeado = false;
        user = "";

        try {
            SessionUtils.redirect(SessionUtils.getContextPath() + "/login.xhtml");
            System.out.println("Sesión cerrada con EXITO!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String isLoggedInForwardHome() {
        if (logeado) {
            try {
                SessionUtils.redirect(SessionUtils.getContextPath() + "/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        return null;
    }

    public void verificarInicioSesion() throws IOException {
        System.out.println("verificarInicioSesion!!");
        //Se Busca el Usuario en sesion , si este no se ha logueado se redirecciona al inicio de sesion
        if (SessionUtils.getSessionParameter("idUser") == null) {
            System.out.println("verificarInicioSesion redireccionar a login");
            //Si no hay Usuario logueado se redirecciona a la pagina de Login
            String url = SessionUtils.getContextPath() + "/login.xhtml";
            SessionUtils.redirect(url);
        }
    }

}
