
package org.eclipse.core.tests.internal.databinding.observable;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.observable.ConstantObservableValue;
import org.eclipse.jface.databinding.conformance.ObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ConstantObservableValueTest extends AbstractDefaultRealmTestCase {
	public void testConstructor_NullRealm() {
		try {
			new ConstantObservableValue(null, null, null);
			fail("Constructor should throw an exception when null realm is passed in");
		} catch (RuntimeException expected) {
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("ConstantValueTest");
		suite.addTestSuite(ConstantObservableValueTest.class);
		suite.addTest(UnchangeableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
	}

	private static class Delegate extends
			AbstractObservableValueContractDelegate {
		@Override
		public IObservableValue createObservableValue(Realm realm) {
			return new ConstantObservableValue(realm, new Object(),
					Object.class);
		}

		@Override
		public Object getValueType(IObservableValue observable) {
			return Object.class;
		}
	}

	public static class UnchangeableObservableValueContractTest extends
			ObservableValueContractTest {
		public UnchangeableObservableValueContractTest(String name,
				IObservableValueContractDelegate delegate) {
			super(name, delegate);
		}

		@Override
		public void testChange_OrderOfNotifications() {
		}

		@Override
		public void testChange_ValueChangeEvent() {
		}

		@Override
		public void testChange_ValueChangeEventDiff() {
		}

		@Override
		public void testChange_ValueChangeEventFiredAfterValueIsSet() {
		}

		@Override
		public void testRemoveValueChangeListener_RemovesListener()
				throws Exception {
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
		public void testIsStale_RealmChecks() {
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

		public static Test suite(IObservableValueContractDelegate delegate) {
			return new SuiteBuilder().addObservableContractTest(
					UnchangeableObservableValueContractTest.class, delegate)
					.build();
		}
	}
}
