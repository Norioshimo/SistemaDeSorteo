package entity;

import entity.Premios;
import entity.Sorteos;
import entity.Usuarios;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-25T16:35:38")
@StaticMetamodel(Rifas.class)
public class Rifas_ { 

    public static volatile SingularAttribute<Rifas, Integer> rifaid;
    public static volatile SingularAttribute<Rifas, BigDecimal> precio;
    public static volatile ListAttribute<Rifas, Premios> premiosList;
    public static volatile SingularAttribute<Rifas, Sorteos> sorteoid;
    public static volatile SingularAttribute<Rifas, Integer> nrorifa;
    public static volatile SingularAttribute<Rifas, Usuarios> usuarioid;
    public static volatile SingularAttribute<Rifas, Date> fechacompra;

}