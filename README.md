

## Deployar(hubicar en el raiz del proyecto)
heroku war:deploy target/SorteoWeb.war  --includes context.xml --app sorteoweb

--copiar el context.xml en el directorio donde ejecuta el script
heroku deploy:war --war "./target/SorteoWeb.war" --includes context.xml


## Abrir el navegador
heroku open --app sorteoweb


## Deployed link
 https://sorteoweb.herokuapp.com/


## Comando de heroku
obtener el nombre de la aplicación: heroku apps
obtener informacion de la postgresql: heroku pg
obtener las configuraciones: heroku config
Agregar control remoto: heroku git:remote -a sorteoweb
ayuda: heroku --help
ver la informacion de conexion: heroku git:remote -a sorteoweb
Informacion de heroku: heroku info



## Anexos
https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html#PostgreSQL
https://stackoverflow.com/questions/37271657/configure-datasource-in-heroku-deployment-with-jndi-lookup






