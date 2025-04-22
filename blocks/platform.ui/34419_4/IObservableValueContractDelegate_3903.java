
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;

public interface IObservableContractDelegate {
	public void setUp();

	public void tearDown();

	public void setStale(IObservable observable, boolean stale);

	public IObservable createObservable(Realm realm);

	public void change(IObservable observable);
}
