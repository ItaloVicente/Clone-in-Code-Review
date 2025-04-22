package org.eclipse.core.databinding.bind;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TwoWayConversionBinding<T2, T1> extends TwoWayBinding<T2> implements ITargetBinding<T1> {
	private final IModelBinding<T1> modelBinding;
	private final IBidiConverter<T1, T2> converter;

	public TwoWayConversionBinding(IModelBinding<T1> modelBinding, IBidiConverter<T1, T2> converter,
			boolean pullInitialValue) {
		super(pullInitialValue);
		this.modelBinding = modelBinding;
		this.converter = converter;
	}

	@Override
	public T2 getModelValue() {
		T1 modelValue = modelBinding.getModelValue();
		return converter.modelToTarget(modelValue);
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
		T2 valueOnTargetSide = converter.modelToTarget(valueOnModelSide);
		targetBinding.setTargetValue(valueOnTargetSide);
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
