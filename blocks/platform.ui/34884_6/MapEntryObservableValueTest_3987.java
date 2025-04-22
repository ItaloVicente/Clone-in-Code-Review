
package org.eclipse.core.tests.internal.databinding.observable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.identity.IdentityObservableSet;
import org.eclipse.jface.databinding.conformance.MutableObservableSetContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;

public class IdentityObservableSetTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(IdentityObservableSetTest.class
				.getName());
		suite.addTest(MutableObservableSetContractTest.suite(new Delegate()));
		return suite;
	}

	private static class Delegate extends
			AbstractObservableCollectionContractDelegate {

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			IdentityObservableSet set = new IdentityObservableSet(realm,
					Object.class);
			for (int i = 0; i < elementCount; i++)
				set.add(createElement(set));
			return set;
		}

		@Override
		public Object createElement(IObservableCollection collection) {
			return new Object();
		}

		@Override
		public void change(IObservable observable) {
			IObservableSet set = (IObservableSet) observable;
			set.add(createElement(set));
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return Object.class;
		}
	}
}
