package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueOneWaySteps {
	public interface ModelStep extends Step {
		<M> ModelSetStep<M> model(IObservableValue<M> source);
	}

	public interface ConvertToTargetStep<M> extends Step {
		<T> TargetStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);

		default <T> TargetStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
	}

	public interface UseDefaultConvertionStep<M> extends Step {
		UntypedTargetStep<M> useDefaultConvertion();
	}

	public interface TargetStep<T, M> extends Step {
		ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface UntypedTargetStep<M> extends Step {
		<T> ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface ConfigStep<T, M> extends Step {
		Binding bind(DataBindingContext context);
		ConfigStep<T, M> convertOnly();
		ConfigStep<T, M> manualUpdate();

		ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator);
		ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator);
	}

	public interface ModelSetStep<M> extends ConvertToTargetStep<M>, TargetStep<M, M>, UseDefaultConvertionStep<M> {
	}

	private ValueOneWaySteps() {}
}
