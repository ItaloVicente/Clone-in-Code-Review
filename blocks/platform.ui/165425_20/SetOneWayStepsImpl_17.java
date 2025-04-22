package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateSetStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.SetOneWaySteps.OneWaySetBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.SetTwoWaySteps.TwoWaySetBindConfigStep;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

class SetCommonStepsImpl {
	private SetCommonStepsImpl() {
	}

	static class SetConfigStepImpl<F, T, THIS extends SetConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements //
			OneWaySetBindWriteConfigStep<F, T, THIS>, //
			TwoWaySetBindConfigStep<F, T, THIS> //
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
