
package org.eclipse.jface.databinding.conformance;

import junit.framework.TestCase;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.conformance.delegate.IObservableContractDelegate;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;

public class ObservableDelegateTest extends TestCase {
	private IObservableContractDelegate delegate;

	private Realm previousRealm;

	private IObservable observable;
	private String debugInfo;

	public ObservableDelegateTest(IObservableContractDelegate delegate) {
		this(null, delegate);
	}

	public ObservableDelegateTest(String testName,
			IObservableContractDelegate delegate) {
		super(testName);
		this.delegate = delegate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		previousRealm = Realm.getDefault();

		delegate.setUp();
		observable = doCreateObservable();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		delegate.tearDown();
		observable.dispose();
		observable = null;

		RealmTester.setDefault(previousRealm);

		observable = null;
		previousRealm = null;
	}

	protected IObservable doCreateObservable() {
		return delegate.createObservable(new CurrentRealm(true));
	}

	protected IObservable getObservable() {
		return observable;
	}

	protected IObservableContractDelegate getObservableContractDelegate() {
		return delegate;
	}

	protected String formatFail(String message) {
		return message + getDebugString();
	}

	private String getDebugString() {
		if (debugInfo == null) {
			debugInfo = "(Test: " + this.getClass().getName() + ", Delegate: "
					+ delegate.getClass().getName() + ")";
		}

		return debugInfo;
	}

	protected void assertGetterCalled(Runnable runnable, String methodName,
			IObservable observable) {
		IObservable[] observables = ObservableTracker.runAndMonitor(runnable,
				null, null);

		int count = 0;
		for (int i = 0; i < observables.length; i++) {
			if (observables[i] == observable) {
				count++;
			}
		}

		assertEquals(formatFail(methodName
				+ " should invoke ObservableTracker.getterCalled() once."), 1,
				count);
	}
}
