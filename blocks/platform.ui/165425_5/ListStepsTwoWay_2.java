package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.BindStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ConverterToModelStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ConverterToTargetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.ModelSetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.ListStepsTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

@SuppressWarnings("rawtypes")
class ListBinderTwoWay<T, M> extends AbstractBinder<T, M> implements //
		ModelStep, //
		ConverterToTargetStep, //
		ConverterToModelStep, //
		UntypedTargetStep, //
		TargetStep, //
		BindStep, //
		ModelSetStep {

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
	public ModelSetStep model(IObservableList model) {
		toModel.setObservable(model);
		return this;
	}

	@Override
	public TargetStep converterToModel(IConverter converter) {
		toTarget.setConverter(converter);
		return this;
	}

	@Override
	public ConverterToModelStep converterToTarget(IConverter converter) {
		toTarget.setConverter(converter);
		return this;
	}

	@Override
	public UntypedTargetStep defaultConverter() {
		return this;
	}

	@Override
	public Binding bind(DataBindingContext context) {
		return bindValue(context);
	}
}
