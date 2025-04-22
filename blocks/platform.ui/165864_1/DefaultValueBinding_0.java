package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.CoreException;

public class Bind {

	static class TwoWayModelBinding<V> extends TwoWayBinding<V> {
		private final IObservableValue<V> modelObservable;

		private final boolean takeOwnership;

		private boolean isModelChanging = false;

		private IValueChangeListener<V> modelListener = event -> {
			if (!isModelChanging) {
				targetBinding.setTargetValue(event.diff.getNewValue());
			}
		};

		public TwoWayModelBinding(IObservableValue<V> modelObservable, boolean takeOwnership) {
			super(true);
			this.modelObservable = modelObservable;
			this.takeOwnership = takeOwnership;

			modelObservable.addValueChangeListener(modelListener);
		}

		@Override
		public V getModelValue() {
			return modelObservable.getValue();
		}

		@Override
		public void setModelValue(V newValue) {
			isModelChanging = true;
			try {
				modelObservable.setValue(newValue);
			} finally {
				isModelChanging = false;
			}
		}

		@Override
		public void removeModelListener() {
			modelObservable.removeValueChangeListener(modelListener);

			if (takeOwnership) {
				modelObservable.dispose();
			}
		}
	}

	static class OneWayModelBinding<V> extends OneWayBinding<V> {
		private final IObservableValue<V> modelObservable;

		private final boolean takeOwnership;

		IValueChangeListener<V> modelListener = new IValueChangeListener<V>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends V> event) {
				targetBinding.setTargetValue(event.diff.getNewValue());
			}
		};

		public OneWayModelBinding(IObservableValue<V> modelObservable, boolean takeOwnership) {
			this.modelObservable = modelObservable;
			this.takeOwnership = takeOwnership;

			modelObservable.addValueChangeListener(modelListener);
		}

		@Override
		public V getModelValue() {
			return modelObservable.getValue();
		}

		@Override
		public void removeModelListener() {
			modelObservable.removeValueChangeListener(modelListener);

			if (takeOwnership) {
				modelObservable.dispose();
			}
		}
	}

	public static <V> IOneWayBinding<V> oneWay(IObservableValue<V> modelObservable) {
		return new OneWayModelBinding<V>(modelObservable, false);
	}

	public static <S, V> IOneWayBinding<V> oneWay(IValueProperty<S, V> modelProperty, S source) {
		IObservableValue<V> modelObservable = modelProperty.observe(source);
		return new OneWayModelBinding<V>(modelObservable, true);
	}

	public static <V> ITwoWayBinding<V> twoWay(IObservableValue<V> modelObservable) {
		return new TwoWayModelBinding<V>(modelObservable, false);
	}

	public static <S, V> ITwoWayBinding<V> twoWay(IValueProperty<S, V> modelProperty, S source) {
		IObservableValue<V> modelObservable = modelProperty.observe(source);
		return new TwoWayModelBinding<V>(modelObservable, true);
	}

	public static <S, V> ITwoWayBinding<V> twoWay(IValueProperty<S, V> modelProperty,
			IObservableValue<? extends S> source) {
		IObservableValue<V> modelObservable = modelProperty.observeDetail(source);
		return new TwoWayModelBinding<V>(modelObservable, true);
	}

		.to(SWTObservables.observeText(textControl, SWT.FocusOut));
	public static <T1, T2> ITwoWayBinding<T2> bounceBack(final IBidiConverter<T1, T2> converter) {
		return new TwoWayBinding<T2>(false) {

			@Override
			public T2 getModelValue() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void setModelValue(T2 valueFromTarget) {
				try {
					T1 modelSideValue = converter.targetToModel(valueFromTarget);
					T2 valueBackToTarget = converter.modelToTarget(modelSideValue);
					this.targetBinding.setTargetValue(valueBackToTarget);
				} catch (CoreException e) {
				}
			}

			@Override
			public void removeModelListener() {
			}
		};
	}

}
