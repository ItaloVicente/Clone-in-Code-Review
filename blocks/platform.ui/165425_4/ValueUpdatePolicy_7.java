package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueTwoWaySteps {
	public interface ModelStep extends Step {
		<M> ModelSetStep<M> model(IObservableValue<M> source);
	}

	public interface UseDefaultConvertionStep<M> extends Step {
		UntypedTargetStep<M> useDefaultConvertion();
	}

	public interface ConvertToTargetStep<M> extends Step {
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);

		default <T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
	}

	public interface ConvertToModelStep<T, M> extends Step {
		TargetStep<T, M> convertToModel(IConverter<? super T, ? extends M> converter);

		default TargetStep<T, M> convertToModel(Function<? super T, ? extends M> converter) {
			return convertToModel(IConverter.create(converter));
		}
	}

	public interface TargetStep<T, M> extends Step {
		ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface UntypedTargetStep<M> extends Step {
		<T> ConfigStep<T, M> target(IObservableValue<T> target);
	}

	public interface ModelSetStep<M> extends ConvertToTargetStep<M>, TargetStep<M, M>, UseDefaultConvertionStep<M> {
	}

	public interface ModelValidatorSetStep<T, M> extends ConvertToTargetStep<M> {
	}

	public interface ConfigStep<T, M> {
		Binding bind(DataBindingContext context);

		ConfigStep<T, M> targetUpdatePolicy(ValueUpdatePolicy policy);
		ConfigStep<T, M> modelUpdatePolicy(ValueUpdatePolicy policy);

		ConfigStep<T, M> validateModelAfterGet(IValidator<? super T> validator);
		ConfigStep<T, M> validateModelAfterConvert(IValidator<? super M> validator);
		ConfigStep<T, M> validateModelBeforeSet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator);
		ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator);
		ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator);
		ConfigStep<T, M> validateModel(IValidator<? super M> validator);
		ConfigStep<T, M> validateTarget(IValidator<? super T> validator);
	}

	private ValueTwoWaySteps() {}
}
