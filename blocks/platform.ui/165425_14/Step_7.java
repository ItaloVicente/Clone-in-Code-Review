package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetReadConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class SetTwoWaySteps {
	private SetTwoWaySteps() {
	}

	public interface TwoWaySetToStep<F, T> extends SetToStep<F, T> {
		@Override
		TwoWaySetBindConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface TwoWaySetConvertToStep<F, THIS extends TwoWaySetConvertToStep<F, THIS>> extends //
			TwoWaySetToStep<F, F>, //
			ConvertToStep<F>, //
			TwoWaySetConfigStep<F, F, THIS>, //
			SetDefaultConvertToStep //
	{
		@Override
		<T> TwoWaySetBindConfigStep<F, T, ?> defaultConvertTo(IObservableSet<T> to);

		@Override
		<T> TwoWaySetConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		TwoWaySetBindConfigStep<F, F, ?> to(IObservableSet<F> to);
	}

	public interface TwoWaySetConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWaySetToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface TwoWaySetConfigStep<F, T, THIS extends TwoWaySetConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			SetReadConfigStep<F, T, THIS>, //
			SetWriteConfigStep<F, T, THIS> //
	{
	}

	public interface TwoWaySetBindConfigStep<F, T, THIS extends TwoWaySetBindConfigStep<F, T, THIS>> extends //
			TwoWaySetConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
