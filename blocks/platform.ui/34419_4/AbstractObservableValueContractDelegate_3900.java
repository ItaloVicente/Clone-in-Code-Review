
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;

public abstract class AbstractObservableContractDelegate implements
		IObservableContractDelegate {
	@Override
	public void setUp() {
	}

	@Override
	public void tearDown() {
	}

	@Override
	public void change(IObservable observable) {
	}

	@Override
	public void setStale(IObservable observable, boolean stale) {
	}
}
