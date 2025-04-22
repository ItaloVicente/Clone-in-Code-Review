package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayBindConfigStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayConvertFromStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.ListTwoWayToStep;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.ListCommonStepsImpl.ListConfigStepImpl;

final class ListTwoWayStepsImpl {
	private ListTwoWayStepsImpl() {
	}

	private static final class ConvertTwoWayFromStepImpl<F, T> extends AbstractStep
			implements ListTwoWayConvertFromStep<F, T> {
		public ConvertTwoWayFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ListTwoWayToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new ListTwoWayToStepImpl<>(builder);
		}
	}

	private static final class ListTwoWayToStepImpl<F, T> extends AbstractStep implements ListTwoWayToStep<F, T> {
		public ListTwoWayToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public ListTwoWayBindConfigStep<F, T, ?> to(IObservableList<T> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}
	}

	static final class ListTwoWayConvertToStepImpl<F>
			extends ListConfigStepImpl<F, F, ListTwoWayConvertToStepImpl<F>>
			implements ListTwoWayConvertToStep<F, ListTwoWayConvertToStepImpl<F>>,
			ListTwoWayBindConfigStep<F, F, ListTwoWayConvertToStepImpl<F>> {

		public ListTwoWayConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> ListTwoWayBindConfigStep<F, T, ?> defaultConvertTo(IObservableList<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			ListTwoWayBindConfigStep<F, T, ?> thisCasted = (ListTwoWayBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> ListTwoWayConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertTwoWayFromStepImpl<>(builder);
		}

		@Override
		public ListTwoWayBindConfigStep<F, F, ?> to(IObservableList<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}
}
