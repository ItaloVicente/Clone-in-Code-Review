package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.ListOneWaySteps;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayConvertStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayToStep;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.ListOneWayUntypedTo;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ListCommonStepsImpl.ListConfigStepImpl;

final class ListOneWayStepsImpl {
	private ListOneWayStepsImpl() {
	}

	static final class ListOneWayConvertToStepImpl<F>
			extends ListConfigStepImpl<F, F, ListOneWayConvertToStepImpl<F>> //
			implements //
			ListOneWayConvertStep<F, ListOneWayConvertToStepImpl<F>>//
	{
		public ListOneWayConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ListOneWayBindWriteConfigStep<F, F, ?> to(IObservableList<F> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}

		@Override
		public <T2> ListOneWayToStep<F, T2> convertTo(IConverter<? super F, ? extends T2> converter) {
			builder.toEntry.setConverter(converter);
			return new ListOneWayToStepImpl<>(builder);
		}

		@Override
		public ListOneWayUntypedTo<F> defaultConvert() {
			builder.setDefaultConvert();
			return new ListOneWayUntypedToStepImpl<>(builder);
		}
	}

	private static final class ListOneWayToStepImpl<F, T> extends AbstractStep implements ListOneWayToStep<F, T> {
		public ListOneWayToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ListOneWayBindWriteConfigStep<F, T, ?> to(IObservableList<T> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}
	}

	private static final class ListOneWayUntypedToStepImpl<F> extends AbstractStep implements ListOneWayUntypedTo<F> {
		public ListOneWayUntypedToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> ListOneWayBindWriteConfigStep<F, T, ?> to(IObservableList<T> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}
	}
}
