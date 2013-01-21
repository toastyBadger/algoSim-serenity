/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Algorithms;

/**
 *
 * @author Knyazev Alexander <aknyazev@kkb.kz>
 */
public class MergeSort {

    private static void merge(Comparable[] items, Comparable[] helper, int begin, int mid, int end) {
        for (int i = begin; i <= end; i++) {
            helper[i] = items[i];
        }
        int i = begin;
        int j = mid + 1;
        for (int k = begin; k <= end; k++) {
            if (i > mid) {
                items[k] = helper[j++];
            } else if (j > end) {
                items[k] = helper[i++];
            } else if (helper[i].compareTo(helper[j]) < 0) {
                items[k] = helper[i++];
            } else {
                items[k] = helper[j++];
            }
        }

    }

    private static void sort(Comparable[] items, Comparable[] helper, int begin, int end) {
        int mid = (begin + end) / 2;
        if (end - begin > 2) {
            sort(items, helper, begin, mid);
            sort(items, helper, mid + 1, end);
        }
        merge(items, helper, begin, mid, end);

    }

    public static void sort(Comparable[] items) {
        sort(items, new Comparable[items.length], 0, items.length - 1);
    }
}
