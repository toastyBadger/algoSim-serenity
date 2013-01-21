/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Algorithms;

/**
 *
 * @author Knyazev Alexander <aknyazev@kkb.kz>
 */
public class SelectionSort {

    public static void sort(Comparable[] items) {
        for (int i = 0; i < items.length; i++) {
            int minIdx = i;
            for (int j = i; j < items.length; j++) {
                if(items[minIdx].compareTo(items[j])>0) {
                    minIdx = j;
                }
            }
            Comparable temp = items[i];
            items[i] = items[minIdx];
            items[minIdx] = temp;
        }
    }
}
