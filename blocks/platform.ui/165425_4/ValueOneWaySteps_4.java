package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.ConfigStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.ModelStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.TargetStep;
import org.eclipse.core.databinding.binder.ValueOneWaySteps.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

class ValueOneWayBinder extends AbstractBinder {
	public ValueOneWayBinder() {
		super(UpdateValueStrategy.POLICY_UPDATE, UpdateValueStrategy.POLICY_NEVER);
	}

	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ModelSetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ModelSetStepImpl<>();
		}
	}

	private class UntypedTargetStepImpl<M> implements UntypedTargetStep<M> {
		@Override
		public <T> ConfigStep<T, M> target(IObservableValue<T> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	private class TargetStepImpl<T, M> implements TargetStep<T, M> {
		@Override
		public ConfigStep<T, M> target(IObservableValue<T> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	private final class ConfigStepImpl<T, M> implements ConfigStep<T, M> {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindValue(context);
		}

		@Override
		public ConfigStep<T, M> convertOnly() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return this;
		}

		@Override
		public ConfigStep<T, M> manualUpdate() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return this;
		}

		@Override
		public ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator) {
			toTarget.setAfterGetValidator(validator);
			return this;
		}

		@Override
		public ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator) {
			toTarget.setAfterConvertValidator(validator);
			return this;
		}
		@Override
		public ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			return this;
		}
	}

	private final class ModelSetStepImpl<M> implements ModelSetStep<M> {
		@Override
		public <T> TargetStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new TargetStepImpl<>();
		}

		@Override
		public UntypedTargetStep<M> useDefaultConvertion() {
			toTarget.setUseDefaults(true);
			toModel.setUseDefaults(true);
			return new UntypedTargetStepImpl<>();
		}

		@Override
		public ConfigStep<M, M> target(IObservableValue<M> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	public static ModelStep create() {
		ValueOneWayBinder b = new ValueOneWayBinder();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_NEVER);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b.new ModelStepImpl();
	}
}
