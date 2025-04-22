package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.BindStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ConvertToModelStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ModelValidatorSetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ValidateModelStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.ValidateTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

/**
 * @since 1.9
 */
@SuppressWarnings("rawtypes")
class ValueBinderTwoWay<T, M> extends AbstractBinder<T, M> implements //
		ModelStep, //
		ValidateModelStep, //
		ValidateTargetStep, //
		ConvertToTargetStep, //
		ConvertToModelStep, //
		UntypedTargetStep, //
		TargetStep, //
		BindStep, //
		ModelSetStep, //
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
	public ModelSetStep model(IObservableValue model) {
		toModel.setObservable(model);
		return this;
	}

	@Override
	public ModelValidatorSetStep validateModel(IValidator validator) {
		toModel.setBeforeSetValidator(validator);
		return this;
	}

	@Override
	public BindStep validateTarget(IValidator validator) {
		toTarget.setBeforeSetValidator(validator);
		return this;
	}

	@Override
	public TargetStep convertToModel(IConverter converter) {
		toTarget.setConverter(converter);
		return this;
	}

	@Override
	public ConvertToModelStep convertToTarget(IConverter converter) {
		toTarget.setConverter(converter);
		return this;
	}

	@Override
	public UntypedTargetStep userDefaultConvertion() {
		return this;
	}

	@Override
	public Binding bind(DataBindingContext context) {
		return bindValue(context);
	}

}
