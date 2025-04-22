package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetFromStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetReadConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class SetOneWaySteps {
	private SetOneWaySteps() {
	}

	public interface SetOneWayFromStep extends SetFromStep {
		@Override
		<F> SetOneWayConvertStep<F, ?> from(IObservableSet<F> from);
	}

	public interface SetOneWayToStep<F, T> extends SetToStep<F, T> {
		@Override
		SetOneWayBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetOneWayConvertStep<F, THIS extends SetOneWayConvertStep<F, THIS>> extends //
			SetOneWayToStep<F, F>, //
			SetToStep<F, F>, //
			ConvertToStep<F>, //
			SetReadConfigStep<F, F, THIS>, //
			SetDefaultConvertToStep //
	{
		@Override
		<T2> SetOneWayBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableSet<T2> to);

		@Override
		<T2> SetOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface SetOneWayBindWriteConfigStep<F, T, //
			THIS extends SetOneWayBindWriteConfigStep<F, T, THIS>> //
			extends SetWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
