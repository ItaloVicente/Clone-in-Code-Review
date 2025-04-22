package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueBindConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertFromStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueConvertToStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueToStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.bind.BindingBuilder.AbstractStep;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

final class ValueTwoWayStepsImpl {
	private ValueTwoWayStepsImpl() {
	}

	private static final class ConvertFromStepImpl<F, T> extends AbstractStep
			implements TwoWayValueConvertFromStep<F, T> {

		public ConvertFromStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayValueToStep<F, T> convertFrom(IConverter<? super T, ? extends F> converter) {
			builder.fromEntry.setConverter(converter);
			return new ToStepImpl<>(builder);
		}
	}

	private static final class ToStepImpl<F, T> extends AbstractStep implements TwoWayValueToStep<F, T> {

		public ToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public TwoWayValueBindConfigStep<F, T, ?> to(IObservableValue<T> to) {
			builder.updateToObservable(to);
			return new ValueConfigStepImpl<>(builder);
		}
	}

	static final class TwoWayValueConvertToStepImpl<F>
			extends ValueConfigStepImpl<F, F, TwoWayValueConvertToStepImpl<F>>
			implements TwoWayValueConvertToStep<F, TwoWayValueConvertToStepImpl<F>>,
			TwoWayValueBindConfigStep<F, F, TwoWayValueConvertToStepImpl<F>> {

		public TwoWayValueConvertToStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public <T> TwoWayValueBindConfigStep<F, T, ?> defaultConvertTo(IObservableValue<T> to) {
			builder.toEntry.setProvideDefaults(true);
			builder.fromEntry.setProvideDefaults(true);
			builder.updateToObservable(to);
			@SuppressWarnings("unchecked")
			TwoWayValueBindConfigStep<F, T, ?> thisCasted = (TwoWayValueBindConfigStep<F, T, ?>) this;
			return thisCasted;
		}

		@Override
		public <T> TwoWayValueConvertFromStep<F, T> convertTo(IConverter<? super F, ? extends T> converter) {
			builder.toEntry.setConverter(converter);
			return new ConvertFromStepImpl<>(builder);
		}

		@Override
		public TwoWayValueBindConfigStep<F, F, ?> to(IObservableValue<F> to) {
			builder.updateToObservable(to);
			return this;
		}
	}

	static class ValueConfigStepImpl<F, T, THIS extends ValueConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements TwoWayValueBindConfigStep<F, T, THIS> //
	{
		public ValueConfigStepImpl(BindingBuilder builder) {
			super(builder);
		}

		@Override
		public final THIS validateAfterGet(IValidator<? super F> validator) {
			builder.getPasiveEntry().setAfterGetValidator(validator);
			return thisSelfType();
		}

		@Override
		public final THIS validateAfterConvert(IValidator<? super T> validator) {
			builder.getActiveEntry().setAfterConvertValidator(validator);
			return thisSelfType();
		}

		@Override
		public final THIS validateBeforeSet(IValidator<? super T> validator) {
			builder.getActiveEntry().setBeforeSetValidator(validator);
			return thisSelfType();
		}

		@Override
		public final THIS validateTwoWay(IValidator<? super T> validator) {
			builder.getPasiveEntry().setAfterGetValidator(validator);
			builder.getActiveEntry().setAfterConvertValidator(validator);
			return thisSelfType();
		}

		@Override
		public final THIS convertOnly() {
			builder.toEntry.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return thisSelfType();
		}

		@Override
		protected <T2, M2> Binding doBind(DataBindingContext context, UpdataStrategyEntry toModelEntry,
				UpdataStrategyEntry toTargetEntry) {

			UpdateValueStrategy<T2, M2> targetToModel = toModelEntry.createUpdateValueStrategy();
			UpdateValueStrategy<M2, T2> modelToTarget = toTargetEntry.createUpdateValueStrategy();

			@SuppressWarnings("unchecked")
			IObservableValue<M2> model = (IObservableValue<M2>) toModelEntry.getObservable();
			@SuppressWarnings("unchecked")
			IObservableValue<T2> target = (IObservableValue<T2>) toTargetEntry.getObservable();

			return context.bindValue(target, model, targetToModel, modelToTarget);
		}
	}
}
