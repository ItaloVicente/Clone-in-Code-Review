package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListDefaultConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListFromStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListOneWaySteps {
	private ListOneWaySteps() {
	}

	public interface ListOneWayFromStep extends ListFromStep {
		@Override
		<F> ListOneWayConvertStep<F, ?> from(IObservableList<F> from);
	}

	public interface ListOneWayToStep<F, T> extends ListToStep<F, T> {
		@Override
		ListOneWayBindWriteConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface ListOneWayConvertStep<F, THIS extends ListOneWayConvertStep<F, THIS>> extends //
			ListOneWayToStep<F, F>, //
			ListToStep<F, F>, //
			ConvertToStep<F>, //
			ListReadConfigStep<F, F, THIS>, //
			ListDefaultConvertToStep //
	{
		@Override
		<T2> ListOneWayBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableList<T2> to);

		@Override
		<T2> ListOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter);
	}

	public interface ListOneWayBindWriteConfigStep<F, T, //
			THIS extends ListOneWayBindWriteConfigStep<F, T, THIS>> //
			extends ListWriteConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
