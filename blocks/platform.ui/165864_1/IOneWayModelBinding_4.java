package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;

public interface IOneWayBinding<T1> {

	<T2> IOneWayBinding<T2> convert(IConverter<T1, T2> converter);

	<T2> IOneWayBinding<T2> convertWithTracking(IConverter<T1, T2> converter);

	void to(IObservableValue<T1> targetObservable);

	<S> void to(IValueProperty<S, T1> targetProperty, S source);

	ITwoWayBinding<T1> untilTargetChanges();
}
