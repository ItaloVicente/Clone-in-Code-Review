
package org.eclipse.jface.databinding.conformance.swt;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.jface.databinding.conformance.MutableObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.DelegatingRealm;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

public class SWTMutableObservableValueContractTest extends
		MutableObservableValueContractTest {
	private IObservableValueContractDelegate delegate;

	public SWTMutableObservableValueContractTest(
			IObservableValueContractDelegate delegate) {
		this(null, delegate);
	}

	public SWTMutableObservableValueContractTest(String testName,
			IObservableValueContractDelegate delegate) {
		super(testName, delegate);
		this.delegate = delegate;
	}

	@Override
	protected IObservable doCreateObservable() {
		Display display = Display.getCurrent();
		if (display == null) {
			display = new Display();
		}
		DelegatingRealm delegateRealm = new DelegatingRealm(
				SWTObservables.getRealm(display));
		delegateRealm.setCurrent(true);

		return delegate.createObservable(delegateRealm);
	}

	public static Test suite(IObservableValueContractDelegate delegate) {
		return new SuiteBuilder()
				.addObservableContractTest(
						SWTMutableObservableValueContractTest.class, delegate)
				.addObservableContractTest(
						SWTObservableValueContractTest.class, delegate).build();
	}
}
