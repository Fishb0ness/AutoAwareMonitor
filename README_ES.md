# AutoAwareMonitor

AutoAwareMonitor es una aplicación de monitoreo de vehículos desarrollada con Spring Boot, MongoDB y Gradle. La aplicación se basa en una combinación de Diseño Dirigido por el Dominio (DDD) y Arquitectura Hexagonal (HEX).

## Arquitectura

Este proyecto sigue una combinación de Diseño Dirigido por el Dominio (DDD) y Arquitectura Hexagonal (HEX).

DDD es un enfoque para el desarrollo de software que centra el desarrollo en la programación de un modelo de dominio que tiene un conocimiento profundo de los procesos y reglas de un dominio. Este enfoque se utiliza típicamente para sistemas complejos donde el dominio en sí es complejo.

La Arquitectura Hexagonal, también conocida como Puertos y Adaptadores, es un patrón de diseño utilizado en el desarrollo de aplicaciones de software. Su objetivo es crear componentes de aplicación con un acoplamiento suelto que puedan conectarse fácilmente a su entorno de software mediante puertos y adaptadores. Esto hace que los componentes del sistema sean intercambiables en cualquier nivel y facilita la automatización de pruebas.

## Requisitos

- Java 17
- Gradle
- Docker
- MongoDB

## Configuración

Para configurar el proyecto, sigue estos pasos:

1. Clona el repositorio en tu máquina local.
2. Navega hasta el directorio del proyecto.
3. Ejecuta `gradle build` para compilar el proyecto.

## Ejecución de la base de datos

Para ejecutar la base de datos MongoDB, necesitarás Docker y Docker Compose instalados en tu máquina. Una vez que los tengas instalados, puedes iniciar la base de datos con el siguiente comando:

```bash
docker-compose -f compose.yaml up
```

## Ejecución

Para ejecutar el proyecto, sigue estos pasos:

1. Asegúrate de que MongoDB está en ejecución.
2. Ejecuta `gradle bootRun` para iniciar la aplicación.

## Pruebas

Para ejecutar las pruebas, utiliza el comando `gradle test`.

## Problemas conocidos

### Error al levantar la base de datos para las pruebas

En algunas ocasiones, al intentar levantar la base de datos MongoDB con Docker, puede aparecer el siguiente error: org.testcontainers.containers.MongoDBContainer$ReplicaSetInitializationException

Este error indica que la base de datos no pudo inicializarse correctamente en el tiempo esperado. Esto puede hacer que los tests aparezcan como fallidos.

#### Solución

Si te encuentras con este error, puedes intentar las siguientes soluciones:

1. Asegúrate de que Docker está funcionando correctamente en tu máquina.
2. Intenta aumentar el tiempo de espera en tu configuración de Testcontainers.
3. Reinicia Docker y vuelve a intentar ejecutar los tests.

## Contribuir

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir lo que te gustaría cambiar.