
package org.eclipse.jface.tests.internal.databinding.viewers;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.conformance.MutableObservableSetContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;
import org.eclipse.jface.internal.databinding.viewers.ObservableViewerElementSet;
import org.eclipse.jface.viewers.IElementComparer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ObservableViewerElementSetTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(ObservableViewerElementSetTest.class.getName());
		suite.addTest(MutableObservableSetContractTest.suite(new Delegate()));
		return suite;
	}

	private static class Delegate extends
			AbstractObservableCollectionContractDelegate {

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			ObservableViewerElementSet set = new ObservableViewerElementSet(realm,
					Object.class, new IdentityElementComparer());
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

	private static class IdentityElementComparer implements IElementComparer {
		@Override
		public boolean equals(Object a, Object b) {
			return a == b;
		}

		@Override
		public int hashCode(Object element) {
			return System.identityHashCode(element);
		}
	}
}
