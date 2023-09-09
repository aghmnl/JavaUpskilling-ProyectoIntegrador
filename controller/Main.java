/* By Agus */
package controller;

import utils.Menu;

import static config.TableSQL.*;


public class Main {
    public static void main(String[] args) {
        // Estos dos métodos sólo son necesarios para crear las tablas la primera vez que se ejecuta el programa
        // Por lo que se comprueba primero si existen las tablas correspondientes.
        if(!tableExists("categorias")) createTableCategories();
        if(!tableExists("gastos")) createTableExpenses();

        Menu menu = new Menu();
        menu.optionsMenu();
    }
}