package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayFromStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayFromStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayFromStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.SetTwoWayFromStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.ValueOneWayFromStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayFromStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.IObservableCollection;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class CommonSteps {
	private CommonSteps() {
	}

	public interface DirectionStep<SELF extends DirectionStep<SELF>> extends Step {
		SELF targetToModel();

		SELF modelToTarget();
	}

	public interface OneWayConfigAndFromStep<SELF extends OneWayConfigAndFromStep<SELF>> extends //
			DirectionStep<SELF>, //
			ValueOneWayFromStep, //
			ListOneWayFromStep, //
			SetOneWayFromStep //
	{
	}

	public interface TwoWayConfigAndFromStep<SELF extends TwoWayConfigAndFromStep<SELF>> extends //
			DirectionStep<SELF>, //
			ValueTwoWayFromStep, //
			ListTwoWayFromStep, //
			SetTwoWayFromStep //
	{
	}

	public interface ConvertToStep<F> extends Step {

		<T> Step convertTo(IConverter<? super F, ? extends T> converter);

		Step defaultConvert();
	}

	public interface ConvertFromStep<F, T> extends Step {
		Step convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface WriteConfigStep<F, T, THIS extends WriteConfigStep<F, T, THIS>> extends Step {
		THIS updateOnlyOnRequest();
	}

	public interface ReadConfigStep<F, T, THIS extends ReadConfigStep<F, T, THIS>> {
	}

	public interface BindConfigStep<F, T, THIS extends BindConfigStep<F, T, THIS>>
			extends WriteConfigStep<F, T, THIS> {
		Binding bind(DataBindingContext bindingContext);

		Binding bindWithNewContext();
	}
}
