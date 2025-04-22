package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListConvertStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ListCommonStepsImpl.ListConfigStepImpl;

final class ListOneWayStepsImpl {
	private ListOneWayStepsImpl() {
	}

	static final class OneWayListConvertToStepImpl<F>
			extends ListConfigStepImpl<F, F, OneWayListConvertToStepImpl<F>> //
			implements //
			OneWayListConvertStep<F, OneWayListConvertToStepImpl<F>>//
	{
		public OneWayListConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWayListBindWriteConfigStep<F, F, ?> to(IObservableList<F> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}

		@Override
		public <T2> OneWayListBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableList<T2> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}

		@Override
		public <T2> OneWayListToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new OneWayListToStepImpl<>(builder);
		}
	}

	private static final class OneWayListToStepImpl<F, T> extends AbstractStep implements OneWayListToStep<F, T> {
		public OneWayListToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWayListBindWriteConfigStep<F, T, ?> to(IObservableList<T> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}
	}
}
