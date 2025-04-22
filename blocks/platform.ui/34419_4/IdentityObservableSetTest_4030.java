
package org.eclipse.core.tests.internal.databinding.observable;

import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.internal.databinding.observable.EmptyObservableSet;
import org.eclipse.jface.databinding.conformance.ObservableCollectionContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.delegate.IObservableCollectionContractDelegate;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EmptyObservableSetTest {
	public static Test suite() {
		TestSuite suite = new TestSuite(EmptyObservableSetTest.class.getName());
		suite.addTest(ImmutableObservableSetContractTest.suite(new Delegate()));
		return suite;
	}

	public static class ImmutableObservableSetContractTest extends
			ObservableCollectionContractTest {
		public static Test suite(IObservableCollectionContractDelegate delegate) {
			return new SuiteBuilder().addObservableContractTest(
					ImmutableObservableSetContractTest.class, delegate).build();
		}

		public ImmutableObservableSetContractTest(
				IObservableCollectionContractDelegate delegate) {
			super(delegate);
		}

		public ImmutableObservableSetContractTest(String name,
				IObservableCollectionContractDelegate delegate) {
			super(name, delegate);
		}

		@Override
		public void testChange_ChangeEvent() {
		}

		@Override
		public void testChange_EventObservable() {
		}

		@Override
		public void testChange_ObservableRealmIsTheCurrentRealm() {
		}

		@Override
		public void testChange_RealmCheck() {
		}

		@Override
		public void testRemoveChangeListener_RemovesListener() {
		}

		@Override
		public void testContains_GetterCalled() {
		}

		@Override
		public void testContainsAll_GetterCalled() {
		}

		@Override
		public void testEquals_GetterCalled() {
		}

		@Override
		public void testHashCode_GetterCalled() {
		}

		@Override
		public void testIsEmpty_GetterCalled() {
		}

		@Override
		public void testIterator_GetterCalled() {
		}

		@Override
		public void testSize_GetterCalled() {
		}

		@Override
		public void testToArray_GetterCalled() {
		}

		@Override
		public void testToArrayWithObjectArray_GetterCalled() {
		}

		@Override
		public void testIsStale_GetterCalled() throws Exception {
		}

		@Override
		public void testIsDisposed() throws Exception {
		}

		@Override
		public void testAddDisposeListener_HandleDisposeInvoked() {
		}
	}

	private static class Delegate extends
			AbstractObservableCollectionContractDelegate {
		private Object elementType = new Object();

		@Override
		public IObservableCollection createObservableCollection(Realm realm,
				int elementCount) {
			return new EmptyObservableSet(realm, elementType);
		}

		@Override
		public Object getElementType(IObservableCollection collection) {
			return elementType;
		}
	}
}
