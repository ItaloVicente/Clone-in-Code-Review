
package org.eclipse.core.tests.databinding.observable;

import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.observable.value.ValueDiff;

public class DiffsTest extends TestCase {
	public void test_SetDiff() {
		SetDiff diff = new SetDiff() {
			@Override
			public Set<?> getAdditions() {
				return null;
			}

			@Override
			public Set<?> getRemovals() {
				return null;
			}
		};

		try {
			diff.toString();
			assertTrue(true);
		} catch (NullPointerException e) {
			fail("NPE was thrown.");
		}
	}

	public void test_ValueDiff() {
		ValueDiff diff = new ValueDiff() {
			@Override
			public Object getNewValue() {
				return null;
			}

			@Override
			public Object getOldValue() {
				return null;
			}
		};

		try {
			diff.toString();
			assertTrue(true);
		} catch (NullPointerException e) {
			fail("NPE was thrown.");
		}
	}

}
