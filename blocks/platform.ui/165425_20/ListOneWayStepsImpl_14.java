package org.eclipse.core.internal.databinding.bind;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.bind.steps.Step;
import org.eclipse.core.databinding.bind.steps.ListOneWaySteps.OneWayListBindWriteConfigStep;
import org.eclipse.core.databinding.bind.steps.ListTwoWaySteps.TwoWayListBindConfigStep;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.internal.databinding.bind.CommonStepsImpl.ConfigStepImpl;

class ListCommonStepsImpl {
	private ListCommonStepsImpl() {
	}

	static class ListConfigStepImpl<F, T, THIS extends ListConfigStepImpl<F, T, THIS>> //
			extends ConfigStepImpl<F, T, THIS> //
			implements //
			OneWayListBindWriteConfigStep<F, T, THIS>, //
			TwoWayListBindConfigStep<F, T, THIS> //
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
