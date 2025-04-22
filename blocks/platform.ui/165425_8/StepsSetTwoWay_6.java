package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsSetCommon.SetDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsSetCommon.SetToStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.SourceValidationConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class StepsSetOneWay {
	private StepsSetOneWay() {
	}

	public interface OneWaySetToStep<T> extends SetToStep<T> {
		@Override
		FinalDestinationConfigStep<?> to(IObservableSet<T> target);
	}

	public interface OneWaySetConvertStep<F, THIS extends OneWaySetConvertStep<F, THIS>> extends //
			OneWaySetToStep<F>, //
			ConvertToStep<F>, //
			SourceValidationConfigStep<F, THIS>, //
			SetDefaultConvertToStep //
	{
		@Override
		<T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableSet<T> to);

		@Override
		<T> OneWaySetToStep<F> convertTo(IConverter<? super F, ? extends T> converter);
	}
}
