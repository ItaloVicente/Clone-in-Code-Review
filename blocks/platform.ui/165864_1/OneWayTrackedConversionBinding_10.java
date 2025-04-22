package org.eclipse.core.databinding.bind;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;

public class OneWayTrackedConversionBinding<T2, T1> extends OneWayBinding<T2> implements ITargetBinding<T1> {
	private final IOneWayModelBinding<T1> modelBinding;
	private final IConverter<T1, T2> converter;

	private IObservable[] dependencies = null;

	private class PrivateChangeInterface implements IChangeListener {
		@Override
		public void handleChange(ChangeEvent event) {
			T1 valueFromModelSide = modelBinding.getModelValue();
			setTargetValue(valueFromModelSide);
		}
	}

	private IChangeListener privateChangeInterface = new PrivateChangeInterface();

	public OneWayTrackedConversionBinding(IOneWayModelBinding<T1> modelBinding, IConverter<T1, T2> converter) {
		this.modelBinding = modelBinding;
		this.converter = converter;
	}

	@Override
	public T2 getModelValue() {
		T1 modelValue = modelBinding.getModelValue();
		return convertAndTrack(modelValue);
	}

	@Override
	public void setTargetValue(T1 valueOnModelSide) {
		T2 valueOnTargetSide = convertAndTrack(valueOnModelSide);
		targetBinding.setTargetValue(valueOnTargetSide);
	}

	private T2 convertAndTrack(final T1 valueOnModelSide) {
		stopListening();

		final List<T2> valueOnTargetSide = new ArrayList<T2>();

		Runnable privateRunnableInterface = new Runnable() {
			@Override
			public void run() {
				valueOnTargetSide.add(converter.convert(valueOnModelSide));
			}
		};

		IObservable[] newDependencies = ObservableTracker.runAndMonitor(privateRunnableInterface,
				privateChangeInterface, null);
		dependencies = newDependencies;

		return valueOnTargetSide.get(0);
	}

	private void stopListening() {
		if (dependencies != null) {
			for (IObservable observable : dependencies) {
				observable.removeChangeListener(privateChangeInterface);
			}
			dependencies = null;
		}
	}

	@Override
	public void removeModelListener() {
		modelBinding.removeModelListener();
	}
}
