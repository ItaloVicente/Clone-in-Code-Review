
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;

public interface IObservableCollectionContractDelegate extends
		IObservableContractDelegate {
	public IObservableCollection createObservableCollection(Realm realm, int elementCount);

	public Object createElement(IObservableCollection collection);

	public Object getElementType(IObservableCollection collection);
}
