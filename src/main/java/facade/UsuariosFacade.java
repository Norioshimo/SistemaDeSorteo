package facade;

import entity.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    //@PersistenceContext(unitName = "sorteo_SorteoWebTomEE_war_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /*System.out.println("Obtener el entity manager");
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("sorteo_SorteoWebTomEE_war_1PU");
        em = emf.createEntityManager();
        System.out.println("Crear EntityManager");*/

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("sorteo_SorteoWebTomEE_war_1PU");
        em = emf.createEntityManager();
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    public Usuarios findUsuarioByNroDocumento(String nroDocumento) {
        try {
            return (Usuarios) getEntityManager().createQuery("SELECT u FROM Usuarios u where u.nrodocumento=:nroDocumento").setParameter("nroDocumento", nroDocumento).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuarios> findUsuarioByTipoUsuario(String tipousuario) {
        try {
            return getEntityManager().createQuery("SELECT u FROM Usuarios u where u.tipousuario=:tipousuario").setParameter("tipousuario", tipousuario).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuarios findByUsuario(String user) {
        System.out.println("Buscar por usr");
        Query q = getEntityManager().createQuery("select u from Usuarios u where u.nrodocumento=:nrodocumento");
        q.setParameter("nrodocumento", user);
        System.out.println("Resultado de usuario");
        if (q.getResultList().isEmpty() != true) {
            return (Usuarios) q.getResultList().get(0);
        } else {
            return null;
        }
    }

}
