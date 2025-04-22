
package org.eclipse.jgit.diff;

import junit.framework.TestCase;

public class MyersDiffPerformanceTest extends TestCase {
	long millis;

	private String[] generateTestData(int characters) {
		StringBuilder ab = new StringBuilder();
		StringBuilder bb = new StringBuilder();
		for (int i = 0
			if (i % 13 < 10) {
				j++;
				ab.append(i % 10);
			}
			if (i % 21 < 19) {
				bb.append(i % 10);
			}
		}
		return (new String[] { ab.toString()
	}

	public void test1000() {
		test(1000);
	}

	public void test5000() {
		test(5000);
	}

	public void test20000() {
		test(20000);
	}

	public void test50000() {
		test(50000);
	}

	public void test80000() {
		test(80000);
	}

	private void test(int characters) {
		String[] testData = generateTestData(characters);
		CharArray ac = toCharArray(testData[0]);
		CharArray bc = toCharArray(testData[1]);
		millis = System.currentTimeMillis();
		new MyersDiff(ac
		millis = System.currentTimeMillis() - millis;
		System.out.println("diffing " + ac.size() + " against " + bc.size()
				+ " bytes took " + millis + " ms. " + (ac.size() / millis)
				+ " bytes/ms");
	}

	private static CharArray toCharArray(String s) {
		return new CharArray(s);
	}

	private static class CharArray implements Sequence {
		char[] array;

		public CharArray(String s) {
			array = s.toCharArray();
		}

		public int size() {
			return array.length;
		}

		public boolean equals(int i
			CharArray o = (CharArray) other;
			return array[i] == o.array[j];
		}
	}
}
