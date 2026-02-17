# Sistema de Finanzas Personales - API REST - SPRING BOOT

Backend para mi sistema personal de finanzas:

### Caracteristicas del sistema

Administracion de usuarios por medio de permisos y perfiles
Login con JWT
Adminstracion de Gastos e ingresos | Consultas, Altas, Actualizacion y Eliminacion
Administracion de Ahorros | Consultas, Altas, Actualizacion y Eliminacion | Abonos por ahorros
Administracion de categorias de gastos e ingresos
Graficas comparativas

### Recursos:
* IntelliJ IDEA (Community Edition)  
* Plugin de Lombok para IntelliJ IDEA
* Postman
* Navicat
* Git

### Tecnologias
* Java 17
* Spring boot | maven | JPA | JWT | postgresql | jackson | lombok
* Postgres 16

### Instalacion
* Clonar repositorio
* Crear archivo src/main/java/resources/application.properties con las siguientes variables:

spring.application.name=nombre_sistema
spring.datasource.url=***
spring.datasource.username=****
spring.datasource.password=****
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

y Configurar valores para la conexion a base de datos
