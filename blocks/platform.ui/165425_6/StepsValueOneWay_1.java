package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class StepsListTwoWay {
	public interface ModelStep extends Step {
		<M> ConvertToTargetStep<M> model(IObservableList<M> model);
	}

	public interface TargetStep<M> extends Step {
		<T> ConfigStep<T, M> target(IObservableList<T> target);
	}

	public interface UntypedTargetStep<M> extends Step {
		<T> ConfigStep<T, M> target(IObservableList<T> target);
	}

	public interface ConvertToTargetStep<M> extends TargetStep<M> {
		UntypedTargetStep<M> useDefaultConvertion();
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);
		<T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter);
	}

	public interface ConvertToModelStep<T, M> extends Step {
		TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter);
		TargetStep<T> convertToModel(Function<? super T, ? extends M> converter);
	}

	public interface ConfigStep<T, M> extends Step {
		Binding bind(DataBindingContext context);

		ConfigStep<T, M> updateTargetOnlyOnRequest();
		ConfigStep<T, M> updateModelOnlyOnRequest();
	}
}
