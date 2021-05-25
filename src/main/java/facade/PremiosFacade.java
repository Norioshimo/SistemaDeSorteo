/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Premios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author HP
 */
@Stateless
public class PremiosFacade extends AbstractFacade<Premios> {

    @PersistenceContext(unitName = "sorteo_SorteoWeb_war_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PremiosFacade() {
        super(Premios.class);
    }

    public List<Premios> findPremiosBySorteoId(Integer id) {
        return em.createQuery("SELECT s FROM Premios s where s.sorteoid.sorteoid=:id").setParameter("id", id).getResultList();
    }
}
