package entity;

import entity.Premios;
import entity.Rifas;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-25T16:35:38")
@StaticMetamodel(Sorteos.class)
public class Sorteos_ { 

    public static volatile SingularAttribute<Sorteos, String> descripcion;
    public static volatile SingularAttribute<Sorteos, Date> fechasorteo;
    public static volatile ListAttribute<Sorteos, Rifas> rifasList;
    public static volatile SingularAttribute<Sorteos, BigDecimal> preciorifa;
    public static volatile SingularAttribute<Sorteos, String> estado;
    public static volatile ListAttribute<Sorteos, Premios> premiosList;
    public static volatile SingularAttribute<Sorteos, Integer> sorteoid;
    public static volatile SingularAttribute<Sorteos, BigDecimal> totalventa;
    public static volatile SingularAttribute<Sorteos, String> comentario;

}