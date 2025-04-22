package org.eclipse.core.internal.databinding.property.value;

import java.util.function.Function;

import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.value.SimpleValueProperty;

public class ConvertedValueProperty<S, T> extends SimpleValueProperty<S, T> {
	private final Function<? super S, ? extends T> converter;
	private final Object valueType;

	public ConvertedValueProperty(Object valueType, Function<? super S, ? extends T> converter) {
		this.valueType = valueType;
		this.converter = converter;
	}

	@Override
	public Object getValueType() {
		return valueType;
	}

	@Override
	public T getValue(S source) {
		return doGetValue(source);
	}

	@Override
	protected T doGetValue(S source) {
		return converter.apply(source);
	}

	@Override
	protected void doSetValue(S source, T value) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted value!"); //$NON-NLS-1$
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ValueDiff<? extends T>> listener) {
		return null;
	}
}
