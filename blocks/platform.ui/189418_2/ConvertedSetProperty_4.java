package org.eclipse.core.internal.databinding.property.value;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.property.INativePropertyListener;
import org.eclipse.core.databinding.property.ISimplePropertyListener;
import org.eclipse.core.databinding.property.map.SimpleMapProperty;

public class ConvertedMapProperty<S, K, V> extends SimpleMapProperty<S, K, V> {
	private final Function<? super S, ? extends Map<K, V>> converter;
	private final Object keyType;
	private final Object valueType;

	public ConvertedMapProperty(Object keyType, Object valueType, Function<? super S, ? extends Map<K, V>> converter) {
		this.keyType = keyType;
		this.valueType = valueType;
		this.converter = converter;
	}

	@Override
	public Object getKeyType() {
		return keyType;
	}

	@Override
	public Object getValueType() {
		return valueType;
	}


	@Override
	public Map<K, V> getMap(S source) {
		return doGetMap(source);
	}

	@Override
	protected Map<K, V> doGetMap(S source) {
		return converter.apply(source);
	}


	@Override
	protected void doSetMap(S source, Map<K, V> map, MapDiff<K, V> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted map!"); //$NON-NLS-1$
	}

	@Override
	protected void doUpdateMap(S source, MapDiff<K, V> diff) {
		throw new UnsupportedOperationException(this + ": Setter not supported on a converted map!"); //$NON-NLS-1$
	}

	@Override
	public INativePropertyListener<S> adaptListener(ISimplePropertyListener<S, MapDiff<K, V>> listener) {
		return null;
	}
}
