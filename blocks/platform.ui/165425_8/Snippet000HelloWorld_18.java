package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueConvertFromStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueFinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ValueTwoWayBindingBuilder extends AbstractBindingBuilder {
	private final class ConvertFromStepImpl<F, T> implements TwoWayValueConvertFromStep<F, T> {
		@Override
		public TwoWayValueToStep<T> convertFrom(IConverter<? super T, ? extends F> converter) {
			fromEntry.setConverter(converter);
			return new ToStepImpl<>();
		}
	}

	private final class ToStepImpl<T> implements TwoWayValueToStep<T> {
		@Override
		public TwoWayValueFinalDestinationConfigStep<T, ?> to(IObservableValue<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	final class TwoWayValuConvertToStepImpl<F> extends ConfigStepImpl<F, TwoWayValuConvertToStepImpl<F>>
			implements TwoWayValueConvertToStep<F, TwoWayValuConvertToStepImpl<F>>,
			TwoWayValueFinalDestinationConfigStep<F, TwoWayValuConvertToStepImpl<F>> {
		@Override
		public <T> TwoWayValueFinalDestinationConfigStep<T, ?> defaultConvertTo(IObservableValue<T> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWayValueFinalDestinationConfigStep<T, ?> thisCasted = (TwoWayValueFinalDestinationConfigStep<T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWayValueConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			toEntry.setConverter(converter);
			return new ConvertFromStepImpl<>();
		}

		@Override
		public TwoWayValueFinalDestinationConfigStep<F, ?> to(IObservableValue<F> to) {
			updateToObservable(to);
			return this;
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindValue(context, fromEntry, toEntry);
	}
}
