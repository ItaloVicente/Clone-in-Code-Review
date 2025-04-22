package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.StepsCommon.ConvertFromStep;
import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.SourceValidationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.ValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

public final class StepsValueTwoWay {
	private StepsValueTwoWay() {
	}

	public interface TwoWayValueToStep<T> extends ValueToStep<T> {
		@Override
		TwoWayValueFinalDestinationConfigStep<T, ?> to(IObservableValue<T> to);
	}

	public interface TwoWayValueConvertToStep<F, THIS extends TwoWayValueConvertToStep<F, THIS>> extends //
			TwoWayValueToStep<F>, //
			ConvertToStep<F>, //
			TwoWayValueDestinationConfigStep<F, THIS>, //
			ValueDefaultConvertToStep //
	{
		@Override
		<T> TwoWayValueFinalDestinationConfigStep<T, ?> defaultConvertTo(IObservableValue<T> to);

		@Override
		<T> TwoWayValueConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		TwoWayValueFinalDestinationConfigStep<F, ?> to(IObservableValue<F> to);
	}

	public interface TwoWayValueConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWayValueToStep<T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface TwoWayValueDestinationConfigStep<T, THIS extends TwoWayValueDestinationConfigStep<T, THIS>>
			extends //
			DestinationConfigStep<THIS>, //
			ValueDestinationConfigStep<T, THIS>, //
			SourceValidationConfigStep<T, THIS> //
	{
		THIS validateTwoWay(IValidator<? super T> validator);
	}

	public interface TwoWayValueFinalDestinationConfigStep<T, //
			THIS extends TwoWayValueFinalDestinationConfigStep<T, THIS>>
			extends TwoWayValueDestinationConfigStep<T, THIS>, FinalDestinationConfigStep<THIS> //
	{
	}
}
