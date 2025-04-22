
package org.eclipse.core.tests.databinding.observable;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ObservableList;
import org.eclipse.core.internal.databinding.observable.UnmodifiableObservableList;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ObservablesTest extends AbstractDefaultRealmTestCase {
	public void testUnmodifableObservableListExceptions() throws Exception {
		try {
			Observables.unmodifiableObservableList(null);
			fail("IllegalArgumentException should have been thrown.");
		} catch (IllegalArgumentException e) {
		}
	}

	public void testUnmodifiableObservableList() throws Exception {
		IObservableList unmodifiable = Observables.unmodifiableObservableList(new ObservableListStub(
				new ArrayList<Object>(0), String.class));
		assertTrue(unmodifiable instanceof UnmodifiableObservableList);
	}

	private static class ObservableListStub extends ObservableList {
		protected ObservableListStub(List<Object> wrappedList, Object elementType) {
			super(wrappedList, elementType);
		}
	}
}
