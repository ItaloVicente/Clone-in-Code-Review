package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.DisposeEvent;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IStatus;

public abstract class OneWayBinding<T2> implements IOneWayBinding<T2>, IOneWayModelBinding<T2> {

	protected ITargetBinding<T2> targetBinding;

	@Override
	public <T3> IOneWayBinding<T3> convert(final IConverter<T2, T3> converter) {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		OneWayConversionBinding<T3, T2> nextBinding = new OneWayConversionBinding<T3, T2>(this, converter);
		targetBinding = nextBinding;
		return nextBinding;
	}

	@Override
	public <T3> IOneWayBinding<T3> convertWithTracking(final IConverter<T2, T3> converter) {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		OneWayTrackedConversionBinding<T3, T2> nextBinding = new OneWayTrackedConversionBinding<T3, T2>(this,
				converter);
		targetBinding = nextBinding;
		return nextBinding;
	}

	@Override
	public ITwoWayBinding<T2> untilTargetChanges() {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		DefaultValueBinding<T2> nextBinding = new DefaultValueBinding<T2>(this);

		targetBinding = nextBinding;
		return nextBinding;
	}

	@Override
	public void to(final IObservableValue<T2> targetObservable) {
		targetObservable.setValue(getModelValue());

		targetBinding = new ITargetBinding<T2>() {
			@Override
			public void setTargetValue(T2 targetValue) {
				targetObservable.setValue(targetValue);
			}

			@Override
			public void setStatus(IStatus status) {
			}
		};

		targetObservable.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent event) {
				removeModelListener();
			}
		});
	}

	@Override
	public <S> void to(IValueProperty<S, T2> targetProperty, S source) {
		IObservableValue<T2> targetObservable = targetProperty.observe(source);
		to(targetObservable);
	}

	public void setStatus(IStatus status) {
		targetBinding.setStatus(status);
	}
}
