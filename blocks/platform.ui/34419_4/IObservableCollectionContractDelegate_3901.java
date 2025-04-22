
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public abstract class AbstractObservableValueContractDelegate extends
		AbstractObservableContractDelegate implements
		IObservableValueContractDelegate {

	@Override
	public final IObservable createObservable(Realm realm) {
		return createObservableValue(realm);
	}

	@Override
	public Object getValueType(IObservableValue observable) {
		return null;
	}

	@Override
	public Object createValue(IObservableValue observable) {
		return null;
	}
}
