package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetFromStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetReadConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetUntypedTo;
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
			SetReadConfigStep<F, F, THIS> //
	{
		@Override
		SetOneWayUntypedTo<F> defaultConvert();

		@Override
		<T2> SetOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface SetOneWayUntypedTo<F> extends SetUntypedTo<F> {
		@Override
		<T> SetOneWayBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetOneWayBindWriteConfigStep<F, T, //
			THIS extends SetOneWayBindWriteConfigStep<F, T, THIS>> //
			extends SetWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
