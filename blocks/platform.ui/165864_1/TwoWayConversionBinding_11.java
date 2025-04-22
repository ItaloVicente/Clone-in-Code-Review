package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.observable.DisposeEvent;
import org.eclipse.core.databinding.observable.IDisposeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

public abstract class TwoWayBinding<T2> implements ITwoWayBinding<T2>, IModelBinding<T2> {

	protected boolean pullInitialValue;

	protected ITargetBinding<T2> targetBinding;

	public TwoWayBinding(boolean pullInitialValue) {
		this.pullInitialValue = pullInitialValue;
	}

	@Override
	public <T3> ITwoWayBinding<T3> convert(final IBidiConverter<T2, T3> converter) {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		TwoWayConversionBinding<T3, T2> nextBinding = new TwoWayConversionBinding<T3, T2>(this, converter,
				pullInitialValue);
		targetBinding = nextBinding;
		return nextBinding;
	}

	@Override
	public <T3> ITwoWayBinding<T3> convertWithTracking(final IBidiConverter<T2, T3> converter) {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		TwoWayTrackedConversionBinding<T3, T2> nextBinding = new TwoWayTrackedConversionBinding<T3, T2>(this, converter,
				pullInitialValue);
		targetBinding = nextBinding;
		return nextBinding;
	}

	public ITwoWayBinding<T2> validate(final IValidator<T2> validator) {
		if (targetBinding != null) {
			throw new RuntimeException("When chaining together a binding, you cannot chain more than one target."); //$NON-NLS-1$
		}

		TwoWayValidationBinding<T2> nextBinding = new TwoWayValidationBinding<T2>(this, validator, pullInitialValue);
		targetBinding = nextBinding;
		return nextBinding;
	}

	@Override
	public void to(final IObservableValue<T2> targetObservable) {
		to(targetObservable, null);
	}

	@Override
	public <S> void to(IValueProperty<S, T2> targetProperty, S source) {
		IObservableValue<T2> targetObservable = targetProperty.observe(source);
		to(targetObservable);
	}

	public void to(final IObservableValue<T2> targetObservable, final IObservableValue<IStatus> statusObservable) {
		if (pullInitialValue) {
			targetObservable.setValue(getModelValue());
		}

		final boolean[] isChanging = new boolean[] { false };

		targetBinding = new ITargetBinding<T2>() {
			@Override
			public void setTargetValue(T2 targetValue) {
				try {
					isChanging[0] = true;
					targetObservable.setValue(targetValue);
				} finally {
					isChanging[0] = false;
				}
			}

			@Override
			public void setStatus(IStatus status) {
				if (statusObservable != null) {
					statusObservable.setValue(status);
				}
			}
		};

		targetObservable.addValueChangeListener(new IValueChangeListener<T2>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends T2> event) {
				if (!isChanging[0]) {
					setModelValue(event.diff.getNewValue());
				}
			}
		});

		targetObservable.addDisposeListener(new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent event) {
				removeModelListener();
			}
		});
	}
}
