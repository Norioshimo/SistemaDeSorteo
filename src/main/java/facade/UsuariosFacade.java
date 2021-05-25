/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "sorteo_SorteoWeb_war_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    public Usuarios findUsuarioByNroDocumento(String nroDocumento) {
        try {
            return (Usuarios) em.createQuery("SELECT u FROM Usuarios u where u.nrodocumento=:nroDocumento").setParameter("nroDocumento", nroDocumento).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Usuarios> findUsuarioByTipoUsuario(String tipousuario) {
        try {
            return em.createQuery("SELECT u FROM Usuarios u where u.tipousuario=:tipousuario").setParameter("tipousuario", tipousuario).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public Usuarios findByUsuario(String user) {
        Query q = getEntityManager().createNamedQuery("Usuarios.findByNrodocumento");
        q.setParameter("nrodocumento", user);
        if (q.getResultList().isEmpty() != true) {
            return (Usuarios) q.getResultList().get(0);
        } else {
            return null;
        }
    }

}
