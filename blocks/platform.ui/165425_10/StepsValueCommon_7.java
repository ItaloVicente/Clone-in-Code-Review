package org.eclipse.core.databinding.bind;

import org.eclipse.core.databinding.bind.StepsCommon.ConvertFromStep;
import org.eclipse.core.databinding.bind.StepsCommon.ConvertToStep;
import org.eclipse.core.databinding.bind.StepsCommon.DestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsSetCommon.SetDefaultConvertToStep;
import org.eclipse.core.databinding.bind.StepsSetCommon.SetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class StepsSetTwoWay {
	private StepsSetTwoWay() {
	}

	public interface TwoWaySetToStep<T> extends SetToStep<T> {
		@Override
		FinalDestinationConfigStep<?> to(IObservableSet<T> to);
	}

	public interface TwoWaySetConvertToStep<F, THIS extends TwoWaySetConvertToStep<F, THIS>> extends //
			TwoWaySetToStep<F>, //
			ConvertToStep<F>, //
			DestinationConfigStep<THIS>, //
			SetDefaultConvertToStep //
	{
		@Override
		<T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableSet<T> to);

		@Override
		<T> TwoWaySetConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		FinalDestinationConfigStep<?> to(IObservableSet<F> to);
	}

	public interface TwoWaySetConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWaySetToStep<T> convertFrom(IConverter<? super T, ? extends F> converter);
	}
}
