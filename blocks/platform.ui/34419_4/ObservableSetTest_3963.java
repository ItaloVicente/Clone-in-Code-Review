
package org.eclipse.core.tests.databinding.observable.set;

import java.util.Collections;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.DecoratingObservableSet;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.jface.databinding.conformance.MutableObservableCollectionContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;

public class DecoratingObservableSetTest {
	public static Test suite() {
		TestSuite suite = new TestSuite(DecoratingObservableSetTest.class
				.getName());
		suite.addTest(MutableObservableCollectionContractTest
				.suite(new Delegate()));
		return suite;
	}

	static class Delegate extends AbstractObservableCollectionContractDelegate {
		private Object elementType = Object.class;

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			IObservableSet wrappedSet = new WritableSet(realm,
					Collections.EMPTY_SET, elementType);
			for (int i = 0; i < elementCount; i++)
				wrappedSet.add(createElement(wrappedSet));
			return new DecoratingObservableSetStub(wrappedSet);
		}

		@Override
		public Object createElement(IObservableCollection collection) {
			return new Object();
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return elementType;
		}

		@Override
		public void change(IObservable observable) {
			DecoratingObservableSetStub set = (DecoratingObservableSetStub) observable;
			set.wrappedSet.add(createElement(set));
		}
	}

	static class DecoratingObservableSetStub extends DecoratingObservableSet {
		IObservableSet wrappedSet;

		DecoratingObservableSetStub(IObservableSet wrappedSet) {
			super(wrappedSet, true);
			this.wrappedSet = wrappedSet;
		}
	}
}
