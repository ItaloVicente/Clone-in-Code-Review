package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.BindStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ConverterToModelStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ConverterToTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelStepStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelValidatorSetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelValidatorStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.TargetValidatorStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

@SuppressWarnings("rawtypes")
class ValueBinderTwoWay<T, M> extends AbstractBinder<T, M> implements //
		ModelStep, //
		ModelValidatorStep, //
		TargetValidatorStep, //
		ConverterToTargetStep, //
		ConverterToModelStep, //
		UntypedTargetStep, //
		TargetStep, //
		BindStep, //
		ModelStepStep, //
		ModelValidatorSetStep, //
		TargetSetStep {

	public static ModelStep create() {
		ValueBinderTwoWay<?, ?> b = new ValueBinderTwoWay<>();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TargetSetStep target(IObservableValue target) {
		toTarget.setObservable(target);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ModelStepStep model(IObservableValue model) {
		toModel.setObservable(model);
		return this;
	}

	@Override
	public ModelValidatorSetStep modelValidator(IValidator validator) {
		toModel.setBeforeSetValidator(validator);
		return this;
	}

	@Override
	public BindStep targetValidator(IValidator validator) {
		toTarget.setBeforeSetValidator(validator);
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
