package org.eclipse.core.internal.databinding.property.value;

import java.util.Set;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.set.SetDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.set.SimpleSetProperty;

public class ConvertedSetProperty<S, E> extends SimpleSetProperty<S, E> {
	private final Function<? super S, ? extends Set<E>> converter;
	private final Object elementType;

	public ConvertedSetProperty(Object valueType, Function<? super S, ? extends Set<E>> converter) {
		this.elementType = valueType;
		this.converter = converter;
	}

	@Override
	public Object getElementType() {
		return elementType;
	}

	@Override
	public Set<E> getSet(S source) {
		return doGetSet(source);
	}

	@Override
	protected Set<E> doGetSet(S source) {
		return converter.apply(source);
	}


	@Override
	protected void doSetSet(S source, Set<E> set, SetDiff<E> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted set!"); //$NON-NLS-1$
	}

	@Override
	protected void doUpdateSet(S source, SetDiff<E> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted set!"); //$NON-NLS-1$
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, SetDiff<E>> listener) {
		return null;
	}
}
