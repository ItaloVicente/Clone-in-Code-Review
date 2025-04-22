package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetFromStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetReadConfigStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetToStep;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetUntypedTo;
import org.eclipse.core.databinding.bind.steps.SetCommonSteps.SetWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public final class SetTwoWaySteps {
	private SetTwoWaySteps() {
	}

	public interface SetTwoWayFromStep extends SetFromStep {
		@Override
		<F> SetTwoWayConvertToStep<F, ?> from(IObservableSet<F> from);
	}

	public interface SetTwoWayToStep<F, T> extends SetToStep<F, T> {
		@Override
		SetTwoWayBindConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetTwoWayConvertToStep<F, THIS extends SetTwoWayConvertToStep<F, THIS>> extends //
			SetTwoWayToStep<F, F>, //
			ConvertToStep<F>, //
			SetTwoWayConfigStep<F, F, THIS> //
	{
		@Override
		<T> SetTwoWayConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		SetTwoWayUntypedToStep<F> defaultConvert();
	}

	public interface SetTwoWayUntypedToStep<F> extends SetUntypedTo<F> {
		@Override
		<T> SetTwoWayBindConfigStep<F, T, ?> to(IObservableSet<T> to);
	}

	public interface SetTwoWayConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		SetTwoWayToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface SetTwoWayConfigStep<F, T, THIS extends SetTwoWayConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			SetReadConfigStep<F, T, THIS>, //
			SetWriteConfigStep<F, T, THIS> //
	{
	}

	public interface SetTwoWayBindConfigStep<F, T, THIS extends SetTwoWayBindConfigStep<F, T, THIS>> extends //
			SetTwoWayConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
