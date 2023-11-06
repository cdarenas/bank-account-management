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

Transaction Service posee los endpoints para crear transacciones para una cuenta bancaria, por ejemplo, realizar extracciones de dinero o depositar dinero. Este microservicio recibe peticiones de transacciones y envia eventos al microservicio Account Service para que previa ejecución de validaciones de negocio, efectué el cambio de estado de la cuenta según el tipo de transacción indicada. Al finalizar, Account Service le notifica a Transaction Service acerca del estado de la operación.

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
