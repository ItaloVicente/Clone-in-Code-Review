package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ValueOneWaySteps.OneWayValueBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ValueTwoWaySteps.TwoWayValueBindConfigStep;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

class ValueCommonStepsImpl {
	private ValueCommonStepsImpl() {
	}

	static class ValueConfigStepImpl<F, T, THIS extends ValueConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements //
			OneWayValueBindWriteConfigStep<F, T, THIS>, //
			TwoWayValueBindConfigStep<F, T, THIS> //
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
