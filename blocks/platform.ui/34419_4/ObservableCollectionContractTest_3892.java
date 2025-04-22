
package org.eclipse.jface.databinding.conformance;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.ChangeEventTracker;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;

public class MutableObservableValueContractTest extends ObservableDelegateTest {
	private IObservableValueContractDelegate delegate;

	private IObservableValue observable;

	public MutableObservableValueContractTest(
			IObservableValueContractDelegate delegate) {
		this(null, delegate);
	}

	public MutableObservableValueContractTest(String testName,
			IObservableValueContractDelegate delegate) {
		super(testName, delegate);
		this.delegate = delegate;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.observable = (IObservableValue) getObservable();
	}

	public void testSetValue_SetsValue() throws Exception {
		Object value = delegate.createValue(observable);

		observable.setValue(value);
		assertEquals(
				formatFail("IObservableValue.setValue(Object) should set the value of the observable."),
				value, observable.getValue());
	}

	public void testSetValue_ChangeEvent() throws Exception {
		ChangeEventTracker listener = ChangeEventTracker.observe(observable);

		observable.setValue(delegate.createValue(observable));

		assertEquals(formatFail("Change event listeners were not notified"), 1,
				listener.count);
		assertEquals(
				formatFail("IObservableValue.setValue(Object) should fire one ChangeEvent."),
				1, listener.count);
		assertEquals(
				formatFail("IObservableValue.setValue(Object)'s change event observable should be the created observable."),
				observable, listener.event.getObservable());
	}

	public void testSetValue_SameValue() throws Exception {
		delegate.change(observable);

		ValueChangeEventTracker valueChangeListener = ValueChangeEventTracker
				.observe(observable);
		ChangeEventTracker changeListener = ChangeEventTracker
				.observe(observable);
		Object value = observable.getValue();
		observable.setValue(value);

		assertEquals(
				formatFail("IObservableValue.setValue() should not fire a value change event when the value has not change."),
				0, valueChangeListener.count);
		assertEquals(
				formatFail("IObservableValue.setValue() should not fire a change event when the value has not change."),
				0, changeListener.count);
	}

	public void testSetValue_RealmChecks() throws Exception {
		RealmTester.exerciseCurrent(new Runnable() {
			@Override
			public void run() {
				observable.setValue(delegate.createValue(observable));
			}
		}, (CurrentRealm) observable.getRealm());
	}

	public static Test suite(IObservableValueContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						MutableObservableValueContractTest.class, delegate)
				.addObservableContractTest(ObservableValueContractTest.class,
						delegate).build();
	}
}
