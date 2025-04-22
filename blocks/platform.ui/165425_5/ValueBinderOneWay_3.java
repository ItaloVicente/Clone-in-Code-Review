package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class ListStepsTwoWay {
	public interface ModelStep {
		<M> ModelSetStep<M> model(IObservableList<M> source);
	}

	public interface ConverterToTargetStep<M> {
		<T> ConverterToModelStep<M, T> converterToTarget(IConverter<? super M, ? extends T> converter);
	}

	public interface ConverterToModelStep<T, M> {
		TargetStep<T> converterToModel(IConverter<? super T, ? extends M> converter);
	}

	public interface TargetStep<T> {
		BindStep target(IObservableList<T> target);
	}

	public interface DefaultConverterStep {
		UntypedTargetStep defaultConverter();
	}

	public interface UntypedTargetStep {
		BindStep target(IObservableList<?> target);
	}

	public interface BindStep {
		Binding bind(DataBindingContext context);
	}

	public interface ModelSetStep<M> extends ConverterToTargetStep<M>, TargetStep<M>, DefaultConverterStep {
	}

	private ListStepsTwoWay() {}
}
