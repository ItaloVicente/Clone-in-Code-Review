package org.eclipse.core.internal.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.StepsValueOneWay.ConfigStep;
import org.eclipse.core.databinding.binder.StepsValueOneWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.StepsValueOneWay.ModelStep;
import org.eclipse.core.databinding.binder.StepsValueOneWay.TargetStep;
import org.eclipse.core.databinding.binder.StepsValueOneWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ValueOneWayBinder extends AbstractBinder {
	public ValueOneWayBinder() {
		super(UpdateValueStrategy.POLICY_UPDATE, UpdateValueStrategy.POLICY_NEVER);
	}

	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ConvertToTargetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ConvertToTargetStepImpl<>();
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

	private final class ConvertToTargetStepImpl<M> implements ConvertToTargetStep<M> {
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

		@Override
		public <T> TargetStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
	}

	private final class ConfigStepImpl<T, M> extends CommonConfigStepImpl<T, M, ConfigStep<T, M>>
			implements ConfigStep<T, M> {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindValue(context);
		}
	}

	public static ModelStep create() {
		ValueOneWayBinder b = new ValueOneWayBinder();
		return b.new ModelStepImpl();
	}
}
