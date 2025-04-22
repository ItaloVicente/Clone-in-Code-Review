
package org.eclipse.core.tests.databinding.observable;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.DisposeEvent;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IStaleListener;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.StaleEvent;
import org.eclipse.jface.databinding.conformance.ObservableContractTest;
import org.eclipse.jface.databinding.conformance.ObservableStaleContractTest;
import org.eclipse.jface.databinding.conformance.delegate.AbstractObservableContractDelegate;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.conformance.util.StaleEventTracker;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class AbstractObservableTest extends AbstractDefaultRealmTestCase {
	private ObservableStub observable;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		observable = new ObservableStub(Realm.getDefault());
	}

	public void testStaleListener() throws Exception {
		assertFalse(observable.hasListeners());

		StaleEventTracker listener1 = new StaleEventTracker();

		assertFalse(observable.firstListenerAdded);
		observable.addStaleListener(listener1);
		assertTrue(observable.firstListenerAdded);
		observable.firstListenerAdded = false; // reset

		assertTrue(observable.hasListeners());
		assertEquals(0, listener1.count);

		observable.fireStale();

		assertEquals(1, listener1.count);
		assertSame(observable, listener1.event.getObservable());

		StaleEventTracker listener2 = new StaleEventTracker();
		assertEquals(0, listener2.count);
		observable.addStaleListener(listener2);
		observable.fireStale();

		assertEquals(2, listener1.count);
		assertEquals(1, listener2.count);

		StaleEventTracker listener3 = new StaleEventTracker();
		observable.addStaleListener(listener3);
		assertEquals(0, listener3.count);

		observable.fireStale();

		assertEquals(3, listener1.count);
		assertEquals(2, listener2.count);
		assertEquals(1, listener3.count);

		assertFalse(observable.lastListenerRemoved);
		observable.removeStaleListener(listener1);
		observable.removeStaleListener(listener2);
		observable.removeStaleListener(listener3);
		assertTrue(observable.lastListenerRemoved);

		assertFalse(observable.hasListeners());
	}

	public void testChangeListener() throws Exception {
		assertFalse(observable.hasListeners());

		ChangeEventTracker listener1 = new ChangeEventTracker();

		assertFalse(observable.firstListenerAdded);
		observable.addChangeListener(listener1);
		assertTrue(observable.firstListenerAdded);
		observable.firstListenerAdded = false;

		assertTrue(observable.hasListeners());
		assertEquals(0, listener1.count);

		observable.fireChange();

		assertEquals(1, listener1.count);
		assertSame(observable, listener1.event.getSource());

		ChangeEventTracker listener2 = new ChangeEventTracker();
		observable.addChangeListener(listener2);
		assertEquals(0, listener2.count);

		observable.fireChange();
		assertEquals(2, listener1.count);
		assertEquals(1, listener2.count);

		ChangeEventTracker listener3 = new ChangeEventTracker();
		observable.addChangeListener(listener3);
		assertEquals(0, listener3.count);

		observable.fireChange();

		assertEquals(3, listener1.count);
		assertEquals(2, listener2.count);
		assertEquals(1, listener3.count);

		assertFalse(observable.lastListenerRemoved);
		observable.removeChangeListener(listener1);
		observable.removeChangeListener(listener2);
		observable.removeChangeListener(listener3);
		assertTrue(observable.lastListenerRemoved);

		assertFalse(observable.hasListeners());
	}

	public void testHasListenersWithChangeAndStaleListeners() throws Exception {
		ChangeEventTracker changeListener = new ChangeEventTracker();
		StaleEventTracker staleListener = new StaleEventTracker();

		assertFalse(observable.hasListeners());
		assertFalse(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.addChangeListener(changeListener);
		assertTrue(observable.hasListeners());
		assertTrue(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.firstListenerAdded = false;
		observable.lastListenerRemoved = false;

		observable.addStaleListener(staleListener);
		assertTrue(observable.hasListeners());
		assertFalse(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.removeChangeListener(changeListener);
		assertTrue(observable.hasListeners());
		assertFalse(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.removeStaleListener(staleListener);
		assertFalse(observable.hasListeners());
		assertFalse(observable.firstListenerAdded);
		assertTrue(observable.lastListenerRemoved);
	}

	public void testFireStaleRealmChecks() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));

		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable = new ObservableStub();
				observable.fireStale();
			}
		});
	}

	public void testFireChangeRealmChecks() throws Exception {
		RealmTester.setDefault(new CurrentRealm(true));

		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable = new ObservableStub();
				observable.fireChange();
			}
		});
	}

	public void testAddDisposeListener_HasListenersFalse() {
		IDisposeListener disposeListener = new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent staleEvent) {
			}
		};
		IStaleListener staleListener = new IStaleListener() {
			@Override
			public void handleStale(StaleEvent staleEvent) {
			}
		};

		assertFalse(observable.hasListeners());

		observable.addDisposeListener(disposeListener);
		assertFalse(observable.hasListeners());
		assertFalse(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.addStaleListener(staleListener);
		assertTrue(observable.hasListeners());
		assertTrue(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.removeDisposeListener(disposeListener);
		assertTrue(observable.hasListeners());
		assertTrue(observable.firstListenerAdded);
		assertFalse(observable.lastListenerRemoved);

		observable.removeStaleListener(staleListener);
		assertFalse(observable.hasListeners());
		assertTrue(observable.firstListenerAdded);
		assertTrue(observable.lastListenerRemoved);
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(AbstractObservableTest.class.getName());
		suite.addTestSuite(AbstractObservableTest.class);
		Delegate delegate = new Delegate();
		suite.addTest(ObservableContractTest.suite(delegate));
		suite.addTest(ObservableStaleContractTest.suite(delegate));
		return suite;
	}

			AbstractObservableContractDelegate {

		@Override
		public void change(IObservable observable) {
			((ObservableStub) observable).fireChange();
		}

		@Override
		public void setStale(IObservable observable, boolean stale) {
			((ObservableStub) observable).setStale(stale);
		}

		@Override
		public IObservable createObservable(Realm realm) {
			return new ObservableStub(realm);
		}
	}

	private static class ObservableStub extends AbstractObservable {
		private boolean stale;

		public ObservableStub() {
			this(Realm.getDefault());
		}

		public ObservableStub(Realm realm) {
			super(realm);
		}

		private boolean firstListenerAdded;

		private boolean lastListenerRemoved;

		@Override
		protected void fireStale() {
			super.fireStale();
		}

		@Override
		protected void fireChange() {
			super.fireChange();
		}

		@Override
		public boolean isStale() {
			getterCalled();
			return stale;
		}

		private void getterCalled() {
			ObservableTracker.getterCalled(this);
		}

		public void setStale(boolean stale) {
			boolean old = this.stale;
			this.stale = stale;

			if (stale && !old) {
				fireStale();
			}
		}

		@Override
		protected boolean hasListeners() {
			return super.hasListeners();
		}

		@Override
		protected void firstListenerAdded() {
			firstListenerAdded = true;
		}

		@Override
		protected void lastListenerRemoved() {
			lastListenerRemoved = true;
		}
	}
}
