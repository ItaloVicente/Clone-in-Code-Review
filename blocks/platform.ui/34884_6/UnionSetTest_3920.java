
package org.eclipse.core.tests.databinding.observable.set;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.ObservableSet;
import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.jface.databinding.conformance.ObservableCollectionContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;

public class ObservableSetTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(ObservableSetTest.class.getName());
		suite.addTest(ObservableCollectionContractTest.suite(new Delegate()));
		return suite;
	}
	
	private static class Delegate extends AbstractObservableCollectionContractDelegate {	
		private Delegate() {	
		}
		
		@Override
		public void change(IObservable observable) {
			((ObservableSetStub) observable).fireSetChange(Diffs.createSetDiff(new HashSet(), new HashSet()));
		}
		
		@Override
		public Object createElement(IObservableCollection collection) {
			return Integer.toString(collection.size());
		}
	
		@Override
		public Object getElementType(IObservableCollection collection) {
			return String.class;
		}

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			IObservableSet set = new ObservableSetStub(realm, new HashSet(), String.class);
			
			for (int i = 0; i < elementCount; i++) {
				set.add(Integer.toString(i));
			}
			
			return set;
		}
	}

	private static class ObservableSetStub extends ObservableSet {
		protected ObservableSetStub(Realm realm, Set wrappedSet, Object elementType) {
			super(realm, wrappedSet, elementType);
		}

		@Override
		public void fireSetChange(SetDiff diff) {
			super.fireSetChange(diff);
		}
	}
}
