package org.eclipse.ui.tests.harness.util;

import java.util.Random;

public class ArrayUtil {
    private static Random randomBox = new Random();

    public static Object pickRandom(Object[] array) {
        int num = randomBox.nextInt(array.length);
        return array[num];
    }

    public static boolean checkNotNull(Object[] array) {
		if (array == null)
			return false;
		for (int i = 0; i < array.length; i++)
			if (array[i] == null)
				return false;
		return true;
    }

    public static boolean contains(Object[] array, Object element) {
		if (array == null || element == null)
			return false;
		for (int i = 0; i < array.length; i++)
			if (array[i] == element)
				return true;
		return false;
    }

    public static boolean equals(Object[] one, Object[] two) {
		if (one.length != two.length)
			return false;
		for (int i = 0; i < one.length; i++)
			if (one[i] != two[i])
				return false;
		return true;
    }
}
