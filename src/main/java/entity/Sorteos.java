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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HP
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sorteos.findAll", query = "SELECT s FROM Sorteos s")
    , @NamedQuery(name = "Sorteos.findBySorteoid", query = "SELECT s FROM Sorteos s WHERE s.sorteoid = :sorteoid")
    , @NamedQuery(name = "Sorteos.findByDescripcion", query = "SELECT s FROM Sorteos s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "Sorteos.findByFechasorteo", query = "SELECT s FROM Sorteos s WHERE s.fechasorteo = :fechasorteo")
    , @NamedQuery(name = "Sorteos.findByEstado", query = "SELECT s FROM Sorteos s WHERE s.estado = :estado")
    , @NamedQuery(name = "Sorteos.findByComentario", query = "SELECT s FROM Sorteos s WHERE s.comentario = :comentario")})
public class Sorteos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer sorteoid;
    @Size(max = 200)
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechasorteo;
    @Size(max = 1)
    private String estado;
    @Size(max = 255)
    private String comentario;
    @OneToMany(mappedBy = "sorteoid",cascade = CascadeType.ALL)
    private List<Rifas> rifasList;
    @OneToMany(mappedBy = "sorteoid",cascade = CascadeType.ALL)
    private List<Premios> premiosList;

    @Column(name = "totalventa")
    private BigDecimal totalventa;

    @Column(name = "preciorifa")
    private BigDecimal preciorifa;

    public Sorteos() {
    }

    public Sorteos(Integer sorteoid) {
        this.sorteoid = sorteoid;
    }

    public BigDecimal getTotalventa() {
        return totalventa;
    }

    public void setTotalventa(BigDecimal totalventa) {
        this.totalventa = totalventa;
    }

    public BigDecimal getPreciorifa() {
        return preciorifa;
    }

    public void setPreciorifa(BigDecimal preciorifa) {
        this.preciorifa = preciorifa;
    }

    public Integer getSorteoid() {
        return sorteoid;
    }

    public void setSorteoid(Integer sorteoid) {
        this.sorteoid = sorteoid;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechasorteo() {
        return fechasorteo;
    }

    public void setFechasorteo(Date fechasorteo) {
        this.fechasorteo = fechasorteo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @XmlTransient
    public List<Rifas> getRifasList() {
        return rifasList;
    }

    public void setRifasList(List<Rifas> rifasList) {
        this.rifasList = rifasList;
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
        hash += (sorteoid != null ? sorteoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sorteos)) {
            return false;
        }
        Sorteos other = (Sorteos) object;
        if ((this.sorteoid == null && other.sorteoid != null) || (this.sorteoid != null && !this.sorteoid.equals(other.sorteoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sorteos[ sorteoid=" + sorteoid + " ]";
    }

}
