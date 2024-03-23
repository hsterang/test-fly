# TEST FLY PASS

## Requisitos de Ejecución de la Aplicación

Para ejecutar esta aplicación correctamente, asegúrate de cumplir con los siguientes requisitos de configuración previa.

### Requisitos del Sistema

- **Java 17**: Asegúrate de tener instalado Java Development Kit (JDK) versión 17 en tu sistema. Puedes descargar e instalar Java 17 desde [AdoptOpenJDK](https://adoptopenjdk.net/) u otros proveedores de JDK.

- **PostgreSQL**: Necesitarás tener un servidor PostgreSQL configurado y en funcionamiento. Puedes descargar e instalar PostgreSQL desde el [sitio oficial de PostgreSQL](https://www.postgresql.org/download/) o utilizar un servicio de base de datos en la nube compatible.

### Configuración de la Base de Datos

Puedes configurar la base de datos a traves de este comando de docker en tu entorno local

```shell
docker run --name mi-postgres -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```

Esta API utiliza autenticación basada en API keys para proteger sus endpoints. Sigue las instrucciones a continuación para autenticarte correctamente.

Autenticación mediante API Key
Para autenticarte en esta API, necesitas incluir tu API key en la cabecera de tus solicitudes HTTP. La clave de API se proporciona como un valor en el encabezado X-API-KEY.

Configuración de la API Key
Antes de comenzar a hacer solicitudes a la API, debes configurar tu API key.

Para obtener una Clave de API

Ponte en contacto con el administrador del sistema para obtener una clave de API válida para tu uso o si es una demo puedes usar test-hamil.
