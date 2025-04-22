package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.bind.StepsCommon.ConvertFromStep;
import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsListCommon.ListDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsListCommon.ListToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class StepsListTwoWay {
	private StepsListTwoWay() {
	}

	public interface TwoWayListToStep<T> extends ListToStep<T> {
		@Override
		FinalDestinationConfigStep<?> to(IObservableList<T> to);
	}

	public interface TwoWayListConvertToStep<F, THIS extends TwoWayListConvertToStep<F, THIS>> extends //
			TwoWayListToStep<F>, //
			ConvertToStep<F>, //
			DestinationConfigStep<THIS>, //
			ListDefaultConvertToStep //
	{
		@Override
		<T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableList<T> to);

		@Override
		<T> TwoWayListConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		FinalDestinationConfigStep<?> to(IObservableList<F> to);
	}

	public interface TwoWayListConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWayListToStep<T> convertFrom(IConverter<? super T, ? extends F> converter);
	}
}
