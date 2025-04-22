package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.BindStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ConvertToModelStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ModelSetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ModelStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.ModelValidatorSetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.TargetSetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.TargetStep;
import org.eclipse.core.databinding.binder.ValueTwoWaySteps.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;

class ValueTwoWayBinder extends AbstractBinder {
	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ModelSetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ModelSetStepImpl<>();
		}
	}

	private class ConvertToModelStepImpl<T, M> implements ConvertToModelStep<T, M> {
		@Override
		public TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter) {
			toModel.setConverter(converter);
			return new TargetStepImpl<>();
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
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public UntypedTargetStep useDefaultConvertion() {
			return ValueTwoWayBinder.this.useDefaultConvertion();
		}

		@Override
		public TargetSetStep<M> target(IObservableValue<M> target) {
			toTarget.setObservable(target);
			return new TargetSetStepImpl<>();
		}

		@Override
		public ModelValidatorSetStep<M> validateModel(IValidator<? super M> validator) {
			toModel.setBeforeSetValidator(validator);
			return new ModelValidatorSetStepImpl<>();
		}
	}

	private final class ModelValidatorSetStepImpl<M> implements ModelValidatorSetStep<M> {
		@Override
		public TargetSetStep<M> target(IObservableValue<M> target) {
			toTarget.setObservable(target);
			return new TargetSetStepImpl<>();
		}

		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public UntypedTargetStep useDefaultConvertion() {
			return ValueTwoWayBinder.this.useDefaultConvertion();
		}
	}

	public static ModelStep create() {
		ValueTwoWayBinder b = new ValueTwoWayBinder();
		b.toModel.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		b.toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_UPDATE);
		return b.new ModelStepImpl();
	}

	private UntypedTargetStep useDefaultConvertion() {
		toTarget.setUseDefaults(true);
		toModel.setUseDefaults(true);
		return new UntypedTargetStepImpl();
	}
}
