
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.Realm;

public abstract class AbstractObservableCollectionContractDelegate extends
		AbstractObservableContractDelegate implements
		IObservableCollectionContractDelegate {

	@Override
	public final IObservable createObservable(Realm realm) {
		return createObservableCollection(realm, 0);
	}

	@Override
	public Object createElement(IObservableCollection collection) {
		return null;
	}

	@Override
	public Object getElementType(IObservableCollection collection) {
		return null;
	}
}
