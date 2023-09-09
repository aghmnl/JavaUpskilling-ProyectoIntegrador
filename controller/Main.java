/* By Agus */
package controller;

import utils.Menu;

import static config.TableSQL.*;


public class Main {
    public static void main(String[] args) {
        // Estos dos métodos sólo son necesarios para crear las tablas la primera vez que se ejecuta el programa
        // Por lo que se comprueba primero si existen las tablas correspondientes.
        // ATENCIÓN: el nombre de las tablas debe ser puesto en mayúsculas por requerimiento de SQL
        if(tableDoesNotExist("CATEGORIAS")) createTableCategories();
        if(tableDoesNotExist("GASTOS")) createTableExpenses();

        // En este caso no uso método static porque dentro del mismo no tengo tampoco métodos static, que es requerimiento
        Menu menu = new Menu();
        menu.optionsMenu();
    }
}