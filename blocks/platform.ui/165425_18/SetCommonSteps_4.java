package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListTwoWaySteps {
	private ListTwoWaySteps() {
	}

	public interface TwoWayListToStep<F, T> extends ListToStep<F, T> {
		@Override
		TwoWayListBindConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface TwoWayListConvertToStep<F, THIS extends TwoWayListConvertToStep<F, THIS>> extends //
			TwoWayListToStep<F, F>, //
			ConvertToStep<F>, //
			TwoWayListConfigStep<F, F, THIS>, //
			ListDefaultConvertToStep //
	{
		@Override
		<T> TwoWayListBindConfigStep<F, T, ?> defaultConvertTo(IObservableList<T> to);

		@Override
		<T> TwoWayListConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		TwoWayListBindConfigStep<F, F, ?> to(IObservableList<F> to);
	}

	public interface TwoWayListConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		TwoWayListToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface TwoWayListConfigStep<F, T, THIS extends TwoWayListConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			ListReadConfigStep<F, T, THIS>, //
			ListWriteConfigStep<F, T, THIS> //
	{
	}

	public interface TwoWayListBindConfigStep<F, T, THIS extends TwoWayListBindConfigStep<F, T, THIS>> extends //
			TwoWayListConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
