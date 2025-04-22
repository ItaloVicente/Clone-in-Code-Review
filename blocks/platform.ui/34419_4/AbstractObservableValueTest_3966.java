package org.eclipse.core.tests.databinding.observable.set;

import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.jface.databinding.conformance.MutableObservableSetContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class WritableSetTest extends AbstractDefaultRealmTestCase {
	public void testWithElementType() throws Exception {
		Object elementType = String.class;
		WritableSet set = WritableSet.withElementType(elementType);
		assertNotNull(set);
		assertEquals(Realm.getDefault(), set.getRealm());
		assertEquals(elementType, set.getElementType());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(WritableSetTest.class.getName());
		suite.addTestSuite(WritableSetTest.class);
		suite.addTest(MutableObservableSetContractTest.suite(new Delegate()));
		return suite;
	}

	private static class Delegate extends
			AbstractObservableCollectionContractDelegate {
		private Delegate() {
			super();
		}

		@Override
		public void change(IObservable observable) {
			IObservableSet set = (IObservableSet) observable;
			set.add(createElement(set));
		}

		@Override
		public Object createElement(IObservableCollection collection) {
			return new Object();
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return String.class;
		}

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			IObservableSet set = new WritableSet(realm, Collections.EMPTY_SET,
					String.class);
			for (int i = 0; i < elementCount; i++) {
				set.add(createElement(set));
			}

			return set;
		}
	}
}
