package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetConvertStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.SetTwoWayStepsImpl.SetConfigStepImpl;

final class SetOneWayStepsImps {
	private static final class OneWaySetToStepImpl<F, T> extends AbstractStep implements OneWaySetToStep<F, T> {
		public OneWaySetToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWaySetBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new OneWaySetConvertToStepImpl<>(builder);
		}
	}

	static final class OneWaySetConvertToStepImpl<F, T>
			extends SetConfigStepImpl<F, T, OneWaySetConvertToStepImpl<F, T>>
			implements //
			OneWaySetConvertStep<F, T, OneWaySetConvertToStepImpl<F, T>>, //
			OneWaySetToStep<F, T>, //
			OneWaySetBindWriteConfigStep<F, T, OneWaySetConvertToStepImpl<F, T>> //
	{
		public OneWaySetConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public OneWaySetBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return thisCoersed();
		}

		@Override
		public <T2> OneWaySetBindWriteConfigStep<F, T2, ?> defaultConvertTo(IObservableSet<T2> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			return thisCoersed();
		}

		@Override
		public <T2> OneWaySetToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new OneWaySetToStepImpl<>(builder);
		}

		private <T2> OneWaySetBindWriteConfigStep<F, T2, ?> thisCoersed() {
			@SuppressWarnings("unchecked")
			OneWaySetBindWriteConfigStep<F, T2, ?> thisCoersed = (OneWaySetBindWriteConfigStep<F, T2, ?>) this;
			return thisCoersed;
		}
	}



}
