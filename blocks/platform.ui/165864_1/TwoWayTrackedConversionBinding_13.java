package org.eclipse.core.databinding.bind;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TwoWayTrackedConversionBinding<T2, T1> extends TwoWayBinding<T2> implements ITargetBinding<T1> {
	private final IModelBinding<T1> modelBinding;
	private final IBidiConverter<T1, T2> converter;

	private IObservable[] dependencies = null;

	private class PrivateChangeInterface implements IChangeListener {
		@Override
		public void handleChange(ChangeEvent event) {
			T1 valueFromModelSide = modelBinding.getModelValue();
			setTargetValue(valueFromModelSide);
		}
	}

	private IChangeListener privateChangeInterface = new PrivateChangeInterface();

	public TwoWayTrackedConversionBinding(IModelBinding<T1> modelBinding, IBidiConverter<T1, T2> converter,
			boolean pullInitialValue) {
		super(pullInitialValue);
		this.modelBinding = modelBinding;
		this.converter = converter;
	}

	@Override
	public T2 getModelValue() {
		T1 modelValue = modelBinding.getModelValue();
		return convertAndTrack(modelValue);
	}

	@Override
	public void setModelValue(T2 valueOnTargetSide) {
		try {
			T1 valueOnModelSide = converter.targetToModel(valueOnTargetSide);
			modelBinding.setModelValue(valueOnModelSide);
			targetBinding.setStatus(Status.OK_STATUS);
		} catch (CoreException e) {
			targetBinding.setStatus(e.getStatus());
		}
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
				valueOnTargetSide.add(converter.modelToTarget(valueOnModelSide));
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
	public void setStatus(IStatus status) {
		targetBinding.setStatus(status);
	}

	@Override
	public void removeModelListener() {
		modelBinding.removeModelListener();
	}
}
