package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.bind.StepsCommon.FinalDestinationConfigStep;
import org.eclipse.core.databinding.bind.StepsSetTwoWay.TwoWaySetConvertFromStep;
import org.eclipse.core.databinding.bind.StepsSetTwoWay.TwoWaySetConvertToStep;
import org.eclipse.core.databinding.bind.StepsSetTwoWay.TwoWaySetToStep;
import org.eclipse.core.databinding.bind.StepsValueTwoWay.TwoWayValueFinalDestinationConfigStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;

public class SetTwoWayBindingBuilder extends AbstractBindingBuilder {
	private class SetConvertFromStepImpl<F, T> implements TwoWaySetConvertFromStep<F, T> {
		@Override
		public TwoWaySetToStep<T> convertFrom(IConverter<? super T, ? extends F> converter) {
			fromEntry.setConverter(converter);
			return new ToStepImpl<>();
		}
	}

	private class ToStepImpl<T> implements TwoWaySetToStep<T> {
		@Override
		public TwoWayValueFinalDestinationConfigStep<T, ?> to(IObservableSet<T> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	class TwoWaySetConvertToStepImpl<F> extends ConfigStepImpl<F, TwoWaySetConvertToStepImpl<F>>
			implements TwoWaySetConvertToStep<F, TwoWaySetConvertToStepImpl<F>> {
		@Override
		public <T> FinalDestinationConfigStep<?> defaultConvertTo(IObservableSet<T> to) {
			toEntry.setProvideDefaults(true);
			fromEntry.setProvideDefaults(true);
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}

		@Override
		public <T> TwoWaySetConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			toEntry.setConverter(converter);
			return new SetConvertFromStepImpl<>();
		}

		@Override
		public TwoWayValueFinalDestinationConfigStep<F, ?> to(IObservableSet<F> to) {
			updateToObservable(to);
			return new ConfigStepImpl<>();
		}
	}

	@Override
	protected Binding doBind(DataBindingContext context, UpdataStrategyEntry fromEntry, UpdataStrategyEntry toEntry) {
		return bindSet(context, fromEntry, toEntry);
	}
}
