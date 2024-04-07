# API Creacion de usuarios

## Resumen
Aplicación que expone una API RESTful de creación de usuarios.
Todos los endpoints deben aceptar y retornar solamente JSON, inclusive al para los mensajes de error.
Todos los mensajes deben seguir el formato:
{"mensaje": "mensaje de error"}

## Caracteristicas Claves
- **Profile Management**: Creación de Usuaio.
- **API Documentation**: Swagger UI based documentation.

## Stack Technologico
- **Spring Boot**
- **HSQLDB Database**
- **Hibernate**
- **Swagger UI**

## Para comenzar
### Requisitos
- JDK (Version 17+)
- Maven
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Instalación
1. **Clonar el Repositorio**
```git clone https://github.com/palopinformatico/ms_usuario```
2. **Correr la Aplicación**
```mvn spring-boot:run```


## Para Testear la Aplicación
- **API Client**: Testear endpoints usando Postman or herramientas similares.

## Uso
- **Registrar Usuario**: `/api/v1/creaUsuario
    ```json 
	{
		"name": "Juan Rodriguez",
		"email": "juan@rodriguez.org",
		"password": "hunter2",
		"phones": [
			{
				"number": "1234567",
				"citycode": "1",
				"contrycode": "57"
			}
		]
	}
  ```

## Documentacion
Diagramas de flujo y secuencia así como enunciado y otros documentos se encuentran en directorio documentacion.
