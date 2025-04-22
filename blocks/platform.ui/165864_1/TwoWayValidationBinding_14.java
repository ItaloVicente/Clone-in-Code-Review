package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

public class TwoWayValidationBinding<T> extends TwoWayBinding<T> implements ITargetBinding<T> {
	private final IModelBinding<T> modelBinding;
	private final IValidator<T> validator;

	public TwoWayValidationBinding(IModelBinding<T> modelBinding, IValidator<T> validator, boolean pullInitialValue) {
		super(pullInitialValue);
		this.modelBinding = modelBinding;
		this.validator = validator;
	}

	@Override
	public T getModelValue() {
		return modelBinding.getModelValue();
	}

	@Override
	public void setModelValue(T valueOnTargetSide) {
		IStatus status = validator.validate(valueOnTargetSide);
		targetBinding.setStatus(status);

		if (status.getSeverity() >= IStatus.WARNING) {
			modelBinding.setModelValue(valueOnTargetSide);
		}
	}

	@Override
	public void setTargetValue(T valueOnModelSide) {
		targetBinding.setTargetValue(valueOnModelSide);
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
