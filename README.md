# MICROSERVICE TRANSACTION
Microservicio que registra las transacciones del cliente

## INSTALACION
Este microservicio es parte de una infraestructura de microservicios y requiere la ejecucion de otro app para su funcionamiento

Primero se debe descargar los siguiente repositorios
1. https://github.com/JhojanGuiller/BankApiGateway
2. https://github.com/JhojanGuiller/ConfigServer
3. https://github.com/JhojanGuiller/EurekaBankService
4. https://github.com/JhojanGuiller/BankAccountService
4. Actual repositorio

Ejecutarlos en el mismo orden que se descarga

## ESTRUCTURA DEL PRODUCTO
{
  "idTransaccion": Integer,
  "idCuenta": Integer,
  "tipoTransaccion": String, //Deposito o Retiro
  "montoTransaccion": Double,
  "fechaTransaccion": String
}

## RUTAS
Revisar el repositorio https://github.com/JhojanGuiller/BankApiGateway
