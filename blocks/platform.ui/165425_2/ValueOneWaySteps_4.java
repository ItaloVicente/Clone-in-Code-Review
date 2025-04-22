package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.BindStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.ModelStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.TargetStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

class ValueOneWayBinder extends AbstractBinder {
	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ModelSetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ModelSetStepImpl<>();
		}
	}

	private class UntypedTargetStepImpl implements UntypedTargetStep {
		@Override
		public <T> TargetSetStep<T> target(IObservableValue<T> target) {
			toTarget.setObservable(target);
			return new TargetSetStepImpl<>();
		}
	}

	private class TargetStepImpl<T> implements TargetStep<T> {
		@Override
		public TargetSetStep<T> target(IObservableValue<T> target) {
			toTarget.setObservable(target);
			return new TargetSetStepImpl<>();
		}
	}

	private class BindStepImpl implements BindStep {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindValue(context);
		}
	}

	private final class TargetSetStepImpl<T> extends BindStepImpl implements TargetSetStep<T> {
		@Override
		public BindStep validateTarget(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			return new BindStepImpl();
		}

	}

	private final class ModelSetStepImpl<M> implements ModelSetStep<M> {
		@Override
		public <T> TargetStep<T> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new TargetStepImpl<>();
		}

		@Override
		public UntypedTargetStep useDefaultConvertion() {
			toTarget.setUseDefaults(true);
			toModel.setUseDefaults(true);
			return new UntypedTargetStepImpl();
		}

		@Override
		public TargetSetStep<M> target(IObservableValue<M> target) {
			toTarget.setObservable(target);
			return new TargetSetStepImpl<>();
		}
	}

	public static ModelStep create() {
		ValueOneWayBinder b = new ValueOneWayBinder();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b.new ModelStepImpl();
	}
}
