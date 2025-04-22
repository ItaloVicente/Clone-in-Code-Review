package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class StepsValueTwoWay {
	public interface ModelStep extends Step {
		<M> ConvertToTargetStep<M> model(IObservableValue<M> source);
	}

	public interface TargetStep<T, M> extends Step {
		ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface UntypedTargetStep<M> extends Step {
		<T> ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface ConvertToTargetStep<M> extends TargetStep<M, M> {
		UntypedTargetStep<M> useDefaultConvertion();
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);
		<T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter);
	}

	public interface ConvertToModelStep<T, M> extends Step {
		TargetStep<T, M> convertToModel(IConverter<? super T, ? extends M> converter);
		TargetStep<T, M> convertToModel(Function<? super T, ? extends M> converter);
	}

	public interface ConfigStep<T, M> {
		Binding bind(DataBindingContext context);

		ConfigStep<T, M> convertOnlyToTarget();
		ConfigStep<T, M> convertOnlyToModel();
		ConfigStep<T, M> updateTargetOnlyOnRequest();
		ConfigStep<T, M> updateModelOnlyOnRequest();

		ConfigStep<T, M> validateModelAfterGet(IValidator<? super T> validator);
		ConfigStep<T, M> validateModelAfterConvert(IValidator<? super M> validator);
		ConfigStep<T, M> validateModelBeforeSet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator);
		ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator);
		ConfigStep<T, M> validateModel(IValidator<? super M> validator);
		ConfigStep<T, M> validateTarget(IValidator<? super T> validator);
	}
}
