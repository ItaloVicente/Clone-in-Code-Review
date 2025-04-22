package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class StepsValueOneWay {
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
		<T> TargetStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);
		<T> TargetStep<T, M> convertToTarget(Function<? super M, ? extends T> converter);
	}

	public interface ConfigStep<T, M> extends Step {
		Binding bind(DataBindingContext context);

		ConfigStep<T, M> convertOnlyToTarget();
		ConfigStep<T, M> updateTargetOnlyOnRequest();

		ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator);
		ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator);
	}
}
