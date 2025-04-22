package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsListOneWay.OneWayListConvertStep;
import org.eclipse.core.databinding.bind.StepsListOneWay.OneWayListToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class ListOneWayBindingBuilder extends AbstractBindingBuilder {
	private final class OneWayListToStepImpl<T> implements OneWayListToStep<T> {
		@Override
		public FinalDestinationConfigStep<?> to(IObservableList<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	final class OneWayListConvertToStepImpl<T> extends ConfigStepImpl<T, OneWayListConvertToStepImpl<T>> implements //
			OneWayListConvertStep<T, OneWayListConvertToStepImpl<T>>, //
			OneWayListToStep<T> //
	{
		@Override
		public FinalDestinationConfigStep<?> to(IObservableList<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T2> FinalDestinationConfigStep<?> defaultConvertTo(IObservableList<T2> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T2> OneWayListToStep<T> convertTo(IConverter<? super T, ? extends T2> converter) {
			toEntry.setConverter(converter);
			return new OneWayListToStepImpl<>();
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindList(context, fromEntry, toEntry);
	}
}
