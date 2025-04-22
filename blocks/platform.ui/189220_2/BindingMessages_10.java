package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueFromStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueTwoWaySteps {
	private ValueTwoWaySteps() {
	}

	public interface ValueTwoWayFromStep extends ValueFromStep {
		@Override
		<F> ValueTwoWayConvertToStep<F, ?> from(IObservableValue<F> from);
	}

	public interface ValueTwoWayToStep<F, T> extends ValueToStep<F, T> {
		@Override
		ValueTwoWayBindConfigStep<F, T, ?> to(IObservableValue<T> to);
	}

	public interface ValueTwoWayConvertToStep<F, THIS extends ValueTwoWayConvertToStep<F, THIS>> extends //
			ValueTwoWayToStep<F, F>, //
			ConvertToStep<F>, //
			ValueTwoWayConfigStep<F, F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T> ValueTwoWayBindConfigStep<F, T, ?> defaultConvertTo(IObservableValue<T> to);

		@Override
		<T> ValueTwoWayConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);
	}

	public interface ValueTwoWayConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		ValueTwoWayToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface ValueTwoWayConfigStep<F, T, THIS extends ValueTwoWayConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			ValueReadConfigStep<F, T, THIS>, //
			ValueWriteConfigStep<F, T, THIS> //
	{
		THIS validateTwoWay(IValidator<? super T> validator);
	}

	public interface ValueTwoWayBindConfigStep<F, T, THIS extends ValueTwoWayBindConfigStep<F, T, THIS>> extends //
			ValueTwoWayConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
