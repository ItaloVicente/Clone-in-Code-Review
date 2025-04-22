package org.eclipse.core.databinding.fluent;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public interface IModelSet<M> {

	<T> IModelAndStrategySet<M, T> by(UpdateValueStrategy<? super M, ? extends T> model2Target);

	<T> ITargetSet<M, T> to(IObservableValue<T> target);

}
