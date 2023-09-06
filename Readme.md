# Upskilling Java - Henry

Este es el proyecto integrador del curso de **Upskilling de Java** dictado por [HENRY](http://www.soyhenry.com)

### Especificaciones
Para el mismo se usó [Intelli IDEA](https://www.jetbrains.com/idea/download/?section=mac) como IDE, azulk17 como versión de JDK y [H2 Version 2.1.214](https://www.h2database.com/html/download.html) como base de datos.

La librería de H2 se incluye en el repositorio. Se debe agregar yendo a File > Project Structure > Modules > Dependencies > agregar

### Funcionalidad del proyecto
El proyecto consta de un menú principal para gestionar gastos y categorías:
1. Ingresar un gasto.
2. Ver, editar o eliminar un gasto.
3. Gestionar categorías.
4. Ver gastos por rubro.
5. Ver gastos por periodo.
6. Salir de la aplicación.

Los gastos constan de:
* monto
* descripción
* categoría
* fecha

### Precarga de datos

#### Gastos
Se precargan los siguientes gastos a modo de ejemplo cada vez que se ejecuta la aplicación:
1. $10.0 frutas [Supermercado] 10-09-2023
2. $54.0 cena [Restaurant] 23-07-2023
3. $78.0 limpieza [Auto] 15-02-2023
4. $25.0 luz [Casa] 06-05-2023

#### Categorías
Se precargan las siguientes categorías a modo de ejemplo cada vez que se ejecuta la aplicación:
1. Auto
2. Casa
3. Supermercado
4. Restaurant
5. Otra
