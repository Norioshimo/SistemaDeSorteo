/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Rifas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class RifasFacade extends AbstractFacade<Rifas> {

    @PersistenceContext(unitName = "sorteo_SorteoWeb_war_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RifasFacade() {
        super(Rifas.class);
    }

    public Integer findLastNroRifaBySorte(Integer id) {
        try {
            return (Integer) em.createQuery("SELECT MAX(r.nrorifa) FROM Rifas r where r.sorteoid.sorteoid=:id").setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rifas> findRifasBySorteo(Integer id) {
        try {
            return em.createQuery("SELECT r FROM Rifas r where r.sorteoid.sorteoid=:id").setParameter("id", id).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rifas> findRifasByIdUsuario(Integer id) {
        try {
            return em.createQuery("SELECT r FROM Rifas r where r.usuarioid.usuarioid=:id").setParameter("id", id).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
