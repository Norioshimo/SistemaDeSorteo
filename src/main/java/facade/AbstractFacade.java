/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

/**
 *
 * @author HP
 */
public abstract class AbstractFacade<T> implements Serializable{

    protected Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        System.out.println("FindAll:"+entityClass);
        List<T> lista = getEntityManager().createQuery(cq).getResultList();
        System.out.println(lista);
        return lista;
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<T> findRange(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(entityRoot);
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        if (sortField != null && sortField.length() > 0) {
            javax.persistence.criteria.Path<Object> pkFieldPath = null;
            if (sortField.contains(".")) {
                String[] campos = sortField.split("\\.");
                for (String camp : campos) {// se recorre los campos hasta obtener el path de la clase hoja
                    pkFieldPath = pkFieldPath != null ? pkFieldPath.get(camp) : entityRoot.get(camp);
                }
            } else {
                pkFieldPath = entityRoot.get(sortField);
            }
            if (pkFieldPath != null) {
                if (sortOrder.startsWith("ASC")) {;
                    cq.orderBy(cb.asc(pkFieldPath));
                }
                if (sortOrder.startsWith("DESC")) {
                    cq.orderBy(cb.desc(pkFieldPath));
                }
            }

        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public List<T> findRange(int first, int pageSize, Map<String, String> sortFields, Map<String, FilterMeta> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(entityRoot);
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        if (sortFields != null && !sortFields.isEmpty()) {
            for (String sortField : sortFields.keySet()) {
                if (entityRoot.get(sortField) != null) {
                    String sortOrder = sortFields.get(sortField);
                    if (sortOrder.startsWith("ASC")) {
                        cq.orderBy(cb.asc(entityRoot.get(sortField)));
                    }
                    if (sortOrder.startsWith("DESC")) {
                        cq.orderBy(cb.desc(entityRoot.get(sortField)));
                    }
                }
            }
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(pageSize);
        q.setFirstResult(first);
        return q.getResultList();
    }

    public int count(Map<String, FilterMeta> filters) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery cq = cb.createQuery();
        javax.persistence.criteria.Root<T> entityRoot = cq.from(entityClass);
        cq.select(cb.count(entityRoot));
        List<javax.persistence.criteria.Predicate> predicates = getPredicates(cb, entityRoot, filters);
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
        }
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected List<Predicate> getPredicates(CriteriaBuilder cb, Root<T> entityRoot, Map<String, FilterMeta> filters) {
        javax.persistence.metamodel.Metamodel entityModel = this.getEntityManager().getMetamodel();
        javax.persistence.metamodel.ManagedType<T> entityType = entityModel.managedType(entityClass);
        java.util.Set<javax.persistence.metamodel.EmbeddableType<?>> embeddables = entityModel.getEmbeddables();
        String fieldTypeName = null;

        List<javax.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();

        for (String s : filters.keySet()) {
            FilterMeta fm = filters.get(s);
            MatchMode m = filters.get(s).getFilterMatchMode();

            if (fm.getFilterValue() == null) {
                //System.out.println("Nombre Key:" + s + " ==No Entra como la condicion de filtro");
                continue;
            }

            javax.persistence.criteria.Path<Object> pkFieldPath = null;
            if (s.contains(".")) {
                String[] campos = s.split("\\.");
                Class<?> clase = entityClass;
                for (String camp : campos) { // se recorre los campos hasta obtener la clase hoja
                    try {
                        pkFieldPath = pkFieldPath != null ? pkFieldPath.get(camp) : entityRoot.get(camp);
                        clase = clase.getDeclaredField(camp).getType();
                    } catch (NoSuchFieldException | SecurityException ex) {
                        Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                fieldTypeName = clase.getName();
            } else {
                pkFieldPath = entityRoot.get(s);
                fieldTypeName = entityType.getAttribute(s).getJavaType().getName();
            }
            System.out.println("FilterField:" + fm.getFilterField() + " ==Value:" + fm.getFilterValue() + " ==MatchModeName:" + m.getName() + " ==pkFieldPath:" + pkFieldPath + " ==fieldTypeName" + fieldTypeName);
            if (pkFieldPath != null && fieldTypeName != null) {
                if (fieldTypeName.contains("String")) {
                    filterPredicates(predicates, cb, pkFieldPath, fm, m);
                } else if (fieldTypeName.contains("java.util.Date")) {
                    DateFormat hourdateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String[] a = filters.get(s).getFilterValue().toString().split("-");
                    Date d = new Date(a[0] + "/" + a[1] + "/" + a[2]);
                    predicates.add(cb.equal((javax.persistence.criteria.Expression<?>) pkFieldPath, hourdateFormat.format(d)));
                } else {
                    String v = filters.get(s).getFilterValue().toString();
                    v = v.replace(" ", "");
                    javax.persistence.criteria.Expression<?> filterExpression = getCastExpression(v, fieldTypeName, cb);
                    if (filterExpression != null) {
                        predicates.add(cb.equal((javax.persistence.criteria.Expression<?>) pkFieldPath, filterExpression));
                    } else {
                        filterPredicates(predicates, cb, pkFieldPath, fm, m);
                    }
                }
            }
        }

        return predicates;
    }

    protected Expression<?> getCastExpression(String searchValue, String typeName, CriteriaBuilder cb) {
        //System.out.println("getCastExpression(String searchValue, String typeName, CriteriaBuilder cb) Abstract");
        javax.persistence.criteria.Expression<?> expression = null;
        //System.out.println("typeName : " + typeName);
        switch (typeName) {
            case "short":
                expression = cb.literal(Short.parseShort(searchValue));
                break;
            case "byte":
                expression = cb.literal(Byte.parseByte(searchValue));
                break;
            case "int":
            case "java.lang.Integer":
                expression = cb.literal(Integer.parseInt(searchValue));
                break;
            case "long":
                expression = cb.literal(Long.parseLong(searchValue));
                break;
            case "float":
                expression = cb.literal(Float.parseFloat(searchValue));
                break;
            case "double":
            case "java.math.BigDecimal":
                expression = cb.literal(Double.parseDouble(searchValue));
                break;
            case "boolean":
                expression = cb.literal(Boolean.parseBoolean(searchValue));
                break;
            default:
                break;
        }
        return expression;
    }

    public T getMergedEntity(T entity) {
        if (isEntityManaged(entity)) {
            return entity;
        } else {
            return getEntityManager().merge(entity);
        }
    }

    public boolean isEntityManaged(T entity) {
        return getEntityManager().contains(entity);
    }

    public T findWithParents(T entity) {
        return entity;
    }

    protected void filterPredicates(List<javax.persistence.criteria.Predicate> predicates, CriteriaBuilder cb,
            javax.persistence.criteria.Path<Object> pkFieldPath, FilterMeta fm, MatchMode m) {
        if (m.CONTAINS.getName().equals(m.getName())) {
            predicates.add(cb.like(cb.lower((javax.persistence.criteria.Expression) pkFieldPath), "%" + fm.getFilterValue().toString().toLowerCase() + "%"));
        } else if (m.STARTS_WITH.getName().equals(m.getName())) {
            predicates.add(cb.like(cb.lower((javax.persistence.criteria.Expression) pkFieldPath), fm.getFilterValue().toString().toLowerCase() + "%"));
        } else if (m.ENDS_WITH.getName().equals(m.getName())) {
            predicates.add(cb.like(cb.lower((javax.persistence.criteria.Expression) pkFieldPath), "%" + fm.getFilterValue().toString().toLowerCase()));
        } else {
            predicates.add(cb.like(cb.lower((javax.persistence.criteria.Expression) pkFieldPath), fm.getFilterValue().toString().toLowerCase()));
        }
    }
}
