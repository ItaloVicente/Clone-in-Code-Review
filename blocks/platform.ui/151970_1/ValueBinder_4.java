package org.eclipse.core.databinding.fluent;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ValueBinder {

	public static <M> IModelSet<M> from(IObservableValue<M> model) {
		return new IModelSet<M>() {

			@Override
			public <T> ITargetSet<M, T> to(IObservableValue<T> target) {
				return createTargetSet(model, target, null);
			}

			@Override
			public <T> IModelAndStrategySet<M, T> by(UpdateValueStrategy<? super M, ? extends T> model2Target) {
				return target -> createTargetSet(model, target, model2Target);
			}
		};
	}

	private static <M, T> ITargetSet<M, T> createTargetSet(IObservableValue<M> model, IObservableValue<T> target,
			UpdateValueStrategy<? super M, ? extends T> model2Target) {

		return new ITargetSet<M, T>() {

			@Override
			public Binding bind(DataBindingContext dbc) {
				return createBinding(dbc, model, target, model2Target, null);
			}

			@Override
			public ITargetAndStrategySet by(UpdateValueStrategy<? super T, ? extends M> target2Model) {
				return dbc -> createBinding(dbc, model, target, model2Target, target2Model);
			}
		};
	}

	private static <M, T> Binding createBinding(DataBindingContext dbc,
			IObservableValue<M> model, IObservableValue<T> target,
			UpdateValueStrategy<? super M, ? extends T> model2Target, UpdateValueStrategy<? super T, ? extends M> target2Model) {
		return dbc.bindValue(target, model, target2Model, model2Target);
	}
}
