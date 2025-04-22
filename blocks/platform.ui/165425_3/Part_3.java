package org.eclipse.core.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListTwoWaySteps {
	public interface ModelStep extends Step {
		<M> ModelSetStep<M> model(IObservableList<M> model);
	}

	public interface ModelSetStep<M> extends TargetStep<M> {
		UntypedTargetStep useDefaultConvertion();

		<T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter);

		default <T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
	}

	public interface ConvertToModelStep<T, M> extends Step {
		TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter);

		default TargetStep<T> convertToModel(Function<? super T, ? extends M> converter) {
			return convertToModel(IConverter.create(converter));
		}
	}

	public interface TargetStep<T> extends Step {
		BindStep target(IObservableList<T> target);
	}

	public interface UntypedTargetStep extends Step {
		BindStep target(IObservableList<?> target);
	}

	public interface BindStep extends Step {
		Binding bind(DataBindingContext context);
	}

	private ListTwoWaySteps() {}
}
