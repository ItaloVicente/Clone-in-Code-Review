package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class ValueTwoWaySteps {
	private ValueTwoWaySteps() {
	}

	public interface TwoWayValueToStep<F, T> extends ValueToStep<F, T> {
		@Override
		TwoWayValueBindConfigStep<F, T, ?> to(IObservableValue<T> to);
	}

	public interface TwoWayValueConvertToStep<F, THIS extends TwoWayValueConvertToStep<F, THIS>> extends //
			TwoWayValueToStep<F, F>, //
			ConvertToStep<F>, //
			TwoWayValueConfigStep<F, F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T> TwoWayValueBindConfigStep<F, T, ?> defaultConvertTo(IObservableValue<T> to);

		@Override
		<T> TwoWayValueConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);
	}

	public interface TwoWayValueConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWayValueToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface TwoWayValueConfigStep<F, T, THIS extends TwoWayValueConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			ValueReadConfigStep<F, T, THIS>, //
			ValueWriteConfigStep<F, T, THIS> //
	{
		THIS validateTwoWay(IValidator<? super T> validator);
	}

	public interface TwoWayValueBindConfigStep<F, T, THIS extends TwoWayValueBindConfigStep<F, T, THIS>> extends //
			TwoWayValueConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
