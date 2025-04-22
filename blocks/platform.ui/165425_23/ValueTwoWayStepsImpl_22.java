package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.ValueOneWayBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.ValueOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.ValueOneWayToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ValueCommonStepsImpl.ValueConfigStepImpl;

final class ValueOneWayStepsImpl {
	private ValueOneWayStepsImpl() {
	}

	static final class ValueOneWayConvertToStepImpl<F>
			extends ValueConfigStepImpl<F, F, ValueOneWayConvertToStepImpl<F>> //
			implements //
			ValueOneWayConvertStep<F, ValueOneWayConvertToStepImpl<F>>//
	{
		public ValueOneWayConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ValueOneWayBindWriteConfigStep<F, F, ?> to(IObservableValue<F> to) {
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}

		@Override
		public <T2> ValueOneWayBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableValue<T2> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}

		@Override
		public <T2> ValueOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new ValueOneWayToStepImpl<>(builder);
		}
	}

	private static final class ValueOneWayToStepImpl<F, T> extends AbstractStep implements ValueOneWayToStep<F, T> {
		public ValueOneWayToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ValueOneWayBindWriteConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}
	}
}
