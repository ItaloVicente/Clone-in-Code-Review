package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;

public interface ITwoWayBinding<D> {

	<T2> ITwoWayBinding<T2> convert(final IBidiConverter<D, T2> converter);

	<T2> ITwoWayBinding<T2> convertWithTracking(IBidiConverter<D, T2> converter);

	void to(IObservableValue<D> targetObservable);

	<S> void to(IValueProperty<S, D> targetProperty, S source);

}
