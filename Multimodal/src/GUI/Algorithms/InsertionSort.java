/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Algorithms;

/**
 *
 * @author Knyazev Alexander <aknyazev@kkb.kz>
 */
public class InsertionSort {

    public static void sort(Comparable[] items) {
        for (int i = 1; i < items.length; i++) {
            int j = i;
            while (j > 0 && items[j].compareTo(items[j - 1]) < 0) {
                Comparable temp = items[j - 1];
                items[j - 1] = items[j];
                items[j] = temp;
                j--;
            }
        }
    }
}
