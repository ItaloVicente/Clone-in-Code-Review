package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListBindConfigStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListConvertFromStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListConvertToStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

final class ListTwoWayStepsImpl {
	private ListTwoWayStepsImpl() {
	}

	private static final class ConvertFromStepImpl<F, T> extends AbstractStep
			implements TwoWayListConvertFromStep<F, T> {

		public ConvertFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayListToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new ToStepImpl<>(builder);
		}
	}

	private static final class ToStepImpl<F, T> extends AbstractStep implements TwoWayListToStep<F, T> {

		public ToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayListBindConfigStep<F, T, ?> to(IObservableList<T> to) {
			builder.updateToObservable(to);
			return new ListConfigStepImpl<>(builder);
		}
	}

	static final class TwoWayListConvertToStepImpl<F>
			extends ListConfigStepImpl<F, F, TwoWayListConvertToStepImpl<F>>
			implements TwoWayListConvertToStep<F, TwoWayListConvertToStepImpl<F>>,
			TwoWayListBindConfigStep<F, F, TwoWayListConvertToStepImpl<F>> {

		public TwoWayListConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> TwoWayListBindConfigStep<F, T, ?> defaultConvertTo(IObservableList<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWayListBindConfigStep<F, T, ?> thisCasted = (TwoWayListBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWayListConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertFromStepImpl<>(builder);
		}

		@Override
		public TwoWayListBindConfigStep<F, F, ?> to(IObservableList<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}

	static class ListConfigStepImpl<F, T, THIS extends ListConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements TwoWayListBindConfigStep<F, T, THIS> //
	{
		public ListConfigStepImpl(BindingBuilder builder) {
			super(builder);
		}
		@Override
		protected <T2, M2> Binding doBind(DataBindingContext context, UpdataStrategyEntry toModelEntry,
				UpdataStrategyEntry toTargetEntry) {

			UpdateListStrategy<T2, M2> targetToModel = toModelEntry.createUpdateListStrategy();
			UpdateListStrategy<M2, T2> modelToTarget = toTargetEntry.createUpdateListStrategy();

			@SuppressWarnings("unchecked")
			IObservableList<M2> model = (IObservableList<M2>) toModelEntry.getObservable();
			@SuppressWarnings("unchecked")
			IObservableList<T2> target = (IObservableList<T2>) toTargetEntry.getObservable();

			return context.bindList(target, model, targetToModel, modelToTarget);
		}
	}
}
