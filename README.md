# Bank Account Management Challenge

Objetivos:
La solución propuesta se basa en la implementación de un sistema compuesto por un backend de servicios construido en Java (17) con Spring Boot 3. Si bien los requerimientos son acordes al objetivo que persigue el challenge, la idea fue la de plantear un diseño simple para resolverlo pero implementando una arquitectura basada en microservicios orientada a eventos, teniendo en cuenta la necesidad de lograr robustez en la solución, alta escalabilidad y una performance aceptable de los servicios.

CQRS y Event Sourcing:
Debido a que posiblemente se espere un gran volumen de usuarios concurrentes realizando transacciones, pero sobre todo consultas dentro del sistema, se decidió implementar CQRS como patrón de arquitectura con Mediator que nos permite separar el modelo de lectura y escritura, lo que permite un esquema de datos más optimizado y poder escalar ambos modelos de manera independiente según las necesidades. También se implementó Event Sourcing que nos da la posibilidad de gestinar los flujos de datos dentro del sistema.

Para simplificar la solución propuesta, las bases de datos del Read Model tanto de Account Service como del Transaction Service son H2 (una base de datos relacional desarrollada en Java). El Event Store está basado en Axon Server para simplificar la implementación de CQRS y el Event Store. En otros casos podríamos haber utilizado Apache Kafka, RabbitMQ o algun Event Bus de los sercvicios Cloud más conocidos, pero la idea era poder completar la propuesta en el menor tiempo posible.

Arquitectura General:

![Arquitectura](https://github.com/cdarenas/bank-account-management/blob/main/images/arquitectura.png)

Proyectos de la solución:

Front-end ReactJS (No completo)

Backend:
  - Core
  - AccountService
  - TransactionService
  - DiscoveryService
  - ApiGateway

Account Service posee los endpoints que permiten dar de alta una cuenta bancaria, consultar el detalle de una cuenta existente, consultar el balance de una cuenta y tambien posee un endpoint que permite enviar mediante Server Sent Events el saldo de la cuenta bancaria al cliente web, cada 3 segundos.

Transaction Service posee los endpoints para crear transacciones para una cuenta bancaria, por ejemplo, realizar extracciones de dinero o depositar dinero. Este microservicio recibe peticiones de transacciones y envia eventos al microservicio Account Service para que previa ejecución de validaciones de negocio, efectué el cambio de estado de la cuenta según el tipo de transacción indicada. Al finalizar, Account Service le notifica a Transaction Service acerca del estado de la operación de manera de realizar los cambios de estados necesarios y completar la transacción.

Aclaración importante del proceso transaccional: Debido a que el éxito de una transacción depende de varios factores y el flujo del proceso implica validaciones y cambios de estados entre los dos microservicios y que ante cualquier falla o evento que pueda implicar inconsistencias de datos como resultado del proceso, se implementó el patrón SAGA que permite controlar transacciones respetando ACID en arquitecturas distribuidas.

Flujo de una transacción de dinero: 

Caso de negocio

Se envía una petición para real¡zar un retiro de dinero, el request llega al Transaction Service, se crea una transacción para el usuario en cuestión en estado CREATED, se almacena en el EventStore y en la base de datos de lectura, se envía un evento a Account Service el cual ejecutará las validaciones de negocio, por ejemplo, validar que la cuenta posea saldo suficiente. Si la cuenta no posee saldo suficiente, se notifica al Transaction Service de esta situación y éste cambia el estado a FAILED, de lo contrario, en Account Service se realiza el descuento del dinero a retirar y se notifica para que la transacción pase a APPROVED. El proceso es gestionado con SAGA, lo que asegura la atomicidad de la misma y evitar inconsistencias posibles durante el proceso.

![Proyectos](https://github.com/cdarenas/bank-account-management/blob/main/images/proyectos.png)

Eureka Service Discovery

![Eureka](https://github.com/cdarenas/bank-account-management/blob/main/images/ServiceDiscovery.png)

Test de endpoints desde Postman:

Creación de cuenta bancaria

![NuevaCuenta](https://github.com/cdarenas/bank-account-management/blob/main/images/CreateAccount.png)

Recupero del detalle de una cuenta existente

![Cuenta](https://github.com/cdarenas/bank-account-management/blob/main/images/GetAccountById.png)

Crear transacción (DEPOSIT o WITHDRAW)

![Transaccion](https://github.com/cdarenas/bank-account-management/blob/main/images/CreateTransaction.png)

Event Store:

![Store](https://github.com/cdarenas/bank-account-management/blob/main/images/AxonEventStore.png)

Consulta a bases de datos de lectura después de realizar una transacción

![Store](https://github.com/cdarenas/bank-account-management/blob/main/images/DBTransaction.png)

![Store](https://github.com/cdarenas/bank-account-management/blob/main/images/DBAccount.png)


Cómo correr la solución:

Requerimientos: Java 17, Maven, Axon Server corriendo en Docker, IDE Java (IntelliJ, Spring Tool Suite, Eclipse, VS Code)

*Todos los proyectos fueron desarrollados para el challenge, probados y están libres de errores de compilación. En las pruebas realizadas no se detectaron bugs.
*H2 Database es una base de datos embebida dentro de las aplicaciones, no se precisa instalar nada especial para poder utilizarlas durante las pruebas.
*El cliente web en ReactJs fue empezado pero por cuestiones de tiempo quedó en progreso.
