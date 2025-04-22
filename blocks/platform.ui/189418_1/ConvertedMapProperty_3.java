package org.eclipse.core.internal.databinding.property.value;

import java.util.List;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.list.SimpleListProperty;

public class ConvertedListProperty<S, E> extends SimpleListProperty<S, E> {
	private final Function<? super S, ? extends List<E>> converter;
	private final Object elementType;

	public ConvertedListProperty(Object valueType, Function<? super S, ? extends List<E>> converter) {
		this.elementType = valueType;
		this.converter = converter;
	}

	@Override
	public Object getElementType() {
		return elementType;
	}

	@Override
	public List<E> getList(S source) {
		return doGetList(source);
	}

	@Override
	protected List<E> doGetList(S source) {
		return converter.apply(source);
	}

	@Override
	protected void doSetList(S source, List<E> list, ListDiff<E> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted list!"); //$NON-NLS-1$
	}

	@Override
	protected void doUpdateList(S source, ListDiff<E> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted list!"); //$NON-NLS-1$
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, ListDiff<E>> listener) {
		return null;
	}
}
