/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rifas.findAll", query = "SELECT r FROM Rifas r")
    , @NamedQuery(name = "Rifas.findByRifaid", query = "SELECT r FROM Rifas r WHERE r.rifaid = :rifaid")
    , @NamedQuery(name = "Rifas.findByNrorifa", query = "SELECT r FROM Rifas r WHERE r.nrorifa = :nrorifa")
    , @NamedQuery(name = "Rifas.findByPrecio", query = "SELECT r FROM Rifas r WHERE r.precio = :precio")
    , @NamedQuery(name = "Rifas.findByFechacompra", query = "SELECT r FROM Rifas r WHERE r.fechacompra = :fechacompra")})
public class Rifas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer rifaid;
    private Integer nrorifa;
    private BigDecimal precio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacompra;
    @JoinColumn(name = "sorteoid", referencedColumnName = "sorteoid")
    @ManyToOne
    private Sorteos sorteoid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "usuarioid")
    @ManyToOne
    private Usuarios usuarioid;
    @OneToMany(mappedBy = "rifaid")
    private List<Premios> premiosList;

    public Rifas() {
    }

    public Rifas(Integer rifaid) {
        this.rifaid = rifaid;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getRifaid() {
        return rifaid;
    }

    public void setRifaid(Integer rifaid) {
        this.rifaid = rifaid;
    }

    public Integer getNrorifa() {
        return nrorifa;
    }

    public void setNrorifa(Integer nrorifa) {
        this.nrorifa = nrorifa;
    }

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public Sorteos getSorteoid() {
        return sorteoid;
    }

    public void setSorteoid(Sorteos sorteoid) {
        this.sorteoid = sorteoid;
    }

    public Usuarios getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuarios usuarioid) {
        this.usuarioid = usuarioid;
    }

    @XmlTransient
    public List<Premios> getPremiosList() {
        return premiosList;
    }

    public void setPremiosList(List<Premios> premiosList) {
        this.premiosList = premiosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifaid != null ? rifaid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rifas)) {
            return false;
        }
        Rifas other = (Rifas) object;
        if ((this.rifaid == null && other.rifaid != null) || (this.rifaid != null && !this.rifaid.equals(other.rifaid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rifas[ rifaid=" + rifaid + " ]";
    }

}
