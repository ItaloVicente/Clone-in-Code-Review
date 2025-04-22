package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListTwoWaySteps {
	public interface ModelStep {
		<M> ModelSetStep<M> model(IObservableList<M> model);
	}

	public interface ModelSetStep<M> extends TargetStep<M> {
		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);
		UntypedTargetStep useDefaultConvertion();
	}

	public interface ConvertToModelStep<T, M> {
		TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter);
	}

	public interface TargetStep<T> {
		BindStep target(IObservableList<T> target);
	}

	public interface UntypedTargetStep {
		BindStep target(IObservableList<?> target);
	}

	public interface BindStep {
		Binding bind(DataBindingContext context);
	}

	private ListTwoWaySteps() {}
}
