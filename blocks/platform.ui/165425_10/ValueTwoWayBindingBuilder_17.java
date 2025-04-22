package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsValueOneWay.OneWayValueConvertStep;
import org.eclipse.core.databinding.bind.StepsValueOneWay.OneWayValueFinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsValueOneWay.OneWayValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

final public class ValueOneWayBindingBuilder extends AbstractBindingBuilder {
	private final class OneWayToStepImpl<T> implements OneWayValueToStep<T> {
		@Override
		public OneWayValueFinalDestinationConfigStep<?, ?> to(IObservableValue<T> to) {
			updateToObservable(to);
			return new OneWayValueConvertToStepImpl<>();
		}
	}

	final class OneWayValueConvertToStepImpl<T> extends ConfigStepImpl<T, OneWayValueConvertToStepImpl<T>> implements //
			OneWayValueConvertStep<T, OneWayValueConvertToStepImpl<T>>, //
			OneWayValueToStep<T>, //
			OneWayValueFinalDestinationConfigStep<T, OneWayValueConvertToStepImpl<T>> //
	{

		@Override
		public OneWayValueFinalDestinationConfigStep<?, ?> to(IObservableValue<T> to) {
			updateToObservable(to);
			return this;
		}

		@Override
		public <T2> OneWayValueFinalDestinationConfigStep<?, ?> defaultConvertTo(IObservableValue<T2> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			return this;
		}

		@Override
		public <T2> OneWayValueToStep<T> convertTo(IConverter<? super T, ? extends T2> converter) {
			toEntry.setConverter(converter);
			return new OneWayToStepImpl<>();
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindValue(context, fromEntry, toEntry);
	}
}
