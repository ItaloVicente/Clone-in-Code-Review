package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListOneWaySteps {
	private ListOneWaySteps() {
	}

	public interface OneWayListToStep<F, T> extends ListToStep<F, T> {
		@Override
		OneWayListBindWriteConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface OneWayListConvertStep<F, T, THIS extends OneWayListConvertStep<F, T, THIS>> extends //
			OneWayListToStep<F, T>, //
			ConvertToStep<F>, //
			ListReadConfigStep<F, T, THIS>, //
			ListDefaultConvertToStep //
	{
		@Override
		<T2> OneWayListBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableList<T2> to);

		@Override
		<T2> OneWayListToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface OneWayListBindWriteConfigStep<F, T, //
			THIS extends OneWayListBindWriteConfigStep<F, T, THIS>> //
			extends ListWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}

}
