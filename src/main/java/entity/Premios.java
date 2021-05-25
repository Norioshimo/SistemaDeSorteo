/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Premios.findAll", query = "SELECT p FROM Premios p")
    , @NamedQuery(name = "Premios.findByPremioid", query = "SELECT p FROM Premios p WHERE p.premioid = :premioid")
    , @NamedQuery(name = "Premios.findByPuesto", query = "SELECT p FROM Premios p WHERE p.puesto = :puesto")
    , @NamedQuery(name = "Premios.findByDescripcion", query = "SELECT p FROM Premios p WHERE p.descripcion = :descripcion")})
public class Premios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer premioid;
    private Integer puesto;
    @Size(max = 200)
    private String descripcion;
    @JoinColumn(name = "rifaid", referencedColumnName = "rifaid")
    @ManyToOne
    private Rifas rifaid;
    @JoinColumn(name = "sorteoid", referencedColumnName = "sorteoid")
    @ManyToOne
    private Sorteos sorteoid;

    public Premios() {
    }

    public Premios(Integer premioid) {
        this.premioid = premioid;
    }

    public Integer getPremioid() {
        return premioid;
    }

    public void setPremioid(Integer premioid) {
        this.premioid = premioid;
    }

    public Integer getPuesto() {
        return puesto;
    }

    public void setPuesto(Integer puesto) {
        this.puesto = puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Rifas getRifaid() {
        return rifaid;
    }

    public void setRifaid(Rifas rifaid) {
        this.rifaid = rifaid;
    }

    public Sorteos getSorteoid() {
        return sorteoid;
    }

    public void setSorteoid(Sorteos sorteoid) {
        this.sorteoid = sorteoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (premioid != null ? premioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Premios)) {
            return false;
        }
        Premios other = (Premios) object;
        if ((this.premioid == null && other.premioid != null) || (this.premioid != null && !this.premioid.equals(other.premioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Premios[ premioid=" + premioid + " ]";
    }
    
}
