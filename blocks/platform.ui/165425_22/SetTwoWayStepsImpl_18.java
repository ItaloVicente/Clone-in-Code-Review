package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetConvertStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.SetCommonStepsImpl.SetConfigStepImpl;

final class SetOneWayStepsImpl {
	private SetOneWayStepsImpl() {
	}

	static final class OneWaySetConvertToStepImpl<F>
			extends SetConfigStepImpl<F, F, OneWaySetConvertToStepImpl<F>> //
			implements //
			OneWaySetConvertStep<F, OneWaySetConvertToStepImpl<F>>//
	{
		public OneWaySetConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWaySetBindWriteConfigStep<F, F, ?> to(IObservableSet<F> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}

		@Override
		public <T2> OneWaySetBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableSet<T2> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}

		@Override
		public <T2> OneWaySetToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new OneWaySetToStepImpl<>(builder);
		}
	}

	private static final class OneWaySetToStepImpl<F, T> extends AbstractStep implements OneWaySetToStep<F, T> {
		public OneWaySetToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWaySetBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}
	}
}
