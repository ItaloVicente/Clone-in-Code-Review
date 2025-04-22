package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueFromStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueToStep;
import org.eclipse.core.databinding.bind.steps.ValueCommonSteps.ValueWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class ValueOneWaySteps {
	private ValueOneWaySteps() {
	}

	public interface ValueOneWayFromStep extends ValueFromStep {
		@Override
		<F> ValueOneWayConvertStep<F, ?> from(IObservableValue<F> from);
	}

	public interface ValueOneWayToStep<F, T> extends ValueToStep<F, T> {
		@Override
		ValueOneWayBindWriteConfigStep<F, T, ?> to(IObservableValue<T> to);
	}

	public interface ValueOneWayConvertStep<F, THIS extends ValueOneWayConvertStep<F, THIS>> extends //
			ValueOneWayToStep<F, F>, //
			ValueToStep<F, F>, //
			ConvertToStep<F>, //
			ValueReadConfigStep<F, F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T2> ValueOneWayBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableValue<T2> to);

		@Override
		<T2> ValueOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface ValueOneWayBindWriteConfigStep<F, T, //
			THIS extends ValueOneWayBindWriteConfigStep<F, T, THIS>> //
			extends ValueWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
