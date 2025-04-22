package org.eclipse.core.internal.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.ConfigStep;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.ConvertToModelStep;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.StepsValueTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

public class ValueTwoWayBinder extends AbstractBinder {
	public ValueTwoWayBinder() {
		super(UpdateValueStrategy.POLICY_UPDATE, UpdateValueStrategy.POLICY_UPDATE);
	}

	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ConvertToTargetStep<M> model(IObservableValue<M> model) {
			toModel.setObservable(model);
			return new ConvertToTargetStepImpl<>();
		}
	}

	private class ConvertToModelStepImpl<T, M> implements ConvertToModelStep<T, M> {
		@Override
		public TargetStep<T, M> convertToModel(IConverter<? super T, ? extends M> converter) {
			toModel.setConverter(converter);
			return new TargetStepImpl<>();
		}

		@Override
		public TargetStep<T, M> convertToModel(Function<? super T, ? extends M> converter) {
			return convertToModel(IConverter.create(converter));
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
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}

		@Override
		public UntypedTargetStep<M> useDefaultConvertion() {
			ValueTwoWayBinder.this.toTarget.setUseDefaults(true);
			ValueTwoWayBinder.this.toModel.setUseDefaults(true);
			return new UntypedTargetStepImpl<>();
		}

		@Override
		public ConfigStep<M, M> target(IObservableValue<M> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	private final class ConfigStepImpl<T, M> extends CommonConfigStepImpl<T, M, ConfigStep<T, M>>
			implements ConfigStep<T, M> {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindValue(context);
		}
	}

	public static ModelStep createTwoWay() {
		ValueTwoWayBinder b = new ValueTwoWayBinder();
		return b.new ModelStepImpl();
	}
}
