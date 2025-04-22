package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.BindStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ModelStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.TargetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.ValidateTargetStep;
import org.eclipse.core.databinding.binder.ValueStepsOneWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

/**
 * @since 1.9
 */
@SuppressWarnings("rawtypes")
class ValueBinderOneWay<T, M> extends AbstractBinder implements //
		ModelStep, //
		ConvertToTargetStep<M>, //
		UntypedTargetStep, //
		ValidateTargetStep<T>, //
		BindStep, //
		ModelSetStep<M>, //
		TargetSetStep<T> {

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
	public BindStep validateTarget(IValidator<? super T> validator) {
		toTarget.setBeforeSetValidator(validator);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T2> TargetStep<T2> convertToTarget(IConverter<? super M, ? extends T2> converter) {
		toTarget.setConverter(converter);
		return (TargetStep<T2>) this;
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
