package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ConfigStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ConvertToModelStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ModelStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.TargetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

class ValueTwoWayBinder extends AbstractBinder {
	public ValueTwoWayBinder() {
		super(UpdateValueStrategy.POLICY_UPDATE, UpdateValueStrategy.POLICY_UPDATE);
	}

	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ModelSetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ModelSetStepImpl<>();
		}
	}

	private class ConvertToModelStepImpl<T, M> implements ConvertToModelStep<T, M> {
		@Override
		public TargetStep<T, M> convertToModel(IConverter<? super T, ? extends M> converter) {
			toModel.setConverter(converter);
			return new TargetStepImpl<>();
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

	private final class ModelSetStepImpl<M> implements ModelSetStep<M> {
		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public UntypedTargetStep<M> useDefaultConvertion() {
			return ValueTwoWayBinder.this.useDefaultConvertion();
		}

		@Override
		public ConfigStep<M, M> target(IObservableValue<M> target) {
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
		public ConfigStep<T, M> validateModelAfterGet(IValidator<? super T> validator) {
			toModel.setAfterGetValidator(validator);
			return this;
		}

		@Override
		public ConfigStep<T, M> validateModelAfterConvert(IValidator<? super M> validator) {
			toModel.setAfterConvertValidator(validator);
			return this;
		}

		@Override
		public ConfigStep<T, M> validateModelBeforeSet(IValidator<? super M> validator) {
			toModel.setBeforeSetValidator(validator);
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

		@Override
		public ConfigStep<T, M> validateModel(IValidator<? super M> validator) {
			toModel.setBeforeSetValidator(validator);
			toTarget.setAfterGetValidator(validator);
			return this;
		}

		@Override
		public ConfigStep<T, M> validateTarget(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			toModel.setAfterGetValidator(validator);
			return this;
		}

		@Override
		public ConfigStep<T, M> targetUpdatePolicy(ValueUpdatePolicy policy) {
			toTarget.setUpdatePolicy(policy.getPolicy());
			return this;
		}

		@Override
		public ConfigStep<T, M> modelUpdatePolicy(ValueUpdatePolicy policy) {
			toModel.setUpdatePolicy(policy.getPolicy());
			return this;
		}
	}

	public static ModelStep create() {
		ValueTwoWayBinder b = new ValueTwoWayBinder();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b.new ModelStepImpl();
	}

	private <M> UntypedTargetStep<M> useDefaultConvertion() {
		toTarget.setUseDefaults(true);
		toModel.setUseDefaults(true);
		return new UntypedTargetStepImpl<>();
	}
}
