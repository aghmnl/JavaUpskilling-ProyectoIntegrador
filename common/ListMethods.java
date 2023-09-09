package common;

import java.util.List;

public class ListMethods {
    public static <T> void printList(List<T> list) {
        int i = 1;
        for (T item : list) {
            System.out.println(i + ". " + item);
        i++;
        }
    }
}
