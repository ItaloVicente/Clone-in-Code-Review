package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueBindConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertFromStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ValueCommonStepsImpl.ValueConfigStepImpl;

final class ValueTwoWayStepsImpl {
	private ValueTwoWayStepsImpl() {
	}

	private static final class TwoWayConvertFromStepImpl<F, T> extends AbstractStep
			implements TwoWayValueConvertFromStep<F, T> {
		public TwoWayConvertFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayValueToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new TwoWayValueToStepImpl<>(builder);
		}
	}

	private static final class TwoWayValueToStepImpl<F, T> extends AbstractStep implements TwoWayValueToStep<F, T> {
		public TwoWayValueToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayValueBindConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}
	}

	static final class TwoWayValueConvertToStepImpl<F>
			extends ValueConfigStepImpl<F, F, TwoWayValueConvertToStepImpl<F>>
			implements TwoWayValueConvertToStep<F, TwoWayValueConvertToStepImpl<F>>,
			TwoWayValueBindConfigStep<F, F, TwoWayValueConvertToStepImpl<F>> {

		public TwoWayValueConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> TwoWayValueBindConfigStep<F, T, ?> defaultConvertTo(IObservableValue<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWayValueBindConfigStep<F, T, ?> thisCasted = (TwoWayValueBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWayValueConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new TwoWayConvertFromStepImpl<>(builder);
		}

		@Override
		public TwoWayValueBindConfigStep<F, F, ?> to(IObservableValue<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}
}
