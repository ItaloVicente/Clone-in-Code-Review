package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsListCommon.ListToStep;
import org.eclipse.core.databinding.bind.StepsListTwoWay.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueToStep;
import org.eclipse.core.databinding.bind.StepsValueOneWay.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class StepsCommon {
	private StepsCommon() {
	}

	public interface DirectionStep<NEXT extends FromStep> extends FromStep {
		NEXT targetToModel();

		NEXT modelToTarget();
	}

	public interface TwoWayDirectionAndFromStep<NEXT extends TwoWayFromStep>
			extends TwoWayFromStep, DirectionStep<NEXT> {
	}

	public interface FromStep extends Step {
		<T> Step from(IObservableValue<T> from);

		<E> Step from(IObservableList<E> from);

		<E> Step from(IObservableSet<E> from);
	}

	public interface TwoWayFromStep extends FromStep {
		@Override
		<T> TwoWayValueConvertToStep<T, ?> from(IObservableValue<T> from);

		@Override
		<E> TwoWayListConvertToStep<E, ?> from(IObservableList<E> from);

		@Override
		<E> ConvertToStep<E> from(IObservableSet<E> from);
	}

	public interface OneWayFromStep extends FromStep {
		@Override
		<T> OneWayValueConvertStep<T, ?> from(IObservableValue<T> from);

		@Override
		<E> ConvertToStep<E> from(IObservableList<E> from);

		@Override
		<E> ConvertToStep<E> from(IObservableSet<E> from);
	}

	public interface ConvertToStep<F> extends Step {

		<T> Step convertTo(IConverter<? super F, ? extends T> converter);
	}

	public interface ConvertFromStep<F, T> extends Step {
		Step convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface OneWayDirectionAndFromStep<NEXT extends FromStep>
			extends OneWayFromStep, DirectionStep<NEXT> {
	}

	public interface DestinationConfigStep<THIS extends DestinationConfigStep<THIS>> {
		THIS updateOnlyOnRequest();
	}

	public interface FinalDestinationConfigStep<THIS extends FinalDestinationConfigStep<THIS>>
			extends DestinationConfigStep<THIS>, BindStep {
	}

	public interface BindStep extends Step {
		Binding bind(DataBindingContext bindingContext);

		Binding bind();
	}
}

