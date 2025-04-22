package org.eclipse.core.internal.databinding.bind;

import java.util.function.Consumer;

import org.eclipse.core.databinding.observable.IObservablesListener;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;

class SetterObservableValue<T> extends AbstractObservableValue<T> {
	private final Consumer<T> setter;

	public SetterObservableValue(Consumer<T> setter) {
		this.setter = setter;
	}

	@Override
	protected T doGetValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void doSetValue(T value) {
		setter.accept(value);
	}

	@Override
	public Object getValueType() {
		return null;
	}

	@Override
	protected void addListener(Object listenerType, IObservablesListener listener) {
	}

	@Override
	protected void removeListener(Object listenerType, IObservablesListener listener) {
	}
}
