package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueOneWaySteps {
	public interface ModelStep {
		<M> ModelSetStep<M> model(IObservableValue<M> source);
	}

	public interface ConvertToTargetStep<M> {
		<T> TargetStep<T> convertToTarget(IConverter<? super M, ? extends T> converter);
	}

	public interface UseDefaultConvertionStep {
		UntypedTargetStep useDefaultConvertion();
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

	public interface ValidateTargetStep<T> {
		BindStep validateTarget(IValidator<? super T> validator);
	}

	public interface ModelSetStep<M> extends ConvertToTargetStep<M>, TargetStep<M>, UseDefaultConvertionStep {
	}

	public interface TargetSetStep<T> extends BindStep, ValidateTargetStep<T> {
	}

	private ValueOneWaySteps() {}
}
