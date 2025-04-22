
package org.eclipse.jface.databinding.conformance.delegate;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public interface IObservableValueContractDelegate extends
		IObservableContractDelegate {

	public IObservableValue createObservableValue(Realm realm);

	public Object getValueType(IObservableValue observable);
	
	public Object createValue(IObservableValue observable);
}
