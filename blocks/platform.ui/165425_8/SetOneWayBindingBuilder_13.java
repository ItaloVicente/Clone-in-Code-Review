package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsListTwoWay.TwoWayListConvertFromStep;
import org.eclipse.core.databinding.bind.StepsListTwoWay.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.StepsListTwoWay.TwoWayListToStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueFinalDestinationConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class ListTwoWayBindingBuilder extends AbstractBindingBuilder {
	private class ListConvertFromStepImpl<F, T> implements TwoWayListConvertFromStep<F, T> {
		@Override
		public TwoWayListToStep<T> convertFrom(IConverter<? super T, ? extends F> converter) {
			fromEntry.setConverter(converter);
			return new ToStepImpl<>();
		}
	}

	private class ToStepImpl<T> implements TwoWayListToStep<T> {
		@Override
		public TwoWayValueFinalDestinationConfigStep<T, ?> to(IObservableList<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	class TwoWayListConvertToStepImpl<F> extends ConfigStepImpl<F, TwoWayListConvertToStepImpl<F>>
			implements TwoWayListConvertToStep<F, TwoWayListConvertToStepImpl<F>> {
		@Override
		public <T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableList<T> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T> TwoWayListConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			toEntry.setConverter(converter);
			return new ListConvertFromStepImpl<>();
		}

		@Override
		public TwoWayValueFinalDestinationConfigStep<F, ?> to(IObservableList<F> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindList(context, fromEntry, toEntry);
	}
}
