package org.eclipse.core.internal.databinding.binder;

import java.util.function.Function;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.binder.StepsListTwoWay.ConfigStep;
import org.eclipse.core.databinding.binder.StepsListTwoWay.ConvertToModelStep;
import org.eclipse.core.databinding.binder.StepsListTwoWay.ConvertToTargetStep;
import org.eclipse.core.databinding.binder.StepsListTwoWay.ModelStep;
import org.eclipse.core.databinding.binder.StepsListTwoWay.TargetStep;
import org.eclipse.core.databinding.binder.StepsListTwoWay.UntypedTargetStep;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;

public class ListTwoWayBinder extends AbstractBinder {
	public ListTwoWayBinder() {
		super(UpdateListStrategy.POLICY_UPDATE, UpdateListStrategy.POLICY_UPDATE);
	}

	private class ModelStepImpl implements ModelStep {
		@Override
		public <M> ConvertToTargetStep<M> model(IObservableList<M> model) {
			toModel.setObservable(model);
			return new ConvertToTargetStepImpl<>();
		}
	}

	private class ConvertToModelStepImpl<T, M> implements ConvertToModelStep<T, M> {
		@Override
		public TargetStep<T> convertToModel(IConverter<? super T, ? extends M> converter) {
			toTarget.setConverter(converter);
			return new TargetStepImpl<>();
		}

		@Override
		public TargetStep<T> convertToModel(Function<? super T, ? extends M> converter) {
			return convertToModel(IConverter.create(converter));
		}
	}

	private class UntypedTargetStepImpl<M> implements UntypedTargetStep<M> {
		@Override
		public <T> ConfigStep<T, M> target(IObservableList<T> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	private class TargetStepImpl<M> implements TargetStep<M> {
		@Override
		public <T> ConfigStep<T, M> target(IObservableList<T> target) {
			toTarget.setObservable(target);
			return new ConfigStepImpl<>();
		}
	}

	private final class ConvertToTargetStepImpl<M> extends TargetStepImpl<M> implements ConvertToTargetStep<M> {
		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(IConverter<? super M, ? extends T> converter) {
			toTarget.setConverter(converter);
			return new ConvertToModelStepImpl<>();
		}

		@Override
		public UntypedTargetStep<M> useDefaultConvertion() {
			toTarget.setUseDefaults(true);
			toModel.setUseDefaults(true);
			return new UntypedTargetStepImpl<>();
		}

		@Override
		public <T> ConvertToModelStep<T, M> convertToTarget(Function<? super M, ? extends T> converter) {
			return convertToTarget(IConverter.create(converter));
		}
	}

	private final class ConfigStepImpl<T, M> extends CommonConfigStepImpl<T, M, ConfigStep<T, M>>
			implements ConfigStep<T, M> {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindList(context);
		}
	}

	public static ModelStep create() {
		ListTwoWayBinder b = new ListTwoWayBinder();
		return b.new ModelStepImpl();
	}
}
