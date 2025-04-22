package org.eclipse.core.databinding.fluent;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;

public interface ITargetSet<M, T> {

	ITargetAndStrategySet by(UpdateValueStrategy<? super T, ? extends M> target2Model);

	Binding bind(DataBindingContext dbc);
}
