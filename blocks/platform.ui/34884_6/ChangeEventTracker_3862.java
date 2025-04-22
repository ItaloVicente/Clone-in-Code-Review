
package org.eclipse.jface.databinding.conformance.swt;

import junit.framework.Test;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.jface.databinding.conformance.ObservableValueContractTest;
import org.eclipse.jface.databinding.conformance.delegate.IObservableValueContractDelegate;
import org.eclipse.jface.databinding.conformance.util.DelegatingRealm;
import org.eclipse.jface.databinding.conformance.util.SuiteBuilder;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

public class SWTObservableValueContractTest extends ObservableValueContractTest {
	private IObservableValueContractDelegate delegate;

	public SWTObservableValueContractTest(
			IObservableValueContractDelegate delegate) {
		this(null, delegate);
	}

	public SWTObservableValueContractTest(String testName,
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
		return new SuiteBuilder().addObservableContractTest(
				SWTObservableValueContractTest.class, delegate).build();
	}
}
