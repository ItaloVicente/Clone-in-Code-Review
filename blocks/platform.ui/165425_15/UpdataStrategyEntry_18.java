package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetBindConfigStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertFromStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetConvertToStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

final class SetTwoWayStepsImpl {
	private SetTwoWayStepsImpl() {
	}

	private static final class ConvertFromStepImpl<F, T> extends AbstractStep
			implements TwoWaySetConvertFromStep<F, T> {

		public ConvertFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWaySetToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new ToStepImpl<>(builder);
		}
	}

	private static final class ToStepImpl<F, T> extends AbstractStep implements TwoWaySetToStep<F, T> {

		public ToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWaySetBindConfigStep<F, T, ?> to(IObservableSet<T> to) {
			builder.updateToObservable(to);
			return new SetConfigStepImpl<>(builder);
		}
	}

	static final class TwoWaySetConvertToStepImpl<F>
			extends SetConfigStepImpl<F, F, TwoWaySetConvertToStepImpl<F>>
			implements TwoWaySetConvertToStep<F, TwoWaySetConvertToStepImpl<F>>,
			TwoWaySetBindConfigStep<F, F, TwoWaySetConvertToStepImpl<F>> {

		public TwoWaySetConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> TwoWaySetBindConfigStep<F, T, ?> defaultConvertTo(IObservableSet<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWaySetBindConfigStep<F, T, ?> thisCasted = (TwoWaySetBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWaySetConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertFromStepImpl<>(builder);
		}

		@Override
		public TwoWaySetBindConfigStep<F, F, ?> to(IObservableSet<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}

	static class SetConfigStepImpl<F, T, THIS extends SetConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements TwoWaySetBindConfigStep<F, T, THIS> //
	{
		public SetConfigStepImpl(BindingBuilder builder) {
			super(builder);
		}
		@Override
		protected <T2, M2> Binding doBind(DataBindingContext context, UpdataStrategyEntry toModelEntry,
				UpdataStrategyEntry toTargetEntry) {

			UpdateSetStrategy<T2, M2> targetToModel = toModelEntry.createUpdateSetStrategy();
			UpdateSetStrategy<M2, T2> modelToTarget = toTargetEntry.createUpdateSetStrategy();

			@SuppressWarnings("unchecked")
			IObservableSet<M2> model = (IObservableSet<M2>) toModelEntry.getObservable();
			@SuppressWarnings("unchecked")
			IObservableSet<T2> target = (IObservableSet<T2>) toTargetEntry.getObservable();

			return context.bindSet(target, model, targetToModel, modelToTarget);
		}
	}
}
