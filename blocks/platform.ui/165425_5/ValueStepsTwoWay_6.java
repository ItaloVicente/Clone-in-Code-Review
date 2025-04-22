package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public class ValueStepsOneWay {
	public interface ModelStep {
		<M> ModelSetStep<M> model(IObservableValue<M> source);
	}

	public interface ConverterToTargetStep<M> {
		<T> TargetStep<T> converterToTarget(IConverter<? super M, ? extends T> converter);
	}

	public interface TargetValidatorStep<S> {
		BindStep targetValidator(IValidator<? super S> validator);
	}

	public interface DefaultConverterStep {
		UntypedTargetStep defaultConverter();
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

	public interface ModelSetStep<M> extends ConverterToTargetStep<M>, TargetStep<M>, DefaultConverterStep {
	}

	public interface TargetSetStep<T> extends BindStep, TargetValidatorStep<T> {
	}

	private ValueStepsOneWay() {}
}
