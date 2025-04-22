package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsSetOneWay.OneWaySetConvertStep;
import org.eclipse.core.databinding.bind.StepsSetOneWay.OneWaySetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public class SetOneWayBindingBuilder extends AbstractBindingBuilder {
	private final class OneWaySetToStepImpl<T> implements OneWaySetToStep<T> {
		@Override
		public FinalDestinationConfigStep<?> to(IObservableSet<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	final class OneWaySetConvertToStepImpl<T> extends ConfigStepImpl<T, OneWaySetConvertToStepImpl<T>> implements //
			OneWaySetConvertStep<T, OneWaySetConvertToStepImpl<T>>, //
			OneWaySetToStep<T> //
	{
		@Override
		public FinalDestinationConfigStep<?> to(IObservableSet<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T2> FinalDestinationConfigStep<?> defaultConvertTo(IObservableSet<T2> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T2> OneWaySetToStep<T> convertTo(IConverter<? super T, ? extends T2> converter) {
			toEntry.setConverter(converter);
			return new OneWaySetToStepImpl<>();
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindSet(context, fromEntry, toEntry);
	}
}
