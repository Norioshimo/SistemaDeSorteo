package entity;

import entity.Rifas;
import entity.Sorteos;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-25T16:35:38")
@StaticMetamodel(Premios.class)
public class Premios_ { 

    public static volatile SingularAttribute<Premios, String> descripcion;
    public static volatile SingularAttribute<Premios, Integer> puesto;
    public static volatile SingularAttribute<Premios, Rifas> rifaid;
    public static volatile SingularAttribute<Premios, Sorteos> sorteoid;
    public static volatile SingularAttribute<Premios, Integer> premioid;

}