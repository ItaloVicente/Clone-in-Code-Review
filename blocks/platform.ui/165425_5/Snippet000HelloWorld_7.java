package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public class ValueStepsTwoWay {
	public interface ModelStep {
		<M> ModelStepStep<M> model(IObservableValue<M> source);
	}

	public interface ModelValidatorStep<M> {
		ModelValidatorSetStep<M> modelValidator(IValidator<? super M> validator);
	}

	public interface DefaultConverterStep {
		UntypedTargetStep defaultConverter();
	}

	public interface ConverterToTargetStep<M> {
		<T> ConverterToModelStep<M, T> converterToTarget(IConverter<? super M, ? extends T> converter);
	}

	public interface ConverterToModelStep<T, M> {
		TargetStep<T> converterToModel(IConverter<? super T, ? extends M> converter);
	}

	public interface TargetValidatorStep<S> {
		BindStep targetValidator(IValidator<? super S> validator);
	}

	public interface TargetStep<T> {
		TargetSetStep<T> target(IObservableValue<T> target);
	}

	public interface UntypedTargetStep {
		<T> TargetSetStep<T> target(IObservableValue<T> target);
	}

	public interface BindStep {
		Binding bind(DataBindingContext context);
	}

	public interface ModelStepStep<M> extends ModelValidatorStep<M>, ConverterToTargetStep<M>, TargetStep<M>, DefaultConverterStep {
	}

	public interface ModelValidatorSetStep<M> extends ConverterToTargetStep<M>, TargetStep<M> {
	}

	public interface TargetSetStep<T> extends BindStep, TargetValidatorStep<T> {
	}

	private ValueStepsTwoWay() {}
}
