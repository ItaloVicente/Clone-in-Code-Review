package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsListCommon.ListDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsListCommon.ListToStep;
import org.eclipse.core.databinding.bind.StepsValueCommon.SourceValidationConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class StepsListOneWay {
	private StepsListOneWay() {
	}

	public interface OneWayListToStep<T> extends ListToStep<T> {
		@Override
		FinalDestinationConfigStep<?> to(IObservableList<T> target);
	}

	public interface OneWayListConvertStep<F, THIS extends OneWayListConvertStep<F, THIS>> extends //
			OneWayListToStep<F>, //
			ConvertToStep<F>, //
			SourceValidationConfigStep<F, THIS>, //
			ListDefaultConvertToStep //
	{
		@Override
		<T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableList<T> to);

		@Override
		<T> OneWayListToStep<F> convertTo(IConverter<? super F, ? extends T> converter);
	}
}
