package org.eclipse.core.databinding.bind;

import org.eclipse.core.runtime.IStatus;

public class DefaultValueBinding<T1> extends TwoWayBinding<T1> implements ITargetBinding<T1> {

	private final IOneWayModelBinding<T1> modelBinding;

	private boolean stopped = false;

	public DefaultValueBinding(IOneWayModelBinding<T1> modelBinding) {
		super(true);
		this.modelBinding = modelBinding;
	}

	@Override
	public T1 getModelValue() {
		return modelBinding.getModelValue();
	}

	@Override
	public void setTargetValue(T1 valueOnModelSide) {
		targetBinding.setTargetValue(valueOnModelSide);
	}

	@Override
	public void setStatus(IStatus status) {
		targetBinding.setStatus(status);
	}

	@Override
	public void setModelValue(T1 newValue) {
		if (!stopped) {
			stopped = true;
			modelBinding.removeModelListener();
		}
	}

	@Override
	public void removeModelListener() {
		if (!stopped) {
			modelBinding.removeModelListener();
		}
	}
}
