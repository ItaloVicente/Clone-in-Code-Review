package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class ValueOneWaySteps {
	private ValueOneWaySteps() {
	}

	public interface OneWayValueToStep<F, T> extends ValueToStep<F, T> {
		@Override
		OneWayValueBindWriteConfigStep<F, T, ?> to(IObservableValue<T> to);
	}

	public interface OneWayValueConvertStep<F, THIS extends OneWayValueConvertStep<F, THIS>> extends //
			OneWayValueToStep<F, F>, //
			ValueToStep<F, F>, //
			ConvertToStep<F>, //
			ValueReadConfigStep<F, F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T2> OneWayValueBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableValue<T2> to);

		@Override
		<T2> OneWayValueToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface OneWayValueBindWriteConfigStep<F, T, //
			THIS extends OneWayValueBindWriteConfigStep<F, T, THIS>> //
			extends ValueWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}

}
