package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.SetOneWaySteps;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayToStep;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.SetOneWayUntypedTo;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.SetCommonStepsImpl.SetConfigStepImpl;

final class SetOneWayStepsImpl {
	private SetOneWayStepsImpl() {
	}

	static final class SetOneWayConvertToStepImpl<F>
			extends SetConfigStepImpl<F, F, SetOneWayConvertToStepImpl<F>> //
			implements //
			SetOneWayConvertStep<F, SetOneWayConvertToStepImpl<F>>//
	{
		public SetOneWayConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public SetOneWayBindWriteConfigStep<F, F, ?> to(IObservableSet<F> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}

		@Override
		public <T2> SetOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new SetOneWayToStepImpl<>(builder);
		}

		@Override
		public SetOneWayUntypedTo<F> defaultConvert() {
			builder.setDefaultConvert();
			return new SetOneWayUntypedToStepImpl<>(builder);
		}
	}

	private static final class SetOneWayToStepImpl<F, T> extends AbstractStep implements SetOneWayToStep<F, T> {
		public SetOneWayToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public SetOneWayBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}
	}

	private static final class SetOneWayUntypedToStepImpl<F> extends AbstractStep implements SetOneWayUntypedTo<F> {
		public SetOneWayUntypedToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> SetOneWayBindWriteConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}
	}
}
