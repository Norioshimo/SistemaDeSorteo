package controller;

import facade.AbstractFacade;
import facade.LazyEntityDataModel;
import controller.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import java.util.ResourceBundle;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public abstract class AbstractController<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    protected AbstractFacade<T> ejbFacade;
    protected Class<T> itemClass;
    protected T selected;
    protected List<T> items;
    protected LazyEntityDataModel<T> lazyItems;
    protected List<T> filteredItems;

    protected enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    public AbstractController() {
    }

    public AbstractController(Class<T> itemClass) {
        this.itemClass = itemClass;
    }

    protected AbstractFacade<T> getFacade() {
        return ejbFacade;
    }

    protected void setChildrenEmptyFlags() {
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        if (selected != null) {
            if (this.selected == null || !this.selected.equals(selected)) {
                this.selected = this.ejbFacade.findWithParents(selected);
                this.setChildrenEmptyFlags();
            }
        } else {
            this.selected = null;
        }
    }

    protected void setEmbeddableKeys() {
        // Nothing to do if entity does not have any embeddable key.
    }

    ;

    protected void initializeEmbeddableKey() {
        // Nothing to do if entity does not have any embeddable key.
    }

    public Collection<T> getItems() {
        System.out.println("getItems");
        if (items == null) {
            System.out.println("Intes es null");
            items = this.ejbFacade.findAll();
        }else{
            System.out.println(items);
        }
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public LazyEntityDataModel<T> getLazyItems() {
        if (lazyItems == null) {
            lazyItems = new LazyEntityDataModel<>(this.ejbFacade);
        }
        return lazyItems;
    }

    public void setLazyItems(LazyEntityDataModel<T> lazyItems) {
        this.lazyItems = lazyItems;
    }

    public void setLazyItems(Collection<T> items) {
        if (items instanceof List) {
            lazyItems = new LazyEntityDataModel<>((List<T>) items);
        } else {
            lazyItems = new LazyEntityDataModel<>(new ArrayList<>(items));
        }
    }

    public List<T> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<T> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public void save(ActionEvent event) {
        String msg = ResourceBundle.getBundle("/MyBundle").getString(itemClass.getSimpleName() + "Updated");
        persist(PersistAction.UPDATE, msg);

        if (!isValidationFailed()) {

            // Update the existing entity inside the item list
            List<T> itemList = refreshItem(this.selected, this.items);
            // If the original list has changed (it is a new object)
            if (this.items != itemList) {
                this.setItems(itemList);
            }

            // Also refresh the filteredItems list in case the user has filtered the DataTable
            if (filteredItems != null) {
                refreshItem(this.selected, this.filteredItems);
            }

        }

    }

    public void saveNew(ActionEvent event) {
        String msg = ResourceBundle.getBundle("/MyBundle").getString(itemClass.getSimpleName() + "Created");
        persist(PersistAction.CREATE, msg);
        if (!isValidationFailed()) {
            items = null; // Invalidate list of items to trigger re-query.
            lazyItems = null; // Invalidate list of lazy items to trigger re-query.
        }
    }

    public void delete(ActionEvent event) {
        String msg = ResourceBundle.getBundle("/MyBundle").getString(itemClass.getSimpleName() + "Deleted");
        persist(PersistAction.DELETE, msg);
        if (!isValidationFailed()) {
            selected = null; // Remove selection
            items = null; // Invalidate list of items to trigger re-query.
            lazyItems = null; // Invalidate list of lazy items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            this.setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    this.ejbFacade.edit(selected);
                } else {
                    this.ejbFacade.remove(selected);
                }
                this.setChildrenEmptyFlags();
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                Throwable cause = JsfUtil.getRootCause(ex.getCause());
                if (cause != null) {
                    if (cause instanceof ConstraintViolationException) {
                        ConstraintViolationException excp = (ConstraintViolationException) cause;
                        for (ConstraintViolation s : excp.getConstraintViolations()) {
                            JsfUtil.addErrorMessage(s.getMessage());
                        }
                    } else {
                        String msg = cause.getLocalizedMessage();
                        if (msg.length() > 0) {
                            JsfUtil.addErrorMessage(msg);
                        } else {
                            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/MyBundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public T prepareCreate(ActionEvent event) {
        T newItem;
        try {
            newItem = itemClass.newInstance();
            this.selected = newItem;
            initializeEmbeddableKey();
            return newItem;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isValidationFailed() {
        return JsfUtil.isValidationFailed();
    }

    public String getComponentMessages(String clientComponent, String defaultMessage) {
        return JsfUtil.getComponentMessages(clientComponent, defaultMessage);
    }

    private List<T> refreshItem(T item, Collection<T> items) {
        List<T> itemList;
        if (this.items instanceof List) {
            itemList = (List<T>) items;
        } else {
            itemList = new ArrayList<>(items);
        }
        int i = itemList.indexOf(item);
        if (i >= 0) {
            try {
                itemList.set(i, item);
            } catch (UnsupportedOperationException ex) {
                return refreshItem(item, new ArrayList<>(items));
            }
        }
        return itemList;
    }
}
