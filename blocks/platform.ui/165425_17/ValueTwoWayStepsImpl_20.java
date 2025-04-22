package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ValueTwoWayStepsImpl.ValueConfigStepImpl;

final class ValueOneWayStepsImpl {
	private ValueOneWayStepsImpl() {
	}

	private static final class OneWayValueToStepImpl<F, T> extends AbstractStep implements OneWayValueToStep<F, T> {
		public OneWayValueToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWayValueBindWriteConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return new OneWayValueConvertToStepImpl<>(builder);
		}
	}

	static final class OneWayValueConvertToStepImpl<F, T>
			extends ValueConfigStepImpl<F, T, OneWayValueConvertToStepImpl<F, T>>
			implements //
			OneWayValueConvertStep<F, T, OneWayValueConvertToStepImpl<F, T>>, //
			OneWayValueToStep<F, T>, //
			OneWayValueBindWriteConfigStep<F, T, OneWayValueConvertToStepImpl<F, T>> //
	{
		public OneWayValueConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWayValueBindWriteConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return thisCoersed();
		}

		@Override
		public <T2> OneWayValueBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableValue<T2> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			return thisCoersed();
		}

		@Override
		public <T2> OneWayValueToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new OneWayValueToStepImpl<>(builder);
		}

		private <T2> OneWayValueBindWriteConfigStep<F, T2, ?> thisCoersed() {
			@SuppressWarnings("unchecked")
			OneWayValueBindWriteConfigStep<F, T2, ?> thisCoersed = (OneWayValueBindWriteConfigStep<F, T2, ?>) this;
			return thisCoersed;
		}
	}



}
