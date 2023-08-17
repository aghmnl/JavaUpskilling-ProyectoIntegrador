/* By Agus */

package utils;

public class ScreenMethods {
    public static void cleanScreen() {
        // The objective of this method is to clean the screen, but is not achieving its objective
        System.out.print("\033[H\033[2J");
        System.out.println("\u001b[2J");
        System.out.flush();
        System.out.println();
    }
}
