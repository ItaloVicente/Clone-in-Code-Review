package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueTwoWaySteps {
	public interface ModelStep {
		<M> ModelSetStep<M> model(IObservableValue<M> source);
	}

	public interface ValidateModelStep<M> {
		ModelValidatorSetStep<M> validateModel(IValidator<? super M> validator);
	}

	public interface UseDefaultConvertionStep {
		UntypedTargetStep useDefaultConvertion();
	}

	public interface ConvertToTargetStep<M> {
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);
	}

	public interface ConvertToModelStep<T, M> {
		TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter);
	}

	public interface TargetStep<T> {
		TargetSetStep<T> target(IObservableValue<T> target);
	}

	public interface ValidateTargetStep<T> {
		BindStep validateTarget(IValidator<? super T> validator);
	}

	public interface UntypedTargetStep {
		<T> TargetSetStep<T> target(IObservableValue<T> target);
	}

	public interface ModelSetStep<M>
			extends ValidateModelStep<M>, ConvertToTargetStep<M>, TargetStep<M>, UseDefaultConvertionStep {
	}

	public interface ModelValidatorSetStep<M> extends ConvertToTargetStep<M>, TargetStep<M>, UseDefaultConvertionStep {
	}

	public interface BindStep {
		Binding bind(DataBindingContext context);
	}

	public interface TargetSetStep<T> extends BindStep, ValidateTargetStep<T> {
	}

	private ValueTwoWaySteps() {}
}
