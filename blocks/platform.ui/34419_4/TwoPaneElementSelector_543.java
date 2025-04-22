package org.eclipse.ui.dialogs;

import java.util.Comparator;

import org.eclipse.core.runtime.Assert;


    private Comparator fComparator;

    public static final class StringComparator implements Comparator {
        private boolean fIgnoreCase;

        StringComparator(boolean ignoreCase) {
            fIgnoreCase = ignoreCase;
        }

        @Override
		public int compare(Object left, Object right) {
            return fIgnoreCase ? ((String) left)
                    .compareToIgnoreCase((String) right) : ((String) left)
                    .compareTo((String) right);
        }
    }

    public TwoArrayQuickSorter(boolean ignoreCase) {
        fComparator = new StringComparator(ignoreCase);
    }

    public TwoArrayQuickSorter(Comparator comparator) {
        fComparator = comparator;
    }

    public void sort(Object[] keys, Object[] values) {
        if ((keys == null) || (values == null)) {
            Assert.isTrue(false, "Either keys or values in null"); //$NON-NLS-1$
            return;
        }

        if (keys.length <= 1) {
			return;
		}

        internalSort(keys, values, 0, keys.length - 1);
    }

    private void internalSort(Object[] keys, Object[] values, int left,
            int right) {
        int original_left = left;
        int original_right = right;

        Object mid = keys[(left + right) / 2];
        do {
            while (fComparator.compare(keys[left], mid) < 0) {
				left++;
			}

            while (fComparator.compare(mid, keys[right]) < 0) {
				right--;
			}

            if (left <= right) {
                swap(keys, left, right);
                swap(values, left, right);
                left++;
                right--;
            }
        } while (left <= right);

        if (original_left < right) {
			internalSort(keys, values, original_left, right);
		}

        if (left < original_right) {
			internalSort(keys, values, left, original_right);
		}
    }

    private static final void swap(Object x[], int a, int b) {
        Object t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

}
