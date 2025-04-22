package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListToStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListConvertStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetConvertStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueToStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class CommonSteps {
	private CommonSteps() {
	}

	public interface DirectionStep<NEXT extends FromStep> extends FromStep {
		NEXT targetToModel();

		NEXT modelToTarget();
	}

	public interface TwoWayDirectionAndFromStep<NEXT extends TwoWayFromStep>
			extends TwoWayFromStep, DirectionStep<NEXT> {
	}

	public interface FromStep extends Step {
		<F> Step from(IObservableValue<F> from);

		<F> Step from(IObservableList<F> from);

		<F> Step from(IObservableSet<F> from);
	}

	public interface TwoWayFromStep extends FromStep {
		@Override
		<F> TwoWayValueConvertToStep<F, ?> from(IObservableValue<F> from);

		@Override
		<F> TwoWayListConvertToStep<F, ?> from(IObservableList<F> from);

		@Override
		<F> TwoWaySetConvertToStep<F, ?> from(IObservableSet<F> from);
	}

	public interface OneWayFromStep extends FromStep {
		@Override
		<F> OneWayValueConvertStep<F, ?> from(IObservableValue<F> from);

		@Override
		<F> OneWayListConvertStep<F, ?> from(IObservableList<F> from);

		@Override
		<F> OneWaySetConvertStep<F, ?> from(IObservableSet<F> from);
	}

	public interface ConvertToStep<F> extends Step {

		<T> Step convertTo(IConverter<? super F, ? extends T> converter);
	}

	public interface ConvertFromStep<F, T> extends Step {
		Step convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface OneWayDirectionAndFromStep<NEXT extends OneWayFromStep>
			extends OneWayFromStep, DirectionStep<NEXT> {
	}

	public interface WriteConfigStep<F, T, THIS extends WriteConfigStep<F, T, THIS>> extends Step {
		THIS updateOnlyOnRequest();
	}

	public interface ReadConfigStep<F, T, THIS extends ReadConfigStep<F, T, THIS>> {
	}

	public interface BindConfigStep<F, T, THIS extends BindConfigStep<F, T, THIS>>
			extends WriteConfigStep<F, T, THIS> {
		Binding bind(DataBindingContext bindingContext);

		Binding bind();
	}
}
