package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetReadConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class SetOneWaySteps {
	private SetOneWaySteps() {
	}

	public interface OneWaySetToStep<F, T> extends SetToStep<F, T> {
		@Override
		OneWaySetBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface OneWaySetConvertStep<F, THIS extends OneWaySetConvertStep<F, THIS>> extends //
			OneWaySetToStep<F, F>, //
			SetToStep<F, F>, //
			ConvertToStep<F>, //
			SetReadConfigStep<F, F, THIS>, //
			SetDefaultConvertToStep //
	{
		@Override
		OneWaySetBindWriteConfigStep<F, F, ?> to(IObservableSet<F> to);

		@Override
		<T2> OneWaySetBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableSet<T2> to);

		@Override
		<T2> OneWaySetToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface OneWaySetBindWriteConfigStep<F, T, //
			THIS extends OneWaySetBindWriteConfigStep<F, T, THIS>> //
			extends SetWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}

}
