package controller.util;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Norio
 */
public class SessionUtils {

    public static void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public static Object getSessionParameter(String key) {
        return SessionUtils.getHttpRequest().getSession().getAttribute(key);
    }

    public static void addSessionParameter(String key, Object value) {
        SessionUtils.getHttpRequest().getSession().setAttribute(key, value);
    }

    public static String getContextPath() {
        return SessionUtils.getHttpRequest().getContextPath();
    }

    public static HttpServletRequest getHttpRequest() {
        try {
            return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        } catch (Exception ex) {
        }
        return null;
    }

    public static void invalidarSesion() {
        SessionUtils.getHttpRequest().getSession().invalidate();
    }

    public static String getSesionId() {
        String idSesion = "";

        try {
            idSesion = SessionUtils.getHttpRequest().getSession().getId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return idSesion;
    }
}
