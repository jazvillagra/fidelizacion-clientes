# Fidelización de clientes - Backend
### Developers:
* Paula Jazmín Villagra Zayas (@jazvillagra)
* Matias Fare Cabañas (@matiasfare)
* Javier Heisecke Echeverría (@JHeisecke)
### Herramientas utilizadas
1. Maven 3.x
2. Java EE (OpenJDK 14.x)
    - EJB3
    - JPA + Hibernate
    - JAX-RS 
3. PostgreSQL (desde 9.x en adelante)
4. Para deploy: Wildfly 18.x
5. IntelliJ & Spring STS para el desarrollo
6. Postman & Insomnia para testing de requests 

### How-to
#### Crear base de datos
Crear la base de datos `fidelizacion` en el Postgres:

```create database fidelizacion owner postgres```

Se tiene un script `scriptcreacionbasededatos.sql` que se debe ejecutar para la creación de las tablas y relaciones.
#### Construir el proyecto
Cuando el proyecto se ejecuta por primera vez es conveniente asegurarse de que las dependencias se instalan correctamente:

```mvn clean install```

Luego, para construir el proyecto solo se necesita ejecutar

```mvn clean package```

#### Levantar el Wildfly
1. Asegurarse de que en el archivo `standalone.xml` se encuentran los datos correctos para la conexón del `datasource`.
2. Limpiar los directorios `standalone/deployments`, `standalone/data` y `standalone/tmp`
3. Copiar el war generado por el `package` realizado en el paso anterior al directorio `standalone/deployments`
4. Ejecutar el script de inicio del wildfly


```sh standalone.sh```

La aplicación se levantará en **http://localhost:8080/fidelizacion**