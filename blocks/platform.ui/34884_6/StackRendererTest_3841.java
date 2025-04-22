
package org.eclipse.e4.ui.tests.workbench;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.e4.ui.internal.workbench.TopologicalSort;

public class TopoSortTests extends TestCase {
	enum Type {
		REQUIREMENTS, DEPENDENCIES, BOTH
	};

	static class TestSorter extends TopologicalSort<Integer, Integer> {
		Type type = Type.REQUIREMENTS;

		public Integer[] getTestData() {
			return new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		}

		@Override
		protected Collection<Integer> getRequirements(Integer value) {
			if (type == Type.DEPENDENCIES) {
				return Collections.emptyList();
			}

			switch (value) {
			case 1:
				return Collections.emptyList();
			case 2:
			case 3:
			case 5:
			case 7:
				return Collections.singleton(1);
			case 4:
				return Collections.singleton(2);
			case 6:
				return Arrays.asList(2, 3);
			case 8:
				return Collections.singleton(4);
			case 9:
				return Collections.singleton(3);
			case 10:
				return Arrays.asList(2, 5);
			default:
				throw new IllegalArgumentException(value.toString());
			}
		}

		@Override
		protected Collection<Integer> getDependencies(Integer value) {
			if (type == Type.REQUIREMENTS) {
				return Collections.emptyList();
			}
			switch (value) {
			case 1:
				return Arrays.asList(2, 3, 5, 7);
			case 2:
				return Arrays.asList(4, 6, 10);
			case 3:
				return Arrays.asList(6, 9);
			case 4:
				return Arrays.asList(8);
			case 5:
				return Arrays.asList(10);
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return Collections.emptyList();
			default:
				throw new IllegalArgumentException(value.toString());
			}
		}

		@Override
		protected Integer getId(Integer o) {
			return o;
		}
	}

	public void testTopoSorter() {
		TestSorter ts = new TestSorter();
		for (Type type : Type.values()) {
			ts.type = type;

			List<Integer> results = Arrays.asList(ts.sort(ts.getTestData()));
			assertTrue(results.indexOf(1) < results.indexOf(2));
			assertTrue(results.indexOf(1) < results.indexOf(3));
			assertTrue(results.indexOf(1) < results.indexOf(5));
			assertTrue(results.indexOf(1) < results.indexOf(7));
			assertTrue(results.indexOf(2) < results.indexOf(4));
			assertTrue(results.indexOf(2) < results.indexOf(6));
			assertTrue(results.indexOf(2) < results.indexOf(10));
			assertTrue(results.indexOf(3) < results.indexOf(6));
			assertTrue(results.indexOf(3) < results.indexOf(9));
			assertTrue(results.indexOf(4) < results.indexOf(8));
			assertTrue(results.indexOf(5) < results.indexOf(10));
		}
	}

	static class CycleTestSorter extends TopologicalSort<String, String> {
		Type type = Type.REQUIREMENTS;

		public String[] getTestData() {
			return new String[] { "A", "B", "C", "D" };
		}

		@Override
		protected String getId(String o) {
			return o;
		}

		@Override
		protected Collection<String> getRequirements(String id) {
			if (type == Type.DEPENDENCIES) {
				return null;
			}

			if (id.equals("A")) {
				return Collections.singleton("B");
			} else if (id.equals("B")) {
				return Collections.singleton("C");
			} else if (id.equals("C")) {
				return Collections.singleton("A");
			} else if (id.equals("D")) {
				return Collections.singleton("A");
			}
			throw new IllegalArgumentException(id);
		}

		@Override
		protected Collection<String> getDependencies(String id) {
			if (type == Type.REQUIREMENTS) {
				return null;
			}

			if (id.equals("A")) {
				return Arrays.asList("C", "D");
			} else if (id.equals("B")) {
				return Collections.singleton("A");
			} else if (id.equals("C")) {
				return Collections.singleton("B");
			} else if (id.equals("D")) {
				return null;
			}
			throw new IllegalArgumentException(id);
		}
	}

	public void testCycles() {
		CycleTestSorter ts = new CycleTestSorter();
		for (Type type : Type.values()) {
			ts.type = type;

			List<String> results = Arrays.asList(ts.sort(ts.getTestData()));
			assertTrue(results.indexOf("A") < results.indexOf("D"));
			assertTrue(results.indexOf("B") < results.indexOf("D"));
			assertTrue(results.indexOf("C") < results.indexOf("D"));
			assertTrue(results.indexOf("A") < results.indexOf("D"));
		}
	}

}
