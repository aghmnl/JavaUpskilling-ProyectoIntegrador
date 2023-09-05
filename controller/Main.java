/* By Agus */
package controller;

import utils.Menu;

import static config.TableSQL.createTableCategories;
import static config.TableSQL.createTableExpenses;

public class Main {
    public static void main(String[] args) {
        // Estos dos métodos sólo son necesarios para crear las tablas la primera vez que se ejecuta el programa
        createTableCategories();
        createTableExpenses();

        Menu menu = new Menu();
        menu.optionsMenu();
    }
}