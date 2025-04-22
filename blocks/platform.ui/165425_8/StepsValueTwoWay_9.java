package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.SourceValidationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public final class StepsValueOneWay {
	private StepsValueOneWay() {
	}

	public interface OneWayValueToStep<T> extends ValueToStep<T> {
		@Override
		OneWayValueFinalDestinationConfigStep<?, ?> to(IObservableValue<T> to);
	}

	public interface OneWayValueConvertStep<F, THIS extends OneWayValueConvertStep<F, THIS>> extends //
			OneWayValueToStep<F>, //
			ConvertToStep<F>, //
			SourceValidationConfigStep<F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T> OneWayValueFinalDestinationConfigStep<?, ?> defaultConvertTo(IObservableValue<T> to);

		@Override
		<T> OneWayValueToStep<F> convertTo(IConverter<? super F, ? extends T> converter);
	}

	public interface OneWayValueFinalDestinationConfigStep<T, //
			THIS extends OneWayValueFinalDestinationConfigStep<T, THIS>>
			extends ValueDestinationConfigStep<T, THIS>, FinalDestinationConfigStep<THIS> //
	{
	}

}
