/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByUsuarioid", query = "SELECT u FROM Usuarios u WHERE u.usuarioid = :usuarioid")
    , @NamedQuery(name = "Usuarios.findByNrodocumento", query = "SELECT u FROM Usuarios u WHERE u.nrodocumento = :nrodocumento")
    , @NamedQuery(name = "Usuarios.findByNombrecompleto", query = "SELECT u FROM Usuarios u WHERE u.nombrecompleto = :nombrecompleto")
    , @NamedQuery(name = "Usuarios.findByClave", query = "SELECT u FROM Usuarios u WHERE u.clave = :clave")
    , @NamedQuery(name = "Usuarios.findByCelular", query = "SELECT u FROM Usuarios u WHERE u.celular = :celular")
    , @NamedQuery(name = "Usuarios.findByEstadi", query = "SELECT u FROM Usuarios u WHERE u.estadi = :estadi")
    , @NamedQuery(name = "Usuarios.findByTipousuario", query = "SELECT u FROM Usuarios u WHERE u.tipousuario = :tipousuario")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer usuarioid;
    @Size(max = 10)
    private String nrodocumento;
    @Size(max = 100)
    private String nombrecompleto;
    @Size(max = 255)
    private String clave;
    @Size(max = 20)
    private String celular;
    @Size(max = 1)
    private String estadi;
    @Size(max = 1)
    private String tipousuario;
    @OneToMany(mappedBy = "usuarioid")
    private List<Rifas> rifasList;

    public Usuarios() {
    }

    public Usuarios(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Integer getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEstadi() {
        return estadi;
    }

    public void setEstadi(String estadi) {
        this.estadi = estadi;
    }

    public String getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(String tipousuario) {
        this.tipousuario = tipousuario;
    }

    @XmlTransient
    public List<Rifas> getRifasList() {
        return rifasList;
    }

    public void setRifasList(List<Rifas> rifasList) {
        this.rifasList = rifasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioid != null ? usuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuarioid == null && other.usuarioid != null) || (this.usuarioid != null && !this.usuarioid.equals(other.usuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarios[ usuarioid=" + usuarioid + " ]";
    }
    
}
