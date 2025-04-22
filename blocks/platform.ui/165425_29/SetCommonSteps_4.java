package org.eclipse.core.databinding.bind.steps;

import org.eclipse.core.databinding.bind.steps.CommonSteps.BindConfigStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertFromStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.ConvertToStep;
import org.eclipse.core.databinding.bind.steps.CommonSteps.WriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListFromStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListReadConfigStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListToStep;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListUntypedTo;
import org.eclipse.core.databinding.bind.steps.ListCommonSteps.ListWriteConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public final class ListTwoWaySteps {
	private ListTwoWaySteps() {
	}

	public interface ListTwoWayFromStep extends ListFromStep {
		@Override
		<F> ListTwoWayConvertToStep<F, ?> from(IObservableList<F> from);
	}

	public interface ListTwoWayToStep<F, T> extends ListToStep<F, T> {
		@Override
		ListTwoWayBindConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface ListTwoWayConvertToStep<F, THIS extends ListTwoWayConvertToStep<F, THIS>> extends //
			ListTwoWayToStep<F, F>, //
			ConvertToStep<F>, //
			ListTwoWayConfigStep<F, F, THIS> //
	{
		@Override
		<T> ListTwoWayConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter);

		@Override
		ListTwoWayUntypedToStep<F> defaultConvert();
	}

	public interface ListTwoWayUntypedToStep<F> extends ListUntypedTo<F> {
		@Override
		<T> ListTwoWayBindConfigStep<F, T, ?> to(IObservableList<T> to);
	}

	public interface ListTwoWayConvertFromStep<F, T> extends ConvertFromStep<F, T> {
		@Override
		ListTwoWayToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter);
	}

	public interface ListTwoWayConfigStep<F, T, THIS extends ListTwoWayConfigStep<F, T, THIS>>
			extends //
			WriteConfigStep<F, T, THIS>, //
			ListReadConfigStep<F, T, THIS>, //
			ListWriteConfigStep<F, T, THIS> //
	{
	}

	public interface ListTwoWayBindConfigStep<F, T, THIS extends ListTwoWayBindConfigStep<F, T, THIS>> extends //
			ListTwoWayConfigStep<F, T, THIS>, //
			BindConfigStep<F, T, THIS> //
	{
	}
}
