package org.eclipse.jface.tests.viewers;

import java.util.Comparator;

public class TestComparator implements Comparator {

    public volatile int comparisons = 0;

    @Override
	public int compare(Object arg0, Object arg1) {
        comparisons++;

        return (arg0.toString()).compareTo(arg1.toString());
    }
}
