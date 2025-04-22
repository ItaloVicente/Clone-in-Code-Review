package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.BindStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ConverterToTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ModelStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.TargetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.TargetValidatorStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

@SuppressWarnings("rawtypes")
class ValueBinderOneWay<T, M> extends AbstractBinder<T, M> implements //
		ModelStep, //
		TargetValidatorStep, //
		ConverterToTargetStep, //
		UntypedTargetStep, //
		TargetStep, //
		BindStep, //
		ModelSetStep, //
		TargetSetStep {

	public static ModelStep create() {
		ValueBinderOneWay<?, ?> b = new ValueBinderOneWay<>();
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_NEVER);
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
	public BindStep targetValidator(IValidator validator) {
		toTarget.setBeforeSetValidator(validator);
		return this;
	}

	@Override
	public TargetStep converterToTarget(IConverter converter) {
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
