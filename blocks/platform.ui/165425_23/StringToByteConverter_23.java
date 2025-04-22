package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayBindConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayConvertFromStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.ValueTwoWayToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ValueCommonStepsImpl.ValueConfigStepImpl;

final class ValueTwoWayStepsImpl {
	private ValueTwoWayStepsImpl() {
	}

	private static final class ConvertTwoWayFromStepImpl<F, T> extends AbstractStep
			implements ValueTwoWayConvertFromStep<F, T> {
		public ConvertTwoWayFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ValueTwoWayToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new ValueTwoWayToStepImpl<>(builder);
		}
	}

	private static final class ValueTwoWayToStepImpl<F, T> extends AbstractStep implements ValueTwoWayToStep<F, T> {
		public ValueTwoWayToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ValueTwoWayBindConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}
	}

	static final class ValueTwoWayConvertToStepImpl<F>
			extends ValueConfigStepImpl<F, F, ValueTwoWayConvertToStepImpl<F>>
			implements ValueTwoWayConvertToStep<F, ValueTwoWayConvertToStepImpl<F>>,
			ValueTwoWayBindConfigStep<F, F, ValueTwoWayConvertToStepImpl<F>> {

		public ValueTwoWayConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> ValueTwoWayBindConfigStep<F, T, ?> defaultConvertTo(IObservableValue<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			ValueTwoWayBindConfigStep<F, T, ?> thisCasted = (ValueTwoWayBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> ValueTwoWayConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertTwoWayFromStepImpl<>(builder);
		}

		@Override
		public ValueTwoWayBindConfigStep<F, F, ?> to(IObservableValue<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}
}
