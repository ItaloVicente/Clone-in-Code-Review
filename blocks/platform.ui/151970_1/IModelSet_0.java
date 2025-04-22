package org.eclipse.core.databinding.fluent;

import org.eclipse.core.databinding.observable.value.IObservableValue;

public interface IModelAndStrategySet<M, T> {
	ITargetSet<M, T> to(IObservableValue<T> target);
}
