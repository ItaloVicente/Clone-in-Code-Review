package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetBindConfigStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertFromStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.SetCommonStepsImpl.SetConfigStepImpl;

final class SetTwoWayStepsImpl {
	private SetTwoWayStepsImpl() {
	}

	private static final class ConvertFromStepImpl<F, T> extends AbstractStep
			implements TwoWaySetConvertFromStep<F, T> {
		public ConvertFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWaySetToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new TwoWaySetToStepImpl<>(builder);
		}
	}

	private static final class TwoWaySetToStepImpl<F, T> extends AbstractStep implements TwoWaySetToStep<F, T> {
		public TwoWaySetToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWaySetBindConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}
	}

	static final class TwoWaySetConvertToStepImpl<F>
			extends SetConfigStepImpl<F, F, TwoWaySetConvertToStepImpl<F>>
			implements TwoWaySetConvertToStep<F, TwoWaySetConvertToStepImpl<F>>,
			TwoWaySetBindConfigStep<F, F, TwoWaySetConvertToStepImpl<F>> {

		public TwoWaySetConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> TwoWaySetBindConfigStep<F, T, ?> defaultConvertTo(IObservableSet<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWaySetBindConfigStep<F, T, ?> thisCasted = (TwoWaySetBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWaySetConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertFromStepImpl<>(builder);
		}

		@Override
		public TwoWaySetBindConfigStep<F, F, ?> to(IObservableSet<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}
}
