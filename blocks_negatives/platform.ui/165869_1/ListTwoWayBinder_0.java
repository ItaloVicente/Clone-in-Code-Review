package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.BindStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ConvertToModelStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ModelSetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

/**
 * @since 1.9
 */
@SuppressWarnings("rawtypes")
class ListBinderTwoWay<T, M> extends AbstractBinder<T, M> implements //
		ModelStep, //
		ConvertToTargetStep<M>, //
		ConvertToModelStep<T, M>, //
		UntypedTargetStep, //
		BindStep, //
		ModelSetStep<M> {

	public static ModelStep create() {
		ListBinderTwoWay<?, ?> b = new ListBinderTwoWay<>();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b;
	}

	@Override
	public BindStep target(IObservableList target) {
		toTarget.setObservable(target);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelSetStep<M> model(IObservableList model) {
		toModel.setObservable(model);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T2> ConvertToModelStep<T2, M> convertToTarget(IConverter<? super M, ? extends T2> converter) {
		toTarget.setConverter(converter);
		return (ConvertToModelStep<T2, M>) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter) {
		toTarget.setConverter(converter);
		return (TargetStep<T>) this;
	}

	@Override
	public UntypedTargetStep useDefaultConvertion() {
		toTarget.setUseDefaults(true);
		toModel.setUseDefaults(true);
		return this;
	}

	@Override
	public Binding bind(DataBindingContext context) {
		return bindValue(context);
	}
}
